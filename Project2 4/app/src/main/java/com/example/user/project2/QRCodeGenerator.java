package com.example.user.project2;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.ByteArrayOutputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static com.example.user.project2.TempQrStorage.QRcodeWidth;

public class QRCodeGenerator extends AppCompatActivity {
    Spinner year,sem,branch ;
    Button genbtn;
    ImageView img;
    String text2Qr;
    List yr,sm,br;
    private Bitmap bitmap;
    ArrayAdapter yearadapter,semadapter,branchadapter;
    StorageReference mStorage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_generator);
        year=findViewById(R.id.spinner_year_generate_qr_code);
        sem=findViewById(R.id.spinner_sem_generate_qr_code);
        branch=findViewById(R.id.spinner_branch_generate_qr_code);


        genbtn = (Button) findViewById(R.id.genbtn);
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

        yearadapter = new ArrayAdapter(QRCodeGenerator.this, R.layout.support_simple_spinner_dropdown_item, yr);
        year.setAdapter(yearadapter);

        semadapter = new ArrayAdapter(QRCodeGenerator.this, R.layout.support_simple_spinner_dropdown_item, sm);
        sem.setAdapter(semadapter);

        branchadapter = new ArrayAdapter(QRCodeGenerator.this, R.layout.support_simple_spinner_dropdown_item, br);
        branch.setAdapter(branchadapter);



        genbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                long yourmiliseconds =System.currentTimeMillis();
                text2Qr=  yourmiliseconds+"";
                saveUserInformation();
                mStorage = FirebaseStorage.getInstance().getReference("abc/"+yourmiliseconds);
            }
        });
    }//end of ()......


    Bitmap TextToImageEncode(String Value) throws WriterException {
        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(
                    Value,
                    BarcodeFormat.DATA_MATRIX.QR_CODE,
                    QRcodeWidth, QRcodeWidth, null
            );

        } catch (IllegalArgumentException Illegalargumentexception) {
            Toast.makeText(QRCodeGenerator.this,""+Illegalargumentexception.getMessage(),Toast.LENGTH_SHORT);
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
            Toast.makeText(QRCodeGenerator.this, "Saved...1111", Toast.LENGTH_LONG).show();
            bitmap = TextToImageEncode("lol");
            Toast.makeText(QRCodeGenerator.this, "Saved...2222", Toast.LENGTH_LONG).show();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] data = baos.toByteArray();

            UploadTask uploadTask = mStorage.putBytes(data);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Toast.makeText(QRCodeGenerator.this, "3333333..."+exception.getMessage(), Toast.LENGTH_LONG).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(QRCodeGenerator.this, "Succesfully Saved...", Toast.LENGTH_LONG).show();
                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    taskSnapshot.getMetadata();

                }
            });

            //imageView.setImageBitmap(bitmap);

        } catch (WriterException e) {
            Toast.makeText(QRCodeGenerator.this, "Saved...4"+e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }//end of ()....


}//end of class.....
