package com.example.trackexpenses;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class ExpenseItem extends AppCompatActivity {

    private String name;
    private double amount;

    public ExpenseItem(String name, double amount) {
        this.name = name;
        this.amount = amount;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public double getAmount() {
        return amount;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemName, itemAmount;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.itemNameTextView);
            itemAmount = itemView.findViewById(R.id.itemAmountTextView);

        }
    }
}