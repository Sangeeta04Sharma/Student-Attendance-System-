package com.example.user.project2;

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

public class TeacherLogin extends AppCompatActivity {
    EditText techemail, techpass ;
    Button techlogin;
    TextView forgotpass ;
    FirebaseAuth fbauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_login);

        fbauth = FirebaseAuth.getInstance();

        SharedPreferences sp=getSharedPreferences("teacherdetails",0);
        if(!sp.getString("email","").isEmpty())
        {
            startActivity(new Intent(TeacherLogin.this,TempQrStorage.class));
        }//end of if.....




        techemail =(EditText) findViewById(R.id.teacheremail);
        techpass = (EditText) findViewById(R.id.techpass);
        techlogin = (Button) findViewById(R.id.techlogin);
        forgotpass = (TextView) findViewById(R.id.forgotpass);


        techlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = techemail.getText().toString().trim().toUpperCase();
                String password = techpass.getText().toString().trim().toUpperCase();
                validation(email,password);
            }
        });

    }//end of ()......

    public void validation(String email , final String password){

        if (TextUtils.isEmpty(email)){
            Toast.makeText(getApplicationContext(),"Enter email Address",Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)){
            Toast.makeText(getApplicationContext(),"Enter password",Toast.LENGTH_SHORT).show();
            return;
        }

        fbauth.signInWithEmailAndPassword(email, password).addOnCompleteListener(TeacherLogin.this,new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()){

                    if (password.length()<6){
                        techpass.setError("password has at least 6 digit");
                    }//end of if...

                }//end of if......
                else {

                    SharedPreferences sp=getSharedPreferences("teacherdetails",0);
                    SharedPreferences.Editor editor=sp.edit();
                    editor.putString("email",fbauth.getCurrentUser().getEmail());
                    editor.commit();
                    finish();
                    startActivity(new Intent(TeacherLogin.this,TempQrStorage.class));
                }//end of else......

            }
        });

    }//end of ()...
}//end of class.....
