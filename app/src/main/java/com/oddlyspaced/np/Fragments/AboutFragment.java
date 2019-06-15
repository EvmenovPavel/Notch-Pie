package com.oddlyspaced.np.Fragments;

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

import com.oddlyspaced.np.R;

public class AboutFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View main = inflater.inflate(R.layout.fragment_about, container, false);
        return attach(main);
    }

    String[] amounts = {"$1", "$2", "$3", "$4", "$5", "$6", "$7", "$8", "$9", "$10"};
    String[] amount_id = {"one_dollar", "two_dollar", "three_dollars", "four_dollar", "five_dollars", "six_dollars", "seven_dollar", "eight_dollars", "nine_dollars", "ten_dollars"};
    int current = 0;

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
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/oddlyspaced/Notch-Pie-New"));
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
                        Toast.makeText(getContext(), amount_id[which], Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getContext(), amount_id[current], Toast.LENGTH_SHORT).show();
            }
        });

        return main;
    }
}
