package com.example.dummyapp;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class WeatherHandler {
    private Weather weather;
    private XmlPullParser parser;

    public Weather getWeather() {
        return weather;
    }

    public WeatherHandler() {
        weather = new Weather();
    }

    public WeatherHandler(XmlPullParser parser) {
        this();
        this.parser = parser;
    }

    private void setCondition(String name) throws IOException, XmlPullParserException {
        String text = parser.nextText();

        switch (name) {
            case "temperature":
                weather.temperature = text;
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

    public void parse(String node) throws IOException, XmlPullParserException {
        String name = parser.getName();

        if (node.equalsIgnoreCase("currentConditions") && isValidCondition(name)) {
            this.setCondition(name);
        }

        if (node.equalsIgnoreCase("wind") && isValidWind(name)) {
            this.setWind(name);
        }
    }

    public boolean isValidNode(String nodeName) {
        return nodeName.equalsIgnoreCase("currentConditions") || nodeName.equalsIgnoreCase("wind");
    }

    public boolean isValidCondition(String nodeName) {
        Set<String> validTags = new HashSet<String>(Arrays.asList(new String[]{
                "temperature", "dewChill", "windChill", "pressure", "relativeHumidity"
        }));
        return validTags.contains(nodeName);
    }

    public boolean isValidWind(String nodeName) {
        Set<String> validTags = new HashSet<String>(Arrays.asList(new String[]{ "speed", "direction" }));
        return validTags.contains(nodeName);
    }
}
