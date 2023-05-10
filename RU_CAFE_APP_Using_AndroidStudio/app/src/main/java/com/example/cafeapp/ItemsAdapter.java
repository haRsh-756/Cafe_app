package com.example.cafeapp;

import android.content.Context;
import android.content.DialogInterface;
import android.icu.text.DecimalFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * class that manages the ItemAdapter activity using Recycler View
 * @author harsh_patel, giancarlo_andretta
 */
public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemsHolder>{

    /**
     * context instance
     */
    private Context context;
    /**
     * fixed decimal format instance
     */
    private DecimalFormat df = new DecimalFormat("$#0.00");
    /**
     * flavors list based on donut type selection
     */
    private ArrayList<DataHelper> options;

    /**
     * itemsAdapter default constructor
     * @param context context instance
     * @param options flavors option instance of DataHelper
     */
    public ItemsAdapter(Context context,ArrayList<DataHelper> options){
        this.context = context;
        this.options = options;
    }

    /**
     * creates cardView as a recycler view which is scrollable
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return new ItemHolder object
     */
    @NonNull
    @Override
    public ItemsAdapter.ItemsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_view, parent, false);
        return new ItemsHolder(view);
    }

    /**
     * responsive cardView which can be interactive by user
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull ItemsAdapter.ItemsHolder holder, int position) {
        final DataHelper item = options.get(position);
        holder.tv_name.setText(item.getName());
        holder.tv_qty.setText(String.valueOf(item.getQuantity()));
        holder.subTotal.setText(df.format(0));
        holder.btn_add.setOnClickListener(v -> {
            int quantity = item.getQuantity();
            if(quantity >= 10){
                item.setQuantity(10);
            }
            else {
                item.setQuantity(quantity + 1);
            }
            holder.tv_qty.setText(String.valueOf(item.getQuantity()));
            holder.subTotal.setText(df.format(item.getQuantity() * item.getPrice()));
        });
        holder.btn_minus.setOnClickListener(v -> {
            int quantity = item.getQuantity();
            if (quantity > 0) {
                item.setQuantity(quantity - 1);
                holder.tv_qty.setText(String.valueOf(item.getQuantity()));
                holder.subTotal.setText(df.format(item.getQuantity() * item.getPrice()));
            }
        });
        holder.btn_addToOrder.setOnClickListener(view -> {
            addToOrder(view,holder,item);
        });
    }

    /**
     * addToOrder button event handler
     * @param itemView view instance
     * @param holder itemsAdapter instance
     * @param currentItem Datahelper object instance
     */
    private void addToOrder(@NonNull View itemView, @NonNull ItemsAdapter.ItemsHolder holder, DataHelper currentItem){
        AlertDialog.Builder alert = new AlertDialog.Builder(itemView.getContext());
        alert.setTitle("Add to order");
        alert.setMessage(holder.tv_name.getText().toString());
        //handle the "YES" click
        alert.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(itemView.getContext(),
                        holder.tv_name.getText().toString() + " added.", Toast.LENGTH_LONG).show();
                //DataManager dataManager = DataManager.getInstance();
                //List<MenuItem> itemList = dataManager.getItemsList();
                //dataManager.setItemsList(itemList);
                Donut newDonut = new Donut(DonutType.valueOf(currentItem.getDonutType()),
                        currentItem.getName(),currentItem.getQuantity());
                if(!DataManager.getInstance().getItemsList().contains(newDonut)) {
                    DataManager.getInstance().getItemsList().add(newDonut);
                }
                else{
                    for (MenuItem item: DataManager.getInstance().getItemsList()) {
                        if (item instanceof Donut d) {
                            if (d.equals(newDonut)) {
                                int newQty = d.getQty() + currentItem.getQuantity();
                                d.setQty(newQty);
                            }
                        }
                    }
                }
                currentItem.setQuantity(0);
                holder.tv_qty.setText(String.valueOf(currentItem.getQuantity()));
                holder.subTotal.setText(df.format(currentItem.getQuantity() * currentItem.getPrice()));
            }
            //handle the "NO" click
        }).setNegativeButton("no", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(itemView.getContext(),
                        holder.tv_name.getText().toString() + " not added.", Toast.LENGTH_LONG).show();
            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();
    }

    /**
     * get the itemCount
     * @return itemCount
     */
    @Override
    public int getItemCount() {
        return options.size();
    }

    /**
     * sets the options based on donutType selection
     * @param data
     */
    public void setData(ArrayList<DataHelper> data) {
            this.options = data;
    }

    /**
     * public inner class ItemsHolder that extends Recycler View
     */
    public static class ItemsHolder extends RecyclerView.ViewHolder {
        /**
         * button instance
         */
        private Button btn_add, btn_minus, btn_addToOrder;
        /**
         * constraint layout instance
         */
        private ConstraintLayout parentLayout;
        /**
         * text view instance
         */
        private TextView tv_name, tv_qty;
        /**
         * editText instance
         */
        private EditText subTotal;
        /**
         * ItemsHolder default constructor that sets the button ids
         */
        public ItemsHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.donutName);
            tv_qty = itemView.findViewById(R.id.qty);
            btn_add = itemView.findViewById(R.id.add);
            btn_minus = itemView.findViewById(R.id.remove);
            subTotal = itemView.findViewById(R.id.subTotal);
            btn_addToOrder = itemView.findViewById(R.id.addToOrder);
            //setAddButtonOnClick(itemView);
            //setMinusButtonOnClick(itemView);
        }

    }
}
/*public void setItemsList(List<MenuItem> itemList){
        this.itemList = itemList;
    }

    public List<MenuItem> getItemList() {
        return itemList;
    }*/
/* private void setMinusButtonOnClick(@NonNull View itemView){
            btn_minus.setOnClickListener(view -> {
                count--;
                if(count <= 0){
                    count = 0;
                }
            });
        }
        private void setAddButtonOnClick(@NonNull View itemView) {
            btn_add.setOnClickListener(view -> {
                count++;
                if(count > 10){
                    count = 10;
                }
            });
            /*btn_add.setOnClickListener(view -> {
                AlertDialog.Builder alert = new AlertDialog.Builder(itemView.getContext());
                alert.setTitle("Add to order");
                alert.setMessage(tv_name.getText().toString());
                //handle the "YES" click
                alert.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(itemView.getContext(),
                                tv_name.getText().toString() + " added.", Toast.LENGTH_LONG).show();
                    }
                    //handle the "NO" click
                }).setNegativeButton("no", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(itemView.getContext(),
                                tv_name.getText().toString() + " not added.", Toast.LENGTH_LONG).show();
                    }
                });
                AlertDialog dialog = alert.create();
                dialog.show();
            });*/
//}