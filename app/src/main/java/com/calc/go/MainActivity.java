package com.calc.go;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private EditText op1, op2;
    private TextView res, opSign;
    private String selectedOp = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        op1 = findViewById(R.id.editOp1);
        op2 = findViewById(R.id.editOp2);
        res = findViewById(R.id.textResult);
        opSign = findViewById(R.id.textOpSign);

        findViewById(R.id.btnAdd).setOnClickListener(v -> setOperator("+"));
        findViewById(R.id.btnSubtract).setOnClickListener(v -> setOperator("-"));
        findViewById(R.id.btnMultiply).setOnClickListener(v -> setOperator("×"));
        findViewById(R.id.btnDivide).setOnClickListener(v -> setOperator("÷"));
        findViewById(R.id.btnModulus).setOnClickListener(v -> setOperator("%"));
        
        findViewById(R.id.btnEqual).setOnClickListener(v -> calculate());
        
        findViewById(R.id.btnAC).setOnClickListener(v -> clearAll());
    }

    private void setOperator(String op) {
        selectedOp = op;
        opSign.setText(op);
    }

    private void calculate() {
        if (selectedOp.isEmpty()) {
            res.setText(R.string.error_select);
            return;
        }
        
        String s1 = op1.getText().toString(), s2 = op2.getText().toString();
        if (s1.isEmpty() || s2.isEmpty()) {
            res.setText(R.string.error_empty);
            return;
        }
        
        try {
            double n1 = Double.parseDouble(s1), n2 = Double.parseDouble(s2), r = 0;
            switch (selectedOp) {
                case "+": r = n1 + n2; break;
                case "-": r = n1 - n2; break;
                case "×": r = n1 * n2; break;
                case "÷": 
                    if (n2 == 0) { res.setText(R.string.error_div_zero); return; }
                    r = n1 / n2; 
                    break;
                case "%": 
                    if (n2 == 0) { res.setText(R.string.error_mod_zero); return; }
                    r = n1 % n2; 
                    break;
            }
            res.setText(String.format(Locale.US, "%s %s %s = %s", fmt(n1), selectedOp, fmt(n2), fmt(r)));
        } catch (Exception e) {
            res.setText(R.string.error_invalid);
        }
    }

    private void clearAll() {
        op1.setText("");
        op2.setText("");
        res.setText(R.string.result_placeholder);
        opSign.setText(R.string.op_placeholder);
        selectedOp = "";
    }

    private String fmt(double d) {
        return d == (long) d ? String.format(Locale.US, "%d", (long) d) : String.valueOf(d);
    }
}
