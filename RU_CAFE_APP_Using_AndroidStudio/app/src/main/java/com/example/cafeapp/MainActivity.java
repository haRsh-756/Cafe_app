package com.example.cafeapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The MainActivity class is the main activity of the application.
 * It contains the buttons to order donuts and coffee, view cart, and store orders.
 */
public class MainActivity extends AppCompatActivity {

    /**
     * The button to order donuts.
     */
    ImageButton orderDonutBtn;

    /**
     * The button to order coffee.
     */
    ImageButton orderCoffeeBtn;

    /**
     * The button to view the cart.
     */
    ImageButton viewBasketBtn;

    /**
     * The button to store the orders.
     */
    ImageButton storeOrdersBtn;

    /**
     * The title text view.
     */
    TextView textView;

    /**
     * Called when the activity is starting.
     * Sets the content view and initializes the UI components.
     *
     * @param savedInstanceState The saved instance state bundle.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        orderDonutBtn = findViewById(R.id.img_button1);
        orderCoffeeBtn = findViewById(R.id.img_button2);
        viewBasketBtn = findViewById(R.id.img_button3);
        storeOrdersBtn = findViewById(R.id.img_button4);
        textView = findViewById(R.id.title_textview);

        orderDonutBtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, OrderDonutsActivity.class);
            startActivity(intent);
        });
        orderCoffeeBtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, OrderCoffeeActivity.class);
            startActivity(intent);
        });

        viewBasketBtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, CartActivity.class);
            startActivity(intent);
        });

        storeOrdersBtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, StoreOrdersActivity.class);
            startActivity(intent);
        });
    }
}

//DataManager dataManager = DataManager.getInstance();
//intent.putExtra("key_list", (Parcelable) items);
//intent.putParcelableArrayListExtra("key_list", (ArrayList<? extends Parcelable>) items);
//intent.putParcelableArrayListExtra("key_list", (ArrayList<? extends Parcelable>) dataList);