package com.oddlyspaced.np.Service;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.widget.ImageView;
import android.widget.Toast;

import com.oddlyspaced.np.Constants.ConstantHolder;
import com.oddlyspaced.np.Interface.OnBatteryLevelChanged;
import com.oddlyspaced.np.Interface.OnRotate;
import com.oddlyspaced.np.Modal.ColorLevel;
import com.oddlyspaced.np.R;
import com.oddlyspaced.np.Receiver.BatteryBroadcastReceiver;
import com.oddlyspaced.np.Receiver.OrientationBroadcastReceiver;
import com.oddlyspaced.np.Utils.BatteryConfigManager;
import com.oddlyspaced.np.Utils.SettingsManager;
import com.oddlyspaced.np.Utils.NotchManager;

// The Service class to show and handle the overlay
public class OverlayAccessibilityService extends AccessibilityService {

    private ConstantHolder constants;
    private WindowManager windowManager;
    private View overlayView;

    private NotchManager notchManager;
    private SettingsManager settingsManager;
    private BatteryConfigManager batteryManager;

    private boolean isInApp = false;
    private int batteryLevel;
    private int currentRotation = Surface.ROTATION_0;

    // Executed when service started
    @Override
    protected void onServiceConnected() {
        //Configure these here for compatibility with API 13 and below.
        AccessibilityServiceInfo config = new AccessibilityServiceInfo();
        config.eventTypes = AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED;
        config.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC;
        config.flags = AccessibilityServiceInfo.FLAG_INCLUDE_NOT_IMPORTANT_VIEWS;

        setServiceInfo(config);
        init();
        startReceivers();
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        if (event.getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            if (event.getPackageName() != null && event.getClassName() != null) {
                ComponentName componentName = new ComponentName(
                        event.getPackageName().toString(),
                        event.getClassName().toString()
                );

                ActivityInfo activityInfo = getCurrentActivity(componentName);
                boolean isActivity = activityInfo != null;
                if (isActivity) {
                    String activity = componentName.flattenToShortString();
                    if (activity.startsWith(constants.getPackageName())) {
                        isInApp = true;
                        readConfigsContinuously();
                    } else {
                        isInApp = false;
                    }
                }
            }
        }
    }

    // gets the foreground activity name
    // https://stackoverflow.com/questions/3873659/android-how-can-i-get-the-current-foreground-activity-from-a-service/27642535#27642535
    private ActivityInfo getCurrentActivity(ComponentName componentName) {
        try {
            return getPackageManager().getActivityInfo(componentName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }

    @Override
    public void onInterrupt() {
        stopReceivers();
    }


    // -- Handlers --
    private void init() {
        constants = new ConstantHolder();
        // getting the window manager
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        // the parent image view in which the bitmap is set
        overlayView = LayoutInflater.from(this).inflate(R.layout.overlay_float, null); // the view which will be overlayed
        notchManager = new NotchManager();
        settingsManager = new SettingsManager();
        batteryManager = new BatteryConfigManager();
        notchManager.read();
        settingsManager.read();
        batteryManager.read();

        readConfigsContinuously();
    }

    // -- Utils --
    private int getStatusBarHeight() {
        Resources resources = this.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return resources.getDimensionPixelSize(resourceId);
        }
        return 0;
    }

    // -- Receiver / Observer handlers --

    private OrientationBroadcastReceiver receiverOrientation;
    private BatteryBroadcastReceiver receiverBattery;

    // this method starts and initialises every receiver to be uses in service
    private void startReceivers() {
        receiverOrientation = new OrientationBroadcastReceiver(new OnRotate() {
            @Override
            public void onPortrait() {
                currentRotation = Surface.ROTATION_0;
                makeOverlay(batteryLevel);
            }

            @Override
            public void onLandscape() {
                currentRotation = windowManager.getDefaultDisplay().getRotation();
                makeOverlay(batteryLevel);
            }
        });
        IntentFilter intentFilterOrientation = new IntentFilter(Intent.ACTION_CONFIGURATION_CHANGED);

        receiverBattery = new BatteryBroadcastReceiver(new OnBatteryLevelChanged() {
            @Override
            public void onChanged(int battery) {
                batteryLevel = battery;
                makeOverlay(batteryLevel);
            }

            @Override
            public void onChargingConnected(int battery) {
                settingsManager.read();
                if (settingsManager.isChargingAnimation() && !isAnimation1Active) {
                    isAnimation1Active = true;
                    tempBatteryLevel1 = batteryLevel;
                    animation1();
                }
            }

            @Override
            public void oncChargingDisconnected(int battery) {
                isAnimation1Active = false;
                makeOverlay(batteryLevel);
            }
        });
        IntentFilter intentFilterBattery = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);

        registerReceiver(receiverOrientation, intentFilterOrientation);
        registerReceiver(receiverBattery, intentFilterBattery);
    }

    private void readConfigsContinuously() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                notchManager.read();
                settingsManager.read();
                batteryManager.read();
                makeOverlay(batteryLevel);
                if (isInApp)
                    readConfigsContinuously();
            }
        }, 100);
    }


    private void stopReceivers() {
        unregisterReceiver(receiverOrientation);
        unregisterReceiver(receiverBattery);
    }

    // -- Overlay Manager --
    private void makeOverlay(int battery) {
        if (currentRotation == Surface.ROTATION_0)
            makeOverlayPortrait(battery);
        else if (currentRotation == Surface.ROTATION_90)
            makeOverlayLandscape(battery);
        else if (currentRotation == Surface.ROTATION_270)
            makeOverlayLandscapeReverse(battery);
        else
            removeOverlay();
    }

    private void makeOverlayPortrait(int battery) {
        Bitmap bitmap = drawNotch(battery);
        ImageView img = overlayView.findViewById(R.id.imageView);
        img.setImageBitmap(bitmap);
        img.setRotation(0);
        overlayView.setRotation(0);
        try {
            windowManager.updateViewLayout(overlayView, generateParamsPortrait(bitmap.getHeight(), bitmap.getWidth()));
        } catch (Exception e) {
            // if this gives an error then its probably because wm is empty
            try {
                windowManager.addView(overlayView, generateParamsPortrait(bitmap.getHeight(), bitmap.getWidth()));
            } catch (Exception ee) {
            }
        }
    }

    private void makeOverlayLandscape(int battery) {
        Bitmap bitmap = drawNotch(battery);
        ImageView img = overlayView.findViewById(R.id.imageView);
        img.setImageBitmap(bitmap);
        img.setRotation(0);
        overlayView.setRotation(0);
        try {
            windowManager.updateViewLayout(overlayView, generateParamsLandscape(bitmap.getHeight(), bitmap.getWidth()));
        } catch (Exception e) {
            // if this gives an error then its probably because wm is empty
            try {
                windowManager.addView(overlayView, generateParamsLandscape(bitmap.getHeight(), bitmap.getWidth()));
            } catch (Exception ee) {
            }
        }
    }

    private void makeOverlayLandscapeReverse(int battery) {
        Bitmap bitmap = drawNotch(battery);
        ImageView img = overlayView.findViewById(R.id.imageView);
        img.setImageBitmap(bitmap);
        img.setRotation(0);
        overlayView.setRotation(0);
        try {
            windowManager.updateViewLayout(overlayView, generateParamsPortraitLandscapeReverse(bitmap.getHeight(), bitmap.getWidth()));
        } catch (Exception e) {
            // if this gives an error then its probably because wm is empty
            try {
                windowManager.addView(overlayView, generateParamsPortraitLandscapeReverse(bitmap.getHeight(), bitmap.getWidth()));
            } catch (Exception ee) {
            }
        }
    }

    // this method removes the overlay from the view
    private void removeOverlay() {
        try {
            windowManager.removeView(overlayView); // this will error out if wm is already empty
        } catch (Exception e) {
            // such haxx much wow
        }
    }

    // -- Layout Parameter Generator --
    private WindowManager.LayoutParams generateParamsPortrait(int h, int w) {
        int layoutParameters = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS |
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
        WindowManager.LayoutParams p = new WindowManager.LayoutParams(
                w,
                h,
                WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY,
                layoutParameters,
                PixelFormat.TRANSLUCENT);
        p.gravity = Gravity.TOP | Gravity.CENTER;
        //setting boundary
        p.x = notchManager.getxPositionPortrait();
        p.y = -(getStatusBarHeight()) - notchManager.getyPositionPortrait();
        overlayView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if (visibility == 0) {
                    overlayView.setVisibility(View.VISIBLE);
                } else {//fullscreen
                    overlayView.setVisibility(View.VISIBLE);
                }
            }
        });
        return p;
    }

    private WindowManager.LayoutParams generateParamsLandscape(int h, int w) {
        int layoutParameters = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS |
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
        WindowManager.LayoutParams p = new WindowManager.LayoutParams(
                w,
                h,
                WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY,
                layoutParameters,
                PixelFormat.TRANSLUCENT);
        p.gravity = Gravity.START | Gravity.CENTER;
        //setting boundary
        p.x = notchManager.getxPositionLandscape();
        p.y = notchManager.getyPositionLandscape();
        overlayView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if (visibility == 0) {
                    overlayView.setVisibility(View.VISIBLE);
                } else {//fullscreen
                    overlayView.setVisibility(View.VISIBLE);
                }
            }
        });
        return p;
    }

    private WindowManager.LayoutParams generateParamsPortraitLandscapeReverse(int h, int w) {
        int layoutParameters = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS |
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
        WindowManager.LayoutParams p = new WindowManager.LayoutParams(
                w,
                h,
                WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY,
                layoutParameters,
                PixelFormat.TRANSLUCENT);
        p.gravity = Gravity.TOP | Gravity.CENTER;
        //setting boundary
        p.x = notchManager.getxPositionPortrait();
        p.y = -(getStatusBarHeight()) - notchManager.getyPositionPortrait();
        overlayView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if (visibility == 0) {
                    overlayView.setVisibility(View.VISIBLE);
                } else {//fullscreen
                    overlayView.setVisibility(View.VISIBLE);
                }
            }
        });
        return p;
    }


    // -- Notch Bitmap Generator --
    // this method draws the notch shape
    // please don't ask me how it works now
    // i kinda forgot myself
    // it just works so i am not commenting out anything in it.
    private Bitmap drawNotch(int battery) {
        int w = notchManager.getWidth();
        int h = notchManager.getHeight();
        int ns = notchManager.getNotchSize();
        int tr = notchManager.getTopRadius();
        int br = notchManager.getBottomRadius();

        float p1 = 0;
        float p2 = (float) (w * 2);
        float p3 = (float) (((w + ns) * 2) + 2);
        float p4 = p3 - (p1 + p1);

        Path path = new Path();
        path.moveTo(p3 - 1.0f, -1.0f + p1);
        float p5 = -p1;
        path.rMoveTo(p5, p5);
        float p6 = h;
        float p7 = ns;
        float p8 = ((tr / 100.0f) * p7);// top radius
        float p9 = 0.0f;
        float p10 = ((br / 100.0f) * p7);
        p4 = -((p4 - ((p7 * 2.0f) + p2)) / 2.0f);
        path.rMoveTo(p4, 0.0F);
        float p11 = -p9;
        p3 = -p7;
        path.rCubicTo(-p8, p9, p10 - p7, p11 + p6, p3, p6);
        path.rLineTo(-p2, 0.0f);
        path.rCubicTo(-p10, p11, p8 - p7, p9 - p6, p3, -p6);

        path.close();

        RectF rectF = new RectF();
        path.computeBounds(rectF, true);
        rectF.height();

        Bitmap bitmap = Bitmap.createBitmap(
                //screenWidth,
                (int) Math.abs(rectF.width()),
                (int) Math.abs(rectF.height()) + 10,// , // Height
                Bitmap.Config.ARGB_8888 // Config
        );
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED);
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setStyle(Paint.Style.FILL);

        int[] colors = getColorArray(battery, settingsManager.isFullStatus());
        float[] positions = getPositionArray();
        SweepGradient sweepGradient = new SweepGradient((int) (Math.abs(rectF.width()) / 2) /*center*/, 0, colors, positions);
        paint.setShader(sweepGradient);

        Canvas canvas = new Canvas(bitmap);
        canvas.drawPath(path, paint);
        return bitmap;
    }

    // -- Array Generators --
    // Array Generators //
    // this method generates the color palette depending upon the config
    private int[] getColorArray(int battery, boolean isFullStatus) {
        int[] c = new int[181];
        if (isFullStatus) { // fill the overlay with one color
            for (ColorLevel item : batteryManager.getColorLevels()) {
                if (battery >= item.getStartLevel() && battery <= item.getEndLevel()) {
                    for (int i = 0; i < 181; i++) {
                        c[i] = Color.parseColor(item.getColor());
                    }
                }
            }
        } else { // need partially filler overlay
            for (int i = 0; i < 181; i++)
                c[i] = (settingsManager.isShowBackground()) ? Color.parseColor(settingsManager.getBackgroundColor()) : Color.TRANSPARENT;
            for (ColorLevel item : batteryManager.getColorLevels()) {
                if (battery >= item.getStartLevel() && battery <= item.getEndLevel()) {
                    int val = (int) ((battery / 100.0) * 180.0);
                    for (int i = 0; i < val; i++) {
                        c[i] = Color.parseColor(item.getColor());
                    }
                }
            }
        }
        return c;
    }

    // this method generates an evenly spread position point array
    private float[] getPositionArray() {
        float a = 0.5F / 180;
        float c = 0;
        float[] p = new float[181];
        for (int i = 0; i < 180; i++) {
            p[i] = c;
            c += a;
        }
        p[180] = 0.5F;
        return p;
    }

    // -- Animation methods --
    // idea by Mohammed ELNagger (@Negrroo) on telegram
    private boolean isAnimation1Active = false;
    private int tempBatteryLevel1 = batteryLevel;

    private void animation1() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (tempBatteryLevel1 == 100)
                    tempBatteryLevel1 = batteryLevel;
                else
                    tempBatteryLevel1++;
                makeOverlay(tempBatteryLevel1);
                if (isAnimation1Active)
                    animation1();
            }
        }, 100);
    }

}
