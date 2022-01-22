package com.sujan.uxcam.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.hilt.android.qualifiers.ApplicationContext;

@Singleton
public class NetworkHelper {
    private ConnectivityManager connectivityManager;

    private Context context;
    private SdkChecker sdkChecker;

    @Inject
    public NetworkHelper(@ApplicationContext Context context, SdkChecker sdkChecker) {
        this.context = context;
        this.sdkChecker = sdkChecker;
        connectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }


    public Boolean isNetworkConnected() {
        if (sdkChecker.isApi23orAbove()) {
            Network networkCapabilities = connectivityManager.getActiveNetwork();
            if (networkCapabilities == null) return false;
            NetworkCapabilities activeNetwork =
                    connectivityManager.getNetworkCapabilities(networkCapabilities);

            if (activeNetwork == null) return false;

            return activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                    || activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                    || activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_VPN)
                    || activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET);
        }

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null) return false;
        int type = networkInfo.getType();
        return type == ConnectivityManager.TYPE_WIFI || type == ConnectivityManager.TYPE_MOBILE || type == ConnectivityManager.TYPE_ETHERNET || type == ConnectivityManager.TYPE_VPN;

    }
}
