package com.example.trackexpenses;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "ExpenseTracker";
    private EditText itemName;
    private EditText itemAmount;
    private TextView totalExpense;
    private Button addButton;
    private Button clearButton;

    private double totalExpenseAmount = 0.0;
    private SharedPreferences sharedPreferences;

    private ExpenseAdapter expenseAdapter;
    private List<ExpenseItem> expenseItemList;

    ArrayList<ExpenseItem> arrItems = new ArrayList<>();
    ExpenseAdapter adapter;

    private static final String EXPENSE_LIST_KEY = "expense_list";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        itemName = findViewById(R.id.itemname);
        itemAmount = findViewById(R.id.itemamount);
        totalExpense = findViewById(R.id.totalexpense);
        addButton = findViewById(R.id.btn1);
        clearButton = findViewById(R.id.btn2);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        sharedPreferences = getSharedPreferences("ExpenseTracker", MODE_PRIVATE);

        // Load total expense
        totalExpenseAmount = sharedPreferences.getFloat("total_expense", 0.0f);
        totalExpense.setText(String.format("%.2f", totalExpenseAmount));

        // Load the saved list from SharedPreferences
        arrItems = loadExpenseList();
        if (arrItems == null) {
            arrItems = new ArrayList<>();  // Initialize if no saved data
        }

        // Set up RecyclerView and adapter
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ExpenseAdapter((Context) this, arrItems);
        recyclerView.setAdapter(adapter);

        addButton.setOnClickListener(view -> addItem());
        clearButton.setOnClickListener(view -> clearAll());
    }

    private void addItem() {
        String name = itemName.getText().toString();
        String amountText = itemAmount.getText().toString();

        if (name.isEmpty() || amountText.isEmpty()) {
            Toast.makeText(this, "Please enter a valid item name and amount", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double amount = Double.parseDouble(amountText);
            totalExpenseAmount += amount;
            totalExpense.setText(String.format("%.2f", totalExpenseAmount));

            // Add new item to the list
            arrItems.add(new ExpenseItem(name, amount));

            // Notify the adapter that the data has changed
            adapter.notifyItemInserted(arrItems.size() - 1);

            // Save the updated list and total expense
            saveExpenseList();
            saveExpense(totalExpenseAmount);

            // Clear the input fields
            itemName.getText().clear();
            itemAmount.getText().clear();
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter a valid amount", Toast.LENGTH_SHORT).show();
        }
    }

    private void clearAll() {
        totalExpenseAmount = 0.0;
        totalExpense.setText(String.format("%.2f", totalExpenseAmount));

        arrItems.clear();
        adapter.notifyDataSetChanged();

        // Save the updated list and total expense
        saveExpenseList();
        saveExpense(totalExpenseAmount);
    }

    // Save the list to SharedPreferences as JSON
    private void saveExpenseList() {
        try {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            Gson gson = new Gson();
            String json = gson.toJson(arrItems);
            editor.putString(EXPENSE_LIST_KEY, json);
            editor.apply();
        } catch (Exception e) {
            Log.e(TAG, "Error saving expense list", e);
        }
    }

    // Load the list from SharedPreferences
    private ArrayList<ExpenseItem> loadExpenseList() {
        try {
            Gson gson = new Gson();
            String json = sharedPreferences.getString(EXPENSE_LIST_KEY, null);
            if (json == null) {
                return new ArrayList<>();  // Return empty list if nothing saved
            }
            Type type = new TypeToken<ArrayList<ExpenseItem>>() {}.getType();
            return gson.fromJson(json, type);
        } catch (Exception e) {
            Log.e(TAG, "Error loading expense list", e);
            return new ArrayList<>();  // Return empty list if error occurs
        }
    }

    private void saveExpense(double amount) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat("total_expense", (float) amount);
        editor.apply();
    }
}
