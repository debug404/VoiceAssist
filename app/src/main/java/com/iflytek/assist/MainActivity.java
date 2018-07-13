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

import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.speech.setting.IatSettings;
import com.iflytek.voicedemo.IatDemo;
import com.iflytek.voicedemo.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ChatAdapter chatAdapter;
    ImageView imageView;

    private ChatAdapter.OnItemClickListener onItemClickListener = new ChatAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(int position) {
            Toast.makeText(MainActivity.this,"我点了",Toast.LENGTH_SHORT).show();
        }
    };

     IATHandler.IATDelegate delegate = new IATHandler.IATDelegate() {
         @Override
         public void onResult(String text, Boolean isLast) {
             System.out.println("888888888888888888");
         }

         @Override
         public void onError(String text) {
             System.out.println("888888888888888888");
         }
     };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        imageView = (ImageView) findViewById(R.id.image_view);


        chatAdapter = new ChatAdapter(this,onItemClickListener);
        chatAdapter.arrayList = new ArrayList<String>();
        chatAdapter.arrayList.add("123123132");
        chatAdapter.arrayList.add("123123132123123132123123132123123132123123132123123132123123132123123132123123132123123132123123132123123132123123132123123132123123132123123132123123132123123132123123132123123132123123132123123132123123132123123132123123132123123132123123132123123132123123132123123132123123132123123132");
        chatAdapter.arrayList.add("123123132123123132123123132123123132123123132123123132123123132123123132123123132123123132123123132123123132123123132123123132123123132123123132123123132123123132123123132123123132123123132123123132123123132123123132123123132123123132123123132123123132123123132123123132123123132123123132");
        chatAdapter.arrayList.add("123123132123123132123123132123123132123123132123123132123123132123123132123123132123123132123123132123123132123123132123123132123123132123123132123123132123123132123123132123123132123123132123123132123123132123123132123123132123123132123123132123123132123123132123123132123123132123123132");
        chatAdapter.arrayList.add("123123132123123132123123132123123132123123132123123132123123132123123132123123132123123132123123132123123132123123132123123132123123132123123132123123132123123132123123132123123132123123132123123132123123132123123132123123132123123132123123132123123132123123132123123132123123132123123132");


        recyclerView.setAdapter(chatAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        chatAdapter.notifyDataSetChanged();






        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent();
//                intent.setClass(MainActivity.this, com.iflytek.voicedemo.MainActivity.class);
//                startActivity(intent);
                view.setBackgroundResource(R.drawable.user);

                IATHandler handler = new IATHandler(MainActivity.this);
                handler.delegate = MainActivity.this.delegate;
                handler.start();


            }
        });


    }


}
