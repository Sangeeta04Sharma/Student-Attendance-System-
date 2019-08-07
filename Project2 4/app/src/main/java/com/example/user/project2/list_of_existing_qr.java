package com.example.user.project2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class list_of_existing_qr extends AppCompatActivity {
ListView listOfExistingQr;
List arr,complete;
DatabaseReference reference;
long currentTime;
String email="";
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_existing_qr);
        listOfExistingQr=findViewById(R.id.list_of_qr);
        arr=new ArrayList();
        complete=new ArrayList();
        currentTime = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy");
        Date resultdate = new Date(currentTime);
        SharedPreferences sp=getSharedPreferences("teacherdetails",0);
        email=sp.getString("email","");
        dialog = new ProgressDialog(list_of_existing_qr.this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("Generating...");
        dialog.show();
        reference= FirebaseDatabase.getInstance().getReference("qrcode").child(sdf.format(resultdate));
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot d:dataSnapshot.getChildren())
                {
                    StoreQrAtDatabase t=d.getValue(StoreQrAtDatabase.class);
                    long maxtime=Long.parseLong(t.getMaxtime());
                    Log.i("1234", "CurrentTime: "+currentTime);
                    Log.i("1234", "Coming time: "+t.maxtime);
                    Log.i("1234", "Compare: "+( maxtime>=currentTime));

                    if(t.getEmail().equals(email) && maxtime>=currentTime)
                    {
                        String temp="Branch= "+t.getBranch()
                                +"\n"+"Year= "+t.getYear()+"\n"+"Semester="+t.getSemester();

                        arr.add(temp);
                        String temp1=t.getBranch()+"\n"+t.getSemester()+"\n"+t.getEmail()
                                +"\n"+t.getImageurl()+"\n"+t.getCurrenttime()+"\n"+t.getYear()
                                +"\n"+t.getMaxtime();
                        complete.add(temp1);
                    }//end of if....
                }//end of for loop.....

                if(arr.size()==0)
                    arr.add("No Qr Code generated");

                ArrayAdapter adapter=new ArrayAdapter(list_of_existing_qr.this,android.R.layout.simple_expandable_list_item_1,arr);
                listOfExistingQr.setAdapter(adapter);
                dialog.dismiss();
            }//end of ()......
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }//end of ()......
        });

        listOfExistingQr.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(list_of_existing_qr.this,generated_qr_code.class);
                i.putExtra("qrdetails", complete.get(position)+"");
                startActivity(i);


            }//end of ().....
        });

    }//end of ()....




}//end of class......
