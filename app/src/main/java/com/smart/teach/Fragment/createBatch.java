package com.smart.teach.Fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.Model;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.smart.teach.R;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;


public class createBatch extends Fragment {

    private static final int PICK_IMAGE = 1;
    View view;
    StorageReference mStorageRef;
    Uri imageUrl;
    String userID;
    ImageView thumbnail;
    ProgressBar imageProgress;
    FirebaseDatabase db;
    DatabaseReference reference;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_create_batch, container, false);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        db = FirebaseDatabase.getInstance();
        reference = db.getReference().child("Live Batches");

        userID = user.getUid();


        thumbnail = view.findViewById(R.id.thumbnail1);
        imageProgress = view.findViewById(R.id.imageProgress);

        EditText discription = view.findViewById(R.id.Course_discription);
        EditText title = view.findViewById(R.id.Course_title);
        Spinner duration = view.findViewById(R.id.duration_spinner);
        EditText price = view.findViewById(R.id.price);
        TextView roomid = view.findViewById(R.id.setId);
        Button create = view.findViewById(R.id.create_batch);


        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Discription = discription.getText().toString();
                String Title = title.getText().toString();
                String Duration = duration.getSelectedItem().toString();
                String Price = price.getText().toString();
                String RoomId = roomid.getText().toString();
                String thumbnail = user.getPhotoUrl().toString();

                if (TextUtils.isEmpty(Title) || TextUtils.isEmpty(Discription) || TextUtils.isEmpty(Duration) || TextUtils.isEmpty(Price) || TextUtils.isEmpty(RoomId)) {
                    Toast.makeText(getActivity(), "Empty field", Toast.LENGTH_SHORT).show();
                } else {
                    //Hashmap
                    HashMap<String, String> livebatch = new HashMap<>();
                    livebatch.put("Title", Title);
                    livebatch.put("Discription", Discription);
                    livebatch.put("Duration", Duration);
                    livebatch.put("Price", Price);
                    livebatch.put("RoomId", RoomId);
                    livebatch.put("Thumbnail",thumbnail);
                    imageProgress.setVisibility(View.VISIBLE);

                    reference.push().setValue(livebatch);

                }


            }

        });

        Button randomId = view.findViewById(R.id.randdom_id);
        TextView setUId = view.findViewById(R.id.setId);
        if (user.getPhotoUrl() != null) {
            imageProgress.setVisibility(View.INVISIBLE);
            Glide.with(this).load(user.getPhotoUrl()).into(thumbnail);
        }
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
        startActivityForResult(Intent.createChooser(intent, "Select thumbnail"), PICK_IMAGE);


    }

    //    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        try {
//            if (requestCode == 1 && data != null && data.getData() != null) {
//                 Uri imgUri = data.getData();
//                thumbnail.setImageURI(imgUri);
//            }
//        } catch (Exception e) {
//            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
//        }
//
//
//    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK) {
            imageUrl = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUrl);
                thumbnail.setImageBitmap(bitmap);

                handleUpload(bitmap);
            } catch (Exception e) {

                e.printStackTrace();
                Log.e("image", e.getMessage());
                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void handleUpload(Bitmap bitmap) {
        String random = UUID.randomUUID().toString();
        ByteArrayOutputStream btos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, btos);
        mStorageRef = FirebaseStorage.getInstance().getReference()
                .child("CourseImage")
                .child(random + ".jpeg");

        mStorageRef.putBytes(btos.toByteArray())
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        imageProgress.setVisibility(View.VISIBLE);

                        // Get a URL to the uploaded content
                        // Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        getDownloadUrl(mStorageRef);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Toast.makeText(getActivity(), exception.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void getDownloadUrl(StorageReference mStorageRef) {
        mStorageRef.getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        // Successfully downloaded data to local file
                        // ...
                        Toast.makeText(getActivity(), "onSuccess", Toast.LENGTH_SHORT).show();
                        setUserProfileUrl(uri);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle failed download
                // ...
            }
        });
    }

    private void setUserProfileUrl(Uri uri) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        UserProfileChangeRequest request = new UserProfileChangeRequest.Builder()
                .setPhotoUri(uri)
                .build();

        user.updateProfile(request)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        imageProgress.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), "Updated Succesfully", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "Profile image failed", Toast.LENGTH_SHORT).show();
                    }
                });

    }
//    private void uploadToFirebase(Uri uri){
//
//        final StorageReference fileRef = reference.child(System.currentTimeMillis() + "." + getFileExtension(uri));
//        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                    @Override
//                    public void onSuccess(Uri uri) {
//
//                        Model model = new Model(uri.toString());
//                        String modelId = root.push().getKey();
//                        mStorageRef.child(modelId).setValue(model);
//                        Toast.makeText(getActivity(), "Uploaded Successfully", Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
//                progressBar.setVisibility(View.VISIBLE);
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                progressBar.setVisibility(View.INVISIBLE);
//                Toast.makeText(getActivity(), "Uploading Failed !!", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

}