package com.example.user.project2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class generated_qr_code extends AppCompatActivity {

    TextView details;
    ImageView image;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generated_qr_code);
        dialog = new ProgressDialog(generated_qr_code.this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("Loading...");
        dialog.show();
        Intent i=getIntent();
        String[] temp=i.getStringExtra("qrdetails").split("\n");
        Log.i("1234", "ImageUrl: "+temp[3]);
        details=findViewById(R.id.details);
        image=findViewById(R.id.qr_code);
        Picasso.get().load(temp[3]).into(image);
        details.setText("Branch="+temp[0]+"\nSemester="+temp[1]);
        dialog.dismiss();
    }//end of ()......
}//end of class
