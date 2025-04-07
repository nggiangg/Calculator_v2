package com.example.calculator_v2;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private CalculatorLogic calculatorLogic; // Đối tượng xử lý logic tính toán
    private StringBuilder currentExpression; // Biểu thức hiện tại

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Khởi tạo các thành phần
        calculatorLogic = new CalculatorLogic();
        currentExpression = new StringBuilder();

        // Liên kết các thành phần giao diện
        TextView displayEdit = findViewById(R.id.display_edit);
        TextView displayResult = findViewById(R.id.display_result);

        // Gắn sự kiện cho các nút
        setupButtonListeners(displayEdit, displayResult);
    }

    private void setupButtonListeners(TextView displayEdit, TextView displayResult) {
        // Nút số
        int[] numberButtonIds = {
                R.id.bt_0, R.id.bt_1, R.id.bt_2, R.id.bt_3, R.id.bt_4,
                R.id.bt_5, R.id.bt_6, R.id.bt_7, R.id.bt_8, R.id.bt_9
        };

        for (int i = 0; i < numberButtonIds.length; i++) {
            int number = i; // Lưu giá trị số
            findViewById(numberButtonIds[i]).setOnClickListener(v -> {
                currentExpression.append(number);
                displayEdit.setText(currentExpression.toString());
            });
        }

        // Nút toán tử
        findViewById(R.id.bt_add).setOnClickListener(v -> appendOperator(Constants.ADD, displayEdit));
        findViewById(R.id.bt_sub).setOnClickListener(v -> appendOperator(Constants.SUBTRACT, displayEdit));
        findViewById(R.id.bt_mul).setOnClickListener(v -> appendOperator(Constants.MULTIPLY, displayEdit));
        findViewById(R.id.bt_div).setOnClickListener(v -> appendOperator(Constants.DIVIDE, displayEdit));
        findViewById(R.id.bt_percent).setOnClickListener(v -> appendOperator(Constants.PERCENT, displayEdit));
        findViewById(R.id.bt_dot).setOnClickListener(v -> appendOperator(Constants.DOT, displayEdit));

        // Nút xóa (DEL)
        findViewById(R.id.bt_del).setOnClickListener(v -> {
            if (currentExpression.length() > 0) {
                currentExpression.deleteCharAt(currentExpression.length() - 1);
                displayEdit.setText(currentExpression.toString());
            }
        });

        // Nút bằng (=)
        findViewById(R.id.bt_equality).setOnClickListener(v -> {
            String result = calculatorLogic.calculate(currentExpression.toString());
            displayResult.setText(result);
        });
    }

    private void appendOperator(String operator, TextView displayEdit) {
        if (currentExpression.length() > 0) {
            currentExpression.append(operator);
            displayEdit.setText(currentExpression.toString());
        }
    }
}