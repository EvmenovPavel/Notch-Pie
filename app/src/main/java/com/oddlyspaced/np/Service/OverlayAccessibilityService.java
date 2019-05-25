package com.oddlyspaced.np.Service;

import android.accessibilityservice.AccessibilityService;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.os.FileObserver;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.oddlyspaced.np.Constants.ConstantHolder;
import com.oddlyspaced.np.Interface.OnBatteryConfigEdited;
import com.oddlyspaced.np.Interface.OnBatteryLevelChanged;
import com.oddlyspaced.np.Interface.OnNotchConfigEdited;
import com.oddlyspaced.np.Interface.OnRotate;
import com.oddlyspaced.np.Interface.OnSettingsConfigEdited;
import com.oddlyspaced.np.Modal.ColorLevel;
import com.oddlyspaced.np.R;
import com.oddlyspaced.np.Receiver.BatteryBroadcastReceiver;
import com.oddlyspaced.np.Receiver.FileBroadcastReceiver;
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

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {

    }

    @Override
    public void onInterrupt() {
        Log.e ("ok", "stoppp");
        stopConfigObservers();
        stopReceivers();
    }

    // Executed when service started
    @Override
    protected void onServiceConnected() {
        init();
        startReceivers();
        startConfigObservers();
    }

    // -- Handlers --
    private void init() {
        constants = new ConstantHolder();
        // getting the window manager
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        // the parent image view in which the bitmap is set
        overlayView = LayoutInflater.from(this).inflate(R.layout.overlay_float, null); // the view which will be overlayed
        overlayView.setRotationY(180); // mirroring
        notchManager = new NotchManager();
        settingsManager = new SettingsManager();
        batteryManager = new BatteryConfigManager();
        notchManager.read();
        settingsManager.read();
        batteryManager.read();
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
    private FileObserver notchConfigObserver;
    private FileObserver settingConfigObserver;
    private FileObserver batteryConfigObserver;

    private final String NOTCH_CONFIG_CHANGED_BROADCAST_ACTION = "NOTCH_CONFIG_CHANGED_BROADCAST_ACTION";
    private final String SETTINGS_CONFIG_CHANGED_BROADCAST_ACTION = "SETTINGS_CONFIG_CHANGED_BROADCAST_ACTION";
    private final String BATTERY_CONFIG_CHANGED_BROADCAST_ACTION = "BATTERY_CONFIG_CHANGED_BROADCAST_ACTION";


    private void startConfigObservers() {
        notchConfigObserver = new FileObserver(constants.getConfigFilePathInternal()) {
            @Override
            public void onEvent(int event, @Nullable String path) {
                if (event == MODIFY && isPortrait) {
                    Intent intent = new Intent(NOTCH_CONFIG_CHANGED_BROADCAST_ACTION);
                    sendBroadcast(intent);
                }
            }
        };
        notchConfigObserver.startWatching();

        settingConfigObserver = new FileObserver(constants.getSettingsFilePathInternal()) {
            @Override
            public void onEvent(int event, @Nullable String path) {
                if (event == MODIFY && isPortrait) {
                    Intent intent = new Intent(SETTINGS_CONFIG_CHANGED_BROADCAST_ACTION);
                    sendBroadcast(intent);
                }
            }
        };
        settingConfigObserver.startWatching();

        batteryConfigObserver = new FileObserver(constants.getBatteryFilePathInternal()) {
            @Override
            public void onEvent(int event, @Nullable String path) {
                if (event == MODIFY && isPortrait) {
                    Intent intent = new Intent(BATTERY_CONFIG_CHANGED_BROADCAST_ACTION);
                    sendBroadcast(intent);
                }
            }
        };
        batteryConfigObserver.startWatching();
    }

    private void stopConfigObservers() {
        notchConfigObserver.stopWatching();
        settingConfigObserver.stopWatching();
        batteryConfigObserver.stopWatching();
    }

    private boolean isPortrait = true;
    private int batteryLevel;

    private OrientationBroadcastReceiver receiverOrientation;
    private BatteryBroadcastReceiver receiverBattery;
    private FileBroadcastReceiver receiverNotchConfig;
    private FileBroadcastReceiver receiverSettingsConfig;
    private FileBroadcastReceiver receiverBatteryConfig;

    // this method starts and initialises every receiver to be uses in service
    private void startReceivers() {
        receiverOrientation = new OrientationBroadcastReceiver(new OnRotate() {
            @Override
            public void onPortrait() {
                isPortrait = true;
                makeOverlay(batteryLevel);
            }

            @Override
            public void onLandscape() {
                isPortrait = false;
                removeOverlay();
            }
        });
        IntentFilter intentFilterOrientation = new IntentFilter(Intent.ACTION_CONFIGURATION_CHANGED);

        receiverBattery = new BatteryBroadcastReceiver(new OnBatteryLevelChanged() {
            @Override
            public void onChanged(int battery) {
                batteryLevel = battery;
                if (isPortrait) {
                    makeOverlay(batteryLevel);
                }
            }

            @Override
            public void onChargingConnected(int battery) {
                Log.e("ood", "odddd");
                settingsManager.read();
                if (settingsManager.isChargingAnimation() && !isAnimation1Active) {
                    isAnimation1Active = true;
                    tempBatteryLevel1 = batteryLevel;
                    animation1();
                }
            }

            @Override
            public void oncChargingDisconnected(int battery) {
                Log.e("ood", "odddssssssssssssssd");
                isAnimation1Active = false;
                makeOverlay(batteryLevel);
            }
        });
        IntentFilter intentFilterBattery = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);

        receiverNotchConfig = new FileBroadcastReceiver(new OnNotchConfigEdited() {
            @Override
            public void onEdited() {
                notchManager.read();
                makeOverlay(batteryLevel);
            }
        });
        IntentFilter intentFilterNotchConfig = new IntentFilter(NOTCH_CONFIG_CHANGED_BROADCAST_ACTION);

        receiverSettingsConfig = new FileBroadcastReceiver(new OnSettingsConfigEdited() {
            @Override
            public void onEdited() {
                settingsManager.read();
                //if (settingsManager.isChargingAnimation1()) {
                  //  isAnimation1Active = true;
                    //animation1();
                //}
                //else {
                  //  isAnimation1Active = false;
                    makeOverlay(batteryLevel);
                //}
            }
        });
        IntentFilter intentFilterSettingsConfig = new IntentFilter(SETTINGS_CONFIG_CHANGED_BROADCAST_ACTION);

        receiverBatteryConfig = new FileBroadcastReceiver(new OnBatteryConfigEdited() {
            @Override
            public void onEdited() {
                batteryManager.read();
                makeOverlay(batteryLevel);
            }
        });
        IntentFilter intentFilterBatteryConfig = new IntentFilter(BATTERY_CONFIG_CHANGED_BROADCAST_ACTION);


        registerReceiver(receiverOrientation, intentFilterOrientation);
        registerReceiver(receiverBattery, intentFilterBattery);
        registerReceiver(receiverNotchConfig, intentFilterNotchConfig);
        registerReceiver(receiverSettingsConfig, intentFilterSettingsConfig);
        registerReceiver(receiverBatteryConfig, intentFilterBatteryConfig);
    }

    private void stopReceivers() {
        unregisterReceiver(receiverOrientation);
        unregisterReceiver(receiverBattery);
        unregisterReceiver(receiverNotchConfig);
        unregisterReceiver(receiverSettingsConfig);
        unregisterReceiver(receiverBatteryConfig);
    }

    // -- Overlay Manager --
    private void makeOverlay(int battery) {
        Bitmap bitmap = drawNotch(battery);
        ImageView img = overlayView.findViewById(R.id.imageView);
        img.setImageBitmap(bitmap);
        img.setRotation(0);
        overlayView.setRotation(0);
        try {
            windowManager.updateViewLayout(overlayView, generateParams(bitmap.getHeight(), bitmap.getWidth()));
            Log.e("yee", "haww");
        } catch (Exception e) {
            Log.e("original", e.toString());
            // if this gives an error then its probably because wm is empty
            try {
                windowManager.addView(overlayView, generateParams(bitmap.getHeight(), bitmap.getWidth()));
            } catch (Exception ee) {
                Log.e("why tho", ee.toString());
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

    // -- Layout Parameter Generator --
    private WindowManager.LayoutParams generateParams(int h, int w) {
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
        p.x = notchManager.getxPosition();
        p.y = - (getStatusBarHeight()) - notchManager.getyPosition();
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
                c[i] = (settingsManager.isShowBackground())?Color.parseColor(settingsManager.getBackgroundColor()):Color.TRANSPARENT;
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
