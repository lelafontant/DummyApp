package com.example.dummyapp;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class XMLHandler {
    private Weather weather;
    private XmlPullParser parser;

    public Weather getWeather() {
        return weather;
    }

    public XMLHandler(){
        weather = new Weather();
    }

    public XMLHandler(XmlPullParser parser){
        this();
        this.parser = parser;
    }

    private void setCondition(String name) throws IOException, XmlPullParserException { ;
        String text = parser.nextText();

        switch (name) {
            case "temperature":
                weather.temperature = Float.parseFloat(text);
                break;
            case "dewChill":
                weather.dewChill = Float.parseFloat(text);
                break;
            case "windChill":
                weather.windChill = Float.parseFloat(text);
                break;
            case "pressure":
                weather.pressure = Float.parseFloat(text);
                break;
            case "relativeHumidity":
                weather.humidity = Float.parseFloat(text);
                break;
        }
    }

    private void setWind(String name) throws IOException, XmlPullParserException {
        String text = parser.nextText();

        switch (name) {
            case "speed":
                weather.wind.speed = Float.parseFloat(text);
                break;
            case "direction":
                weather.wind.direction = text;
                break;
        }
    }

    public void parse(String node) throws  IOException, XmlPullParserException {
        String name = parser.getName();

        if(node.equalsIgnoreCase("currentConditions")) {
            this.setCondition(name);
        }

        if(node.equalsIgnoreCase("wind")) {
            this.setCondition(name);
        }
    }

    public boolean isValidNode(String nodeName) {
        return nodeName.equalsIgnoreCase("currentConditions") || nodeName.equalsIgnoreCase("wind");
    }
}
