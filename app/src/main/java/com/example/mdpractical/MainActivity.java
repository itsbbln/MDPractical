package com.example.mdpractical;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private TextView resultText;
    private Spinner modeSpinner;

    // Number buttons
    private Button num0, num1, num2, num3, num4, num5, num6, num7, num8, num9, dot;
    // Operator buttons
    private Button add, sub, mul, div, openParen, closeParen, equal, clear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.basic_calculator);

        // Spinner
        modeSpinner = findViewById(R.id.modeSpinner) ;
        modeSpinner.setSelection(0); // assuming 0=Basic, 1=BaseNum, 2=Unit
        modeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected = parent.getItemAtPosition(position).toString();

                if (selected.equals("Base Number Calculator")) {
                    startActivity(new Intent(MainActivity.this, BaseNumberCalculator.class));
                    finish();
                } else if (selected.equals("Unit Converter")) {
                    startActivity(new Intent(MainActivity.this, UnitConverter.class));
                    finish();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        // UI references
        editText = findViewById(R.id.editText2);
        resultText = findViewById(R.id.resultText);

        num0 = findViewById(R.id.zero);
        num1 = findViewById(R.id.num1);
        num2 = findViewById(R.id.num2);
        num3 = findViewById(R.id.num3);
        num4 = findViewById(R.id.num4);
        num5 = findViewById(R.id.num5);
        num6 = findViewById(R.id.num6);
        num7 = findViewById(R.id.num7);
        num8 = findViewById(R.id.num8);
        num9 = findViewById(R.id.num9);
        dot = findViewById(R.id.dot);

        add = findViewById(R.id.add);
        sub = findViewById(R.id.sub);
        mul = findViewById(R.id.mul);
        div = findViewById(R.id.div);
        openParen = findViewById(R.id.openP);
        closeParen = findViewById(R.id.closeP);

        equal = findViewById(R.id.equal);
        clear = findViewById(R.id.clear_text);

        // Clear
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText("");
                resultText.setText("0");
            }
        });

        // Digits and dot
        setInputButton(num0, "0");
        setInputButton(num1, "1");
        setInputButton(num2, "2");
        setInputButton(num3, "3");
        setInputButton(num4, "4");
        setInputButton(num5, "5");
        setInputButton(num6, "6");
        setInputButton(num7, "7");
        setInputButton(num8, "8");
        setInputButton(num9, "9");
        setInputButton(dot, ".");

        // Operators
        setInputButton(add, "+");
        setInputButton(sub, "-");
        setInputButton(mul, "*");
        setInputButton(div, "/");
        setInputButton(openParen, "(");
        setInputButton(closeParen, ")");

        // Equal = Evaluate expression
        equal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String expr = editText.getText().toString();
                try {
                    double result = ExpressionEvaluator.evaluate(expr);
                    resultText.setText(String.valueOf(result));
                    editText.setText(String.valueOf(result)); // continue with result
                } catch (Exception e) {
                    resultText.setText("Error");
                }
            }
        });
    }

    // Helper to append input
    private void setInputButton(Button button, final String value) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText(editText.getText().toString() + value);
            }
        });
    }
}
