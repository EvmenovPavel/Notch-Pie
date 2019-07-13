package com.lorenzomoscati.np.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.lorenzomoscati.np.R;

public class BlankFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View main = inflater.inflate(R.layout.fragment_blank, container, false);
        TextView tt = main.findViewById(R.id.textView2);
        tt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "dddd", Toast.LENGTH_SHORT).show();
            }
        });
        return  main;
    }
}
