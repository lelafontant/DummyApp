package com.example.dummyapp;

public class Weather {
    public Weather() {
        this.wind = new Wind();
    }

    public  String station;
    public String temperature;
    public float dewChill;
    public float windChill;
    public float pressure;
    public float humidity;
    public Wind wind;
    public int timeStamp;
    public String unitType;

    class Wind {
        public float speed;
        public String direction;
    }
}
