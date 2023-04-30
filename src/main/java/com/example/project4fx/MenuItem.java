package com.example.project4fx;

import java.io.Serializable;

/**
 * abstract menu item class
 * @author harshpatel, giancarlo andretta
 */
public abstract class MenuItem implements Serializable {

    /**
     * abstract method
     * @return items price
     */
    public abstract double itemPrice(); //subclasses must implement this method
}
