package com.calc.go;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private EditText op1, op2;
    private TextView res, opSign;
    private String selectedOp = "";
    private int defaultResultColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        op1 = findViewById(R.id.editOp1);
        op2 = findViewById(R.id.editOp2);
        res = findViewById(R.id.textResult);
        opSign = findViewById(R.id.textOpSign);

        // Fetch default text color from theme
        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(com.google.android.material.R.attr.colorOnPrimaryContainer, typedValue, true);
        defaultResultColor = typedValue.data;

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
            showError(getString(R.string.error_select));
            return;
        }
        
        String s1 = op1.getText().toString(), s2 = op2.getText().toString();
        if (s1.isEmpty() || s2.isEmpty()) {
            showError(getString(R.string.error_empty));
            return;
        }
        
        try {
            double n1 = Double.parseDouble(s1), n2 = Double.parseDouble(s2), r = 0;
            switch (selectedOp) {
                case "+": r = n1 + n2; break;
                case "-": r = n1 - n2; break;
                case "×": r = n1 * n2; break;
                case "÷": 
                    if (n2 == 0) { showError(getString(R.string.error_div_zero)); return; }
                    r = n1 / n2; 
                    break;
                case "%": 
                    if (n2 == 0) { showError(getString(R.string.error_mod_zero)); return; }
                    r = n1 % n2; 
                    break;
            }
            showResult(String.format(Locale.US, "%s %s %s = %s", fmt(n1), selectedOp, fmt(n2), fmt(r)));
        } catch (Exception e) {
            showError(getString(R.string.error_invalid));
        }
    }

    private void showResult(String result) {
        res.setText(result);
        res.setTextColor(defaultResultColor);
    }

    private void showError(String message) {
        res.setText(message);
        res.setTextColor(getColor(R.color.error));
    }

    private void clearAll() {
        op1.setText("");
        op2.setText("");
        res.setText(R.string.result_placeholder);
        res.setTextColor(defaultResultColor);
        opSign.setText(R.string.op_placeholder);
        selectedOp = "";
        
        // Focus on first operand
        op1.requestFocus();
        
        // Show keyboard
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        }
    }

    private String fmt(double d) {
        if (d == (long) d) return String.format(Locale.US, "%d", (long) d);
        String s = String.format(Locale.US, "%.10f", d);
        return s.replaceAll("0+$", "").replaceAll("\\.$", "");
    }
}
