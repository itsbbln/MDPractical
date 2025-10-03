package com.example.mdpractical;

import android.widget.EditText;

public class BaseNumberCalculator extends AppCompatActivity {
    EditText inputNumber;
    TextView resultBinary, resultOctal, resultHex;
    Button convertButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_number_calculator);

        inputNumber = findViewById(R.id.inputNumber);
        resultBinary = findViewById(R.id.resultBinary);
        resultOctal = findViewById(R.id.resultOctal);
        resultHex = findViewById(R.id.resultHex);
        convertButton = findViewById(R.id.convertButton);

        convertButton.setOnClickListener(v -> {
            try {
                int number = Integer.parseInt(inputNumber.getText().toString());
                resultBinary.setText("Binary: " + Integer.toBinaryString(number));
                resultOctal.setText("Octal: " + Integer.toOctalString(number));
                resultHex.setText("Hexadecimal: " + Integer.toHexString(number).toUpperCase());
            } catch (Exception e) {
                Toast.makeText(this, "Invalid Input", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
