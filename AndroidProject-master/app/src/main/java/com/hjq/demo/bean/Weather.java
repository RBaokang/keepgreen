package com.hjq.demo.bean;

import java.util.List;

public class Weather {
    private List<Wea> list;
    public class Wea{
        private String temp;
        private String weather;
        private String name;
        private String pm;
        private String wind;

        public String getName() {
            return name;
        }

        public String getPm() {
            return pm;
        }

        public String getWeather() {
            return weather;
        }

        public String getTemp() {
            return temp;
        }

        public String getWind() {
            return wind;
        }
    }
}
