package com.sujan.uxcam.utils;

import android.os.Build;

import javax.inject.Singleton;

@Singleton
public
class SdkChecker {
    boolean isApi23orAbove() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }
}
