package com.tharuneniyan.justjava;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity = 2;
    TextView price_text_view ;
    CheckBox checkboxWhippedCream,checkboxChocolate;
    boolean isCheckboxCheckedWhippedCream = false,isCheckboxCheckedChocolate = false;
    EditText nameEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        price_text_view = findViewById(R.id.price_text_view);
        checkboxWhippedCream = findViewById(R.id.checkboxWhippedCream);
        checkboxChocolate = findViewById(R.id.checkboxChocolate);
        nameEditText = findViewById(R.id.nameEditText);

    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        int price = calculatePrice();
        isCheckboxCheckedWhippedCream = checkboxWhippedCream.isChecked();
        isCheckboxCheckedChocolate = checkboxChocolate.isChecked();
        String userName = nameEditText.getText().toString();
        String priceMessage = createOrderSummary(price,isCheckboxCheckedWhippedCream,isCheckboxCheckedChocolate,userName);
        displayMessage(priceMessage);
        
    }

    private String createOrderSummary(int price,boolean isCheckboxChecked,boolean isCheckboxCheckedChocolate,String userName) {
        String priceMessage = "Name: "+userName+"\nAdd whipped cream?"+isCheckboxChecked+"\nAdd Chocolate?"+isCheckboxCheckedChocolate+"\nQuantity: "+quantity+"\nTotal: $" + price;
        priceMessage = priceMessage + "\nThank you!";
        return priceMessage;
    }

    private void displayMessage(String message) {
        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
        priceTextView.setText(message);
    }

    public void increment(View view){
        quantity += 1;
        displayQuantity(quantity);
        displayPrice(quantity*5);
    }

    public void decrement(View view){
        if(quantity>0) {
            quantity -= 1;
            displayQuantity(quantity);
            displayPrice(quantity * 5);
        }
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int numberOfCoffees) {
        TextView quantityTextView =  findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + numberOfCoffees);
    }

    private void displayPrice(int number) {
        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
        priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));
    }

    private int calculatePrice() {
        int price = quantity * 5;
        return price;
    }
}