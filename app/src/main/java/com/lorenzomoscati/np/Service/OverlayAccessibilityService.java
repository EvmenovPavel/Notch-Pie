package com.lorenzomoscati.np.Service;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.widget.ImageView;

import com.lorenzomoscati.np.Constants.ConstantHolder;
import com.lorenzomoscati.np.Interface.OnBatteryLevelChanged;
import com.lorenzomoscati.np.Interface.OnRotate;
import com.lorenzomoscati.np.Modal.ColorLevel;
import com.lorenzomoscati.np.R;
import com.lorenzomoscati.np.Receiver.BatteryBroadcastReceiver;
import com.lorenzomoscati.np.Receiver.OrientationBroadcastReceiver;
import com.lorenzomoscati.np.Utils.BatteryConfigManager;
import com.lorenzomoscati.np.Utils.SettingsManager;
import com.lorenzomoscati.np.Utils.NotchManager;

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



	/// Method to check if the notch pie app is open so that the settings are continuously updated
	@Override
	public void onAccessibilityEvent(AccessibilityEvent event) {

		// Checks if the window has changed
		if (event.getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {

			// Checks if the window is now displaying an app
			if (event.getPackageName() != null && event.getClassName() != null) {

				ComponentName componentName = new ComponentName(
						event.getPackageName().toString(),
						event.getClassName().toString()
				);
				
				ActivityInfo activityInfo = getCurrentActivity(componentName);

				boolean isActivity = activityInfo != null;

				// Checks if the activity belongs to our app
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

	// Gets the foreground activity name
	private ActivityInfo getCurrentActivity(ComponentName componentName) {

		try {

			return getPackageManager().getActivityInfo(componentName, 0);

		} catch (PackageManager.NameNotFoundException e) {

			return null;

		}

	}

	// When the service gets disabled, the receiver are also disabled
	@Override
	public void onInterrupt() {

		stopReceivers();

	}


	// -- Handlers --
	@SuppressLint("InflateParams")
	private void init() {

		constants = new ConstantHolder();

		// Getting the window manager
		windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

		// The parent image view in which the bitmap is set
		overlayView = LayoutInflater.from(this).inflate(R.layout.overlay_float, null);

		// Sets the managers to read notch, color and settings
		notchManager = new NotchManager(getApplicationContext());
		settingsManager = new SettingsManager(getApplicationContext());
		batteryManager = new BatteryConfigManager(getApplicationContext());

		// Reads the settings
		notchManager.read(getApplicationContext());
		settingsManager.read(getApplicationContext());
		batteryManager.read(getApplicationContext());

		// Properly rotates the overlay
		overlayView.setRotationY(180);

		// Every 100 ms the settings are read
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

	
	
	// This method starts and initialises every receiver to be uses in service
	private void startReceivers() {
		
		// Orientation receiver
		receiverOrientation = new OrientationBroadcastReceiver(new OnRotate() {
			
			@Override
			public void onPortrait() {
				
				// When in portrait mode, the rotation is set and the notch is redrawn
				currentRotation = Surface.ROTATION_0;
				makeOverlay(batteryLevel);
				
			}

			@Override
			public void onLandscape() {
				
				// When in landscape mode, the rotation is set and the notch is redrawn
				currentRotation = windowManager.getDefaultDisplay().getRotation();
				makeOverlay(batteryLevel);
				
			}
			
		});
		
		IntentFilter intentFilterOrientation = new IntentFilter(Intent.ACTION_CONFIGURATION_CHANGED);

		// Battery receiver
		receiverBattery = new BatteryBroadcastReceiver(new OnBatteryLevelChanged() {
			
			@Override
			public void onChanged(int battery) {
				
				// When the battery level changes, the notch is redrawn according to the new percentage
				batteryLevel = battery;
				makeOverlay(batteryLevel);
				
			}

			@Override
			public void onChargingConnected(int battery) {
				
				settingsManager.read(getApplicationContext());
				
				// When the battery is charging, if the settings allow so, the charging animation starts
				if (settingsManager.isChargingAnimation() && !isAnimation1Active) {
					
					isAnimation1Active = true;
					tempBatteryLevel1 = batteryLevel;
					animation1();
					
				}
				
			}

			@Override
			public void oncChargingDisconnected(int battery) {
				
				// When the battery is not charging anymore, the charging animation stops
				isAnimation1Active = false;
				makeOverlay(batteryLevel);
				
			}
			
		});
		
		IntentFilter intentFilterBattery = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);

		// Receiver registered
		registerReceiver(receiverOrientation, intentFilterOrientation);
		registerReceiver(receiverBattery, intentFilterBattery);
		
	}

	private void readConfigsContinuously() {
		
		// Every 100 ms the settings are read
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {

				notchManager.read(getApplicationContext());
				settingsManager.read(getApplicationContext());
				batteryManager.read(getApplicationContext());

				makeOverlay(batteryLevel);

				if (isInApp) {

					readConfigsContinuously();

					isAnimation1Active = settingsManager.isChargingAnimation();

				}

				else {

					isAnimation1Active = settingsManager.isChargingAnimation();

					makeOverlay(batteryLevel);

				}

			}

		}, 100);

	}


	private void stopReceivers() {
		
		// When this is called, the receiver are unregistered
		unregisterReceiver(receiverOrientation);
		unregisterReceiver(receiverBattery);
		
	}

	// -- Overlay Manager --
	private void makeOverlay(int battery) {

		// Check if there is support for landscape, and according to this, the notch is drawn
		if (settingsManager.isLandscapeSupport()) {

			if (currentRotation == Surface.ROTATION_0) {

				makeOverlayPortrait(battery);

			}

			else if (currentRotation == Surface.ROTATION_90) {

				makeOverlayLandscape(battery);

			}

			else if (currentRotation == Surface.ROTATION_270) {

				makeOverlayLandscapeReverse(battery);

			}

			else {

				removeOverlay();

			}

		}

		else {

			if (currentRotation == Surface.ROTATION_0) {

				makeOverlayPortrait(battery);

			}

			else if (currentRotation == Surface.ROTATION_90) {

				removeOverlay();

			}

			else if (currentRotation == Surface.ROTATION_270) {

				removeOverlay();

			}

			else {

				removeOverlay();

			}

		}

	}

	// Makes the notch for a portrait view
	private void makeOverlayPortrait(int battery) {

		// Bitmap object
		Bitmap bitmap = drawNotch(battery);

		// Overlay view object
		ImageView img = overlayView.findViewById(R.id.imageView);

		// Bitmap placed on the object, with according rotation
		img.setImageBitmap(bitmap);
		img.setRotation(0);

		// Rotation parameters are set
		overlayView.setRotation(0);
		overlayView.setRotationY(180);
		overlayView.setRotationX(0);

		try {

			// Tries to update the overlay
			windowManager.updateViewLayout(overlayView, generateParamsPortrait(bitmap.getHeight(), bitmap.getWidth()));

		} catch (Exception e) {
			
			// If this gives an error then its probably because view is empty, so a new view is added
			try {

				windowManager.addView(overlayView, generateParamsPortrait(bitmap.getHeight(), bitmap.getWidth()));

			} catch (Exception ignored) {

			}

		}

	}
	
	// Makes the notch for a landscape view
	private void makeOverlayLandscape(int battery) {
		
		// Bitmap object
		Bitmap bitmap = drawNotch(battery);
		// Bitmap rotated
		bitmap = rotateBitmap(bitmap, 90f);
		
		// Overlay view object
		ImageView img = overlayView.findViewById(R.id.imageView);
		
		// Bitmap placed on the object, with according rotation
		img.setImageBitmap(bitmap);
		img.setRotation(0);
		
		// Rotation parameters are set
		overlayView.setRotation(0);
		overlayView.setRotationY(180);
		overlayView.setRotationX(0);

		try {
			
			// Tries to update the overlay
			windowManager.updateViewLayout(overlayView, generateParamsLandscape(bitmap.getHeight(), bitmap.getWidth()));

		} catch (Exception e) {
			
			// If this gives an error then its probably because view is empty, so a new view is added
			try {

				windowManager.addView(overlayView, generateParamsLandscape(bitmap.getHeight(), bitmap.getWidth()));

			} catch (Exception ignored) {

			}

		}

	}
	
	// Makes the notch for a landscape reverse view
	private void makeOverlayLandscapeReverse(int battery) {
		
		// Bitmap object
		Bitmap bitmap = drawNotch(battery);
		// Bitmap rotated
		bitmap = rotateBitmap(bitmap, -90f);
		
		// Overlay view object
		ImageView img = overlayView.findViewById(R.id.imageView);
		
		// Bitmap placed on the object, with according rotation
		img.setImageBitmap(bitmap);
		img.setRotation(0);
		
		// Rotation parameters are set
		overlayView.setRotation(0);

		try {
			
			// Tries to update the overlay
			windowManager.updateViewLayout(overlayView, generateParamsPortraitLandscapeReverse(bitmap.getHeight(), bitmap.getWidth()));

		} catch (Exception e) {
			
			// If this gives an error then its probably because view is empty, so a new view is added
			try {

				windowManager.addView(overlayView, generateParamsPortraitLandscapeReverse(bitmap.getHeight(), bitmap.getWidth()));

			} catch (Exception ignored) {

			}

		}

	}

	// This method removes the overlay from the view
	private void removeOverlay() {

		try {
			
			// Tries to remove the overlay
			windowManager.removeView(overlayView);

		} catch (Exception ignored) {
		
			// The overlay is already removed
			
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

				}

				else {//fullscreen

					overlayView.setVisibility(View.GONE);

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

				}

				else {//fullscreen

					overlayView.setVisibility(View.GONE);

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

		p.gravity = Gravity.END | Gravity.CENTER;

		//setting boundary
		p.x = notchManager.getxPositionLandscape();
		p.y = notchManager.getyPositionLandscape();

		overlayView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {

			@Override
			public void onSystemUiVisibilityChange(int visibility) {

				if (visibility == 0) {

					overlayView.setVisibility(View.VISIBLE);

				}

				else {//fullscreen

					overlayView.setVisibility(View.GONE);

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
		float p8 = ((tr / 100.0f) * (float) ns);// top radius
		float p9 = 0.0f;
		float p10 = ((br / 100.0f) * (float) ns);
		p4 = -((p4 - (((float) ns * 2.0f) + p2)) / 2.0f);
		path.rMoveTo(p4, 0.0F);
		float p11 = -p9;
		p3 = - (float) ns;
		path.rCubicTo(-p8, p9, p10 - (float) ns, p11 + (float) h, p3, (float) h);
		path.rLineTo(-p2, 0.0f);
		path.rCubicTo(-p10, p11, p8 - (float) ns, p9 - (float) h, p3, - (float) h);

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

		float[] positions = getPositionArray();
		int[] colors;

		if (batteryManager.isLinear()) {
			colors = getColorArrayLinear(battery, settingsManager.isFullStatus());
		} else {
			colors = getColorArrayDefined(battery, settingsManager.isFullStatus());
		}

		SweepGradient sweepGradient = new SweepGradient((int) (Math.abs(rectF.width()) / 2) /*center*/, 0, colors, positions);
		paint.setShader(sweepGradient);

		Canvas canvas = new Canvas(bitmap);
		canvas.drawPath(path, paint);


		return bitmap;
	}

	// -- Array Generators --
	// Array Generators //
	// this method generates the color palette depending upon the config
	private int[] getColorArrayDefined(int battery, boolean isFullStatus) {
		int[] c = new int[181];
		if (isFullStatus) { // fill the overlay with one color
			for (ColorLevel item : batteryManager.getColorLevels()) {
				if (battery >= item.getStartLevel() && battery <= item.getEndLevel()) {
					for (int i = 0; i < 181; i++) {
						c[i] = Color.parseColor(item.getColor());
					}
					break;
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
					break;
				}
			}
		}
		return c;
	}

	@SuppressLint("Range")
	private int[] getColorArrayLinear(int battery, boolean isFullStatus) {
		int[] c = new int[181];
		String color1 = batteryManager.getLinearStart();
		String color2 = batteryManager.getLinearEnd();
		int red1 = Integer.parseInt(color1.substring(1, 3), 16);
		int green1 = Integer.parseInt(color1.substring(3, 5), 16);
		int blue1 = Integer.parseInt(color1.substring(5), 16);
		int red2 = Integer.parseInt(color2.substring(1, 3), 16);
		int green2 = Integer.parseInt(color2.substring(3, 5), 16);
		int blue2 = Integer.parseInt(color2.substring(5), 16);

		int red = Math.abs((int) ((red2 - red1) / 100.0) * battery);
		int green = Math.abs((int) ((green2 - green1) / 100.0) * battery);
		int blue = Math.abs((int) ((blue2 - blue1) / 100.0) * battery);

		Log.e("RED", red + " " + green + blue);

		String r = Integer.toString(red, 16);
		if (r.length() == 1)
			r = "0" + r;
		String g = Integer.toString(green, 16);
		if (g.length() == 1)
			g = "0" + g;
		String b = Integer.toString(blue, 16);
		if (b.length() == 1)
			b = "0" + b;
		String color = "#" + r + "" + g + "" + b;
		if (isFullStatus) { // fill the overlay with one color
			for (int i = 0; i < 181; i++) {
				Log.e("color", color);
				c[i] = Color.parseColor(color);
			}

		} else { // need partially filler overlay
			for (int i = 0; i < 181; i++)
				c[i] = (settingsManager.isShowBackground()) ? Color.parseColor(settingsManager.getBackgroundColor()) : Color.TRANSPARENT;
			for (ColorLevel item : batteryManager.getColorLevels()) {
				if (battery >= item.getStartLevel() && battery <= item.getEndLevel()) {
					int val = (int) ((battery / 100.0) * 180.0);
					for (int i = 0; i < val; i++) {
						Log.e("color", color);
						c[i] = Color.parseColor(color);
					}
					break;
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

	private static Bitmap rotateBitmap(Bitmap source, float angle) {
		Matrix matrix = new Matrix();
		matrix.postRotate(angle);
		return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
	}


	// Animation methods
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
