package com.bitplasma.app.ayush_demo;

/**
 * Created by slash on 24-03-2018.
 */

public class Ayurveda {
    private long mUserId;
    private long mId;
    private String mTitle;
    private String mBody;

    public Ayurveda(long userId,long id,String title, String body) {
        mUserId=userId;
        mId=id;
        mTitle=title;
        mBody=body;
    }
    public long getmUserId(){
        return mUserId;
    }
    public long getmId(){
        return mId;
    }
    public String getmTitle() { return mTitle;}
    public String getmBody(){return mBody;}
}
