package com.iflytek.assist.Model;

/**
 * Created by qxb-810 on 2018/7/14.
 */

public class ChatItemModel {

    private String voicePath;
    private String voiceText;
    private Boolean isQuestion;

    public Boolean getQuestion() {
        return isQuestion;
    }

    public void setQuestion(Boolean question) {
        isQuestion = question;
    }

    public String getVoicePath() {
        return voicePath;
    }

    public void setVoicePath(String voicePath) {
        this.voicePath = voicePath;
    }

    public String getVoiceText() {
        return voiceText;
    }

    public void setVoiceText(String voiceText) {
        this.voiceText = voiceText;
    }
}
