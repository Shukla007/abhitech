package com.smart.teach;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class forgot extends AppCompatActivity {

    Button reset;
    TextView email;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);

        auth = FirebaseAuth.getInstance();
        email = findViewById(R.id.email_id);
        reset = findViewById(R.id.reset);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail = email.getText().toString();
                if (TextUtils.isEmpty(mail)) {
                    Toast.makeText(forgot.this, "Email field is Empty.", Toast.LENGTH_SHORT).show();
                } else {
                    auth.sendPasswordResetEmail(mail).addOnCompleteListener(forgot.this, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(forgot.this, "Emal reset link send to your email address check your inbox.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(forgot.this, student_login.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(forgot.this, "Something went wrong.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
};