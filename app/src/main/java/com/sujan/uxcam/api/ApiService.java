package com.sujan.uxcam.api;

import com.sujan.uxcam.model.WeatherResponse;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("weather")
    Flowable<WeatherResponse> getWeather(@Query("q") String city, @Query("appid") String apiKey);


}
