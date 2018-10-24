# Calculation

### 동작 시현

![](/screenshot/calc1.gif)

* '(' ')' 괄호 연산이 가능하다.
* 소수점 연산이 가능하다.

#### 애니메이션

* 입력된 값의 버튼에 애니메이션을 적용하기 위해서 ObjectAnimator를 사용하였다.
* 이동, 회전, 투명 3가지 효과를 코드로 구현하여 사용하였으며, 이 방법들을 xml로 정의하여 사용할 수도 있다.

````java
AnimatorSet aniSet = new AnimatorSet();
aniSet.playTogether(view1AniX, view1AniY, viewAniXRotate, viewAniAlpha);
aniSet.setInterpolator(new DecelerateInterpolator());
aniSet.addListener(new AnimatorListenerAdapter() {
  @Override
  public void onAnimationEnd(Animator animation) {
    fakeView.setVisibility(View.GONE);
  }
});
aniSet.setDuration(500);
aniSet.start();
````

*  이동, 회전, 투명 효과를 xml로 정의하면 아래와 같이 사용할 수 있다.
````xml
<?xml version="1.0" encoding="utf-8"?>
<set xmlns:android="http://schemas.android.com/apk/res/android">
    <translate>
        android:duration="1000"
        android:fromXDelta="0"
        android:fromYDelta="0"
        android:toXDelta="200"
        android:toYDelta="0"
    </translate>
    <rotate>
        android:duration="1000"
        android:pivotX="50%"
        android:pivotY="50%"
        android:fromDegrees="0"
        android:toDegrees="720"
    </rotate>
    <alpha>
        android:duration="1000"
        android:fromAlpha="0.0"
        android:toAlpha="1.0"
    </alpha>
</set>
````