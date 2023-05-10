package com.example.cafeapp;

import android.content.DialogInterface;
import android.icu.text.DecimalFormat;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * class that manages the coffee activity
 * @author harsh_patel, giancarlo_andretta
 */
public class OrderCoffeeActivity extends AppCompatActivity {
    /**
     * check box instances
     */
    private CheckBox caramel, sweet_Cream, black, irish_Cream, french_Vanilla, mocha;
    /**
     * button instance
     */
    private Button addQty, subtractQty, addToOrder;
    /**
     * edittext instance
     */
    private EditText subTotal;
    /**
     * temporary object for data manipulation
     */
    private Coffee tempCoffeeObj; // data manipulation
    /**
     * tempQty
     */
    private int tempQty = 1;
    /**
     * temp addIns instance
     */
    private ArrayList<String> addIns = new ArrayList<>();
    /**
     * text view instance
     */
    private TextView tv_qty;
    /**
     * fixed decimal format instance
     */
    private DecimalFormat df = new DecimalFormat("$#0.00");

    /**
     * creates the view for the coffee activity and sets the necessary event handlers
     * @param savedInstanceState savedInstance
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coffee);
        tempCoffeeObj = new Coffee();
        caramel = findViewById(R.id.caramelFlavor);
        black = findViewById(R.id.black);
        french_Vanilla = findViewById(R.id.frenchVanillaFlavor);
        irish_Cream = findViewById(R.id.irishCreamFlavor);
        sweet_Cream = findViewById(R.id.sweetCreamFlavor);
        mocha = findViewById(R.id.mochaFlavor);
        addQty = findViewById(R.id.addQty);
        subtractQty = findViewById(R.id.minusQty);
        addToOrder = findViewById(R.id.addToOrder);
        tv_qty = findViewById(R.id.qty);
        subTotal = findViewById(R.id.subtotalEditText);
        tv_qty.setText(String.valueOf(tempQty));
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        String [] cupSize = getResources().getStringArray(R.array.cupSize);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, cupSize);
        Spinner spinner = findViewById(R.id.coffeeSizes);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String selectedValue = (String) adapterView.getItemAtPosition(position);
                tempCoffeeObj.setCupSize(position);
                tempCoffeeObj.setQuantity(tempQty);
                updateSubTotal();
                Toast.makeText(getApplicationContext(), "Selected size: " + selectedValue, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //nothing
            }
        });
        handleAddAndMinusBtn();
        handleCheckBox(addIns);
        handleAddToOrderBtn();
    }

    /**
     * add to order button event handler
     */
    private void handleAddToOrderBtn() {
        addToOrder.setOnClickListener(view -> {
            AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
            alert.setTitle("Add to order");
            alert.setMessage("Click Yes to Add Order or NO to cancel");
            //handle the "YES" click
            alert.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(view.getContext(),
                             "Your Coffee(s) added.", Toast.LENGTH_LONG).show();
                    Coffee coffee = new Coffee(tempCoffeeObj.getCUP_SIZE(),addIns,tempQty);
                    if(!DataManager.getInstance().getItemsList().contains(coffee)) {
                        DataManager.getInstance().getItemsList().add(coffee);
                    }
                    else{
                        for (MenuItem item: DataManager.getInstance().getItemsList()) {
                            if (item instanceof Coffee c) {
                                if (c.equals(coffee)) {
                                    int newQty = c.getQty() + tempQty;
                                    c.setQuantity(newQty);
                                }
                            }
                        }
                    }
                }
                //handle the "NO" click
            }).setNegativeButton("no", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(view.getContext(),
                             "Coffee(s) not added.", Toast.LENGTH_LONG).show();
                }
            });
            AlertDialog dialog = alert.create();
            dialog.show();
        });
    }

    /**
     * quantity event handler
     */
    public void handleAddAndMinusBtn(){
        addQty.setOnClickListener(view -> {
            tempQty++;
            if(tempQty >= 10){
                tempQty = 10;
            }
            tv_qty.setText(String.valueOf(tempQty));
            tempCoffeeObj.setQuantity(tempQty);
            updateSubTotal();
        });
        subtractQty.setOnClickListener(view -> {
            if(tempQty > 1) {
                tempQty--;
                tv_qty.setText(String.valueOf(tempQty));
            }
            tempCoffeeObj.setQuantity(tempQty);
            updateSubTotal();
        });
    }

    /**
     * check box event handler
     * @param addIns temp addIns based on checkbox selection
     */
    public void handleCheckBox(ArrayList<String> addIns){
        black.setChecked(true);
        CompoundButton.OnCheckedChangeListener listener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(compoundButton.getId() == R.id.sweetCreamFlavor){
                    if(isChecked){
                        addIns.add("Sweet Cream");
                        addIns.remove("Black");
                        black.setChecked(false);
                        black.setEnabled(false);
                    }else{
                        addIns.remove("Sweet Cream");
                        black.setEnabled(true);
                        black.setChecked(true);
                    }
                }
                else if(compoundButton.getId() == R.id.black){
                    if(isChecked){
                        addIns.add("Black");
                    }else{
                        addIns.add("Black");
                    }
                }
                else if(compoundButton.getId() == R.id.frenchVanillaFlavor){
                    if(isChecked){
                        addIns.add("French Vanilla");
                    }else{
                        addIns.remove("French Vanilla");
                    }
                }
                else if(compoundButton.getId() == R.id.caramelFlavor){
                    if(isChecked){
                        addIns.add("Caramel");
                    }else{
                        addIns.remove("Caramel");
                    }
                }
                else if(compoundButton.getId() == R.id.mochaFlavor){
                    if(isChecked){
                        addIns.add("Mocha");
                    }
                    else{
                        addIns.remove("Mocha");
                    }
                }
                else if(compoundButton.getId() == R.id.irishCreamFlavor){
                    if(isChecked){
                        addIns.add("Irish Cream");
                    }
                    else{
                        addIns.remove("Irish Cream");
                    }
                }
                tempCoffeeObj.setAddIns(addIns);
                updateSubTotal();
            }
        };
        black.setOnCheckedChangeListener(listener);
        sweet_Cream.setOnCheckedChangeListener(listener);
        french_Vanilla.setOnCheckedChangeListener(listener);
        irish_Cream.setOnCheckedChangeListener(listener);
        caramel.setOnCheckedChangeListener(listener);
        mocha.setOnCheckedChangeListener(listener);
    }

    /**
     * fires the Amount to be displayed for coffee
     */
    private void updateSubTotal(){
        subTotal.setText(df.format(tempCoffeeObj.itemPrice()));
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
/*public void handleCheckBox(){
        black.setTag("black");
        sweet_Cream.setTag("sweet_Cream");
        french_Vanilla.setTag("french_Vanilla");
        irish_Cream.setTag("irish_Cream");
        caramel.setTag("caramel");
        mocha.setTag("mocha");
        black.setChecked(true);
        ArrayList<String> addIns = new ArrayList<>();
        View.OnClickListener checkboxListener = v -> {
            String checkboxTag = v.getTag().toString(); // Getting tag for the clicked checkbox
            switch (checkboxTag) { // Determining which checkbox was clicked based on the tag
                case "sweet_Cream":
                    if(sweet_Cream.isChecked()) {
                        addIns.add("Sweet Cream");
                        black.setChecked(false);
                        black.setEnabled(false);
                    }
                    else{
                        black.setEnabled(true);
                        black.setChecked(true);
                    }
                    break;
                case "black":
                    if(black.isChecked() && black.isEnabled()){
                        addIns.add("Black");
                    }
                case "french_Vanilla":
                    if(french_Vanilla.isChecked()){
                        addIns.add("French Vanilla");
                    }
                    break;
                case "caramel":
                    if(caramel.isChecked()){
                        addIns.add("Caramel");
                    }
                    break;
                case "mocha":
                    if(mocha.isChecked()){
                        addIns.add("Mocha");
                    }
                    break;
                case "irish_Cream":
                    if(irish_Cream.isChecked()){
                        addIns.add("Irish Cream");
                    }
                    break;
            }
            for(String i: addIns){
                System.out.println(i);
            }
        };
        black.setOnClickListener(checkboxListener);
        french_Vanilla.setOnClickListener(checkboxListener);
        irish_Cream.setOnClickListener(checkboxListener);
        sweet_Cream.setOnClickListener(checkboxListener);
        caramel.setOnClickListener(checkboxListener);
        black.setOnClickListener(checkboxListener);

        //tempCoffeeObj.setAddIns(addIns);
        //updateSubTotal();
    }*/
/*black.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    addIns.add("Black");
                }
                else{
                    addIns.remove("Black");
                }
            }
        });
        sweet_Cream.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

            }
        });*/
/*if(sweet_Cream.isChecked()){
            addIns.add("Sweet Cream");
            black.setChecked(false);
            black.setEnabled(false);
            //black.s(true);
        }
        else {
            black.setEnabled(true);
        }
        if(caramel.isChecked()){
            addIns.add("Caramel");
        }
        if(irish_Cream.isChecked()){
            addIns.add("Irish Cream");
        }
        if(french_Vanilla.isChecked()){
            addIns.add("French Vanilla");
        }
        if(mocha.isChecked()){
            addIns.add("Mocha");
        }*/