package com.smart.teach;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class register_student extends AppCompatActivity {

    EditText email, pass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_student);

        FirebaseAuth auth = FirebaseAuth.getInstance();

        Button register = (Button) findViewById(R.id.register_btn);
        email = findViewById(R.id.email);
        pass = findViewById(R.id.pass);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail = email.getText().toString();
                String pswd = pass.getText().toString();

                if (TextUtils.isEmpty(mail) || TextUtils.isEmpty(pswd)) {
                    Toast.makeText(register_student.this, "Empty input Field", Toast.LENGTH_SHORT).show();
                } else {
                    auth.createUserWithEmailAndPassword(mail, pswd).addOnCompleteListener(register_student.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(register_student.this, "User created", Toast.LENGTH_SHORT).show();
                                FirebaseUser user = auth.getCurrentUser();
                                Intent intent = new Intent(register_student.this, student_login.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(register_student.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
        });

    }
}