package com.example.ex10;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText etDisplay;
    Button btnAdd, btnSub, btnMul, btnDiv, btnEquals, btnClear;

    double memory;     // stores the number from the previous step
    char operator;     // stores which operation was chosen: +, -, *, /
    boolean firstNum;  // true = no operation chosen yet

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etDisplay = findViewById(R.id.etDisplay);
        btnAdd    = findViewById(R.id.btnAdd);
        btnSub    = findViewById(R.id.btnSub);
        btnMul    = findViewById(R.id.btnMul);
        btnDiv    = findViewById(R.id.btnDiv);
        btnEquals = findViewById(R.id.btnEquals);
        btnClear  = findViewById(R.id.btnClear);

        memory   = 0;
        operator = 0;
        firstNum = true;
    }

    public void clicked(View view) {

        // Input validation — empty field
        String input = etDisplay.getText().toString();
        if (input.isEmpty()) {
            Toast.makeText(this, "Please enter a number", Toast.LENGTH_SHORT).show();
            return;
        }

        double currentNum = Double.parseDouble(input);
        int id = view.getId();

        // ===== A/C button — reset everything =====
        if (id == R.id.btnClear) {
            etDisplay.setText("");
            memory   = 0;
            operator = 0;
            firstNum = true;
            return;
        }

        // ===== Operation buttons: +, -, *, / =====
        if (id == R.id.btnAdd || id == R.id.btnSub ||
                id == R.id.btnMul || id == R.id.btnDiv) {

            if (firstNum) {
                // This is the first number — save it to memory
                memory   = currentNum;
                firstNum = false;
            } else {
                // A number is already in memory — compute intermediate result
                memory = calculate(memory, currentNum, operator);
                etDisplay.setText(formatResult(memory));
            }

            // Save the new operation and clear the field
            if (id == R.id.btnAdd) operator = '+';
            if (id == R.id.btnSub) operator = '-';
            if (id == R.id.btnMul) operator = '*';
            if (id == R.id.btnDiv) operator = '/';

            etDisplay.setText("");
            return;
        }

        // ===== Equals button =====
        if (id == R.id.btnEquals) {
            if (firstNum) {
                Toast.makeText(this, "Please choose an operation first", Toast.LENGTH_SHORT).show();
                return;
            }
            memory = calculate(memory, currentNum, operator);
            etDisplay.setText(formatResult(memory));
        }
    }

    // Performs the actual calculation based on the operator
    double calculate(double num1, double num2, char op) {
        if (op == '+') return num1 + num2;
        if (op == '-') return num1 - num2;
        if (op == '*') return num1 * num2;
        if (op == '/') {
            if (num2 == 0) {
                Toast.makeText(this, "Error: division by zero!", Toast.LENGTH_SHORT).show();
                return 0;
            }
            return num1 / num2;
        }
        return 0;
    }

    // Displays whole numbers without a decimal point (e.g. 8 instead of 8.0)
    String formatResult(double result) {
        if (result == (long) result)
            return String.valueOf((long) result);
        return String.valueOf(result);
    }

}