package com.example.cafeapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Objects;

/**
 * class that manages the order donuts activity
 * @author harsh_patel, giancarlo_andretta
 */
public class OrderDonutsActivity extends AppCompatActivity {
    /**
     * imageView instance
     */
    ImageView imageView;
    /**
     * recylcer view instance
     */
    RecyclerView recyclerView;
    /**
     * textview instance
     */
    TextView tv_donutPrice;
    /**
     * options to view based on donutType selection
     */
    ArrayList<DataHelper> options = new ArrayList<>();
    /**
     * creates the view for the donuts Activity and sets the necessary event handlers
     * @param savedInstanceState savedInstance
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donuts);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        String [] donutType = getResources().getStringArray(R.array.donutType);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, donutType);
        Spinner spinner = findViewById(R.id.spinner);
        recyclerView = findViewById(R.id.rec_View);
        imageView = findViewById(R.id.imageView);
        tv_donutPrice = findViewById(R.id.priceForEach);
        ItemsAdapter adapter = new ItemsAdapter(this,options); //create the adapter
        spinner.setAdapter(arrayAdapter);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint({"NotifyDataSetChanged", "SetTextI18n"})
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedValue = (String) parent.getItemAtPosition(position);
                switch (position) {
                    case 0 -> {
                        imageView.setImageResource(R.drawable.donut);
                        tv_donutPrice.setText(getResources().getString(R.string.dollar) + DonutType.YEAST.price);
                        ArrayList<DataHelper> items = new ArrayList<>();
                        String [] array = getResources().getStringArray(R.array.YeastDonuts);
                        for(String donutName: array){
                            items.add(new DataHelper(donutName,DonutType.YEAST.name(),0,DonutType.YEAST.price));
                        }
                        adapter.setData(items);
                        adapter.notifyDataSetChanged();

                    }
                    case 1 -> {
                        imageView.setImageResource(R.drawable.donuts);
                        tv_donutPrice.setText(getResources().getString(R.string.dollar) + DonutType.CAKE.price);
                        ArrayList<DataHelper> items = new ArrayList<>();
                        String [] array = getResources().getStringArray(R.array.CakeDonuts);
                        for(String donutName: array){
                            items.add(new DataHelper(donutName,DonutType.CAKE.name(), 0,DonutType.CAKE.price));
                        }
                        adapter.setData(items);
                        adapter.notifyDataSetChanged();
                    }
                    case 2 -> {
                        imageView.setImageResource(R.drawable.donutholes);
                        tv_donutPrice.setText(getResources().getString(R.string.dollar) + DonutType.DONUT_HOLE.price);
                        ArrayList<DataHelper> items = new ArrayList<>();
                        String [] array = getResources().getStringArray(R.array.DonutHoles);
                        for(String donutName: array){
                            items.add(new DataHelper(donutName,DonutType.DONUT_HOLE.name(), 0,DonutType.DONUT_HOLE.price));
                        }
                        adapter.setData(items);
                        adapter.notifyDataSetChanged();
                    }
                }
                Toast.makeText(getApplicationContext(), "Selected DonutType: " + selectedValue, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //nothing
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
//List<com.example.cafeapp.MenuItem> list = getIntent().getParcelableArrayListExtra("key_list");
//List<MenuItem> list = intent.getParcelableArrayListExtra("key_list");
//System.out.println(adapter.getItemList().size());
//adapter.getItemList();
// getIntent().putParcelableArrayListExtra("resultant_list", (ArrayList<? extends Parcelable>) list);
// setResult(Activity.RESULT_OK,getIntent());
// finish();
//adapter.setData(new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.YeastDonuts))));
