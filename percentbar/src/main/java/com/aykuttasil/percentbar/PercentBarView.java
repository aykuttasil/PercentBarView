package com.aykuttasil.percentbar;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * Created by aykutasil on 18.09.2016.
 */
public class PercentBarView extends View {

    private static final String TAG = PercentBarView.class.getSimpleName();

    Context mContext;
    boolean ButtonClick = false;
    boolean isFinishAnimBarA = false;
    boolean isFinishAnimBarB = false;
    View mAlphaView = null;

    Paint mPaint;
    Paint mPaintBarA;
    Paint mPaintBarB;
    RectF mRectBarA;
    RectF mRectBarB;

    int mColorBarA;
    int mColorBarB;

    int mValueBarA;
    int mValueBarB;
    int mValueBarS;

    int widthBarA;
    int widthBarB;

    float animAValue;
    float animBValue;
    long animBarDuration;
    long animAlphaViewDuration;
    float alphaViewValue;

    public PercentBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {

        setSaveEnabled(true);
        this.mContext = context;

        TypedArray ta = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomAnswerPercent, 0, 0);
        this.widthBarA = ta.getInt(R.styleable.CustomAnswerPercent_barAWidth, 50);
        this.widthBarB = ta.getInt(R.styleable.CustomAnswerPercent_barBWidth, 50);
        this.mValueBarA = ta.getInt(R.styleable.CustomAnswerPercent_barAValue, 0);
        this.mValueBarB = ta.getInt(R.styleable.CustomAnswerPercent_barBValue, 0);
        this.mValueBarS = ta.getInt(R.styleable.CustomAnswerPercent_barSValue, 0);
        this.mColorBarA = ta.getColor(R.styleable.CustomAnswerPercent_barAColor, Color.RED);
        this.mColorBarB = ta.getColor(R.styleable.CustomAnswerPercent_barBColor, Color.BLUE);
        this.animBarDuration = ta.getInt(R.styleable.CustomAnswerPercent_animBarDuration, 500);
        this.animAlphaViewDuration = ta.getInt(R.styleable.CustomAnswerPercent_animAlphaViewDuration, 300);
        this.alphaViewValue = ta.getFloat(R.styleable.CustomAnswerPercent_alphaViewValue, 0.3f);
        ta.recycle();
    }

    public void addAlphaView(View hostView) {
        this.mAlphaView = hostView;
    }

    public void setAValue(int val) {
        this.mValueBarA = val;
    }

    public void setWidthBarA(int val) {
        this.widthBarA = val;
    }

    public void setWidthBarB(int val) {
        this.widthBarB = val;
    }

    public void setBValue(int val) {
        this.mValueBarB = val;
    }

    public void setSValue(int val) {
        this.mValueBarS = val;
    }

    public void setAnimBarDuration(long duration) {
        this.animBarDuration = duration;
    }

    public void setAnimAlphaViewDuration(long duration) {
        this.animAlphaViewDuration = duration;
    }

    public void setAlphaViewValue(float val) {
        this.alphaViewValue = val;
    }

    public void showResult() throws Exception {

        ButtonClick = true;

        if (mAlphaView == null) {
            Log.e(TAG, "alphaView is null");
            startBar();
        } else {
            ValueAnimator alphaViewValueAnimator = ValueAnimator.ofFloat(1f, alphaViewValue);
            alphaViewValueAnimator.setDuration(animAlphaViewDuration);
            alphaViewValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animator) {
                    mAlphaView.setAlpha((float) animator.getAnimatedValue());
                    if ((float) animator.getAnimatedValue() == alphaViewValue) {
                        startBar();
                    }
                }
            });
            alphaViewValueAnimator.start();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawBarA(canvas);
        drawBarB(canvas);
    }

    private void startBar() {

        // Bar A değerini %60 (Sadece 60) haline dönüştürüyoruz.
        int computePercentA = (int) (((float) mValueBarA / (float) (mValueBarA + mValueBarB)) * 100);

        // Bar ın gösterileceği yüksekliği belirlemek için toplam yükseklikten yazının yüksekliği değerini çıkartıyoruz (100)
        // ve bu değerin yüzdelik değerini buluyoruz.
        // Örnek : Host View yüksekliği = 720 , bar oranı = %50
        // - 100 = 620 -> en üstteki yazı için yer açıyoruz
        // * (50 / 100) = 310 -> bar ın gösterileceği yükseklik
        final float computeBarAValue = ((getHeight() - 130) * computePercentA / 100);

        final ValueAnimator animatorAHeight = ValueAnimator.ofFloat(0, computeBarAValue);
        animatorAHeight.setDuration(animBarDuration);

        animatorAHeight.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                animAValue = (float) animatorAHeight.getAnimatedValue();
                if ((float) animatorAHeight.getAnimatedValue() == computeBarAValue) {
                    isFinishAnimBarA = true;
                }
                invalidate();
            }
        });
        animatorAHeight.start();


        int computePercentB = (int) (((float) mValueBarB / (float) (mValueBarA + mValueBarB)) * 100);
        final float computeBarBValue = ((getHeight() - 130) * computePercentB / 100);

        final ValueAnimator animatorBHeight = ValueAnimator.ofFloat(0, computeBarBValue);
        animatorBHeight.setDuration(animBarDuration);

        animatorBHeight.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                animBValue = (float) animatorBHeight.getAnimatedValue();
                if ((float) animatorBHeight.getAnimatedValue() == computeBarBValue) {
                    isFinishAnimBarB = true;
                }
                invalidate();
            }
        });
        animatorBHeight.start();
    }

    private void drawBarA(Canvas canvas) {
        //float center = getWidth() / 2;
        mPaintBarA = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintBarA.setColor(mColorBarA);


        Resources resources = getResources();
        float scale = resources.getDisplayMetrics().density;

        float startPoint = (getWidth() / 4) - widthBarA;
        float endPoint = (getWidth() / 4) + widthBarA;

        // Eğer xml içerisinde bar değeri verilmişse animValue yi valueABar a eşitliyoruz.
        if (!ButtonClick) animAValue = mValueBarA;
        mRectBarA = new RectF(startPoint, (getHeight() - animAValue), endPoint, getHeight());
        canvas.drawRect(mRectBarA, mPaintBarA);

        if (isFinishAnimBarA) {
            //Logger.i("Anim Bar A finised ");

            int computePercentA = (int) (((float) mValueBarA / (float) (mValueBarA + mValueBarB)) * 100);
            float textStart = startPoint + (mRectBarA.width() / 2);
            float textEnd = getHeight() - mRectBarA.height() - (widthBarA / 2);

            Paint percentText = new Paint();
            percentText.setTextSize((int) (17 * scale));
            percentText.setColor(Color.BLACK);
            percentText.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
            percentText.setTextAlign(Paint.Align.CENTER);

            String barText = computePercentA + " %";
            Rect barTextRect = new Rect();
            percentText.getTextBounds(barText, 0, barText.length(), barTextRect);

            canvas.drawText(barText, textStart, textEnd, percentText);

        }
    }

    private void drawBarB(Canvas canvas) {

        //float center = getWidth() / 2;
        mPaintBarB = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintBarB.setColor(mColorBarB);

        Resources resources = getResources();
        float scale = resources.getDisplayMetrics().density;

        float startPoint = getWidth() - (getWidth() / 4) - widthBarB;
        float endPoint = getWidth() - (getWidth() / 4) + widthBarB;

        // Eğer xml içerisinde bar değeri verilmişse animValue yi valueABar a eşitliyoruz.
        if (!ButtonClick) animBValue = mValueBarB;
        mRectBarB = new RectF(startPoint, (getHeight() - animBValue), endPoint, getHeight());
        canvas.drawRect(mRectBarB, mPaintBarB);

        if (isFinishAnimBarB) {
            int computePercentB = (int) (((float) mValueBarB / (float) (mValueBarA + mValueBarB)) * 100);
            float textStart = startPoint + (mRectBarB.width() / 2);
            float textEnd = getHeight() - mRectBarB.height() - (widthBarB / 2);

            Paint percentText = new Paint();
            percentText.setTextSize((int) (17 * scale));
            percentText.setColor(Color.BLACK);
            percentText.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
            percentText.setTextAlign(Paint.Align.CENTER);

            String barText = computePercentB + " %";
            Rect barTextRect = new Rect();
            percentText.getTextBounds(barText, 0, barText.length(), barTextRect);

            canvas.drawText(barText, textStart, textEnd, percentText);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (getParent() instanceof RelativeLayout) {
            //Logger.i("Parent is RelativeLayout");
        } else {
            Log.i(PercentBarView.class.getSimpleName(), "Parent is not RelativeLayout !");
            return;
        }

        //Logger.i("MeasureSpec.toString(width):" + MeasureSpec.toString(widthMeasureSpec));
        //Logger.i("MeasureSpec.toString(height):" + MeasureSpec.toString(heightMeasureSpec));

        // View in height ını parent ı kadar yapıyoruz.
        // Parent Relative Layout olmak zorunda
        //int specHeight = MeasureSpec.makeMeasureSpec(((RelativeLayout) getParent()).getHeight(), MeasureSpec.UNSPECIFIED);
        //int specWidth = MeasureSpec.makeMeasureSpec(((RelativeLayout) getParent()).getWidth(), MeasureSpec.UNSPECIFIED);
        //Logger.i("makeMeasureSpec.toString(width):" + MeasureSpec.toString(specWidth));
        //Logger.i("makeMeasureSpec.toString(height):" + MeasureSpec.toString(specHeight));
        //setMeasuredDimension(specWidth, specHeight);
        //setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
    }


    private void drawBar(Canvas canvas) {
        /*
        String maxValueString = String.valueOf(maxValue);
        Rect maxValueRect = new Rect();
        maxValuePaint.getTextBounds(maxValueString, 0, maxValueString.length(), maxValueRect);
        float barLength = getWidth() - getPaddingRight() - getPaddingLeft() - circleRadius - maxValueRect.width() - spaceAfterBar;

        float barCenter = getBarCenter();

        float halfBarHeight = barHeight / 2;
        float top = barCenter - halfBarHeight;
        float bottom = barCenter + halfBarHeight;
        float left = getPaddingLeft();
        float right = getPaddingLeft() + barLength;
        RectF rect = new RectF(left, top, right, bottom);
        canvas.drawRoundRect(rect, halfBarHeight, halfBarHeight, barBasePaint);


        float percentFilled = (float) valueToDraw / (float) maxValue;
        float fillLength = barLength * percentFilled;
        float fillPosition = left + fillLength;
        RectF fillRect = new RectF(left, top, fillPosition, bottom);
        canvas.drawRoundRect(fillRect, halfBarHeight, halfBarHeight, barFillPaint);

        canvas.drawCircle(fillPosition, barCenter, circleRadius, circlePaint);

        Rect bounds = new Rect();
        String valueString = String.valueOf(Math.round(valueToDraw));
        currentValuePaint.getTextBounds(valueString, 0, valueString.length(), bounds);
        float y = barCenter + (bounds.height() / 2);
        canvas.drawText(valueString, fillPosition, y, currentValuePaint);
        */
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        canvas.drawLine(0, 0, 200, 200, mPaint);
    }

    private void drawLabel(Canvas canvas) {
        /*
        float x = getPaddingLeft();
        //the y coordinate marks the bottom of the text, so we need to factor in the height
        Rect bounds = new Rect();
        labelPaint.getTextBounds(labelText, 0, labelText.length(), bounds);
        float y = getPaddingTop() + bounds.height();

        canvas.drawText(labelText, x, y, labelPaint);
        */
    }

    private void drawRect(Canvas canvas, Rect rect) {
        /*
        float x = getPaddingLeft();
        //the y coordinate marks the bottom of the text, so we need to factor in the height
        Rect bounds = new Rect();
        labelPaint.getTextBounds(labelText, 0, labelText.length(), bounds);
        float y = getPaddingTop() + bounds.height();

        canvas.drawText(labelText, x, y, labelPaint);
        */
        canvas.drawRect(rect, mPaint);
    }

    private int measureHeight(int measureSpec) {


        int size = getPaddingTop() + getPaddingBottom();
        //size += labelPaint.getFontSpacing();
        //float maxValueTextSpacing = maxValuePaint.getFontSpacing();
        //size += Math.max(maxValueTextSpacing, Math.max(barHeight, circleRadius * 2));
        size += PercentBarView.this.getRootView().getMeasuredHeight();
        return resolveSizeAndState(size, measureSpec, 0);
    }

    private int measureWidth(int measureSpec) {
        /*
        int size = getPaddingLeft() + getPaddingRight();
        Rect bounds = new Rect();
        labelPaint.getTextBounds(labelText, 0, labelText.length(), bounds);
        size += bounds.width();

        bounds = new Rect();
        String maxValueText = String.valueOf(maxValue);
        maxValuePaint.getTextBounds(maxValueText, 0, maxValueText.length(), bounds);
        size += bounds.width();
        */
        //Logger.i("getWidth(): " + getWidth());
        return resolveSizeAndState(getWidth(), measureSpec, 0);
    }
}
