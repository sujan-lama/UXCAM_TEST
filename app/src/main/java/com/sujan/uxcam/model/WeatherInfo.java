package com.sujan.uxcam.model;

import com.sujan.uxcam.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class WeatherInfo {
    long temp;
    long tempMin;
    long tempMax;
    String main;
    String desc;
    String icon;
    String formattedAddress;
    String windSpeed;
    String humidity;
    String pressure;

    public WeatherInfo(long temp, long tempMin, long tempMax, String main, String desc, String icon, String formattedAddress, String windSpeed, String humidity, String pressure) {
        this.temp = temp;
        this.tempMin = tempMin;
        this.tempMax = tempMax;
        this.main = main;
        this.desc = desc;
        this.icon = icon;
        this.formattedAddress = formattedAddress;
        this.windSpeed = windSpeed;
        this.humidity = humidity;
        this.pressure = pressure;
    }

    public String getTemp() {
        return String.format("%d\u2103", temp);
    }


    public String getDesc() {
        return desc;
    }

    public String getIcon() {
        return icon;
    }

    public String getFormattedAddress() {
        return formattedAddress;
    }

    public DetailModel getTemperatureModel() {
        return new DetailModel(getTemp(), "Temperature", R.drawable.ic_temp);
    }

    public DetailModel getWindModel() {
        return new DetailModel(windSpeed + " km/hr", "Wind speed", R.drawable.ic_wind);
    }

    public DetailModel getHumidityModel() {
        return new DetailModel(humidity+"%", "Humidity", R.drawable.ic_humidity);
    }

    public DetailModel getPressureModel() {
        return new DetailModel(pressure+" mBar", "Pressure", R.drawable.ic_pressure);
    }


    public String getCurrentDate() {
        return new SimpleDateFormat("EEEE, MMM dd, yyyy").format(Calendar.getInstance().getTime());
    }
}
