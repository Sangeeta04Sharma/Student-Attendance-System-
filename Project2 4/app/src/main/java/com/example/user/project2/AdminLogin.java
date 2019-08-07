package com.example.user.project2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AdminLogin extends AppCompatActivity {

    EditText clgname , clgemail ,adminpassword ;
    Button login ;
    Boolean flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        clgname = (EditText) findViewById(R.id.clgname);
        clgemail = (EditText) findViewById(R.id.clgemail);
        adminpassword = (EditText ) findViewById(R.id.password);

        login =(Button) findViewById(R.id.login);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registration();

                if (flag==true){
                    Intent s = new Intent(AdminLogin.this , Home.class);
                    startActivity(s);
                }
                else {
                    Toast.makeText(AdminLogin.this,"enter right data",Toast.LENGTH_SHORT).show();
                }



            }
        });

    }



    public  boolean  registration(){

        flag =true;

        String name  = clgname.getText().toString().trim();
        String email = clgemail.getText().toString().trim();
        String password =adminpassword.getText().toString().trim();

        if (TextUtils.isEmpty(name)){
            Toast.makeText(this,"Enter name",Toast.LENGTH_SHORT).show();
            flag =false;
        }
        if (TextUtils.isEmpty(email)){
            Toast.makeText(this,"Enter email",Toast.LENGTH_SHORT).show();
            flag = false;

        }
        if (TextUtils.isEmpty(password) || !(password.equals("admin"))){
            Toast.makeText(this,"Enter password",Toast.LENGTH_SHORT).show();
            flag =false;

        }

        return flag;

    }






}
