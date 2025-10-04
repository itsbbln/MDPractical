package com.example.mdpractical;

import java.util.Stack;

public class ExpressionEvaluator {

    // Check if char is operator
    private static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '^';
    }

    // Operator precedence
    private static int precedence(char op) {
        switch (op) {
            case '+':
            case '-': return 1;
            case '*':
            case '/': return 2;
            case '^': return 3;
        }
        return -1;
    }

    // Convert infix expression to postfix using Shunting Yard
    private static String infixToPostfix(String expression) {
        StringBuilder output = new StringBuilder();
        Stack<Character> stack = new Stack<Character>();

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);

            // Skip spaces
            if (c == ' ') continue;

            // Number (multi-digit)
            if (Character.isDigit(c) || c == '.') {
                while (i < expression.length() &&
                        (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '.')) {
                    output.append(expression.charAt(i));
                    i++;
                }
                output.append(' ');
                i--;
            }
            // Opening parenthesis
            else if (c == '(') {
                stack.push(c);
            }
            // Closing parenthesis
            else if (c == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    output.append(stack.pop()).append(' ');
                }
                stack.pop();
            }
            // Operator
            else if (isOperator(c)) {
                while (!stack.isEmpty() && precedence(stack.peek()) >= precedence(c)) {
                    output.append(stack.pop()).append(' ');
                }
                stack.push(c);
            }
        }

        while (!stack.isEmpty()) {
            output.append(stack.pop()).append(' ');
        }

        return output.toString();
    }

    // Evaluate postfix expression
    private static double evaluatePostfix(String postfix) {
        Stack<Double> stack = new Stack<Double>();

        for (int i = 0; i < postfix.length(); i++) {
            char c = postfix.charAt(i);

            if (c == ' ') continue;

            // Number
            if (Character.isDigit(c) || c == '.') {
                StringBuilder num = new StringBuilder();
                while (i < postfix.length() &&
                        (Character.isDigit(postfix.charAt(i)) || postfix.charAt(i) == '.')) {
                    num.append(postfix.charAt(i));
                    i++;
                }
                stack.push(Double.parseDouble(num.toString()));
                i--;
            }
            // Operator
            else if (isOperator(c)) {
                double b = stack.pop();
                double a = stack.pop();
                switch (c) {
                    case '+': stack.push(a + b); break;
                    case '-': stack.push(a - b); break;
                    case '*': stack.push(a * b); break;
                    case '/': stack.push(a / b); break;
                    case '^': stack.push(Math.pow(a, b)); break;
                }
            }
        }

        return stack.pop();
    }

    // Public evaluate function
    public static double evaluate(String expression) {
        String postfix = infixToPostfix(expression);
        return evaluatePostfix(postfix);
    }
}
