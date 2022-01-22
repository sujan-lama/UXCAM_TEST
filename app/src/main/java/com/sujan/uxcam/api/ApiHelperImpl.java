package com.sujan.uxcam.api;

import com.sujan.uxcam.BuildConfig;
import com.sujan.uxcam.model.WeatherResponse;

import javax.inject.Inject;

import io.reactivex.Flowable;

public class ApiHelperImpl implements ApiHelper {

    ApiService apiService;

    @Inject
    public ApiHelperImpl(ApiService apiService) {
        this.apiService = apiService;
    }


    @Override
    public Flowable<WeatherResponse> getWeather(String city) {
        return apiService.getWeather(city, BuildConfig.API_KEY);
    }
}
