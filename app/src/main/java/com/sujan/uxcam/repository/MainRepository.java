package com.sujan.uxcam.repository;

import androidx.lifecycle.MutableLiveData;

import com.sujan.uxcam.api.ApiHelper;
import com.sujan.uxcam.model.WeatherInfo;
import com.sujan.uxcam.utils.NetworkHelper;
import com.sujan.uxcam.utils.Resource;

import org.json.JSONObject;

import javax.inject.Inject;

import io.reactivex.schedulers.Schedulers;
import kotlin.io.TextStreamsKt;
import retrofit2.HttpException;
import retrofit2.Response;

import static com.sujan.uxcam.constant.Constant.WEATHER_ICON_URL;

public class MainRepository {

    ApiHelper apiHelper;
    private NetworkHelper networkHelper;
    private MutableLiveData<Resource<WeatherInfo>> weather = new MutableLiveData<>();

    @Inject
    public MainRepository(ApiHelper apiHelper, NetworkHelper networkHelper) {
        this.apiHelper = apiHelper;
        this.networkHelper = networkHelper;
    }

    public void fetchWeatherByCity(String city) {
        weather.postValue(Resource.loading(null));
        if (!networkHelper.isNetworkConnected()) {
            weather.postValue(Resource.error("No internet connection", null));
            return;
        }

        apiHelper.getWeather(city).subscribeOn(Schedulers.io()).subscribe(it -> {
            if (it.cod != 200) {
                weather.postValue(Resource.error(it.message, null));
                return;
            }
            WeatherInfo weatherInfo = new WeatherInfo(
                    Math.round(it.main.getTemp() - 273.15),
                    Math.round(it.main.getTempMin() - 273.15),
                    Math.round(it.main.getTempMax() - 273.15),
                    it.weather.get(0).main,
                    it.weather.get(0).description.toUpperCase(),
                    WEATHER_ICON_URL + it.weather.get(0).icon + ".png",
                    it.name,
                    it.wind.getSpeed().toString(),
                    it.main.getHumidity().toString(),
                    it.main.getPressure().toString()
            );
            weather.postValue(Resource.success(weatherInfo));
        }, throwable -> {
            if (throwable instanceof HttpException) {
                Response<?> response = ((HttpException) throwable).response();
                if (response != null && response.errorBody() != null) {
                    JSONObject jsonObj = new JSONObject(TextStreamsKt.readText(response.errorBody().charStream()));
                    weather.postValue(Resource.error(jsonObj.getString("message"), null));
                    return;
                }

            }
            weather.postValue(Resource.error(throwable.getLocalizedMessage(), null));
        });

    }

    public MutableLiveData<Resource<WeatherInfo>> getWeather() {
        return weather;
    }
}
