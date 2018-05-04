package com.speldipn.example.calc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Stack;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView exp;
    TextView result;
    String temp = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        result = findViewById(R.id.result);
        exp = findViewById(R.id.exp);
        for (int i = 0; i < 10; ++i) {
            int resourceId = getResources().getIdentifier("btn" + i, "id", getPackageName());
            findViewById(resourceId).setOnClickListener(this);
        }
        findViewById(R.id.btnPlus).setOnClickListener(this);
        findViewById(R.id.btnMinus).setOnClickListener(this);
        findViewById(R.id.btnDivide).setOnClickListener(this);
        findViewById(R.id.btnMultiply).setOnClickListener(this);
        findViewById(R.id.all).setOnClickListener(this);
        findViewById(R.id.btnCancel).setOnClickListener(this);
        findViewById(R.id.btnDot).setOnClickListener(this);
        findViewById(R.id.btnBack).setOnClickListener(this);
        findViewById(R.id.btnLeftBracket).setOnClickListener(this);
        findViewById(R.id.btnRightBracket).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (exp.getText().equals("0")) {
            temp = "";
        }

        switch (view.getId()) {
            case R.id.btn1:
                temp += "1";
                break;
            case R.id.btn2:
                temp += "2";
                break;
            case R.id.btn3:
                temp += "3";
                break;
            case R.id.btn4:
                temp += "4";
                break;
            case R.id.btn5:
                temp += "5";
                break;
            case R.id.btn6:
                temp += "6";
                break;
            case R.id.btn7:
                temp += "7";
                break;
            case R.id.btn8:
                temp += "8";
                break;
            case R.id.btn9:
                temp += "9";
                break;
            case R.id.btn0:
                temp += "0";
                break;
            case R.id.btnPlus:
                temp += "+";
                break;
            case R.id.btnMinus:
                temp += "-";
                break;
            case R.id.btnDivide:
                temp += "/";
                break;
            case R.id.btnMultiply:
                temp += "*";
                break;xx
            case R.id.btnCancel:
                temp = "0";
                break;
            case R.id.btnDot:
                if (exp.length() <= 1) {
                    if (exp.equals("0")) {
                        temp += "0.";
                    } else {
                        temp += ".";
                    }
                } else {
                    temp += ".";
                }
                break;
            case R.id.btnBack:
                if (temp.length() <= 1) {
                    temp = "0";
                } else {
                    temp = temp.substring(0, temp.length() - 1);
                }
                break;
            case R.id.btnLeftBracket:
                temp += "(";
                break;
            case R.id.btnRightBracket:
                temp += ")";
                break;
            case R.id.all:
                temp = calcParser(temp);
                result.setText(temp);
                break;
        }

        exp.setText(temp);
    }

    public String calcParser(String exp) {
        String copy = new String(exp);
        String temp = "";
        String calced = "";

        Stack<Character> s = new Stack<>();
        for (int i = 0; i < exp.length(); ++i) {
            char ch = exp.charAt(i);
            if (ch == ')') {
                while (s.peek() != '(') {
                    temp = s.pop() + temp;
                }
                s.pop();
                calced = calc(temp);
                copy = copy.replace("(" + temp + ")", calced);
                for (int j = calced.length(); j > 0; --j) {
                    s.push(calced.charAt(j - 1));
                }
                temp = "";
            } else {
                s.push(ch);
            }
        }

        return calc(copy);
    }

    public String calc(String temp) {
        // 5 + 2 * 3
        // 5 + 6
        // 11
        int result = 0;
        String strValue = "";
        ArrayList<String> numList = new ArrayList<>();
        ArrayList<String> operList = new ArrayList<>();
        String[] arr = temp.split("");
        for (int i = 0; i < arr.length; ++i) {
            switch (arr[i]) {
                case "+":
                case "-":
                case "*":
                case "/":
                    numList.add(strValue);
                    operList.add(arr[i]);
                    strValue = "";
                    break;
                default:
                    strValue += arr[i];
            }
        }

        if (strValue.length() > 0) {
            numList.add(strValue);
        }

        for (int i = 0; i < operList.size(); ++i) {
            if (operList.get(i).equals("*")) {
                numList.set(i, doMultiply(numList.get(i), numList.get(i + 1)));
                numList.remove(i + 1);
                operList.remove(i);
                i--;
            } else if (operList.get(i).equals("/")) {
                numList.set(i, doDivide(numList.get(i), numList.get(i + 1)));
                numList.remove(i + 1);
                operList.remove(i);
                i--;
            }
        }

        for (int i = 0; i < operList.size(); ++i) {
            if (operList.get(i).equals("+")) {
                numList.set(i, doPlus(numList.get(i), numList.get(i + 1)));
                numList.remove(i + 1);
                operList.remove(i);
                i--;
            } else if (operList.get(i).equals("-")) {
                numList.set(i, doMinus(numList.get(i), numList.get(i + 1)));
                numList.remove(i + 1);
                operList.remove(i);
                i--;
            }
        }

        return numList.get(0);
    }

    public String doPlus(String a, String b) {
        if (a.indexOf(".") == (-1) && b.indexOf(".") == (-1)) {
            return String.valueOf(Integer.parseInt(a) + Integer.parseInt(b));
        } else {
            return String.valueOf(Double.parseDouble(a) + Double.parseDouble(b));
        }
    }

    public String doMinus(String a, String b) {
        if (a.indexOf(".") == (-1) && b.indexOf(".") == (-1)) {
            return String.valueOf(Integer.parseInt(a) - Integer.parseInt(b));
        } else {
            return String.valueOf(Double.parseDouble(a) - Double.parseDouble(b));
        }
    }

    public String doMultiply(String a, String b) {
        if (a.indexOf(".") == (-1) && b.indexOf(".") == (-1)) {
            return String.valueOf(Integer.parseInt(a) * Integer.parseInt(b));
        } else {
            return String.valueOf(Double.parseDouble(a) * Double.parseDouble(b));
        }
    }

    public String doDivide(String a, String b) {
        if (a.indexOf(".") == (-1) && b.indexOf(".") == (-1)) {
            return String.valueOf(Integer.parseInt(a) / Integer.parseInt(b));
        } else {
            return String.valueOf(Double.parseDouble(a) / Double.parseDouble(b));
        }
    }
}
