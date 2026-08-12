package com.calc.go;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private EditText op1, op2;
    private TextView res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        op1 = findViewById(R.id.editOp1);
        op2 = findViewById(R.id.editOp2);
        res = findViewById(R.id.textResult);

        findViewById(R.id.btnAdd).setOnClickListener(v -> calc("+"));
        findViewById(R.id.btnSubtract).setOnClickListener(v -> calc("-"));
        findViewById(R.id.btnMultiply).setOnClickListener(v -> calc("×"));
        findViewById(R.id.btnDivide).setOnClickListener(v -> calc("÷"));
        findViewById(R.id.btnModulus).setOnClickListener(v -> calc("%"));
        findViewById(R.id.btnAC).setOnClickListener(v -> {
            op1.setText(""); op2.setText(""); res.setText(R.string.result_placeholder);
        });
    }

    private void calc(String op) {
        String s1 = op1.getText().toString(), s2 = op2.getText().toString();
        if (s1.isEmpty() || s2.isEmpty()) { res.setText(R.string.error_empty); return; }
        try {
            double n1 = Double.parseDouble(s1), n2 = Double.parseDouble(s2), r = 0;
            switch (op) {
                case "+": r = n1 + n2; break;
                case "-": r = n1 - n2; break;
                case "×": r = n1 * n2; break;
                case "÷": if (n2 == 0) { res.setText(R.string.error_div_zero); return; } r = n1 / n2; break;
                case "%": if (n2 == 0) { res.setText(R.string.error_mod_zero); return; } r = n1 % n2; break;
            }
            res.setText(String.format(Locale.US, "%s %s %s = %s", fmt(n1), op, fmt(n2), fmt(r)));
        } catch (Exception e) { res.setText(R.string.error_invalid); }
    }

    private String fmt(double d) { return d == (long) d ? String.format(Locale.US, "%d", (long) d) : String.valueOf(d); }
}
