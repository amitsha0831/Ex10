package com.example.ex10;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

        String input = etDisplay.getText().toString();

        // If the field is empty, log and do nothing
        if (input.isEmpty()) {
            Log.i("Calculator", "field is empty");
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
                memory   = currentNum;
                firstNum = false;
            } else {
                memory = calculate(memory, currentNum, operator);
                etDisplay.setText(formatResult(memory));
            }

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
                Log.i("Calculator", "no operation chosen yet");
                return;
            }
            memory = calculate(memory, currentNum, operator);
            etDisplay.setText(formatResult(memory));
        }
    }

    double calculate(double num1, double num2, char op) {
        if (op == '+') return num1 + num2;
        if (op == '-') return num1 - num2;
        if (op == '*') return num1 * num2;
        if (op == '/') {
            if (num2 == 0) {
                Log.i("Calculator", "division by zero");
                etDisplay.setText("Error");
                return 0;
            }
            return num1 / num2;
        }
        return 0;
    }

    String formatResult(double result) {
        if (result == (long) result)
            return String.valueOf((long) result);
        return String.valueOf(result);
    }

}