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
    private Spinner modeSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_number_calculator);

        // UI Components
        modeSpinner = findViewById(R.id.modeSpinner);
        inputNumber = findViewById(R.id.inputNumber);
        convertButton = findViewById(R.id.convertButton);
        resultBinary = findViewById(R.id.resultBinary);
        resultOctal = findViewById(R.id.resultOctal);
        resultHex = findViewById(R.id.resultHex);

        // Spinner setup
        modeSpinner = findViewById(R.id.modeSpinner);
        modeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected = parent.getItemAtPosition(position).toString();

                if (selected.equals("Basic Calculator")) {
                    Intent intent = new Intent(BaseNumberCalculator.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else if (selected.equals("Unit Converter")) {
                    Intent intent = new Intent(BaseNumberCalculator.this, UnitConverter.class);
                    startActivity(intent);
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
                    inputNumber.setError("Please enter a decimal number");
                    return;
                }

                try {
                    int decimal = Integer.parseInt(input);

                    // Conversion
                    String binary = Integer.toBinaryString(decimal);
                    String octal = Integer.toOctalString(decimal);
                    String hex = Integer.toHexString(decimal).toUpperCase();

                    // Show results
                    resultBinary.setText("Binary: " + binary);
                    resultOctal.setText("Octal: " + octal);
                    resultHex.setText("Hexadecimal: " + hex);

                } catch (NumberFormatException e) {
                    inputNumber.setError("Invalid number");
                }
            }
        });
    }
}
