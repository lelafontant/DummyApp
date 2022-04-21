package com.example.dummyapp;

import android.content.Context;
import android.os.StrictMode;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WebAPI {
    public String url;
    public final String baseUrl = "https://dd.weather.gc.ca/citypage_weather/xml/AB/s0000001_e.xml";
    public final String cityUrl = "https://dd.weather.gc.ca/citypage_weather/docs/siteList.xml";
    public XMLHandler handler;
    public CityHandler cityHandler;

    public WebAPI() {

    }

    public WebAPI(Context context) {
        this();
        url = "https://dd.weather.gc.ca/citypage_weather/xml/";
    }

    private String getData(String url) throws IOException {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();

        String data = response.body().string();

        return data;
    }

    private XmlPullParser getParser(String data) throws XmlPullParserException {
        XmlPullParserFactory xmlFactoryObject = XmlPullParserFactory.newInstance();
        XmlPullParser parser = xmlFactoryObject.newPullParser();
        parser.setInput(new StringReader(data));

        return parser;
    }

    public Weather getWeatherData() throws IOException {
        String data = getData(this.baseUrl);

        try {
            XmlPullParser parser = getParser(data);
            this.handler = new XMLHandler(parser);
            int eventType = parser.getEventType();

            Stack<String> nodes = new Stack<>();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                String name = parser.getName();
                if (eventType == XmlPullParser.START_DOCUMENT) {
                } else if (eventType == XmlPullParser.END_DOCUMENT) {
                } else if (eventType == XmlPullParser.START_TAG) {
                    if (handler.isValidNode(name)) {
                        nodes.push(name);
                    }

                    if (nodes.isEmpty()) {
                        break;
                    }

                    handler.parse(nodes.peek());
                } else if (eventType == XmlPullParser.END_TAG) {
                    if (name.equalsIgnoreCase(nodes.peek())) {
                        nodes.pop();
                    }
                }

                eventType = parser.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }

        return this.handler.getWeather();
    }

    public List<City> getCityList() throws IOException {
        String data = getData(this.cityUrl);
        List<City> cities = new ArrayList<>();

        try {
            XmlPullParser parser = getParser(data);
            this.cityHandler = new CityHandler(parser);
            int eventType = parser.getEventType();

            boolean done = true;
            Stack<String> nodes = new Stack<>();
            City city = null;

            while (eventType != XmlPullParser.END_DOCUMENT) {
                String name = parser.getName();
                if (eventType == XmlPullParser.START_DOCUMENT) {
                } else if (eventType == XmlPullParser.END_DOCUMENT) {
                } else if (eventType == XmlPullParser.START_TAG) {
                    if (this.cityHandler.isValidNode(name)) {
                        done = false;
                        city = new City();
                        city.code = parser.getAttributeValue(null, "code");
                    }

                    if (!done) {
                        switch (name) {
                            case "nameFr":
                                city.nameFr = parser.nextText().trim();
                                break;
                            case "nameEn":
                                city.nameEn = parser.nextText().trim();
                                break;
                            case "provinceCode":
                                city.province = parser.nextText().trim();
                                break;
                        }
                    }
                } else if (eventType == XmlPullParser.END_TAG) {
                    if (this.cityHandler.isValidNode(name)) {
                        done = true;
                        cities.add(city);
                    }
                }

                eventType = parser.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }

        return cities;
    }
}

