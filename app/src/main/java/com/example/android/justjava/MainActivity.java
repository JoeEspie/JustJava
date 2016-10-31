/**
 * Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 *
 * package com.example.android.justjava;
 */
package com.example.android.justjava;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import android.net.Uri;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    private int quantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        String subject = getString(R.string.name_appendage) + getName();
        composeEmail(subject, createOrderSummary(whippedCream(), chocolate()));
    }

    public void composeEmail(String subject, String content) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, content);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        if (quantity == 100) {
            // Show an error message as a toast
            Toast.makeText(this, getString(R.string.too_many_coffees), Toast.LENGTH_SHORT).show();
            // Exit this method early because there's nothing left to do
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        if (quantity == 1) {
            // Show an error message as a toast
            Toast.makeText(this, getString(R.string.too_few_coffees), Toast.LENGTH_SHORT).show();
            // Exit this method early because there's nothing left to do
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);
    }

    private boolean whippedCream() {
        CheckBox checkbox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        return checkbox.isChecked();
    }

    private boolean chocolate() {
        CheckBox checkbox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        return checkbox.isChecked();
    }

    private String getName() {
        EditText name = (EditText) findViewById(R.id.name_input);
        return name.getText().toString();
    }

    private String createOrderSummary(boolean whip, boolean choc) {
        String message =  getString(R.string.message) + getName();
        message += "\n" + getString(R.string.cream) + whip;
        message += "\n" + getString(R.string.chocolate) + choc;
        message += "\n" + getString(R.string.quantity) + quantity;
        message += "\n" + getString(R.string.thanks_message);
        message += "\n" + getString(R.string.price_message) + calculatePrice(whip, choc);
        return message;
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderTextView.setText(message);
        //Log.i("MainActivity.java", "This is a test of the logging system");
    }

    /**
     * Calculates the price of the order.
     */
    private int calculatePrice(boolean addWhip, boolean addChoc) {
        int price = 5;

        if(addWhip)
            price = ++price;

        if(addChoc)
            price = price + 2;

        return quantity * price;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

}