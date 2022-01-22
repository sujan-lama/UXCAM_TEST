package com.sujan.uxcam.ui.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sujan.uxcam.R;
import com.sujan.uxcam.data.AppPreference;
import com.sujan.uxcam.databinding.ActivityMainBinding;
import com.sujan.uxcam.ui.adapter.MainAdapter;
import com.sujan.uxcam.utils.Utility;
import com.sujan.uxcam.viewmodel.MainViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity implements MainAdapter.MainAdapterClickListener {

    MainViewModel mainViewModel;
    MainAdapter mainAdapter;

    ActivityMainBinding binding;

    @Inject
    AppPreference preference;

    List<String> searchList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        setupUI();
        setupObserver();
    }


    private void setupUI() {
        searchList = preference.getSearchList();
        toggleRecentSearchVisibility(searchList);
        if(!searchList.isEmpty()){
            startSearch(searchList.get(0));
        }
        mainAdapter = new MainAdapter(searchList, this);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        binding.recyclerView.setAdapter(mainAdapter);

        binding.textClearAll.setOnClickListener(v -> {
            mainAdapter.clearAll();
            searchList.clear();
            binding.layoutWeather.getRoot().setVisibility(View.GONE);
            toggleRecentSearchVisibility(searchList);
        });

        binding.layoutSearchCity.editTextQueryInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s == null || s.toString().isEmpty()) {
                    binding.layoutSearchCity.imageViewClose.setVisibility(View.GONE);
                } else
                    binding.layoutSearchCity.imageViewClose.setVisibility(View.VISIBLE);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.layoutSearchCity.imageViewClose.setOnClickListener(v -> {
            binding.layoutSearchCity.editTextQueryInput.setText("");
        });

        binding.layoutSearchCity.editTextQueryInput.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                startSearch(binding.layoutSearchCity.editTextQueryInput.getText().toString().trim());
                return true;
            }
            return false;
        });

    }

    private void startSearch(String query) {
        Utility.hideKeyboard(this);

        if (query.isEmpty()) {
            Toast.makeText(this, "City Name cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        mainViewModel.fetchWeatherByCity(query);

    }

    private void setupObserver() {

        mainViewModel.getWeather().observe(this, it -> {
            switch (it.getStatus()) {
                case SUCCESS:
                    binding.progressBar.setVisibility(View.GONE);
                    binding.layoutSearchCity.editTextQueryInput.setText("");
                    if (it.getData() != null) {
                        mainAdapter.addData(it.getData().getFormattedAddress());
                        preference.setSearchList(mainAdapter.getData());
                        toggleRecentSearchVisibility(searchList);
                        binding.layoutWeather.setWeather(it.getData());
                    }
                    binding.layoutWeather.getRoot().setVisibility(View.VISIBLE);

                    break;

                case LOADING:
                    binding.progressBar.setVisibility(View.VISIBLE);
                    binding.layoutWeather.getRoot().setVisibility(View.GONE);
                    break;

                case ERROR:
                    binding.progressBar.setVisibility(View.GONE);
                    Toast.makeText(this, it.getMessage(), Toast.LENGTH_LONG).show();
                    break;
            }
        });
    }

    private void toggleRecentSearchVisibility(List<String> searchList) {
        if (searchList.isEmpty()) {
            binding.recentSearchTitle.setVisibility(View.GONE);
            binding.recyclerView.setVisibility(View.GONE);
            binding.textClearAll.setVisibility(View.GONE);
            return;
        }

        binding.recentSearchTitle.setVisibility(View.VISIBLE);
        binding.recyclerView.setVisibility(View.VISIBLE);
        binding.textClearAll.setVisibility(View.VISIBLE);
    }

    @Override
    public void onRecentSearchClicked(String query) {
        startSearch(query);
    }

}