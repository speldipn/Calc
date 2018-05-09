package com.speldipn.example.calc;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Stack;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView exp;
    TextView result;
    TextView aniView;
    String temp = "";
    float aniView_x, aniView_y, aniView_w, aniView_h;
    float cur_x, cur_y;
    ObjectAnimator view1AniX;
    ObjectAnimator view1AniY;
    ObjectAnimator viewAniXRotate;
    ConstraintLayout constraintLayout;
    View prevView = null;
    View fakeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fakeView = new View(this);
        constraintLayout = findViewById(R.id.constraintLayout);
        constraintLayout.addView(fakeView);

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

        aniView = findViewById(R.id.aniView);

        ViewTreeObserver vto = exp.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                aniView_x = aniView.getX();
                aniView_y = aniView.getY();
                aniView_w = aniView.getWidth();
                aniView_h = aniView.getHeight();
            }
        });
    }

    @Override
    public void onClick(View view) {
        boolean runAni = false;

        if (exp.getText().equals("0")) {
            temp = "";
        }

        switch (view.getId()) {
            case R.id.btn1:
                temp += "1";
                runAni = true;
                break;
            case R.id.btn2:
                temp += "2";
                runAni = true;
                break;
            case R.id.btn3:
                temp += "3";
                runAni = true;
                break;
            case R.id.btn4:
                temp += "4";
                runAni = true;
                break;
            case R.id.btn5:
                temp += "5";
                runAni = true;
                break;
            case R.id.btn6:
                temp += "6";
                runAni = true;
                break;
            case R.id.btn7:
                temp += "7";
                runAni = true;
                break;
            case R.id.btn8:
                temp += "8";
                runAni = true;
                break;
            case R.id.btn9:
                temp += "9";
                runAni = true;
                break;
            case R.id.btn0:
                temp += "0";
                runAni = true;
                break;
            case R.id.btnPlus:
                temp += "+";
                runAni = true;
                break;
            case R.id.btnMinus:
                temp += "-";
                runAni = true;
                break;
            case R.id.btnDivide:
                temp += "/";
                runAni = true;
                break;
            case R.id.btnMultiply:
                temp += "*";
                runAni = true;
                break;
            case R.id.btnCancel:
                temp = "0";
                break;
            case R.id.btnDot:
                runAni = true;
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
                runAni = true;
                temp += "(";
                break;
            case R.id.btnRightBracket:
                runAni = true;
                temp += ")";
                break;
            case R.id.all:
                temp = calcParser(temp);
                result.setText(temp);
                break;
        }

        if (runAni) {
            runAnimation(view);
        }

        exp.setText(temp);
    }

    public void runAnimation(View view) {
        int left = view.getLeft();
        int right = view.getRight();
        int top = view.getTop();
        int bottom = view.getBottom();
        fakeView.setLeft(view.getLeft());
        fakeView.setTop(view.getTop());
        fakeView.setRight(view.getRight());
        fakeView.setBottom(view.getBottom());
        fakeView.setVisibility(View.VISIBLE);
        fakeView.setBackgroundColor(Color.GREEN);

//
//        prevView = view;
//        cur_x = view.getX();
//        cur_y = view.getY();
//
//        view1AniX = ObjectAnimator.ofFloat(view, "X",
//                (aniView_x - view.getWidth() / 2) + aniView_w / 2);
//
//        view1AniY = ObjectAnimator.ofFloat(view, "Y",
//                (aniView_y - view.getHeight() / 2) + aniView_h / 2);
//
//        viewAniXRotate = ObjectAnimator.ofFloat(view, "rotation", 0.0f, 1440f);
//
//        AnimatorSet aniSet = new AnimatorSet();
//        aniSet.playTogether(view1AniX, view1AniY, viewAniXRotate);
////        aniSet.setInterpolator(new AccelerateDecelerateInterpolator());
//        aniSet.addListener(new AnimatorListenerAdapter() {
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                if (prevView != null) {
//                    view1AniX = ObjectAnimator.ofFloat(prevView, "X", cur_x);
//                    view1AniY = ObjectAnimator.ofFloat(prevView, "Y", cur_y);
//                    viewAniXRotate = ObjectAnimator.ofFloat(prevView, "rotation", 0.0f, 1440f);
//                    AnimatorSet aniSet = new AnimatorSet();
//                    aniSet.playTogether(view1AniX, view1AniY, viewAniXRotate);
////                    aniSet.setInterpolator(new AccelerateDecelerateInterpolator());
//                    aniSet.setDuration(200);
//                    aniSet.start();
//                }
//            }
//        });
//        aniSet.setDuration(200);
//        aniSet.start();

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
