package com.iflytek.assist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.iflytek.assist.Handler.IATHandler;
import com.iflytek.assist.Handler.TTSHandler;
import com.iflytek.assist.Model.ChatItemModel;
import com.iflytek.voicedemo.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ChatAdapter chatAdapter;
    private ImageView imageView;
    private String speechText = "";
    private IATHandler handler;
    private TTSHandler ttsHandler;
    private ArrayList<String> questionArray = new ArrayList<>();

    private Boolean over;

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
                    //设置路径
                     chatAdapter.getArrayList().get(chatAdapter.getArrayList().size() - 1).setVoicePath(handler.getVoiceFilePath());

                     //answer model
                     ChatItemModel model = new ChatItemModel();
                     model.setVoiceText(speechText);
                     model.setVoicePath(handler.getVoiceFilePath());
                     model.setQuestion(false);

                    chatAdapter.getArrayList().add(model);
                    imageView.setBackgroundResource(R.drawable.voice_normal);

                    //获取最新的question model 加入列表
                    ChatItemModel questionModel = getNewQuestionAndSpeak();
                    if (questionModel != null) {
                        chatAdapter.getArrayList().add(questionModel);
                    }
                    //刷新列表
                     chatAdapter.notifyDataSetChanged();

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

        initQuestion();

        if (ttsHandler == null) {
            ttsHandler = new TTSHandler(MainActivity.this);
        }

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        imageView = (ImageView) findViewById(R.id.image_view);

        chatAdapter = new ChatAdapter(this,onItemClickListener);
        recyclerView.setAdapter(chatAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        chatAdapter.getArrayList().add(getNewQuestionAndSpeak());


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                view.setBackgroundResource(R.drawable.voice_unselected);

                speechText = "";
                if (over) {//5个问题全部结束 进入下一个界面
                    Toast.makeText(MainActivity.this,"问答已经结束",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this, AnswerActivity.class);

                    intent.putParcelableArrayListExtra("values",megerQuestionAndAnswer());//向跳转到的视图传值

                    startActivity(intent);
                    return;
                }
                if (handler == null) {
                    handler = new IATHandler(MainActivity.this);
                    handler.delegate = MainActivity.this.delegate;
                }
                if (!handler.isListening) {//如果语音未启动 则可以start
                    handler.start();
                } else {
                    handler.stop();
                }

//                ttsHandler.speak("Hi,My name is Grace!");
//                ttsHandler.speak(" As the largest Intelligent Speech technology provider in China, iFLYTEK has long-term research accumulation in the Intelligent Speech area, and obtained leading technology world-wide in Chinese Speech Synthesis, Speech Recognition, and Speech Evaluation. iFLYTEK is the only \"National 863 Plan Achievement Industrialization Base\", \"Key Software Enterprises in State Plan\", \"Key high and new-tech enterprise of national Torch Plan\", \"National high-tech industrialization demonstration project\" with the direction of Speech Technology in China, and is determined as the Leading Organization of Chinese speech interaction technology Standards Working group, by Ministry of Information Industry, leading to set the Chinese Speech Technology standard. iFLYTEK obtained the only \"State Science and Technology Awards (Second prize)\" in Chinese speech industry in 2003, and the highest honor of independent innovation of Chinese IT industry \"Major technological inventions in Information Industry Awards\" in 2005, first rank of English Speech Synthesis Intenational Competition (Blizzard Challenge) for four consecutive years from 2006 to 2009, first rank of International Speaker Recognition and Evaluation Competition (National Institute of standards and technology - NIST 2008) in 2008, and first rank of International Language Recogntion Evaluation Contest (NIST 2009) high difficulty confusion dialect test, and second for General Contest in 2009.");
            }
        });
    }

    //初始化问题列表
    public void initQuestion(){
        over = false;
        questionArray.add("What subjects were your favorite?");
//        questionArray.add("What subjects were your least favorite?");
//        questionArray.add("Other than the courses you studied, what is the most important thing you learned from your college experience?");
//        questionArray.add("What kind of person do you want to be?");
//        questionArray.add("What's your favorite?");
    }

    //获取最新问题，并播放
    public ChatItemModel getNewQuestionAndSpeak(){
        if (questionArray.size() > 0) {
            String question = questionArray.get(0);

            ChatItemModel model = new ChatItemModel();
            model.setVoiceText(question);
            model.setVoicePath("");
            model.setQuestion(true);

            ttsHandler.speak(question);
            questionArray.remove(0);
            return model;
        }
        over = true;
        return null;
    }

    //将问题和答案进行合并
    public ArrayList<ChatItemModel> megerQuestionAndAnswer(){

        ArrayList<ChatItemModel> modelArrayList = new ArrayList<>();
        for (int i =0;i < chatAdapter.getArrayList().size();i++) {

            ChatItemModel model = chatAdapter.getArrayList().get(i);

            if (model.getQuestion()) {
                model.setQuestionText(model.getVoiceText());
                modelArrayList.add(model);
            } else {
                modelArrayList.get(modelArrayList.size()-1).setAnswerText(model.getVoiceText());
            }

        }

        return modelArrayList;

    }

}
