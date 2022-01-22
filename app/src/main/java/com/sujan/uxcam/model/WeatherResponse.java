package com.sujan.uxcam.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeatherResponse {

    @SerializedName("weather")
    @Expose
    public List<Weather> weather = null;
    @SerializedName("base")
    @Expose
    public String base;
    @SerializedName("main")
    @Expose
    public Main main;
    @SerializedName("wind")
    @Expose
    public Wind wind;
    @SerializedName("visibility")
    @Expose
    public Integer visibility;
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("cod")
    @Expose
    public Integer cod;


    @Override
    public String toString() {
        return "WeatherResponse{" +
                "weather=" + weather +
                ", base='" + base + '\'' +
                ", main=" + main +
                ", visibility=" + visibility +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", message='" + message + '\'' +
                ", cod=" + cod +
                '}';
    }
}