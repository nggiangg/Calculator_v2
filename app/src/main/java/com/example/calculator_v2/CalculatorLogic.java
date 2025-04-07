package com.example.calculator_v2;

import java.util.Stack;

public class CalculatorLogic {

    // Phương thức chính để tính toán biểu thức
    //expression là biểu thức cần tính toán
    public String calculate(String expression) {
        try {
            double result = evaluateExpression(expression);
            return String.valueOf(result);
        } catch (Exception e) {
            return "Error"; // Trả về lỗi nếu biểu thức không hợp lệ
        }
    }

    // Phương thức để xử lý biểu thức (sử dụng Stack)
    private double evaluateExpression(String expression) {
        Stack<Double> numbers = new Stack<>(); // Stack để lưu trữ số
        Stack<Character> operators = new Stack<>();// Stack để lưu trữ toán tử

        int i = 0;
        while (i < expression.length()) {
            char c = expression.charAt(i);// Đọc từng ký tự trong biểu thức

            // Nếu là số, đọc toàn bộ số (bao gồm cả số thập phân)
            if (Character.isDigit(c) || c == '.') {
                StringBuilder sb = new StringBuilder();
                while (i < expression.length() && (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '.')) {
                    sb.append(expression.charAt(i));
                    i++;
                }
                numbers.push(Double.parseDouble(sb.toString()));// Chuyển đổi chuỗi thành số và đẩy vào Stack
                continue;
            }
            // Nếu là toán tử %
            if (c == '%') {
                if (!numbers.isEmpty()) {
                    double value = numbers.pop();
                    numbers.push(value / 100); // Chuyển đổi giá trị thành phần trăm
                }
                i++;
                continue;
            }
            // Nếu là toán tử, xử lý toán tử
            if (c == '+' || c == '-' || c == '*' || c == '/' ) {
                while (!operators.isEmpty() && precedence(operators.peek()) >= precedence(c)) {
                    double b = numbers.pop();// Lấy toán hạng thứ hai
                    double a = numbers.pop();// Lấy toán hạng thứ nhất
                    char op = operators.pop();
                    numbers.push(applyOperator(a, b, op));
                }
                operators.push(c);
            }
            i++;
        }

        // Xử lý các toán tử còn lại trong Stack
        while (!operators.isEmpty()) {
            double b = numbers.pop();
            double a = numbers.pop();
            char op = operators.pop();
            numbers.push(applyOperator(a, b, op));
        }

        return numbers.pop();
    }

    // Xác định độ ưu tiên của toán tử
    private int precedence(char operator) {
        switch (operator) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
            case '%':
                return 2;
            default:
                return -1;
        }
    }

    // Thực hiện phép toán
    private double applyOperator(double a, double b, char operator) {
        switch (operator) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                if (b == 0) throw new ArithmeticException(Constants.ERROR_DIVIDE_BY_ZERO);
                return a / b;
            default:
                throw new IllegalArgumentException(Constants.ERROR_INVALID_EXPRESSION);
        }
    }
}