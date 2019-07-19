package com.lorenzomoscati.np.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsParams;
import com.android.billingclient.api.SkuDetailsResponseListener;
import com.lorenzomoscati.np.R;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class AboutFragment extends Fragment implements PurchasesUpdatedListener {

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

		setupBilling();

		View main = inflater.inflate(R.layout.fragment_about, container, false);

		return attach(main);

	}

	private String[] amounts = {"$1", "$2", "$3", "$4", "$5", "$6", "$7", "$8", "$9", "$10"};
	private String[] amount_id = {"one_dollar", "two_dollar", "three_dollars", "four_dollar", "five_dollars", "six_dollars", "seven_dollar", "eight_dollars", "nine_dollars", "ten_dollars"};
	private List<SkuDetails> detailsList = null;

	private int current = 0;
	private BillingClient billingClient;

	// Takes the parent view and attaches the listeners to it
	private View attach(View main) {

		// Views
		View xda = main.findViewById(R.id.touchXda);
		View telegam = main.findViewById(R.id.touchTelegram);
		View github = main.findViewById(R.id.touchGithub);
		View donate = main.findViewById(R.id.touchDonation);



		// Applying listener to XDA
		xda.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				// Prepare the link to be opened
				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://forum.xda-developers.com/redmi-note-7-pro/themes/app-notch-pie-ring-overlay-to-battery-t3917168"));

				// Opens the link
				startActivity(browserIntent);

			}

		});

		// Applying listener to Telegram
		telegam.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				// Prepare the link to be opened
				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/notchpie"));

				// Opens the link
				startActivity(browserIntent);

			}

		});

		// Applying listener to GitHub
		github.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				// Prepare the link to be opened
				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/Fly7113/Notch-Pie"));

				// Opens the link
				startActivity(browserIntent);

			}

		});

		// Applying the listener to donate
		donate.setOnClickListener(new View.OnClickListener() {
	
			@Override
			public void onClick(View v) {
		
				// Initializes a dialog window
				AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
		
				// Sets the title
				builder.setTitle("Choose Amount");
		
				// Applies listener
				builder.setSingleChoiceItems(amounts, 0, new DialogInterface.OnClickListener() {
			
					@Override
					public void onClick(DialogInterface dialog, int which) {
				
						current = which;
				
					}
			
				});
				
				builder.setPositiveButton("Donate", new DialogInterface.OnClickListener() {
			
					@Override
					public void onClick(DialogInterface dialog, int which) {
				
						if (detailsList != null) {
					
							try {
								
								BillingFlowParams params = BillingFlowParams.newBuilder().setSkuDetails(detailsList.get(current)).build();
								billingClient.launchBillingFlow(getActivity(), params);
								
							}
							
							catch (IndexOutOfBoundsException ignored) {
								
								Toast.makeText(getContext(), "Index out of bound exception, please try again later", Toast.LENGTH_LONG).show();
								
							}

					
						}
				
						else {
					
							Toast.makeText(getContext(), "Please Try Again Later :(", Toast.LENGTH_SHORT).show();
					
						}
				
					}
			
				});
		
				// Creates the window
				builder.show();
		
			}

		});

		return main;
	}

	private void setupBilling() {

		// Initializes the billing client
		billingClient = BillingClient.newBuilder(Objects.requireNonNull(getContext())).setListener(this).enablePendingPurchases().build();

		// Applying the listener to the billing client
		billingClient.startConnection(new BillingClientStateListener() {

			@Override
			public void onBillingSetupFinished(BillingResult billingResult) {

				// If the billing client is successful the data is loaded
				if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {

					Toast.makeText(getContext(), "Connected to Billing Service", Toast.LENGTH_SHORT).show();
					loadData();

				}

				else {

					// If there is an error, a debug message is toasted
					Toast.makeText(getContext(), billingResult.getDebugMessage(), Toast.LENGTH_SHORT).show();

				}

			}

			@Override
			public void onBillingServiceDisconnected() {

				// If the service is disconnected, the user is notified via a toast
				Toast.makeText(getContext(), "Disconnected from billing service.", Toast.LENGTH_SHORT).show();

			}

		});

	}

	@Override
	public void onPurchasesUpdated(BillingResult billingResult, @Nullable List<Purchase> purchases) {

		if (purchases != null && purchases.size() > 0) {

			// If the purchase has successfully happened the user is greeted
			Toast.makeText(getContext(), "YOU'RE THE BEST!", Toast.LENGTH_SHORT).show();

		}

	}

	private void loadData() {

		if (billingClient.isReady()) {

			// Array to state the amount and the type of the purchase
			SkuDetailsParams params = SkuDetailsParams.newBuilder().setSkusList(Arrays.asList(amount_id)).setType(BillingClient.SkuType.INAPP).build();

			billingClient.querySkuDetailsAsync(params, new SkuDetailsResponseListener() {

				@Override
				public void onSkuDetailsResponse(BillingResult billingResult, List<SkuDetails> skuDetailsList) {

					Toast.makeText(getContext(), skuDetailsList.size()+ " lol", Toast.LENGTH_SHORT).show();

					if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {

						detailsList = skuDetailsList;

					}

				}

			});

		}

		else {

			Toast.makeText(getContext(), "Client not ready.", Toast.LENGTH_SHORT).show();

		}

	}

}
