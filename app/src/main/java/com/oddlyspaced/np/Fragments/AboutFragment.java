package com.oddlyspaced.np.Fragments;

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

    private View attach(View main) {
        View xda = main.findViewById(R.id.touchXda);
        View telegam = main.findViewById(R.id.touchTelegram);
        View github = main.findViewById(R.id.touchGithub);
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
        return main;
    }
}
