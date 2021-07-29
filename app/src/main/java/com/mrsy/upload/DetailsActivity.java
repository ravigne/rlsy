package com.mrsy.upload;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.mrsy.rlsy.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DetailsActivity extends AppCompatActivity {

    TextView dateDetailTextView;
    ImageView teacherDetailImageView;

    private void initializeWidgets(){
        dateDetailTextView= findViewById(R.id.dateDetailTextView);
        teacherDetailImageView=findViewById(R.id.teacherDetailImageView);
    }
    private String getDateToday(){
        @SuppressLint("SimpleDateFormat") DateFormat dateFormat=new SimpleDateFormat("yyyy/MM/dd");
        Date date=new Date();
        return dateFormat.format(date);
    }
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        initializeWidgets();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Intent i=this.getIntent();
        String imageURL=i.getExtras().getString("IMAGE_KEY");

        dateDetailTextView.setText("DATE: "+getDateToday());

        Glide.with(this).load(imageURL)
                .placeholder(R.drawable.loading)
                .into(teacherDetailImageView);


    }

}
