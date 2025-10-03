package com.example.mdpractical;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class BasicCalculator extends AppCompatActivity {

    EditText editText;
    TextView resultText;
    String expression = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.basic_calculator);

        editText = findViewById(R.id.editText2);
        resultText = findViewById(R.id.resultText);

        setNumberClick(R.id.num0, "0");
        setNumberClick(R.id.num1, "1");
        setNumberClick(R.id.num2, "2");
        setNumberClick(R.id.num3, "3");
        setNumberClick(R.id.num4, "4");
        setNumberClick(R.id.num5, "5");
        setNumberClick(R.id.num6, "6");
        setNumberClick(R.id.num7, "7");
        setNumberClick(R.id.num8, "8");
        setNumberClick(R.id.num9, "9");
        setNumberClick(R.id.dot, ".");

        setOperatorClick(R.id.add, "+");
        setOperatorClick(R.id.sub, "-");
        setOperatorClick(R.id.mul, "*");
        setOperatorClick(R.id.div, "/");

        findViewById(R.id.clear_text).setOnClickListener(v -> {
            if (expression.length() > 0) {
                expression = expression.substring(0, expression.length() - 1);
                editText.setText(expression);
            }
        });

        findViewById(R.id.submit).setOnClickListener(v -> {
            try {
                double result = eval(expression);
                resultText.setText(String.valueOf(result));
            } catch (Exception e) {
                resultText.setText("Error");
            }
        });
    }

    private void setNumberClick(int id, String value) {
        findViewById(id).setOnClickListener(v -> {
            expression += value;
            editText.setText(expression);
        });
    }

    private void setOperatorClick(int id, String op) {
        findViewById(id).setOnClickListener(v -> {
            expression += op;
            editText.setText(expression);
        });
    }

    // Evaluate Expression
    public static double eval(final String str) {
        return new Object() {
            int pos = -1, ch;

            void nextChar() { ch = (++pos < str.length()) ? str.charAt(pos) : -1; }

            boolean eat(int charToEat) {
                while (ch == ' ') nextChar();
                if (ch == charToEat) { nextChar(); return true; }
                return false;
            }

            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < str.length()) throw new RuntimeException("Unexpected: " + (char)ch);
                return x;
            }

            double parseExpression() {
                double x = parseTerm();
                for (;;) {
                    if (eat('+')) x += parseTerm();
                    else if (eat('-')) x -= parseTerm();
                    else return x;
                }
            }

            double parseTerm() {
                double x = parseFactor();
                for (;;) {
                    if (eat('*')) x *= parseFactor();
                    else if (eat('/')) x /= parseFactor();
                    else return x;
                }
            }

            double parseFactor() {
                if (eat('+')) return parseFactor();
                if (eat('-')) return -parseFactor();

                double x;
                int startPos = this.pos;
                if ((ch >= '0' && ch <= '9') || ch == '.') {
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = Double.parseDouble(str.substring(startPos, this.pos));
                } else {
                    throw new RuntimeException("Unexpected: " + (char)ch);
                }
                return x;
            }
        }.parse();
    }
}
