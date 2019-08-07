package com.example.user.project2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class StudentLogin extends AppCompatActivity {
    EditText studentemail ,studentpass ;
    Button studentlogin;
    TextView forgotstudent ;
    FirebaseAuth stuAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);

        stuAuth = FirebaseAuth.getInstance();

        studentemail = (EditText) findViewById(R.id.studentemail);
        studentpass = (EditText) findViewById(R.id.studentpass);
        studentlogin = (Button) findViewById(R.id.stuloginbtn);
        forgotstudent = (TextView) findViewById(R.id.forgotstu);

        studentlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = studentemail.getText().toString().trim();
                String password = studentpass.getText().toString().trim();
                Toast.makeText(getApplicationContext(),"coming",Toast.LENGTH_SHORT).show();
                validation(email,password);

            }
        });


    }

    public void  validation(final String email , final String password){
        if (TextUtils.isEmpty(email)){
            Toast.makeText(getApplicationContext(),"Enter email",Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)){
            Toast.makeText(getApplicationContext(),"Enter password",Toast.LENGTH_SHORT).show();
            return;
        }

        stuAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(StudentLogin.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()){
                    if (password.length()<6){
                        studentpass.setError("password has at least 6 digit");
                    }
                }//end of if......
                else {

                    SharedPreferences sp=getSharedPreferences("studentdetails",0);
                    SharedPreferences.Editor editor=sp.edit();
                    editor.putString("email",email);
                    editor.commit();
                    startActivity(new Intent(StudentLogin.this,Scanner.class));
                }//end of else.....

            }
        });
    }//end of validation().........
}//end of class.....
