package com.smart.teach;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class joinUsers_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_users);


        Button student =(Button) findViewById(R.id.student);
        Button teacher =(Button) findViewById(R.id.teacher);

        //Student Login Button
        student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(joinUsers_Activity.this, "Student login", Toast.LENGTH_SHORT).show();
            }
        });

        //Teacher login Button/
        teacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(joinUsers_Activity.this, "Teacher login", Toast.LENGTH_SHORT).show();
            }
        });
    }
}