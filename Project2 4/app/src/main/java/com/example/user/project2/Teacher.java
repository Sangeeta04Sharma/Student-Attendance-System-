package com.example.user.project2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Teacher extends AppCompatActivity {

    Button registration , update ,delete ;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);

        registration = (Button) findViewById(R.id.registration);
        update = (Button) findViewById(R.id.update);
        delete = (Button) findViewById(R.id.deletetech);


        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent s = new Intent(Teacher.this ,Teacher_Registration.class);
                startActivity(s);

            }
        });


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent p = new Intent(Teacher.this,Update_Teacher.class);
                startActivity(p);
            }
        });



        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent q = new Intent(Teacher.this,Delete_Teacher.class);
                startActivity(q);
            }
        });

    }
}
