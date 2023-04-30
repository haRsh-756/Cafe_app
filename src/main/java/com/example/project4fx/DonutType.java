package com.example.project4fx;

import java.io.Serializable;

/**
 * donut type enum class
 * @author harshpatel, giancarlo andretta
 */
public enum DonutType implements Serializable {
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
}
