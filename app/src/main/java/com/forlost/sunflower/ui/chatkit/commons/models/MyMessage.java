package com.forlost.sunflower.ui.chatkit.commons.models;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MyMessage implements IMessage {
    String id;
    String text;
    String type;
    public MyMessage(String type,String id,String text){
        this.type=type;
        this.id=id;
        this.text=text;
    }

    public String getType() {
        return type;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public IUser getUser() {
        return null;
    }

    @Override
    public Date getCreatedAt() {
        return null;
    }
}
