package com.smart.teach.Fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.smart.teach.R;


public class createBatch extends Fragment {

View view;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_create_batch, container, false);


        Button addPic = view.findViewById(R.id.addPic);
        addPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PickImage();
            }

            private void PickImage() {

                Intent intent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                intent.setType("image/*");
                intent.putExtra("crop", "true");
                intent.putExtra("scale", true);
                intent.putExtra("outputX", 256);
                intent.putExtra("outputY", 256);
                intent.putExtra("aspectX", 1);
                intent.putExtra("aspectY", 1);
                intent.putExtra("return-data", true);
                startActivityForResult(intent, 1);
            }
        });

        return view;
    }
}