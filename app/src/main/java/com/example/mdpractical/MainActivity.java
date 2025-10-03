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
    private Button addButton, subtractButton, multiplyButton, divideButton, equalButton, clearButton;
    private Button num1Button, num2Button, num3Button, num4Button;
    private Button num5Button, num6Button, num7Button, num8Button, num9Button, zeroButton, dotButton;
    private double num1, num2;
    private boolean isAddition, isSubtraction, isMultiplication, isDivision;
    private Spinner modeSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.basic_calculator);

        // Spinner setup
        modeSpinner = findViewById(R.id.modeSpinner);
        modeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected = parent.getItemAtPosition(position).toString();

                if (selected.equals("Base Number Calculator")) {
                    Intent intent = new Intent(MainActivity.this, BaseNumberCalculator.class);
                    startActivity(intent);
                } else if (selected.equals("Unit Converter")) {
                    Intent intent = new Intent(MainActivity.this, UnitConverter.class);
                    startActivity(intent);
                }
                // Kung "Basic Calculator", wala lang kay naa nata diri
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        editText = findViewById(R.id.editText2);
        resultText = findViewById(R.id.resultText);
        clearButton = findViewById(R.id.clear_text);

        addButton = findViewById(R.id.add);
        subtractButton = findViewById(R.id.sub);
        multiplyButton = findViewById(R.id.mul);
        divideButton = findViewById(R.id.div);
        equalButton = findViewById(R.id.equal);

        num1Button = findViewById(R.id.num1);
        num2Button = findViewById(R.id.num2);
        num3Button = findViewById(R.id.num3);
        num4Button = findViewById(R.id.num4);
        num5Button = findViewById(R.id.num5);
        num6Button = findViewById(R.id.num6);
        num7Button = findViewById(R.id.num7);
        num8Button = findViewById(R.id.num8);
        num9Button = findViewById(R.id.num9);
        zeroButton = findViewById(R.id.zero);
        dotButton = findViewById(R.id.dot);

        // Clear button
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText("");
                resultText.setText("0");
                isAddition = false;
                isSubtraction = false;
                isMultiplication = false;
                isDivision = false;
            }
        });

        // Operators
        // ADDITION
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().length() > 0) {
                    num1 = Double.parseDouble(editText.getText().toString());
                    isAddition = true;
                    editText.setText(editText.getText().toString() + "+"); // show operator
                }
            }
        });

        // SUBTRACTION
        subtractButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().length() > 0) {
                    num1 = Double.parseDouble(editText.getText().toString());
                    isSubtraction = true;
                    editText.setText(editText.getText().toString() + "-"); // show operator
                }
            }
        });

    // MULTIPLICATION
        multiplyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().length() > 0) {
                    num1 = Double.parseDouble(editText.getText().toString());
                    isMultiplication = true;
                    editText.setText(editText.getText().toString() + "×"); // show operator
                }
            }
        });

// DIVISION
        divideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().length() > 0) {
                    num1 = Double.parseDouble(editText.getText().toString());
                    isDivision = true;
                    editText.setText(editText.getText().toString() + "÷"); // show operator
                }
            }
        });

        // Equal
        equalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().length() > 0) {
                    num2 = Double.parseDouble(editText.getText().toString());
                    if (isAddition) {
                        resultText.setText(String.valueOf(num1 + num2));
                    } else if (isSubtraction) {
                        resultText.setText(String.valueOf(num1 - num2));
                    } else if (isMultiplication) {
                        resultText.setText(String.valueOf(num1 * num2));
                    } else if (isDivision) {
                        if (num2 != 0) {
                            resultText.setText(String.valueOf(num1 / num2));
                        } else {
                            resultText.setText("Error");
                        }
                    }
                    isAddition = false;
                    isSubtraction = false;
                    isMultiplication = false;
                    isDivision = false;
                }
            }
        });

        // Numbers
        num1Button.setOnClickListener(new NumberClickListener("1"));
        num2Button.setOnClickListener(new NumberClickListener("2"));
        num3Button.setOnClickListener(new NumberClickListener("3"));
        num4Button.setOnClickListener(new NumberClickListener("4"));
        num5Button.setOnClickListener(new NumberClickListener("5"));
        num6Button.setOnClickListener(new NumberClickListener("6"));
        num7Button.setOnClickListener(new NumberClickListener("7"));
        num8Button.setOnClickListener(new NumberClickListener("8"));
        num9Button.setOnClickListener(new NumberClickListener("9"));
        zeroButton.setOnClickListener(new NumberClickListener("0"));

        dotButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String current = editText.getText().toString();
                if (!current.contains(".")) {
                    editText.setText(current + ".");
                }
            }
        });
    }

    // Helper class for numbers
    private class NumberClickListener implements View.OnClickListener {
        private String value;
        NumberClickListener(String value) {
            this.value = value;
        }

        @Override
        public void onClick(View v) {
            editText.setText(editText.getText().toString() + value);
        }
    }
}
