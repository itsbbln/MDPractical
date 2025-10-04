package com.example.mdpractical;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class UnitConverter extends AppCompatActivity {

    private Spinner modeSpinner, conversionTypeSpinner, fromUnitSpinner, toUnitSpinner;
    private EditText inputValue;
    private Button convertButton;
    private TextView resultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.unit_converter);

        // Initialize views
        modeSpinner = findViewById(R.id.modeSpinner);
        conversionTypeSpinner = findViewById(R.id.conversionTypeSpinner);
        fromUnitSpinner = findViewById(R.id.fromUnitSpinner);
        toUnitSpinner = findViewById(R.id.toUnitSpinner);
        inputValue = findViewById(R.id.inputValue);
        convertButton = findViewById(R.id.convertButton);
        resultText = findViewById(R.id.resultText);

        // Mode spinner navigation
        modeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected = parent.getItemAtPosition(position).toString();

                if (selected.equals("Basic Calculator")) {
                    Intent intent = new Intent(UnitConverter.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else if (selected.equals("Base Number Calculator")) {
                    Intent intent = new Intent(UnitConverter.this, BaseNumberCalculator.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        // Conversion type spinner setup (Length, Weight, Currency)
        ArrayAdapter<CharSequence> typeAdapter = ArrayAdapter.createFromResource(
                this, R.array.conversionTypes, android.R.layout.simple_spinner_item);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        conversionTypeSpinner.setAdapter(typeAdapter);

        // Populate unit spinners based on type
        conversionTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int arrayId;
                switch (position) {
                    case 0: arrayId = R.array.lengthUnits; break;
                    case 1: arrayId = R.array.weightUnits; break;
                    case 2: arrayId = R.array.currencyUnits; break;
                    default: arrayId = R.array.lengthUnits; break;
                }

                ArrayAdapter<CharSequence> unitAdapter = ArrayAdapter.createFromResource(
                        UnitConverter.this, arrayId, android.R.layout.simple_spinner_item);
                unitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                fromUnitSpinner.setAdapter(unitAdapter);
                toUnitSpinner.setAdapter(unitAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        // Convert button
        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputStr = inputValue.getText().toString().trim();
                if (inputStr.isEmpty()) {
                    inputValue.setError("Please enter a value");
                    return;
                }

                double input = Double.parseDouble(inputStr);
                String type = conversionTypeSpinner.getSelectedItem().toString();
                String from = fromUnitSpinner.getSelectedItem().toString();
                String to = toUnitSpinner.getSelectedItem().toString();

                double result = convertValue(type, from, to, input);
                resultText.setText("Result: " + result);
            }
        });
    }

    // Conversion router
    private double convertValue(String type, String from, String to, double value) {
        if (type.equals("Length")) return convertLength(from, to, value);
        else if (type.equals("Weight")) return convertWeight(from, to, value);
        else if (type.equals("Currency")) return convertCurrency(from, to, value);
        return value;
    }

    // --- Length Conversion --- //
    private double convertLength(String from, String to, double value) {
        double inMeters;
        if (from.equals("Meters")) inMeters = value;
        else if (from.equals("Kilometers")) inMeters = value * 1000;
        else if (from.equals("Centimeters")) inMeters = value / 100;
        else if (from.equals("Inches")) inMeters = value * 0.0254;
        else if (from.equals("Feet")) inMeters = value * 0.3048;
        else inMeters = value;

        if (to.equals("Meters")) return inMeters;
        else if (to.equals("Kilometers")) return inMeters / 1000;
        else if (to.equals("Centimeters")) return inMeters * 100;
        else if (to.equals("Inches")) return inMeters / 0.0254;
        else if (to.equals("Feet")) return inMeters / 0.3048;
        return inMeters;
    }

    // --- Weight Conversion --- //
    private double convertWeight(String from, String to, double value) {
        double inGrams;
        if (from.equals("Kilograms")) inGrams = value * 1000;
        else if (from.equals("Pounds")) inGrams = value * 453.592;
        else if (from.equals("Ounces")) inGrams = value * 28.3495;
        else inGrams = value; // grams

        if (to.equals("Kilograms")) return inGrams / 1000;
        else if (to.equals("Pounds")) return inGrams / 453.592;
        else if (to.equals("Ounces")) return inGrams / 28.3495;
        else if (to.equals("Grams")) return inGrams;
        return inGrams;
    }

    // --- Currency Conversion (PHP ↔ USD) --- //
    private double convertCurrency(String from, String to, double value) {
        double phpToUsd = 0.018;
        double usdToPhp = 56.0;

        if (from.equals(to)) return value;
        if (from.equals("PHP") && to.equals("USD")) return value * phpToUsd;
        if (from.equals("USD") && to.equals("PHP")) return value * usdToPhp;
        return value;
    }
}
