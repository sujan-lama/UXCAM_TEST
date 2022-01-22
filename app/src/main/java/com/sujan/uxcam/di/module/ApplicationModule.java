package com.sujan.uxcam.di.module;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.sujan.uxcam.BuildConfig;
import com.sujan.uxcam.api.ApiHelper;
import com.sujan.uxcam.api.ApiHelperImpl;
import com.sujan.uxcam.api.ApiService;
import com.sujan.uxcam.data.AppPreference;
import com.sujan.uxcam.utils.SdkChecker;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.sujan.uxcam.data.AppPreference.PREF_KEY;


@Module
@InstallIn(SingletonComponent.class)
public class ApplicationModule {

    @Provides
    @Singleton
    public static Context provideApplicationContext(Application application) {
        return application.getApplicationContext();
    }

    @Provides
    @Singleton

    public static OkHttpClient provideOkHttpClient() {
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            return new OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .build();
        } else {
            return new OkHttpClient
                    .Builder()
                    .build();
        }
    }


    @Provides
    @Singleton
    public static Retrofit provideRetrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(BuildConfig.BASE_URL)
                .client(okHttpClient)
                .build();
    }

    @Provides
    @Singleton
    public static ApiService provideApiService(Retrofit retrofit) {
        return retrofit.create(ApiService.class);
    }

    @Provides
    @Singleton
    public static ApiHelper provideApiHelper(ApiHelperImpl apiHelper) {
        return apiHelper;
    }

    @Provides
    @Singleton
    public static AppPreference provideAppPreference(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE);
        return new AppPreference(sharedPreferences);
    }


    @Provides
    @Singleton
    public static SdkChecker provideSdkChecker() {
        return new SdkChecker();
    }


}
