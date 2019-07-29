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

	private final String[] amounts = {"$1", "$2", "$3", "$4", "$5", "$6", "$7", "$8", "$9", "$10"};
	private final String[] amount_id = {"one_dollar", "two_dollar", "three_dollar", "four_dollar", "five_dollar", "six_dollar", "seven_dollar", "eight_dollar", "nine_dollar", "ten_dollar"};
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
				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.XDA_thread_URL)));

				// Opens the link
				startActivity(browserIntent);

			}

		});

		// Applying listener to Telegram
		telegam.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				// Prepare the link to be opened
				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.Telegram_group_URL)));

				// Opens the link
				startActivity(browserIntent);

			}

		});

		// Applying listener to GitHub
		github.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				// Prepare the link to be opened
				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.GitHub_repository_URL)));

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
				builder.setTitle(R.string.donation_title);
		
				// Applies listener
				builder.setSingleChoiceItems(amounts, 0, new DialogInterface.OnClickListener() {
			
					@Override
					public void onClick(DialogInterface dialog, int which) {
				
						current = which;
				
					}
			
				});
				
				builder.setPositiveButton(R.string.donation_confirm, new DialogInterface.OnClickListener() {
			
					@Override
					public void onClick(DialogInterface dialog, int which) {
				
						if (detailsList != null) {
								
							BillingFlowParams params = BillingFlowParams.newBuilder().setSkuDetails(detailsList.get(current)).build();
							billingClient.launchBillingFlow(getActivity(), params);
							
						}
				
						else {
					
							Toast.makeText(getContext(), R.string.billing_error, Toast.LENGTH_SHORT).show();
					
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

					//Toast.makeText(getContext(), R.string.billing_connected, Toast.LENGTH_SHORT).show();
					loadData();

				}

				/*else {

					// If there is an error, a debug message is toasted
					Toast.makeText(getContext(), billingResult.getDebugMessage(), Toast.LENGTH_SHORT).show();

				}*/

			}

			@Override
			public void onBillingServiceDisconnected() {

				// If the service is disconnected, the user is notified via a toast
				//Toast.makeText(getContext(), R.string.billing_disconnected, Toast.LENGTH_SHORT).show();

			}

		});

	}

	@Override
	public void onPurchasesUpdated(BillingResult billingResult, @Nullable List<Purchase> purchases) {

		if (purchases != null && purchases.size() > 0) {

			// If the purchase has successfully happened the user is greeted
			Toast.makeText(getContext(), R.string.donation_thanks, Toast.LENGTH_SHORT).show();

		}

	}

	private void loadData() {

		if (billingClient.isReady()) {

			// Array to state the amount and the type of the purchase
			SkuDetailsParams params = SkuDetailsParams.newBuilder().setSkusList(Arrays.asList(amount_id)).setType(BillingClient.SkuType.INAPP).build();

			billingClient.querySkuDetailsAsync(params, new SkuDetailsResponseListener() {

				@Override
				public void onSkuDetailsResponse(BillingResult billingResult, List<SkuDetails> skuDetailsList) {

					//Toast.makeText(getContext(), skuDetailsList.size()+ " lol", Toast.LENGTH_SHORT).show();

					if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {

						detailsList = skuDetailsList;

					}

				}

			});

		}

		/*else {

			Toast.makeText(getContext(), R.string.billing_notReady, Toast.LENGTH_SHORT).show();

		}*/

	}

}
