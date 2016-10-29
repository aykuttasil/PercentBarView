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
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.afollestad.materialdialogs.MaterialDialog;
import com.aykuttasil.percentbar.models.BarImageModel;
import com.aykuttasil.percentbar.util.PicassoCircleTransform;
import com.aykuttasil.percentbar.util.adapter.MaterialListAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by aykutasil on 18.09.2016.
 */
public class PercentBarView extends View {

    private static final String TAG = PercentBarView.class.getSimpleName();

    Context mContext;
    boolean ButtonClick = false;
    boolean isFinishAnimBarLeft = false;
    boolean isFinishAnimBarRight = false;
    View mAlphaView = null;

    Paint mPaint;
    Paint mPaintBarLeft;
    Paint mPaintBarRight;
    RectF mRectBarLeft;
    RectF mRectBarRight;

    int mColorBarLeft;
    int mColorBarRight;
    int mValueBarLeft;
    int mValueBarRight;
    int mValueBarS;
    int widthBarLeft;
    int widthBarRight;
    long animBarDuration;
    long animAlphaViewDuration;
    float alphaViewValue;
    boolean isAutoShow;

    float animLeftValue;
    float animRightValue;

    public enum BarField {
        LEFT,
        RIGHT
    }

    private List<BarImageModel> mListImages;
    private String ListTitle = "My List";
    private int IMAGES_COUNT = 3;
    private int IMAGES_SIZE = 100;

    public PercentBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {

        this.mContext = context;

        TypedArray ta = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomAnswerPercent, 0, 0);
        this.widthBarLeft = ta.getInt(R.styleable.CustomAnswerPercent_barLeftWidth, 50);
        this.widthBarRight = ta.getInt(R.styleable.CustomAnswerPercent_barRightWidth, 50);
        this.mValueBarLeft = ta.getInt(R.styleable.CustomAnswerPercent_barLeftValue, 0);
        this.mValueBarRight = ta.getInt(R.styleable.CustomAnswerPercent_barRightValue, 0);
        //this.mValueBarS = ta.getInt(R.styleable.CustomAnswerPercent_barSValue, 0);
        this.mColorBarLeft = ta.getColor(R.styleable.CustomAnswerPercent_barLeftColor, Color.RED);
        this.mColorBarRight = ta.getColor(R.styleable.CustomAnswerPercent_barRightColor, Color.BLUE);
        this.animBarDuration = ta.getInt(R.styleable.CustomAnswerPercent_animBarDuration, 500);
        this.animAlphaViewDuration = ta.getInt(R.styleable.CustomAnswerPercent_animAlphaViewDuration, 300);
        this.alphaViewValue = ta.getFloat(R.styleable.CustomAnswerPercent_alphaViewValue, 0.3f);
        this.isAutoShow = ta.getBoolean(R.styleable.CustomAnswerPercent_autoShow, false);
        ta.recycle();
    }

    public void addAlphaView(View hostView) {
        this.mAlphaView = hostView;
    }

    public void setRightBarValue(int val) {
        this.mValueBarRight = val;
    }

    public void setLeftBarWidth(int val) {
        this.widthBarLeft = val;
    }

    public void setRightBarWidth(int val) {
        this.widthBarRight = val;
    }

    public void setLeftBarValue(int val) {
        this.mValueBarLeft = val;
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

    public void setImages(List<BarImageModel> list) {
        this.mListImages = list;
    }

    public void setImagesCount(int count) {
        this.IMAGES_COUNT = count;
    }

    public void setImagesListTitle(String title) {
        this.ListTitle = title;
    }

    public void setImagesListItemSize(int size) {
        this.IMAGES_SIZE = size;
    }

    public void setRightBarColor(int color) {
        this.mColorBarRight = color;
    }

    public void setLeftBarColor(int color) {
        this.mColorBarLeft = color;
    }

    public void setAutoShow(boolean autoShow) {
        this.isAutoShow = autoShow;
    }

    public void showResult() {

        this.ButtonClick = true;
        this.isAutoShow = true;
        isFinishAnimBarLeft = false;
        isFinishAnimBarRight = false;

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
        drawBarLeft(canvas);
        drawBarRight(canvas);
    }

    private void startBar() {

        // Bar LEFT değerini %60 (Sadece 60) haline dönüştürüyoruz.
        int computePercentA = (int) (((float) mValueBarLeft / (float) (mValueBarLeft + mValueBarRight)) * 100);

        // Bar ın gösterileceği yüksekliği belirlemek için toplam yükseklikten yazının yüksekliği değerini çıkartıyoruz (100)
        // ve bu değerin yüzdelik değerini buluyoruz.
        // Örnek : Host View yüksekliği = 720 , bar oranı = %50
        // - 100 = 620 -> en üstteki yazı için yer açıyoruz
        // * (50 / 100) = 310 -> bar ın gösterileceği yükseklik
        final float computeBarLeftValue = ((getHeight() - 130) * computePercentA / 100);

        final ValueAnimator animatorAHeight = ValueAnimator.ofFloat(0, computeBarLeftValue);
        animatorAHeight.setDuration(animBarDuration);

        animatorAHeight.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                animLeftValue = (float) animatorAHeight.getAnimatedValue();
                if ((float) animatorAHeight.getAnimatedValue() == computeBarLeftValue) {
                    isFinishAnimBarLeft = true;
                }
                invalidate();
            }
        });
        animatorAHeight.start();


        int computePercentB = (int) (((float) mValueBarRight / (float) (mValueBarLeft + mValueBarRight)) * 100);
        final float computeBarRightValue = ((getHeight() - 130) * computePercentB / 100);

        final ValueAnimator animatorBHeight = ValueAnimator.ofFloat(0, computeBarRightValue);
        animatorBHeight.setDuration(animBarDuration);

        animatorBHeight.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                animRightValue = (float) animatorBHeight.getAnimatedValue();
                if ((float) animatorBHeight.getAnimatedValue() == computeBarRightValue) {
                    isFinishAnimBarRight = true;
                }
                invalidate();
            }
        });
        animatorBHeight.start();
    }

    private void drawBarLeft(Canvas canvas) {
        //float center = getWidth() / 2;

        if (!isAutoShow) return;

        mPaintBarLeft = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintBarLeft.setColor(mColorBarLeft);


        Resources resources = getResources();
        float scale = resources.getDisplayMetrics().density;
        Log.i(TAG, "Scale: " + scale);

        float startPoint = (getWidth() / 4) - widthBarLeft;
        float endPoint = (getWidth() / 4) + widthBarLeft;

        // Eğer xml içerisinde bar değeri verilmişse animValue yi valueABar a eşitliyoruz.
        if (!ButtonClick) animLeftValue = mValueBarLeft;


        mRectBarLeft = new RectF(startPoint, (getHeight() - animLeftValue), endPoint, getHeight());
        canvas.drawRect(mRectBarLeft, mPaintBarLeft);

        if (isFinishAnimBarLeft) {
            //Logger.i("Anim Bar LEFT finised ");

            int computePercentA = (int) (((float) mValueBarLeft / (float) (mValueBarLeft + mValueBarRight)) * 100);
            float textStart = startPoint + (mRectBarLeft.width() / 2);
            float textEnd = getHeight() - mRectBarLeft.height() - (widthBarLeft / 2);

            Paint percentText = new Paint();
            percentText.setTextSize((int) (17 * scale));
            percentText.setColor(Color.BLACK);
            percentText.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
            percentText.setTextAlign(Paint.Align.CENTER);

            String barText = computePercentA + " %";
            Rect barTextRect = new Rect();
            percentText.getTextBounds(barText, 0, barText.length(), barTextRect);

            canvas.drawText(barText, textStart, textEnd, percentText);

            if (mListImages != null && mListImages.size() > 0) {
                drawImagesLeft();
            }
        }
    }

    private void drawBarRight(Canvas canvas) {

        if (!isAutoShow) return;

        //float center = getWidth() / 2;
        mPaintBarRight = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintBarRight.setColor(mColorBarRight);

        Resources resources = getResources();
        float scale = resources.getDisplayMetrics().density;

        float startPoint = getWidth() - (getWidth() / 4) - widthBarRight;
        float endPoint = getWidth() - (getWidth() / 4) + widthBarRight;

        // Eğer xml içerisinde bar değeri verilmişse animValue yi valueABar a eşitliyoruz.
        if (!ButtonClick) animRightValue = mValueBarRight;
        mRectBarRight = new RectF(startPoint, (getHeight() - animRightValue), endPoint, getHeight());
        canvas.drawRect(mRectBarRight, mPaintBarRight);

        if (isFinishAnimBarRight) {
            int computePercentB = (int) (((float) mValueBarRight / (float) (mValueBarLeft + mValueBarRight)) * 100);
            float textStart = startPoint + (mRectBarRight.width() / 2);
            float textEnd = getHeight() - mRectBarRight.height() - (widthBarRight / 2);

            Paint percentText = new Paint();
            percentText.setTextSize((int) (17 * scale));
            percentText.setColor(Color.BLACK);
            percentText.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
            percentText.setTextAlign(Paint.Align.CENTER);

            String barText = computePercentB + " %";
            Rect barTextRect = new Rect();
            percentText.getTextBounds(barText, 0, barText.length(), barTextRect);

            canvas.drawText(barText, textStart, textEnd, percentText);

            if (mListImages != null && mListImages.size() > 0) {
                drawImagesRight();
            }
        }
    }

    private void drawImagesLeft() {

        final RelativeLayout relativeLayout = (RelativeLayout) getParent();

        Observable.from(mListImages)
                .filter(new Func1<BarImageModel, Boolean>() {
                    @Override
                    public Boolean call(BarImageModel barImageModel) {
                        return barImageModel.getValue() == BarField.LEFT;
                    }
                })
                .take(IMAGES_COUNT)
                .toList()
                .filter(new Func1<List<BarImageModel>, Boolean>() {
                    @Override
                    public Boolean call(List<BarImageModel> barImageModels) {
                        return barImageModels.size() > 0;
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<BarImageModel>>() {
                    @Override
                    public void call(List<BarImageModel> barImageModels) {
                        int index = 0;
                        for (BarImageModel listItem : barImageModels) {

                            ImageView imageView1 = new ImageView(mContext);

                            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_START);
                            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                            layoutParams.leftMargin = 30 - (index % 2 == 0 ? 10 : -10);
                            layoutParams.bottomMargin = index * 50;
                            imageView1.setLayoutParams(layoutParams);
                            relativeLayout.addView(imageView1);

                            Picasso.with(mContext)
                                    .load(listItem.getImageUrl())
                                    .transform(new PicassoCircleTransform())
                                    .resize(IMAGES_SIZE, IMAGES_SIZE)
                                    .centerCrop()
                                    .into(imageView1);

                            index++;
                        }
                        drawAnotherIcon(index, BarField.LEFT);
                    }
                });
    }

    private void drawImagesRight() {

        final RelativeLayout relativeLayout = (RelativeLayout) getParent();

        Observable.from(mListImages)
                .filter(new Func1<BarImageModel, Boolean>() {
                    @Override
                    public Boolean call(BarImageModel barImageModel) {
                        return barImageModel.getValue() == BarField.RIGHT;
                    }
                })
                .take(IMAGES_COUNT)
                .toList()
                .filter(new Func1<List<BarImageModel>, Boolean>() {
                    @Override
                    public Boolean call(List<BarImageModel> barImageModels) {
                        return barImageModels.size() > 0;
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<BarImageModel>>() {
                    @Override
                    public void call(List<BarImageModel> barImageModels) {
                        int index = 0;
                        for (BarImageModel listItem : barImageModels) {

                            ImageView imageView1 = new ImageView(mContext);

                            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_END);
                            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                            layoutParams.rightMargin = 30 - (index % 2 == 0 ? 10 : -10);
                            layoutParams.bottomMargin = index * 50;
                            imageView1.setLayoutParams(layoutParams);
                            relativeLayout.addView(imageView1);

                            Picasso.with(mContext)
                                    .load(listItem.getImageUrl())
                                    .transform(new PicassoCircleTransform())
                                    .resize(IMAGES_SIZE, IMAGES_SIZE)
                                    .centerCrop()
                                    .into(imageView1);

                            index++;
                        }
                        drawAnotherIcon(index, BarField.RIGHT);
                    }
                });
    }

    private void drawAnotherIcon(int index, final BarField barField) {
        RelativeLayout relativeLayout = (RelativeLayout) getParent();
        ImageView imageView = new ImageView(mContext);

        imageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                final List<BarImageModel> listItem = new ArrayList<>();

                rx.Observable.from(mListImages)
                        .filter(new Func1<BarImageModel, Boolean>() {
                            @Override
                            public Boolean call(BarImageModel barImageModel) {
                                return barImageModel.getValue() == barField;
                            }
                        })
                        .subscribe(new Action1<BarImageModel>() {
                            @Override
                            public void call(BarImageModel barImageModel) {
                                listItem.add(barImageModel);
                            }
                        })
                        .unsubscribe();


                MaterialListAdapter adapter = new MaterialListAdapter(mContext, listItem);

                MaterialDialog dialog = new MaterialDialog.Builder(mContext)
                        .title(ListTitle)
                        .adapter(adapter, new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {

                            }
                        }).build();

                dialog.show();
            }
        });

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(barField == BarField.RIGHT ? RelativeLayout.ALIGN_PARENT_END : RelativeLayout.ALIGN_PARENT_START);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        layoutParams.rightMargin = barField == BarField.LEFT ? 30 : 0;
        layoutParams.leftMargin = barField == BarField.RIGHT ? 30 : 0;
        layoutParams.bottomMargin = index * 50;

        imageView.setLayoutParams(layoutParams);

        relativeLayout.addView(imageView);

        Picasso.with(mContext)
                .load(R.drawable.ic_add_circle_indigo_300_48dp)
                .placeholder(R.drawable.ic_add_circle_indigo_300_48dp)
                .resize(IMAGES_SIZE, IMAGES_SIZE)
                .into(imageView);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (getParent() instanceof RelativeLayout) {
            //Logger.i("Parent is RelativeLayout");
        } else {
            Log.i(TAG, "Parent is not RelativeLayout !");
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
