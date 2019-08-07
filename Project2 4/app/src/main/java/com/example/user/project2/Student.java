package com.example.user.project2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Student extends AppCompatActivity {
   Button sturegistration ,stuupdate ,deletestu ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        sturegistration = (Button) findViewById(R.id.sturegistration);
        stuupdate = (Button) findViewById(R.id.stuupdate);
        deletestu = (Button) findViewById(R.id.deletestu);

        sturegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Student.this,Student_Registration.class));
            }
        });


        stuupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Student.this,Update_Student.class));
            }
        });

        deletestu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Student.this,Delete_student.class));
            }
        });
    }

}
