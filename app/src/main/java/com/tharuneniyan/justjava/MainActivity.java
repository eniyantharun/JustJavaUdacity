package com.tharuneniyan.justjava;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
        isCheckboxCheckedWhippedCream = checkboxWhippedCream.isChecked();
        isCheckboxCheckedChocolate = checkboxChocolate.isChecked();
        int price = calculatePrice(isCheckboxCheckedWhippedCream, isCheckboxCheckedChocolate);
        String userName = nameEditText.getText().toString();
        String priceMessage = createOrderSummary(price,isCheckboxCheckedWhippedCream,isCheckboxCheckedChocolate,userName);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
//        intent.putExtra(Intent.EXTRA_EMAIL, addresses); /to field of the email
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for "+userName);
        intent.putExtra(Intent.EXTRA_TEXT,priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
        displayMessage(priceMessage);

    }

    private String createOrderSummary(int price,boolean isCheckboxCheckedWhippedCream,boolean isCheckboxCheckedChocolate,String userName) {
        String priceMessage = "Name: "+userName+"\nAdd whipped cream?"+isCheckboxCheckedWhippedCream+"\nAdd Chocolate?"+isCheckboxCheckedChocolate+"\nQuantity: "+quantity+"\nTotal: $" + price;
        priceMessage = priceMessage + "\nThank you!";
        return priceMessage;
    }

    private void displayMessage(String message) {
        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
        priceTextView.setText(message);
    }

    public void increment(View view){
        if(quantity<=100) {
            quantity += 1;
            displayQuantity(quantity);
            displayPrice(quantity * 5);
        }
        else
            Toast.makeText(this, "100 is the max coffee", Toast.LENGTH_SHORT).show();
    }

    public void decrement(View view){
        if(quantity>0) {
            quantity -= 1;
            displayQuantity(quantity);
            displayPrice(quantity * 5);
        }
        else
            Toast.makeText(this, "Select at least one coffee", Toast.LENGTH_SHORT).show();
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

    private int calculatePrice(boolean isCheckboxCheckedWhippedCream, boolean isCheckboxCheckedChocolate) {
        int basePrice =  5;
        if(isCheckboxCheckedWhippedCream){
            basePrice = basePrice + 1;
        }
        if(isCheckboxCheckedChocolate){
            basePrice = basePrice + 2;
        }
        return basePrice * quantity;
    }
}