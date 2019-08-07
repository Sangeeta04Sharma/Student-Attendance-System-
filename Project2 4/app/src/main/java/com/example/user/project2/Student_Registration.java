package com.example.user.project2;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Student_Registration extends AppCompatActivity {
    EditText sname, sclgid, sphone, semail, spassword , sbranch , syop;
    Button register;
    private FirebaseAuth firebaseAuth;
    DatabaseReference reference;
    ProgressBar progressBar1 ;
    boolean a;
    int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student__registration);

          Toast.makeText(Student_Registration.this,"right1",Toast.LENGTH_SHORT).show();

        firebaseAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("Registered Students List");
        progressBar1 = (ProgressBar) findViewById(R.id.progressBar1);

        Toast.makeText(this,"right2",Toast.LENGTH_SHORT).show();


        sname = (EditText)findViewById(R.id.stuname);
        sclgid = (EditText) findViewById(R.id.stuclgid);
        semail = (EditText)findViewById(R.id.stuemail);
        sphone = (EditText)findViewById(R.id.stuphone);
        spassword = (EditText)findViewById(R.id.stupassword);
        sbranch = (EditText)findViewById(R.id.stubranch);
        syop = (EditText) findViewById(R.id.stuyop);

        register = (Button) findViewById(R.id.sturegistration);

        Toast.makeText(this,"right3",Toast.LENGTH_SHORT).show();


        Toast.makeText(this,"right4",Toast.LENGTH_SHORT).show();

       register.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               Toast.makeText(Student_Registration.this,"right5",Toast.LENGTH_SHORT).show();
               AddStudent();
           }
       });

    }

    public  void AddStudent() {
        final String s_name = sname.getText().toString().trim();
        final String s_cldid = sclgid.getText().toString().trim();
        final String s_phone = sphone.getText().toString().trim();
        final String s_email = semail.getText().toString().trim();
        final String s_password = spassword.getText().toString().trim();
        final String s_branch = sbranch.getText().toString().trim();
        final String s_yop = syop.getText().toString().trim();

        if (TextUtils.isEmpty(s_name)) {
            Toast.makeText(getApplicationContext(), "Enter name", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(s_cldid)) {
            Toast.makeText(getApplicationContext(), "Enter college id", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(s_phone)) {
            Toast.makeText(getApplicationContext(), "Enter phone number", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(s_email)) {
            Toast.makeText(getApplicationContext(), "Enter email address", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(s_password)) {
            Toast.makeText(getApplicationContext(), "Enter password", Toast.LENGTH_SHORT).show();
            return;
        } else if (s_password.length() < 6) {
            Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(s_phone)) {
            Toast.makeText(getApplicationContext(), "Enter branch", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(s_yop)) {
            Toast.makeText(getApplicationContext(), "Enter year of passing", Toast.LENGTH_SHORT).show();
            return;
        }

        else {

            a = false;
             count=0;
            Toast.makeText(Student_Registration.this,"right6",Toast.LENGTH_SHORT).show();

reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Toast.makeText(Student_Registration.this,"right8",Toast.LENGTH_SHORT).show();
                    for(DataSnapshot d:dataSnapshot.getChildren())
                    {
                        String temp=d.getKey();
                        Log.i("123456", "Key: "+temp);
                        Log.i("123456", "Clgid: "+s_cldid);

                        if(temp.equals(s_cldid))
                            a=true;

                    }//end of for loop.....
                    Toast.makeText(Student_Registration.this,"a"+a,Toast.LENGTH_SHORT).show();
                    if(a && count==0)
                    {
                        Toast.makeText(Student_Registration.this, "Student Already Exists", Toast.LENGTH_SHORT).show();
                    }//end of if.....
                    else if(!a) {
                        Toast.makeText(Student_Registration.this,"right7",Toast.LENGTH_SHORT).show();
                        AddStudent addStudent = new AddStudent();
                        addStudent.setStuclgid(s_cldid);
                        addStudent.setStuName(s_name);
                        addStudent.setStuPassword(s_password);
                        addStudent.setStuPhone(s_phone);
                        addStudent.setStuEmail(s_email);
                        addStudent.setStuBranch(s_branch);
                        addStudent.setStuYop(s_yop);
                        count=1;
                        reference.child(s_cldid).setValue(addStudent);
//                Toast.makeText(this, "Student Already Exists", Toast.LENGTH_SHORT).show();
                        progressBar1.setVisibility(View.VISIBLE);

                        firebaseAuth.createUserWithEmailAndPassword(s_email, s_password)
                                .addOnCompleteListener(Student_Registration.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        Toast.makeText(Student_Registration.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                        progressBar1.setVisibility(View.GONE);
                                        if (!task.isSuccessful()) {
                                            Toast.makeText(Student_Registration.this, "Authentication failed." + task.getException(),
                                                    Toast.LENGTH_SHORT).show();
                                        } else {
                                            startActivity(new Intent(Student_Registration.this, Home.class));
                                        }

                                    }

                                });
                        ClearTxt();
                    }//end of else........



                }//ennd of ()....

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });




        }//end of else..........




    }//end of ().....

    public  void  ClearTxt(){
        sname.setText("");
        sclgid.setText("");
        sphone.setText("");
        semail.setText("");
        spassword.setText("");
        sbranch.setText("");
        syop.setText("");
    }



    @Override
    protected void onResume() {
        super.onResume();
        progressBar1.setVisibility(View.GONE);
    }//end of ()....



}//end of class.......
