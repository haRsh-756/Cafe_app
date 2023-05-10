package com.example.cafeapp;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Objects;

/**
 * class that manages the store orders activity
 * @author harsh_patel, giancarlo_andretta
 */
public class StoreOrdersActivity extends AppCompatActivity {
    /**
     * listview Instance
     */
    private ListView listViewOrders;
    /**
     * arrayAdapter to display orders instance
     */
    private ArrayAdapter<Order> adapter;
    /**
     * temp orderlist to store orders so far
     */
    private ArrayList<Order> orderList = new ArrayList<>();

    /**
     * creates the view for the store orders Activity and sets the necessary event handlers
     * @param savedInstanceState savedInstance
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storeorders);
        listViewOrders = findViewById(R.id.listView_Orders);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        orderList.addAll(DataManager.getInstance().getOrderList());
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, orderList);
        listViewOrders.setAdapter(adapter);
        listViewOrders.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
                alert.setTitle("Delete an item");
                alert.setMessage("Delete an item");
                //handle the "YES" click
                alert.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(view.getContext(),
                                "Order removed", Toast.LENGTH_LONG).show();
                        orderList.remove(position);
                        DataManager.getInstance().setOrderList(orderList);
                        adapter.notifyDataSetChanged();
                    }
                    //handle the "NO" click
                }).setNegativeButton("no", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(view.getContext(),
                                "Order not removed", Toast.LENGTH_LONG).show();
                    }
                });
                AlertDialog dialog = alert.create();
                dialog.show();
            }
        });
    }
    /**
     * if back button is pressed then switching activity to main
     * @param item item
     * @return true if back button is pressed
     */
    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        if (item.getItemId() == android.R.id.home) {//finish();
            onBackPressed();
        }
        return true;
    }

    /**
     * if back button is pressed then switching activity to main
     */
    @Override
    public void onBackPressed() {
        for(com.example.cafeapp.MenuItem item: DataManager.getInstance().getItemsList()){
            if(item instanceof Donut donut){
                System.out.println(donut);
            }
            else if(item instanceof Coffee coffee){
                System.out.println(coffee);
            }
        }
        finish();
    }
}
