package com.example.user.project2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

public class Dashboard extends AppCompatActivity {

    ListView listView;
    Button total;
    DatabaseReference reference,reference1;
    SharedPreferences sp;
    String email,branch;
    List list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        sp=getSharedPreferences("studentdetails",0);
        email=sp.getString("email","");
        list=new ArrayList();
        total=findViewById(R.id.percentage);
        listView=findViewById(R.id.attendancelist);
        Intent i=getIntent();
        branch=i.getStringExtra("branch");
        Log.i("1234", "Branch: "+branch);
        long date=System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        Date resultdate = new Date(date);
        Log.i("1234", "Date: "+sdf.format(resultdate));
        reference= FirebaseDatabase.getInstance().getReference("Student total attendance").child(sdf.format(resultdate)).child(branch);
        reference1= FirebaseDatabase.getInstance().getReference("Total attendance").child(sdf.format(resultdate)).child(branch);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot d:dataSnapshot.getChildren())
                {
                    AddAttendance attendance=d.getValue(AddAttendance.class);
                    if(attendance.getStuemail().toUpperCase().equals(email))
                    {
                        long t=Long.parseLong(attendance.getStutime());
                        SimpleDateFormat sf = new SimpleDateFormat("MMM dd,yyyy");
                        Date resultdate = new Date(t);
                        list.add(sf.format(resultdate));
                    }//end of if.......

                }//end of for loop.....
                reference1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        int a= (int) dataSnapshot.getChildrenCount();
                        Log.i("1234", "Count: "+a);
                        Log.i("1234", "list: "+list);
                        ArrayAdapter arrayAdapter=new ArrayAdapter(Dashboard.this,android.R.layout.simple_expandable_list_item_1,list);
                        listView.setAdapter(arrayAdapter);
                        double t=(list.size()*100)/a;
                        total.setText("Total %="+t+"%");
                    }//end of ().....
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }//end of ()......
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




/*
        Log.i("1234", "list: "+list);
        ArrayAdapter arrayAdapter=new ArrayAdapter(Dashboard.this,android.R.layout.simple_expandable_list_item_1,list);
        listView.setAdapter(arrayAdapter);
*/



    }//end of ()....



}//end of class.......
