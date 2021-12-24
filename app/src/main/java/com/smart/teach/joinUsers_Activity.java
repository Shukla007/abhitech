package com.smart.teach;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

public class joinUsers_Activity extends AppCompatActivity {
    private GoogleApiClient googleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_users);


        Button student = (Button) findViewById(R.id.login);
        Button teacher = (Button) findViewById(R.id.register);

        // Login Button
        student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(joinUsers_Activity.this, "Student login", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(joinUsers_Activity.this, student_login.class);
                startActivity(intent);
            }
        });

        // Register Button/
        teacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(joinUsers_Activity.this, "Teacher login", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(joinUsers_Activity.this, register_student.class);
                startActivity(intent);
            }
        });
    }
}