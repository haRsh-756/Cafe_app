package com.example.cafeapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.icu.text.DecimalFormat;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * class that manages the cart activity
 * @author harsh_patel, giancarlo_andretta
 */
public class CartActivity extends AppCompatActivity {
    /**
     * listView instance
     */
    private ListView listView;
    /**
     * editText instance
     */
    private EditText amount;
    /**
     * editText instance
     */
    private EditText salesTax;
    /**
     * editText instance
     */
    private EditText total;
    /**
     * button instance
     */
    private Button removeSelectedItem;
    /**
     * button instance
     */
    private Button placeOrder;
    /**
     * private inner class CustomAdapter for item selection instance
     */
    //private ArrayAdapter<MenuItem> adapter;
    CustomAdapter adapter;
    /**
     * fixed decimal format instance
     */
    private final DecimalFormat df = new DecimalFormat("$#0.00");
    /**
     * temp Items list arraylist to add and remove item
     */
    private ArrayList<MenuItem> itemsList = new ArrayList<>();
    /**
     * subtotal
     */
    private double sub_Total;
    /**
     * salesTax
     */
    private double sales_Tax;
    /**
     * total
     */
    private double Total;
    /**
     * taxRate constant
     */
    private static final double TAXRATE = 0.06625;
    /**
     * private static inner class that extends ArrayAdapter to display menuItems
     */
    private static class CustomAdapter extends ArrayAdapter<MenuItem> {
        /**
         * selctedpos instance
         */
        private int selectedPos = -1;

        /**
         * default constructor for initialization
         * @param context
         * @param resource
         * @param objects
         */
        public CustomAdapter(Context context, int resource, List<MenuItem> objects) {
            super(context, resource, objects);
        }

        /**
         * selected item in a listview gets highlighted and others stay the same
         * @param position position of the selectedItem
         * @param convertView convertView
         * @param parent parent
         * @return the view
         */
        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View view = super.getView(position, convertView, parent);
            if (position == selectedPos) {
                view.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.teal_700));
            } else {
                view.setBackgroundColor(ContextCompat.getColor(getContext(), androidx.cardview.R.color.cardview_light_background));
            }
            return view;
        }

        /**
         * sets the selected item position
         * @param position selected item position
         */
        public void setSelected(int position) {
            selectedPos = position;
            notifyDataSetChanged();
        }

        /**
         * returns the selectedPos
         * @return selectedPos
         */
        public int getSelectedPos() {
            return selectedPos;
        }
    }

    /**
     * creates the view for the cartActivity and sets the necessary event handlers
     * @param savedInstanceState savedInstance
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        listView = findViewById(R.id.orderItems);
        amount = findViewById(R.id.amount);
        salesTax = findViewById(R.id.salesTax);
        total = findViewById(R.id.total);
        removeSelectedItem = findViewById(R.id.remove_SelectedItem);
        placeOrder = findViewById(R.id.placeOrder);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        itemsList.addAll(DataManager.getInstance().getItemsList());
        //adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, itemsList);
        adapter = new CustomAdapter(this, android.R.layout.simple_list_item_1, itemsList);
        listView.setAdapter(adapter);
        fireAmount();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                removeSelectedItem.setEnabled(true);
                adapter.setSelected(position);
            }
        });
        removeSelectedItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!itemsList.isEmpty()) {
                    itemsList.remove(adapter.getSelectedPos());
                    adapter.notifyDataSetChanged();
                    removeSelectedItem.setEnabled(false);
                    fireAmount();
                    DataManager.getInstance().setItemsList(itemsList);
                }
            }
        });
        placeOrder();
    }

    /**
     * places the order on button click and clears the list view
     */
    private void placeOrder(){
        placeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!itemsList.isEmpty()) {
                    DataManager.getInstance().setItemsList(itemsList);
                    Order order = new Order(DataManager.getInstance().generateOrderNum(),DataManager.getInstance().getItemsList());
                    order.setTotalForThisOrder(Total);
                    order.setSalesTax(sales_Tax);
                    order.setSubTotal(sub_Total);
                    DataManager.getInstance().getOrderList().add(order);
                    DataManager.getInstance().getItemsList().clear();
                    itemsList.clear();
                    adapter.notifyDataSetChanged();
                    fireAmount();
                    Toast.makeText(getApplicationContext(), "Your Order has been Placed!!: ", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Cart is Empty!!: ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * fireAmount to be displayed for items subtotal, salesTax, and Total
     */
    private void fireAmount(){
        sub_Total = 0.0;
        for (MenuItem menuItem : itemsList) {
            if (menuItem instanceof Coffee coffee) {
                sub_Total += coffee.itemPrice();
                //double salesTax = coffee.itemPrice();
            } else if (menuItem instanceof Donut donut) {
                //subTotal.setText(String.valueOf(donut.itemPrice()));
                sub_Total += donut.itemPrice();
            }
        }
        sales_Tax = sub_Total * TAXRATE;
        Total = sub_Total + sales_Tax;
        //setTotal(Total);
        amount.setText(df.format(sub_Total));
        salesTax.setText(df.format(sales_Tax));
        total.setText(df.format(Total));
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
/*view.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_light));
                removeSelectedItem.setEnabled(true);
                        //removeSelectedItem.setTag(position);
                        adapter.setSelected(position);
                /*if (previousSelectedView != null) {
                    previousSelectedView.setBackgroundColor(Color.TRANSPARENT);
                }
                view.setBackgroundColor(Color.CYAN);        // Change the background color of the selected item
                previousSelectedView = view;*/
/*if(removeSelectedItem.getTag() != null) {
                    int position = (int) removeSelectedItem.getTag();
                    itemsList.remove(position);
                    //DataManager.getInstance().getItemsList().remove();
                    DataManager.getInstance().setItemsList(itemsList);
                    adapter.notifyDataSetChanged();
                    removeSelectedItem.setEnabled(false);
                    fireAmount();
                }*/
//MenuItem item = (MenuItem) parent.getItemAtPosition(position);
//listView.getSelectedItemPosition();
//listView.setBackgroundColor(Color.CYAN);
///listView.getChildAt(position).setBackgroundColor(Color.CYAN);

//listView.setSelected(position);

//listView.getChildAt(listView.getSelectedItemPosition()).setBackgroundColor(Color.CYAN);
//System.out.println(listView.getChildCount());
                /*for (int i = 0; i < listView.getChildCount(); i++) {
                    if (i == position) {
                        listView.getChildAt(i).setBackgroundColor(Color.CYAN);
                    } else {
                        listView.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
                    }
                }*/
//handleSelectedItem();
//button();


/*private void handleSelectedItem() {*/
        /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                listView.setItemChecked(position, true);
                //String value = adapter.getItem(position);
                // adapter.remove(value);
                // adapter.notifyDataSetChanged();
            }
        });*/
       /* listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //listView.setItemChecked(position, true);
                listView.setItemsCanFocus();
                String selectedItem = (String) parent.getItemAtPosition(position);
                // Do something with the selected item
            }
        });
    }*/
    /*
    listView.setChoiceMode(ListView.CHOICE_MODE_NONE);
     */
    /*private void button(){
        removeSelectedItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = listView.getSelectedItemPosition();
                if (position != ListView.INVALID_POSITION) {
                    adapter.remove(adapter.getItem(position));
                    listView.setChoiceMode(ListView.CHOICE_MODE_NONE);
                    adapter.notifyDataSetChanged();
                    //listView.clearChoices();
                }
            }
        });
        // String[] stringArray = new String[DataManager.getInstance().getItemsList().size()];
        /*for (int i = 0; i < DataManager.getInstance().getItemsList().size(); i++) {
            stringArray[i] = DataManager.getInstance().getItemsList().get(i).toString();
        }*/

        /*removeSelectedItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = listView.getSelectedItemPosition();
                if (position != AdapterView.INVALID_POSITION) {
                    adapter.remove(adapter.getItem(position));
                }
            }
        });
         */
        /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listView.setSelection(position);
            }
        });

        removeSelectedItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = listView.getSelectedItemPosition();
                if (position != ListView.INVALID_POSITION) {
                    DataManager.getInstance().getItemsList().remove(position);
                    adapter.notifyDataSetChanged();
                }
            }
            //ArrayList<MenuItem> itemsList = new ArrayList<>(DataManager.getInstance().getItemsList());
        //MenuItem [] items = DataManager.getInstance().getItemsList().toArray(new MenuItem[0]);
        });*/