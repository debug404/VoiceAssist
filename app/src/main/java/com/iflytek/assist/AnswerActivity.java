package com.iflytek.assist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.iflytek.voicedemo.R;

public class AnswerActivity extends AppCompatActivity {

    private ImageView leftimgBtn;
    private  ImageView rightimgBtn;

    private TextView textView;
    private EditText editText;

    private ImageView voiceimagBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);

        leftimgBtn = (ImageView) findViewById(R.id.leftimg);
        rightimgBtn = (ImageView) findViewById(R.id.rightimg);
        textView = (TextView) findViewById(R.id.title);

        editText = (EditText) findViewById(R.id.editview);

        voiceimagBtn = (ImageView) findViewById(R.id.voice_img_btn);


        leftimgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



            }
        });


    }
}
