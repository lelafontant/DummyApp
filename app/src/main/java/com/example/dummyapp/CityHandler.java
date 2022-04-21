package com.example.dummyapp;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CityHandler {
    private City city;
    private XmlPullParser parser;

    public City getCity() {
        return city;
    }

    public CityHandler(){
        city = new City();
    }

    public void  init() {
        city = new City();
    }

    public CityHandler(XmlPullParser parser){
        this();
        this.parser = parser;
    }

    public void setCity(String name, City city) throws IOException, XmlPullParserException { ;
        String text = parser.nextText();

        switch (name) {
            case "code":
                city.code = text;
                break;
            case "nameFr":
                city.nameFr = text;
                break;
            case "nameEn":
                city.nameEn = text;
                break;
            case "provinceCode":
                city.province = text;
                break;
        }
    }

    public void parse(String node) throws  IOException, XmlPullParserException {
        String name = parser.getName();

        if(node.equalsIgnoreCase("site")) {
            this.setCity(name, this.city);
        }
    }

    public boolean isValidNode(String nodeName) {
        return nodeName.equalsIgnoreCase("site");
    }
}
