package com.smart.teach;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.smart.teach.MainActivity;

public class student_login extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    private static final int RC_SIGN_IN = 1;
    SignInButton signInButton;
    Button button, login_btn;
    EditText login_email, login_pass;
    FirebaseFirestore fStore;
    private GoogleApiClient googleApiClient;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);

        auth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

       if (user != null) {
            Intent intent = new Intent(student_login.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        TextView button = (TextView) findViewById(R.id.link_text);
        login_email = findViewById(R.id.login_email);
        login_pass = findViewById(R.id.login_pass);
        TextView forgot_btn = (TextView) findViewById(R.id.forgot_btn);


        forgot_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(student_login.this, forgot.class);
                startActivity(intent);
            }
        });

        login_btn = findViewById(R.id.login_btn);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = login_email.getText().toString();
                String pass = login_pass.getText().toString();

                if (TextUtils.isEmpty(id) || TextUtils.isEmpty(pass)) {
                    Toast.makeText(student_login.this, "Empty Input Field", Toast.LENGTH_SHORT).show();
                } else {
                    auth.signInWithEmailAndPassword(id, pass).addOnCompleteListener(student_login.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(student_login.this, "Logged In", Toast.LENGTH_SHORT).show();
                                FirebaseUser user = auth.getCurrentUser();
                               // checkUsers(auth.getCurrentUser().getUid());
                                Intent intent = new Intent(student_login.this, MainActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(student_login.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(student_login.this, register_student.class);
                startActivity(intent);
            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, (GoogleApiClient.OnConnectionFailedListener) this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


        signInButton = (SignInButton) findViewById(R.id.google_signIn);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent, RC_SIGN_IN);

            }
        });

    }

   /* private void checkUsers(String uid) {
        DocumentReference df = fStore.collection("Users").document(uid);
        //extract data from document

        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                if (documentSnapshot.getString("isTeacher") != null) {
                    Intent intent = new Intent(student_login.this, Teacher.class);
                    startActivity(intent);
                    finish();
                }
                if (documentSnapshot.getString("isStudent") != null) {
                    Intent intent = new Intent(student_login.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        });
    }*/

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            gotoProfile();
        } else {
            Toast.makeText(student_login.this, "Sign in cancel", Toast.LENGTH_SHORT).show();
        }
    }

    private void gotoProfile() {
        Intent intent = new Intent(student_login.this, profile.class);
        startActivity(intent);
    }


}