package com.sujan.uxcam.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Wind {
    @SerializedName("speed")
    @Expose
    private Double speed;
    @SerializedName("deg")
    @Expose
    private int deg;

    public Double getSpeed() {
        return speed;
    }

    public int getDeg() {
        return deg;
    }
}
