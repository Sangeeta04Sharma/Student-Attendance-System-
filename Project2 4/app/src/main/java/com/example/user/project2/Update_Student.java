package com.example.user.project2;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Update_Student extends AppCompatActivity {

    EditText newnumber , newbranch ,updateyop,clgstudentid;
    Button updatedata ;
    CheckBox checkbranch , checknumber , checkyop ;
    FirebaseDatabase firebaseDatabase   ;
    DatabaseReference myref ;
    String collegeid="";
    String branch="",number="",yop="";
    int count=0;
    boolean flag1,flag2,flag3;
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update__student);

        clgstudentid = (EditText) findViewById(R.id.rollnumber);
        dialog = new ProgressDialog(Update_Student.this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("Please Wait");

        newbranch = (EditText) findViewById(R.id.newbranch);
        newbranch.setEnabled(false);

        newnumber = (EditText) findViewById(R.id.newnumber);
        newnumber.setEnabled(false);

        updateyop = (EditText) findViewById(R.id.updateyop);
        updateyop.setEnabled(false);

        checkbranch = (CheckBox) findViewById(R.id.checkbranch);
        checkbranch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (((CheckBox) view).isChecked()) {
                    Toast.makeText(Update_Student.this, "check2", Toast.LENGTH_SHORT).show();
                    newbranch.setEnabled(true);
                    flag1=true;
                } else {

                    newbranch.setEnabled(false);
                    branch="";
                 flag1=false;
                   // Toast.makeText(Update_Student.this, "check4", Toast.LENGTH_SHORT).show();

                }
            }
        });

        checknumber=(CheckBox) findViewById(R.id.checknumber);
        checknumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (((CheckBox) view).isChecked()){
                    newnumber.setEnabled(true);
                    newnumber.setInputType(InputType.TYPE_CLASS_TEXT);
                    newnumber.setFocusable(true);
flag2=true;
                }
                else {
                    newnumber.setEnabled(false);
                    number="";
           flag2=false;
                }

            }
        });

        checkyop  = (CheckBox) findViewById(R.id.checkyop);
        checkyop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (((CheckBox) view).isChecked()){
                    updateyop.setEnabled(true);
                    updateyop.setInputType(InputType.TYPE_CLASS_TEXT);
                    updateyop.setFocusable(true);
                    flag3=true;
                }
                else {
                    updatedata.setEnabled(false);
                    yop="";
           flag3=false;
                }

            }
        });

        updatedata = (Button) findViewById(R.id.update);

       updatedata.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               String collegeid = clgstudentid.getText().toString().trim();
               Log.i("1234", "Coming ID...5555: "+collegeid);
               if(!newbranch.getText().toString().trim().isEmpty())
               {
                   branch=newbranch.getText().toString().trim();
               }//end of if.....
               else if(flag1)
               {
                   newbranch.setError("Invalid branch");
                   newbranch.requestFocus();
               }
               if(!updateyop.getText().toString().trim().isEmpty())
               {
                   yop=updateyop.getText().toString().trim();
               }//end of if.....
               else if(flag3)
               {
                   updateyop.setError("Invalid Yop");
                   updateyop.requestFocus();
               }

               if(!newnumber.getText().toString().trim().isEmpty())
               {
                   number=newnumber.getText().toString().trim();
               }//end of if.....
               else if(flag2)
               {
                   newnumber.setError("Invalid number");
                   newnumber.requestFocus();
               }


              // SharedPreferences sharedPreferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
              // collegeid = sharedPreferences.getString("collegeid","");

               Toast.makeText(Update_Student.this,"college id = "+collegeid,Toast.LENGTH_SHORT).show();
               if(!collegeid.isEmpty())
               UpdateData(collegeid,branch,number,yop);


           }
       });


    }

    public  void UpdateData(final String collegeid, final String Nbranch, final String Nnumber, final String Nyop){
        count=0;

        Log.e("1234", "Coming ID...333: "+collegeid);
      //  Toast.makeText(Update_Student.this,"update 1",Toast.LENGTH_SHORT).show();
/*
        final String Nbranch = newbranch.getText().toString().trim();
        final String Nnumber = newnumber.getText().toString().trim();
        final String Nyop = updateyop.getText().toString().trim();*/

      //  Toast.makeText(Update_Student.this,"update 2",Toast.LENGTH_SHORT).show();
        dialog.show();
        firebaseDatabase = FirebaseDatabase.getInstance();
        myref= firebaseDatabase.getReference("Registered Students List");

        final AddStudent pojo=new AddStudent();

        Log.i("data", "branch: "+ Nbranch);

        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Toast.makeText(Update_Student.this,"update 3",Toast.LENGTH_SHORT).show();

                 for (DataSnapshot d : dataSnapshot.getChildren()){

                    String clgid = (String) d.child("stuclgid").getValue();
                    String key=d.getKey();
                     Log.e("1234", "College id..1: "+clgid);
                     Log.e("1234", "College id..22: "+collegeid);
                    // Log.i("1234", "College id..3: "+clgid.equals(collegeid));
                     if (clgid.equals(collegeid) && count==0){
                         String num=(String)d.child("stuPhone").getValue();
                         String yop=(String)d.child("stuYop").getValue();
                         String branch=(String)d.child("stuBranch").getValue();
                         String email=(String)d.child("stuEmail").getValue();
                         String name=(String)d.child("stuName").getValue();
                         String pass=(String)d.child("stuPassword").getValue();
                         if(!Nbranch.isEmpty())
                         {
                             pojo.setStuBranch(Nbranch);

                         }
                         else
                             pojo.setStuBranch(branch);

                         if(!Nnumber.isEmpty())
                             pojo.setStuPhone(Nnumber);
                         else
                             pojo.setStuPhone(num);

                         if(!Nyop.isEmpty())
                             pojo.setStuYop(Nyop);
                         else
                             pojo.setStuYop(yop);

                         pojo.setStuEmail(email);
                         pojo.setStuPassword(pass);
                         pojo.setStuName(name);
                         pojo.setStuclgid(clgid);


                         Log.i("data", "onDataChange: "+ pojo.getStuPhone());
                         Log.i("data", "onDataChange: "+ pojo.getStuYop());
                         myref.child(key).setValue(pojo);
                         count=1;
                        dialog.dismiss();
                         Toast.makeText(Update_Student.this,"Successfully Updated",Toast.LENGTH_SHORT).show();

                         break;
                    }//end of if........
                    else {
                        Toast.makeText(Update_Student.this,"invalid",Toast.LENGTH_SHORT).show();
                    }
                 }//end of for loop......
                 startActivity(new Intent(Update_Student.this,Student.class));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



     //  String id = myref.child("Registered Students List").child().setValue(id);

    }


}
