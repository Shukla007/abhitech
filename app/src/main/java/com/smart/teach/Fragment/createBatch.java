package com.smart.teach.Fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.smart.teach.R;

import org.w3c.dom.Text;

import java.util.UUID;


public class createBatch extends Fragment {

    View view;
    Uri imgUri;
    ImageView thumbnail;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_create_batch, container, false);


        ImageView thumbnail = (ImageView) view.findViewById(R.id.thumbnail1);

        Button randomId = view.findViewById(R.id.randdom_id);

        TextView setUId = view.findViewById(R.id.setId);

        randomId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uid = UUID.randomUUID().toString();
                setUId.setText(uid);
            }
        });


        ImageButton addPic = view.findViewById(R.id.addPic);
        addPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickImage();

            }
        });


        return view;
    }

    private void pickImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 100);


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            if (requestCode == 100 && data != null && data.getData() != null) {
                imgUri = data.getData();
                thumbnail.setImageURI(imgUri);
            }
        }catch (Exception e){
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }



    }
}