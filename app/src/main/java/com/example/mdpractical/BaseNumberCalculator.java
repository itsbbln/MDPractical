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

public class BaseNumberCalculator extends AppCompatActivity {

    private EditText inputNumber;
    private Button convertButton;
    private TextView resultBinary, resultOctal, resultHex;
    private Spinner modeSpinner, typeSpinner, resultOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_number_calculator);

        // UI Components
        modeSpinner = findViewById(R.id.modeSpinner);
        typeSpinner = findViewById(R.id.type);
        resultOptions = findViewById(R.id.resultOptions);

        inputNumber = findViewById(R.id.inputNumber);
        convertButton = findViewById(R.id.convertButton);
        resultBinary = findViewById(R.id.resultBinary);
        resultOctal = findViewById(R.id.resultOctal);
        resultHex = findViewById(R.id.resultHex);

        // ✅ Set spinner default to "Base Number Calculator"
        modeSpinner.setSelection(1); // 0=Basic, 1=BaseNum, 2=Unit

        // Mode switching spinner
        modeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected = parent.getItemAtPosition(position).toString();

                if (selected.equals("Basic Calculator")) {
                    startActivity(new Intent(BaseNumberCalculator.this, MainActivity.class));
                    finish();
                } else if (selected.equals("Unit Converter")) {
                    startActivity(new Intent(BaseNumberCalculator.this, UnitConverter.class));
                    finish();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        // Convert Button Click
        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = inputNumber.getText().toString().trim();

                if (input.isEmpty()) {
                    inputNumber.setError("Please enter a number");
                    return;
                }

                try {
                    // Which type user selected (Decimal, Binary, Octal, Hex)
                    String selectedType = typeSpinner.getSelectedItem().toString();
                    int decimal = 0;

                    switch (selectedType) {
                        case "Decimal":
                            decimal = Integer.parseInt(input, 10);
                            break;
                        case "Binary":
                            decimal = Integer.parseInt(input, 2);
                            break;
                        case "Octal":
                            decimal = Integer.parseInt(input, 8);
                            break;
                        case "Hexadecimal":
                            decimal = Integer.parseInt(input, 16);
                            break;
                    }

                    // Conversion from decimal to others
                    String binary = Integer.toBinaryString(decimal);
                    String octal = Integer.toOctalString(decimal);
                    String hex = Integer.toHexString(decimal).toUpperCase();

                    // ✅ Apply result option filter
                    String resultOption = resultOptions.getSelectedItem().toString();

                    if (resultOption.equals("Show All")) {
                        resultBinary.setVisibility(View.VISIBLE);
                        resultOctal.setVisibility(View.VISIBLE);
                        resultHex.setVisibility(View.VISIBLE);

                        resultBinary.setText("Binary: " + binary);
                        resultOctal.setText("Octal: " + octal);
                        resultHex.setText("Hexadecimal: " + hex);

                    } else if (resultOption.equals("Binary Only")) {
                        resultBinary.setVisibility(View.VISIBLE);
                        resultOctal.setVisibility(View.GONE);
                        resultHex.setVisibility(View.GONE);

                        resultBinary.setText("Binary: " + binary);

                    } else if (resultOption.equals("Octal Only")) {
                        resultBinary.setVisibility(View.GONE);
                        resultOctal.setVisibility(View.VISIBLE);
                        resultHex.setVisibility(View.GONE);

                        resultOctal.setText("Octal: " + octal);

                    } else if (resultOption.equals("Hexadecimal Only")) {
                        resultBinary.setVisibility(View.GONE);
                        resultOctal.setVisibility(View.GONE);
                        resultHex.setVisibility(View.VISIBLE);

                        resultHex.setText("Hexadecimal: " + hex);
                    }

                } catch (NumberFormatException e) {
                    inputNumber.setError("Invalid " + typeSpinner.getSelectedItem().toString() + " number");
                }
            }
        });
    }
}
