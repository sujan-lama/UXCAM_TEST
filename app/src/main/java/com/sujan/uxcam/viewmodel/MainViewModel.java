package com.sujan.uxcam.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sujan.uxcam.model.WeatherInfo;
import com.sujan.uxcam.model.WeatherResponse;
import com.sujan.uxcam.repository.MainRepository;
import com.sujan.uxcam.utils.Resource;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class MainViewModel extends ViewModel {

    private MainRepository mainRepository;


    public MutableLiveData<Resource<WeatherInfo>> getWeather() {
        return mainRepository.getWeather();
    }


    @Inject
    public MainViewModel(MainRepository mainRepository) {
        this.mainRepository = mainRepository;
    }

    public void fetchWeatherByCity(String city) {
        mainRepository.fetchWeatherByCity(city);
    }
}
