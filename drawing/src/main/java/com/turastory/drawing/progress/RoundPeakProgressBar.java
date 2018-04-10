package com.turastory.drawing.progress;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.turastory.drawing.R;
import com.turastory.drawing.Section;
import com.turastory.drawing.util.MathUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by tura on 2018-04-05.
 * <p>
 * RoundProgressBar with indicator.
 * <p>
 * TODO: add section
 */

public class RoundPeakProgressBar extends View {
    
    public static final float DEFAULT_HEIGHT_SET = 0;
    
    public static final float DEFAULT_INDICATOR_HEIGHT_RATIO = 0.2f;
    public static final float MAX_INDICATOR_HEIGHT_RATIO = 0.5f;
    
    private Rect baseRect;
    private Rect indicatorRect;
    private Rect progressBackgroundRect;
    private Rect progressForegroundRect;
    
    private Paint indicatorPaint;
    private Path indicatorPath;
    
    private Paint sectionDividerPaint;
    private Path sectionDividerPath;
    
    private List<Section> sections = new ArrayList<>();
    
    // set from users
    
    private float progress;
    private float max;
    
    private float radius;
    
    private float indicatorHeight;
    private int indicatorColor;
    
    private int sectionDividerColor;
    private int progressForegroundColor;
    private int progressBackgroundColor;
    
    public RoundPeakProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        
        TypedArray a = context.getTheme().obtainStyledAttributes(
            attrs, R.styleable.RoundPeakProgressBar, 0, 0);
        
        try {
            progress = a.getFloat(R.styleable.RoundPeakProgressBar_progress, 0);
            max = a.getFloat(R.styleable.RoundPeakProgressBar_max, 100);
            radius = a.getDimension(R.styleable.RoundPeakProgressBar_radius, 0);
            indicatorHeight = a.getDimension(R.styleable.RoundPeakProgressBar_indicatorHeight, DEFAULT_HEIGHT_SET);
            indicatorColor = a.getColor(R.styleable.RoundPeakProgressBar_indicatorColor, Color.BLACK);
            sectionDividerColor = a.getColor(R.styleable.RoundPeakProgressBar_sectionDividerColor, Color.WHITE);
            progressForegroundColor = a.getColor(R.styleable.RoundPeakProgressBar_progressForegroundColor, Color.BLACK);
            progressBackgroundColor = a.getColor(R.styleable.RoundPeakProgressBar_progressBackgroundColor, Color.GRAY);
        } finally {
            a.recycle();
        }
        
        init();
    }
    
    private void init() {
        // rect
        baseRect = new Rect();
        indicatorRect = new Rect();
        progressBackgroundRect = new Rect();
        progressForegroundRect = new Rect();
        
        // indicator paint
        indicatorPaint = new Paint();
        indicatorPaint.setColor(indicatorColor);
        indicatorPaint.setStyle(Paint.Style.FILL);
        indicatorPath = new Path();
        
        // divider paint
        sectionDividerPaint = new Paint();
        sectionDividerPaint.setColor(sectionDividerColor);
        sectionDividerPaint.setStyle(Paint.Style.FILL);
        sectionDividerPath = new Path();
    }
    
    
    // getters
    
    public float getProgress() {
        return progress;
    }
    
    public float getMax() {
        return max;
    }
    
    
    // setters
    
    public void setProgress(float progress) {
        needInvalidate(() -> this.progress = progress);
    }
    
    public void setMax(float max) {
        needInvalidate(() -> this.max = max);
    }
    
    public void setRadius(float radius) {
        needInvalidate(() -> this.radius = radius);
    }
    
    public void setSectionDividerColor(int sectionDividerColor) {
        needInvalidate(() -> {
            this.sectionDividerColor = sectionDividerColor;
            sectionDividerPaint.setColor(sectionDividerColor);
        });
    }
    
    public void setIndicatorColor(int indicatorColor) {
        needInvalidate(() -> {
            this.indicatorColor = indicatorColor;
            indicatorPaint.setColor(indicatorColor);
        });
    }
    
    public void setProgressForegroundColor(int progressForegroundColor) {
        needInvalidate(() -> this.progressForegroundColor = progressForegroundColor);
    }
    
    public void setProgressBackgroundColor(int progressBackgroundColor) {
        needInvalidate(() -> this.progressBackgroundColor = progressBackgroundColor);
    }
    
    public void addSection(float... ratio) {
        Section[] sections = new Section[ratio.length];
        for (int i = 0; i < sections.length; i++)
            sections[i] = new Section(ratio[i]);
        addSection(sections);
    }
    
    public void addSection(Section... section) {
        needInvalidate(() -> sections.addAll(Arrays.asList(section)));
    }
    
    public void clearSection() {
        needInvalidate(() -> sections.clear());
    }
    
    private void needInvalidate(Runnable runnable) {
        runnable.run();
        invalidate();
        requestLayout();
    }
    
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        
        Log.e("asdf", "padding left: " + getPaddingLeft());
        Log.e("asdf", "padding right: " + getPaddingRight());
        Log.e("asdf", "padding bottom: " + getPaddingBottom());
        Log.e("asdf", "padding top: " + getPaddingTop());
        
        // 1. Measured width and height don't affect by padding or margin.
        // 2. MeasuredSpec.getSize(###) == getMeasured###() in this method.
        
        Log.e("asdf", "Measured width: " + MeasureSpec.getSize(widthMeasureSpec));
        Log.e("asdf", "Measured height: " + MeasureSpec.getSize(heightMeasureSpec));
        
        Log.e("asdf", "Measured width by function: " + getMeasuredWidth());
        Log.e("asdf", "Measured height by function: " + getMeasuredHeight());
        
        float height = MeasureSpec.getSize(widthMeasureSpec);
        
        if (indicatorHeight == DEFAULT_HEIGHT_SET)
            indicatorHeight = height * DEFAULT_INDICATOR_HEIGHT_RATIO;
        
        indicatorHeight = MathUtil.clamp(indicatorHeight, 0, height * MAX_INDICATOR_HEIGHT_RATIO);
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        
        getDrawingRect(baseRect);
        Log.e("asdf", "DrawingRect -> " + baseRect.toString());
        
        // Ready rect for draw.
        readyIndicatorRect(baseRect);
        readyProgressRect(baseRect);
        Log.e("asdf", "IndicatorRect -> " + indicatorRect.toString());
        Log.e("asdf", "ProgressRect -> " + progressBackgroundRect.toString());
        
        drawProgressBackground(canvas);
        drawProgressForeground(canvas);
        drawSections(canvas);
        drawIndicator(canvas);
        
        canvas.restore();
    }
    
    protected void drawSections(Canvas canvas) {
        for (Section section : sections)
            drawSection(canvas, section);
    }
    
    private void drawSection(Canvas canvas, Section section) {
        float baseX = getWidth() * section.ratio;
        float sectionDividerHeight = progressBackgroundRect.height() * 0.2f;
        float dx = halfWidth(sectionDividerHeight);
        
        // top side
        sectionDividerPath.reset();
        sectionDividerPath.moveTo(baseX, indicatorHeight + sectionDividerHeight);
        sectionDividerPath.lineTo(baseX - dx, indicatorHeight);
        sectionDividerPath.lineTo(baseX + dx, indicatorHeight);
        sectionDividerPath.close();
        canvas.drawPath(sectionDividerPath, sectionDividerPaint);
        
        // bottom side
        sectionDividerPath.reset();
        sectionDividerPath.moveTo(baseX, progressBackgroundRect.bottom - sectionDividerHeight);
        sectionDividerPath.lineTo(baseX - dx, progressBackgroundRect.bottom);
        sectionDividerPath.lineTo(baseX + dx, progressBackgroundRect.bottom);
        sectionDividerPath.close();
        canvas.drawPath(sectionDividerPath, sectionDividerPaint);
    }
    
    // draw a indicator - right triangle on progress.
    private void drawIndicator(Canvas canvas) {
        float x = getWidth() * ratio();
        float dx = halfWidth(indicatorHeight);
        
        indicatorPath.reset();
        indicatorPath.moveTo(x, indicatorHeight);
        indicatorPath.lineTo(x - dx, 0);
        indicatorPath.lineTo(x + dx, 0);
        indicatorPath.close();
        canvas.drawPath(indicatorPath, indicatorPaint);
    }
    
    private float halfWidth(float height) {
        return (float) (height / Math.tan(Math.toRadians(60)));
    }
    
    private void readyIndicatorRect(Rect rect) {
        progressBackgroundRect.set(rect.left, rect.top, rect.right, (int) indicatorHeight);
    }
    
    private void readyProgressRect(Rect rect) {
        progressBackgroundRect.set(rect.left, (int) indicatorHeight, rect.right, rect.bottom);
        progressForegroundRect.set(rect.left, (int) indicatorHeight, (int) (rect.right * ratio()), rect.bottom);
    }
    
    private float ratio() {
        return MathUtil.clamp(progress / max, 0, 1);
    }
    
    private void drawProgressBackground(Canvas canvas) {
        GradientDrawable drawable = createGradientDrawable(progressBackgroundColor);
        drawable.setCornerRadius(radius);
        drawable.setBounds(progressBackgroundRect);
        drawable.draw(canvas);
    }
    
    private void drawProgressForeground(Canvas canvas) {
        GradientDrawable drawable = createGradientDrawable(progressForegroundColor);
        drawable.setCornerRadius(radius);
        drawable.setBounds(progressForegroundRect);
        drawable.draw(canvas);
    }
    
    private GradientDrawable createGradientDrawable(int color) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        gradientDrawable.setColor(color);
        return gradientDrawable;
    }
}
