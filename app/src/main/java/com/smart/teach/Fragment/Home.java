package com.smart.teach.Fragment;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.smart.teach.MainActivity;
import com.smart.teach.R;
import com.smart.teach.joinUsers_Activity;

import java.util.ArrayList;

public class Home extends Fragment {

    View view;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home, container, false);

        Button button=view.findViewById(R.id.useractivity);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), joinUsers_Activity.class);
                startActivity(intent);
            }
            });
                return view;
    }
}
