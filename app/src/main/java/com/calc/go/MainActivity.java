package com.calc.go;

import android.os.Bundle;
import android.util.TypedValue;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private TextView res, exp;
    private String cur = "", op = "";
    private Double val = null;
    private boolean reset = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        res = findViewById(R.id.textResult);
        exp = findViewById(R.id.textExpression);

        // Numeric buttons
        int[] nums = {R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9, R.id.btnDot};
        for (int id : nums) findViewById(id).setOnClickListener(v -> {
            if (reset) cur = ""; reset = false;
            String s = ((Button) v).getText().toString();
            if (!(s.equals(".") && cur.contains(".")) && cur.length() < 12) { cur += s; show(false); }
        });

        // Operator buttons
        int[] ops = {R.id.btnAdd, R.id.btnSubtract, R.id.btnMultiply, R.id.btnDivide, R.id.btnModulus};
        for (int id : ops) findViewById(id).setOnClickListener(v -> {
            if (!cur.isEmpty()) { if (val != null && !op.isEmpty()) calc(); else val = Double.parseDouble(cur); }
            op = ((Button) v).getText().toString(); reset = true; show(false);
        });

        findViewById(R.id.btnEqual).setOnClickListener(v -> { calc(); op = ""; reset = true; });
        findViewById(R.id.btnAC).setOnClickListener(v -> { cur = ""; val = null; op = ""; reset = true; show(false); });
        findViewById(R.id.btnBackspace).setOnClickListener(v -> { if (!cur.isEmpty() && !reset) { cur = cur.substring(0, cur.length() - 1); show(false); } });
    }

    private void calc() {
        if (val == null || op.isEmpty() || cur.isEmpty()) return;
        double n2 = Double.parseDouble(cur), r = 0;
        boolean err = false;
        switch (op) {
            case "+": r = val + n2; break;
            case "-": r = val - n2; break;
            case "×": r = val * n2; break;
            case "÷": if (n2 == 0) err = true; else r = val / n2; break;
            case "%": if (n2 == 0) err = true; else r = val % n2; break;
        }
        if (err) { cur = "Error"; val = null; show(true); }
        else { val = r; cur = fmt(r); show(false); }
    }

    private void show(boolean err) {
        res.setText(cur.isEmpty() ? "0" : cur);
        exp.setText(val == null || op.isEmpty() ? "" : fmt(val) + " " + op);
        res.setTextSize(TypedValue.COMPLEX_UNIT_SP, err ? 28 : 56);
        res.setTextColor(getColor(err ? R.color.error : android.R.color.tab_indicator_text));
    }

    private String fmt(double d) { return d == (long) d ? String.format(Locale.US, "%d", (long) d) : String.valueOf(d); }
}
