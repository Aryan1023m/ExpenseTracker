package com.example.trackexpenses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseItem.ViewHolder> {

    // ArrayList<ExpenseItem> arrItems;
    Context context;
    private List<ExpenseItem> arrItems;

    ExpenseAdapter(Context context,ArrayList<ExpenseItem> arrItems){
        this.context = context;
        this.arrItems = arrItems;

    }

    public ExpenseAdapter(MainActivity mainActivity, List<ExpenseItem> expenseItemList) {
    }


    @NonNull
    @Override
    public ExpenseItem.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_expense_item,parent,false);
        ExpenseItem.ViewHolder viewHolder = new ExpenseItem.ViewHolder(view);
        return viewHolder ;
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseItem.ViewHolder holder, int position) {
        holder.itemName.setText((CharSequence) arrItems.get(position).getName());
        holder.itemAmount.setText(String.format("%.2f",arrItems.get(position).getAmount()));

    }

    @Override
    public int getItemCount() {
        return arrItems.size();
    }

    public void setExpenseItems(List<ExpenseItem> expenseItemList) {
        this.arrItems = expenseItemList;
    }

}
