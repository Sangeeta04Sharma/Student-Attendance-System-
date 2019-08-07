package com.example.user.project2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Loginpage extends AppCompatActivity {

    Button stulogin ,techlogin, adminlogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginpage);

        techlogin = (Button) findViewById(R.id.teacherlogin);
        stulogin = (Button) findViewById(R.id.studentlogin);
        adminlogin=(Button) findViewById(R.id.adminlogin);

        techlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Loginpage.this,TeacherLogin.class));
            }
        });

        stulogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Loginpage.this,StudentLogin.class));
            }
        });

        adminlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Loginpage.this,AdminLogin.class));
            }
        });



    }
}
