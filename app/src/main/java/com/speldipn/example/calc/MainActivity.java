package com.speldipn.example.calc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

  TextView result;
  String temp = "";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    result = findViewById(R.id.result);
    for (int i = 0; i < 10; ++i) {
      int resourceId = getResources().getIdentifier("btn" + i, "id", getPackageName());
      findViewById(resourceId).setOnClickListener(this);
    }
    findViewById(R.id.btnPlus).setOnClickListener(this);
    findViewById(R.id.btnMinus).setOnClickListener(this);
    findViewById(R.id.btnDivide).setOnClickListener(this);
    findViewById(R.id.btnMultiply).setOnClickListener(this);
    findViewById(R.id.btnCalc).setOnClickListener(this);
    findViewById(R.id.btnCancel).setOnClickListener(this);
  }

  @Override
  public void onClick(View view) {

    if (result.getText().equals("0")) {
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
        break;
      case R.id.btnCancel:
        temp = "0";
        break;
      case R.id.btnCalc:
        temp = calc(temp);
        break;
    }

    result.setText(temp);
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
        int prev = Integer.parseInt(numList.get(i));
        int next = Integer.parseInt(numList.get(i + 1));
        int total = prev * next;
        numList.remove(i + 1);
        numList.set(i, total + "");
        operList.remove(i);
        i--;
      } else if (operList.get(i).equals("/")) {
        int prev = Integer.parseInt(numList.get(i));
        int next = Integer.parseInt(numList.get(i + 1));
        int total = prev / next;
        numList.remove(i + 1);
        numList.set(i, total + "");
        operList.remove(i);
        i--;
      }
    }

    for (int i = 0; i < operList.size(); ++i) {
      if (operList.get(i).equals("+")) {
        int prev = Integer.parseInt(numList.get(i));
        int next = Integer.parseInt(numList.get(i + 1));
        int total = prev + next;
        numList.remove(i + 1);
        numList.set(i, total + "");
        operList.remove(i);
        i--;
      } else if (operList.get(i).equals("-")) {
        int prev = Integer.parseInt(numList.get(i));
        int next = Integer.parseInt(numList.get(i + 1));
        int total = prev - next;
        numList.remove(i + 1);
        numList.set(i, total + "");
        operList.remove(i);
        i--;
      }
    }

    return numList.get(0);
  }
}
