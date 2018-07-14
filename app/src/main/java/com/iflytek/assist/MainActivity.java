package com.iflytek.assist;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.iflytek.assist.Model.ChatItemModel;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.speech.setting.IatSettings;
import com.iflytek.voicedemo.IatDemo;
import com.iflytek.voicedemo.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ChatAdapter chatAdapter;
    private ImageView imageView;
    private String speechText = "";
    private IATHandler handler;

    private ChatAdapter.OnItemClickListener onItemClickListener = new ChatAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(int position) {
            Toast.makeText(MainActivity.this,"我点了",Toast.LENGTH_SHORT).show();
        }
    };

     IATHandler.IATDelegate delegate = new IATHandler.IATDelegate() {
         @Override
         public void onResult(String text, Boolean isLast) {
             Toast.makeText(MainActivity.this,text,Toast.LENGTH_SHORT).show();
             if (text.length() > 0) {
                 speechText = text;
                 if (isLast) {
                     ChatItemModel model = new ChatItemModel();
                     model.setVoiceText(speechText);
                     model.setVoicePath(handler.getVoiceFilePath());

                    chatAdapter.getArrayList().add(model);
                    chatAdapter.notifyDataSetChanged();
                    imageView.setBackgroundResource(R.drawable.voice_normal);
                 }
             }
         }

         @Override
         public void onError(String text) {
             imageView.setBackgroundResource(R.drawable.voice_normal);
             Toast.makeText(MainActivity.this,"出现错误，请检查后重试！",Toast.LENGTH_SHORT).show();
         }
     };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        imageView = (ImageView) findViewById(R.id.image_view);

        chatAdapter = new ChatAdapter(this,onItemClickListener);


        recyclerView.setAdapter(chatAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent();
//                intent.setClass(MainActivity.this, com.iflytek.voicedemo.MainActivity.class);
//                startActivity(intent);
                view.setBackgroundResource(R.drawable.voice_unselected);

                speechText = "";

                if (handler == null) {
                    handler = new IATHandler(MainActivity.this);
                    handler.delegate = MainActivity.this.delegate;
                }

                if (!handler.isListening) {
                    handler.start();
                } else {
                    handler.stop();
                }

            }
        });


    }


}
