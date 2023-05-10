package com.example.cafeapp;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * abstract menu item class
 * @author harshpatel, giancarlo andretta
 */
public abstract class MenuItem implements Parcelable {



    /**
     * abstract method
     * @return items price
     */
    public abstract double itemPrice(); //subclasses must implement this method
}
