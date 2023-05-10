package com.example.cafeapp;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;

/**
 * donut type enum class
 * @author harshpatel, giancarlo andretta
 */
public enum DonutType implements Parcelable {
    /**
     * yeast donut
     */
    YEAST(1.59),
    /**
     * cake donut
     */
    CAKE(1.79),
    /**
     * donut holes
     */
    DONUT_HOLE(0.39);
    /**
     * price
     */
    final double price;
    /**
     * donut type
     */
    DonutType(double v) {
        this.price = v;
    }

    DonutType(Parcel in) {
        price = in.readDouble();
    }

    public static final Creator<DonutType> CREATOR = new Creator<DonutType>() {
        @Override
        public DonutType createFromParcel(Parcel in) {
            return DonutType.values()[in.readInt()];
        }

        @Override
        public DonutType[] newArray(int size) {
            return new DonutType[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeSerializable(price);
    }
}
