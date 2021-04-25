package com.mrsy.help;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mrsy.rlsy.R;

public class help extends AppCompatActivity {
        public EditText mEmail;
        public EditText mSubject;
        public EditText mMessage;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_help);

            mEmail = (EditText)findViewById(R.id.mailID);
            mMessage = (EditText)findViewById(R.id.messageID);
            mSubject = (EditText)findViewById(R.id.subjectID);

            Button fab = findViewById(R.id.fab);


            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mMessage.getText().toString().isEmpty()) {
                        mMessage.setBackgroundColor(Color.CYAN);
                        Toast.makeText(help.this, "cheacked", Toast.LENGTH_SHORT).show();
                    } else {
                        sendMail();
                        // edit
                        // Text is not empty
                    }



                }
            });
        }

        private void sendMail() {
            String mail = mEmail.getText().toString().trim();
            String message = mMessage.getText().toString();
            String subject = mSubject.getText().toString().trim();

            //Send Mail
            JavaMailAPI javaMailAPI = new JavaMailAPI(this,mail,subject,message);

            javaMailAPI.execute();

        }



        }

