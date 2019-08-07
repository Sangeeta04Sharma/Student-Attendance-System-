package com.example.user.project2;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Scanner extends AppCompatActivity {

    Button scanbtn,dashboardbtn;
    AddStudent student;
    DatabaseReference reference,referencestore,studentattendance;
    SharedPreferences sp;
    String email="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);
        reference= FirebaseDatabase.getInstance().getReference("Registered Students List");
        scanbtn = (Button) findViewById(R.id.scanbtn);
        dashboardbtn=findViewById(R.id.dashboardbtn);
        student=new AddStudent();
        sp=getSharedPreferences("studentdetails",0);
        email=sp.getString("email","");
        getData();

        final Activity activity = this;

        scanbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                 Toast.makeText(Scanner.this,"scan1",Toast.LENGTH_SHORT).show();
                IntentIntegrator integrator = new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                integrator.setPrompt(" Scan ");
                integrator.setCameraId(0);
                integrator.setBarcodeImageEnabled(false);
                integrator.setBeepEnabled(false);
                integrator.initiateScan();

                // Toast.makeText(Scanner.this,"scan2",Toast.LENGTH_SHORT).show();

            }
        });

        dashboardbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(Scanner.this,Dashboard.class);
                Log.i("1234", "Branch:Scanner= "+student.getStuBranch());
                i.putExtra("branch",student.getStuBranch());
                startActivity(i);
            }//end of ().....
        });

    }//end of ().....

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result= IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
            if (result != null){
                if (result.getContents()==null){
                    // Toast.makeText(Scanner.this,"scan3",Toast.LENGTH_SHORT).show();
                    Toast.makeText(this,"You cancelled the scanning",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(Scanner.this,"data",Toast.LENGTH_SHORT).show();
                    Toast.makeText(Scanner.this,"temp"+result.getContents(),Toast.LENGTH_SHORT).show();
                    addStudents(result.getContents()+"");

                }//end of else.....
            }
       else {
            super.onActivityResult(requestCode, resultCode, data);
        }//end of else.....
    }//end of onActivityResult()....
    public void getData()
    {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot d:dataSnapshot.getChildren())
                {
                    student=d.getValue(AddStudent.class);
                    if(student.getStuEmail().equals(email))
                    break;
                }//end of for loop....
            }//end of onDataChange()...

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }//end of ()....

    public void addStudents(String content)
    {
        //currentTime+","+years+","+semester+","+branchs
        if(!email.isEmpty()){
            String[] temp=content.split(",");
            long date=Long.parseLong(temp[0]);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
            Date resultdate = new Date(date);
            AddAttendance attendance=new AddAttendance();

            attendance.setStutime(temp[0]+"");
            attendance.setStubranch(student.getStuBranch());
            attendance.setStuclgid(student.getStuclgid());
            attendance.setStuyop(student.getStuYop());
            attendance.setStuphone(student.getStuPhone());
            attendance.setStuname(student.getStuName());
            attendance.setStuemail(student.getStuEmail());
            Log.i("1234", "Samay: "+attendance.getStutime());
            studentattendance=FirebaseDatabase.getInstance().getReference("Student total attendance").child(sdf.format(resultdate)).child(temp[3]);
            studentattendance.push().setValue(attendance);
            Toast.makeText(Scanner.this,"Success",Toast.LENGTH_LONG).show();

        }//end of if...
    }//end of ().....
}//end of class......
