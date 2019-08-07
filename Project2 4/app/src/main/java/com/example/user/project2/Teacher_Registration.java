package com.example.user.project2;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Teacher_Registration extends AppCompatActivity {

    EditText inputname, inputemail, inputpassword, inputphone, inputdept, inputtype, inputclgid;
    Button registration;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher__registration);

        auth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference("Registered Teacher List");
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        inputname = (EditText)findViewById(R.id.name);
        inputclgid = (EditText) findViewById(R.id.clgid);
        inputemail = (EditText)findViewById(R.id.email);
        inputphone = (EditText)findViewById(R.id.phone);
        inputpassword = (EditText)findViewById(R.id.password);
        inputdept = (EditText)findViewById(R.id.dept);
        inputtype = (EditText) findViewById(R.id.type);

        registration =(Button) findViewById(R.id.registration);


        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AddTeacher();

            }
        });

        }




    public  void AddTeacher() {
        String name = inputname.getText().toString().trim();
        String cldid = inputclgid.getText().toString().trim();
        String phone = inputphone.getText().toString().trim();
        String email = inputemail.getText().toString().trim();
        String password = inputpassword.getText().toString().trim();
        String type = inputtype.getText().toString().trim();
        String dept = inputdept.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            Toast.makeText(getApplicationContext(), "Enter name", Toast.LENGTH_SHORT).show();
            return;
        }
      else   if (TextUtils.isEmpty(cldid)) {
            Toast.makeText(getApplicationContext(), "Enter college id", Toast.LENGTH_SHORT).show();
            return;
        }

       else if (TextUtils.isEmpty(phone)) {
            Toast.makeText(getApplicationContext(), "Enter phone number", Toast.LENGTH_SHORT).show();
            return;
        }

       else if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Enter email address", Toast.LENGTH_SHORT).show();
            return;
        }

       else if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Enter password", Toast.LENGTH_SHORT).show();
            return;
        }
       else if (password.length() < 6) {
            Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
            return;
        }

       else if (TextUtils.isEmpty(type)) {
            Toast.makeText(getApplicationContext(), "Enter teacher type", Toast.LENGTH_SHORT).show();
            return;
        }

        else if (TextUtils.isEmpty(dept)) {
            Toast.makeText(getApplicationContext(), "Enter teacher department", Toast.LENGTH_SHORT).show();
            return;
        }

        else {

            String id = ref.push().getKey();
            AddTeacher addTeacher = new AddTeacher(id,name,cldid,phone,email,password,type,dept);
            ref.child(id).child("id").setValue(id);
            ref.child(id).child("name").setValue(name);
            ref.child(id).child("college id").setValue(cldid);
            ref.child(id).child("phone number").setValue(phone);
            ref.child(id).child("email address").setValue(email);
            ref.child(id).child("password").setValue(password);
            ref.child(id).child("teacher type").setValue(type);
            ref.child(id).child("teacher department").setValue(dept);
            ClearTxt();

        }


        progressBar.setVisibility(View.VISIBLE);

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(Teacher_Registration.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Toast.makeText(Teacher_Registration.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(Teacher_Registration.this, "Authentication failed." + task.getException(),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            startActivity(new Intent(Teacher_Registration.this, Teacher.class));
                        }

                    }

                });


    }

    public  void  ClearTxt(){
        inputname.setText("");
        inputclgid.setText("");
        inputphone.setText("");
        inputemail.setText("");
        inputpassword.setText("");
        inputtype.setText("");
        inputdept.setText("");
    }



    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }
}
