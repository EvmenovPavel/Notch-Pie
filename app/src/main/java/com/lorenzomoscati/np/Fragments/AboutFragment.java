package com.lorenzomoscati.np.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
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

public class AboutFragment extends Fragment implements PurchasesUpdatedListener {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setupBilling();
        View main = inflater.inflate(R.layout.fragment_about, container, false);
        return attach(main);
    }

    String[] amounts = {"$1", "$2", "$3", "$4", "$5", "$6", "$7", "$8", "$9", "$10"};
    String[] amount_id = {"one_dollar", "two_dollar", "three_dollars", "four_dollar", "five_dollars", "six_dollars", "seven_dollar", "eight_dollars", "nine_dollars", "ten_dollars"};
    List<SkuDetails> detailsList = null;

    int current = 0;
    BillingClient billingClient;

    private View attach(View main) {
        View xda = main.findViewById(R.id.touchXda);
        View telegam = main.findViewById(R.id.touchTelegram);
        View github = main.findViewById(R.id.touchGithub);
        final TextView amt = main.findViewById(R.id.txAmount);
        View donate = main.findViewById(R.id.touchDonation);

        xda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://forum.xda-developers.com/redmi-note-7-pro/themes/app-notch-pie-ring-overlay-to-battery-t3917168"));
                startActivity(browserIntent);
            }
        });
        telegam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/notchpie"));
                startActivity(browserIntent);
            }
        });
        github.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/Fly7113/Notch-Pie"));
                startActivity(browserIntent);
            }
        });
        amt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Choose Amount");
                builder.setSingleChoiceItems(amounts, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        amt.setText(new String(amounts[which] + " Donation"));
                        current = which;
                    }
                });
                builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
            }
        });
        donate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (detailsList != null) {
                    BillingFlowParams params = BillingFlowParams.newBuilder().setSkuDetails(detailsList.get(current)).build();
                    billingClient.launchBillingFlow(getActivity(), params);
                }
                else {
                    Toast.makeText(getContext(), "Please Try Again Later :(", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return main;
    }

    private void setupBilling() {
        billingClient = BillingClient.newBuilder(getContext()).setListener(this).enablePendingPurchases().build();
        billingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingSetupFinished(BillingResult billingResult) {
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                    //Toast.makeText(getContext(), "Connected to Billing Service", Toast.LENGTH_SHORT).show();
                    loadData();
                } else {
                    //Toast.makeText(getContext(), billingResult.getDebugMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onBillingServiceDisconnected() {
                //Toast.makeText(getContext(), "Disconnected from billing service.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onPurchasesUpdated(BillingResult billingResult, @Nullable List<Purchase> purchases) {
        if (purchases != null && purchases.size() > 0) {
            Toast.makeText(getContext(), "YOU'RE THE BEST!", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadData() {
        if (billingClient.isReady()) {
            SkuDetailsParams params = SkuDetailsParams.newBuilder().setSkusList(Arrays.asList(amount_id)).setType(BillingClient.SkuType.INAPP).build();
            billingClient.querySkuDetailsAsync(params, new SkuDetailsResponseListener() {
                @Override
                public void onSkuDetailsResponse(BillingResult billingResult, List<SkuDetails> skuDetailsList) {
                    //Toast.makeText(getContext(), skuDetailsList.size()+ "", Toast.LENGTH_SHORT).show();
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
