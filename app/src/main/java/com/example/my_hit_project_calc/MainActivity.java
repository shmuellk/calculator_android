package com.example.my_hit_project_calc;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    TextView res;
    double num1 = 0; // First number
    double num2 = 0; // Second number
    char operation = ' '; // Current operation
    boolean lastOperationValid = false; // To track if last operation exists

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        res = findViewById(R.id.textView2);
        updateDisplay(0); // Set initial display to 0
    }

    public void setNumber(View view) {
        Button button = (Button) view;
        String buttonText = button.getText().toString();
        int input = Integer.parseInt(buttonText);

        if (operation == ' ') {
            num1 = num1 * 10 + input;
            updateDisplay(num1);
        } else {
            num2 = num2 * 10 + input;
            String operationDisplay = formatNumber(num1) + operation + formatNumber(num2);
            res.setText(operationDisplay);
        }

        lastOperationValid = false; // New input invalidates last operation
    }

    public void clearDisplay(View view) {
        num1 = 0;
        num2 = 0;
        operation = ' ';
        lastOperationValid = false;
        updateDisplay(0);
    }

    public void add(View view) {
        if (lastOperationValid) {
            operation = '+'; // Keep the operation for repeatable actions
            num2 = 0; // Reset num2 for new input
        } else {
            calculateResult();
            operation = '+';
            num2 = 0;
        }
        res.setText(formatNumber(num1) + "+");
        lastOperationValid = false; // Reset valid operation flag
    }

    public void minus(View view) {
        if (lastOperationValid) {
            operation = '-'; // Keep the operation for repeatable actions
            num2 = 0; // Reset num2 for new input
        } else {
            calculateResult();
            operation = '-';
            num2 = 0;
        }
        res.setText(formatNumber(num1) + "-");
        lastOperationValid = false; // Reset valid operation flag
    }

    public void multiply(View view) {
        if (lastOperationValid) {
            operation = 'x'; // Keep the operation for repeatable actions
            num2 = 0; // Reset num2 for new input
        } else {
            calculateResult();
            operation = 'x';
            num2 = 0;
        }
        res.setText(formatNumber(num1) + "x");
        lastOperationValid = false; // Reset valid operation flag
    }

    public void divide(View view) {
        if (lastOperationValid) {
            operation = '÷'; // Keep the operation for repeatable actions
            num2 = 0; // Reset num2 for new input
        } else {
            calculateResult();
            operation = '÷';
            num2 = 0;
        }
        res.setText(formatNumber(num1) + "÷");
        lastOperationValid = false; // Reset valid operation flag
    }


    public void setRES(View view) {
        if (operation == ' ' && !lastOperationValid) {
            // Do nothing if no prior operation
            return;
        }

        if (!lastOperationValid) {
            // First "=" press, calculate normally
            calculateResult();
            lastOperationValid = true;
        } else {
            // Subsequent "=" presses, repeat last operation
            applyLastOperation();
        }
    }


    private void calculateResult() {
        switch (operation) {
            case '+':
                num1 = num1 + num2;
                break;
            case '-':
                num1 = num1 - num2;
                break;
            case 'x':
                num1 = num1 * num2;
                break;
            case '÷':
                if (num2 == 0) {
                    res.setText("ERROR");
                    num1 = 0;
                    operation = ' ';
                    lastOperationValid = false;
                    return;
                }
                num1 = num1 / num2;
                break;
        }

        updateDisplay(num1);
        lastOperationValid = true; // Mark last operation as valid
    }

    private void applyLastOperation() {
        switch (operation) {
            case '+':
                num1 = num1 + num2;
                break;
            case '-':
                num1 = num1 - num2;
                break;
            case 'x':
                num1 = num1 * num2;
                break;
            case '÷':
                if (num2 == 0) {
                    res.setText("ERROR");
                    num1 = 0;
                    operation = ' ';
                    return;
                }
                num1 = num1 / num2;
                break;
        }

        updateDisplay(num1);
    }

    /**
     * Utility method to format numbers for display.
     * Shows integers if whole, or up to 10 decimal places otherwise.
     */
    private String formatNumber(double number) {
        if (number == Math.floor(number)) {
            return String.valueOf((int) number); // Display as integer
        } else {
            return String.format("%.20f", number).replaceAll("0+$", ""); // Up to 10 decimals
        }
    }

    /**
     * Utility method to update the TextView with a formatted number.
     */
    private void updateDisplay(double value) {
        res.setText(formatNumber(value));
    }
}
