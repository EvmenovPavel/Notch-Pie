package com.lorenzomoscati.np.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

import com.lorenzomoscati.np.R;

public class MainActivity extends AppCompatActivity {

	// Permission request code for storage access
	private static final int CODE_STORAGE_PERMISSION = 1024;
	// Permission request code to draw over other apps
	private static final int CODE_DRAW_OVER_OTHER_APP_PERMISSION = 2084;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		checkPermissions();

	}

	// This method checks for all permissions
	private void checkPermissions() {


		if (!isStoragePermissionGranted()) {

			notifyStoragePermission();

		} else if (!canDrawOverlay()) {

			notifyOverlayPermission();

		} else {

			// Starts the tabbed activity, the main one in the application
			startActivity(new Intent(this,TabbedActivity.class));

			// Terminates the activity
			finish();

		}

	}



	// Storage permission things

	// This method returns true if the storage permission is granted
	private boolean isStoragePermissionGranted() {

		return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;

	}

	// This method shows a dialog notifying the user about the storage permission
	private void notifyStoragePermission() {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setPositiveButton(getString(R.string.popup_positive), new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

				requestStoragePermission();

			}

		});

		builder.setNegativeButton(getString(R.string.popup_negative), new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

				finish();

			}

		});

		builder.setCancelable(false);

		builder.setTitle(R.string.alert_storage_title);

		builder.setMessage(getString(R.string.alert_storage_description));

		builder.show();

	}

	// This method requests the storage permission
	private void requestStoragePermission() {

		ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, CODE_STORAGE_PERMISSION);

	}



	// Draw over the app permission things

	// This method returns true if the overlay permission is granted
	private boolean canDrawOverlay() {

		return Settings.canDrawOverlays(this);

	}

	// This method shows a dialog notifying the user about overlay permission
	private void notifyOverlayPermission() {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setPositiveButton(getString(R.string.popup_positive), new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

				requestOverlayPermission();

			}

		});

		builder.setNegativeButton(getString(R.string.popup_negative), new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

				finish();

			}

		});

		builder.setCancelable(false);

		builder.setTitle(getString(R.string.alert_overlay_title));

		builder.setMessage(getString(R.string.alert_overlay_description));

		builder.show();

	}

	// This method requests overlay permission
	private void requestOverlayPermission() {

		Toast.makeText(getApplicationContext(), getString(R.string.alert_overlay_request), Toast.LENGTH_LONG).show();

		Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
		startActivityForResult(intent, CODE_DRAW_OVER_OTHER_APP_PERMISSION);

	}




	// This method is called when the app comes to foreground
	@Override
	protected void onResume() {

		super.onResume();

		checkPermissions();

	}

	// This method is called when a runtime permission is granted, to check that the permission is actually given
	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

		// xxx permission granted
		if (requestCode == CODE_STORAGE_PERMISSION) {

			if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

				// Checks again the permissions given
				checkPermissions();

			} else {

				// Closes the app
				finish();

			}

		}

	}

}
