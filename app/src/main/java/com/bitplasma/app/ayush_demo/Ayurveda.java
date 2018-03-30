package com.bitplasma.app.ayush_demo;

/**
 * Created by slash on 24-03-2018.
 */

public class Ayurveda {
    private String mName;
    private String mCompany;
    private String mIngredients;

    public Ayurveda(String name,String company,String ingredients) {
        mName=name;
        mCompany=company;
        mIngredients=ingredients;
    }
    public String getmName(){
        return mName;
    }
    public String getmCompany(){
        return mCompany;
    }
    public String getmIngredients() { return mIngredients;}
}
