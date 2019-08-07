package com.example.user.project2;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import static android.R.attr.bitmap;
import static android.R.attr.data;

public class TempQrStorage extends AppCompatActivity {
    //firebase auth object

    private StorageReference mStorage;
    Spinner year,sem,branch ;
    List yr,sm,br;
    String semester;
    String years;
    String branchs;
    ArrayAdapter yearadapter,semadapter,branchadapter;
    private DatabaseReference mDatabse;
    long currentTime,halfAnHourLater;
    //our new views
    private Button buttonSave,buttonLogout,buttonalreadygenerate;
    private ImageView img;
    private ProgressDialog progressDialog;
    public final static int QRcodeWidth = 500;
    private Bitmap bitmap;
    SharedPreferences sp;
    SharedPreferences.Editor edit;
    String email;
    ProgressDialog dialog;
    DatabaseReference reference,reference1;

//<to-do> save Qr with complete info....
// as and when Qr get saved at storage ....save current date....cd+1:10....
// and teacher email....@database
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp_qr_storage);
        progressDialog = new ProgressDialog(this);
        year=findViewById(R.id.spinner_year_generate_qr_code);
        sem=findViewById(R.id.spinner_sem_generate_qr_code);
        branch=findViewById(R.id.spinner_branch_generate_qr_code);
         sp=getSharedPreferences("teacherdetails",0);
         email=sp.getString("email","");
         dialog = new ProgressDialog(TempQrStorage.this);
         dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
         dialog.setMessage("Generating...");

        //getting the views from xml resource
        buttonSave = (Button) findViewById(R.id.button3);
        buttonLogout=findViewById(R.id.logout);
        buttonalreadygenerate=findViewById(R.id.oldqr);

        img = (ImageView) findViewById(R.id.img);

        yr=new ArrayList();
        sm=new ArrayList();
        br=new ArrayList();
        yr.add("Year");
        yr.add("1");
        yr.add("2");
        yr.add("3");
        yr.add("4");

        sm.add("Semester");
        sm.add("1");
        sm.add("2");
        sm.add("3");
        sm.add("4");
        sm.add("5");
        sm.add("6");
        sm.add("7");
        sm.add("8");

        br.add("Branch");
        br.add("CSE");
        br.add("IT");
        br.add("CE");
        br.add("EE");
        br.add("EIC");
        br.add("ME");
        br.add("EC");



        yearadapter = new ArrayAdapter(TempQrStorage.this, R.layout.support_simple_spinner_dropdown_item, yr);
        year.setAdapter(yearadapter);

        semadapter = new ArrayAdapter(TempQrStorage.this, R.layout.support_simple_spinner_dropdown_item, sm);
        sem.setAdapter(semadapter);

        branchadapter = new ArrayAdapter(TempQrStorage.this, R.layout.support_simple_spinner_dropdown_item, br);
        branch.setAdapter(branchadapter);


        if(!email.isEmpty()) {
            buttonSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   // dialog.show();
                 semester=sem.getSelectedItem().toString();
                 years=year.getSelectedItem().toString();
                 branchs=branch.getSelectedItem().toString();
                    if(branchs.equals("Branch"))
                    {
                        Toast.makeText(TempQrStorage.this,"Select Branch",Toast.LENGTH_LONG).show();
                     //   dialog.dismiss();
                    }//end of if of branch........
                    else
                    {
                        if(semester.equals("Semester"))
                        {
                            Toast.makeText(TempQrStorage.this,"Select Semester",Toast.LENGTH_LONG).show();
                       //     dialog.dismiss();
                        }//end of if......
                        else
                        {
                            if(years.equals("Year"))
                            {
                                Toast.makeText(TempQrStorage.this,"Select Year",Toast.LENGTH_LONG).show();
                         //       dialog.dismiss();
                            }//end of year if......
                            else
                            {
                                dialog.show();
                                currentTime = System.currentTimeMillis();

                                String temp=currentTime+","+years+","+semester+","+branchs;
                                mStorage = FirebaseStorage.getInstance().getReference("qr_code/"+temp);

                                halfAnHourLater = currentTime + 4200000;
                                Toast.makeText(TempQrStorage.this,"Success",Toast.LENGTH_LONG);
                                saveUserInformation();

                            }//end of else.......
                        }//end of else......
                    }//end of if.....

                }//end of onClick()......
            });

            buttonLogout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    edit=sp.edit();
                    edit.clear();
                    edit.commit();
                    startActivity(new Intent(TempQrStorage.this,WelcomeScreen.class));
                }//end of onClick()......
            });

            buttonalreadygenerate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(TempQrStorage.this,list_of_existing_qr.class));
                }//end of ().....
            });


        }//end of if.....
    }//end of OnCreate()....


    Bitmap TextToImageEncode(String Value) throws WriterException {
        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(
                    Value,
                    BarcodeFormat.DATA_MATRIX.QR_CODE,
                    QRcodeWidth, QRcodeWidth, null
            );

        } catch (IllegalArgumentException Illegalargumentexception) {
            Toast.makeText(TempQrStorage.this,""+Illegalargumentexception.getMessage(),Toast.LENGTH_SHORT);
            return null;
        }
        int bitMatrixWidth = bitMatrix.getWidth();

        int bitMatrixHeight = bitMatrix.getHeight();

        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];

        for (int y = 0; y < bitMatrixHeight; y++) {
            int offset = y * bitMatrixWidth;

            for (int x = 0; x < bitMatrixWidth; x++) {

                pixels[offset + x] = bitMatrix.get(x, y) ?
                        getResources().getColor(R.color.colorAccent) : getResources().getColor(R.color.white);
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);

        bitmap.setPixels(pixels, 0, 500, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
    }//end of ().....


    private void saveUserInformation() {

        try {

            Toast.makeText(TempQrStorage.this, "Saved...1111", Toast.LENGTH_LONG).show();
            bitmap = TextToImageEncode(currentTime+","+years+","+semester+","+branchs);
            Toast.makeText(TempQrStorage.this, "Saved...2222", Toast.LENGTH_LONG).show();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] data = baos.toByteArray();

            UploadTask uploadTask = mStorage.putBytes(data);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Toast.makeText(TempQrStorage.this, "3333333..."+exception.getMessage(), Toast.LENGTH_LONG).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(TempQrStorage.this, "Succesfully Saved...", Toast.LENGTH_LONG).show();
                    Uri downloadUrl = taskSnapshot.getDownloadUrl();

                    SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy");
                    Date resultdate = new Date(currentTime);
                    reference=FirebaseDatabase.getInstance().getReference("qrcode").child(sdf.format(resultdate));
                    StoreQrAtDatabase database=new StoreQrAtDatabase();
                    database.setYear(years);
                    database.setBranch(branchs);
                    database.setSemester(semester);
                    database.setCurrenttime(currentTime+"");
                    database.setMaxtime(halfAnHourLater+"");
                    Log.i("1234", "Current: "+currentTime);
                    Log.i("1234", "Max: "+halfAnHourLater);
                    Log.i("1234", "compare: "+(currentTime<halfAnHourLater));

                    SimpleDateFormat sf = new SimpleDateFormat("MMM dd,yyyy HH:mm:ss");
                    Date resltdate = new Date(currentTime);
                    Date s=new Date(halfAnHourLater);
                    Log.i("1234", "Current: "+ sf.format(resltdate));
                    Log.i("1234", "Max: "+sf.format(s));
                    database.setEmail(email);
                    database.setImageurl(downloadUrl+"");
                    reference.push().setValue(database);

                    SimpleDateFormat sd = new SimpleDateFormat("yyyy");
                    Date resultdat = new Date(currentTime);

                    reference1=FirebaseDatabase.getInstance().getReference("Total attendance").child(sd.format(resultdat)).child(branchs);
                    reference1.push().setValue(database);
                    taskSnapshot.getMetadata();
                    dialog.dismiss();

                }
            });

            img.setImageBitmap(bitmap);

        } catch (WriterException e) {
            Toast.makeText(TempQrStorage.this, "Saved...4"+e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }//end of ()....

}//end of class...