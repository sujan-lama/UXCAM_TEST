package com.sujan.uxcam.api;

import com.sujan.uxcam.model.WeatherResponse;

import java.util.List;

import io.reactivex.Flowable;

public interface ApiHelper {
    Flowable<WeatherResponse> getWeather(String city);
}
