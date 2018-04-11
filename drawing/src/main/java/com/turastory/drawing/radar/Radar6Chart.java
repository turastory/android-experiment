package com.turastory.drawing.radar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.annimon.stream.function.Consumer;
import com.turastory.drawing.R;
import com.turastory.drawing.util.DpConverter;
import com.turastory.drawing.util.MathUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by tura on 2018-04-10.
 * <p>
 * RadarChart with points and labels..
 * Not that general purpose.
 * <p>
 * Recommended ratio - 9:7 (w:h)
 */
public class Radar6Chart extends View {
    
    public static final float CHART_WIDTH_RATIO = 0.38f;
    public static final float CHART_HEIGHT_RATIO = 0.565f;
    public static final float DELTA_Y_RATIO = 0.24f;
    
    public static final List<String> defaultLabels = new ArrayList<String>() {{
        add("Label1");
        add("Label2");
        add("Label3");
        add("Label4");
        add("Label5");
        add("Label6");
    }};
    
    public static final List<Float> defaultRatios = new ArrayList<Float>() {{
        Random random = new Random();
        add(MathUtil.clamp(random.nextFloat(), 0.25f, 0.75f));
        add(MathUtil.clamp(random.nextFloat(), 0.25f, 0.75f));
        add(MathUtil.clamp(random.nextFloat(), 0.25f, 0.75f));
        add(MathUtil.clamp(random.nextFloat(), 0.25f, 0.75f));
        add(MathUtil.clamp(random.nextFloat(), 0.25f, 0.75f));
        add(MathUtil.clamp(random.nextFloat(), 0.25f, 0.75f));
    }};
    
    
    // set by onMeasure()
    private float chartWidth;
    private float chartHeight;
    private float deltaY;
    
    // basic items!
    private Rect baseRect;
    private Paint paint;
    private PathBuilder pathBuilder;
    
    // set from user
    private List<Float> ratios;
    private List<String> labels;
    
    private Drawable polygonForegroundDrawable;
    private int polygonBackgroundColor;
    private int polygonForegroundColor;
    
    private int dotColor;
    private int dotStrokeColor;
    private float dotRadius;
    private float dotWidth;
    
    private int textColor;
    private float textSize;
    
    public Radar6Chart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        
        TypedArray a = context.getTheme().obtainStyledAttributes(
            attrs, R.styleable.Radar6Chart, 0, 0);
        
        try {
            polygonBackgroundColor = a.getColor(R.styleable.Radar6Chart_polygonBackgroundColor, Color.GRAY);
            polygonForegroundDrawable = a.getDrawable(R.styleable.Radar6Chart_polygonForegroundDrawable);
            polygonForegroundColor = a.getColor(R.styleable.Radar6Chart_polygonForegroundColor, Color.BLUE);
            dotColor = a.getColor(R.styleable.Radar6Chart_dotColor, Color.RED);
            dotStrokeColor = a.getColor(R.styleable.Radar6Chart_dotStrokeColor, Color.WHITE);
            dotRadius = a.getDimension(R.styleable.Radar6Chart_dotRadius, DpConverter.convertDpToPixel(context, 4));
            dotWidth = a.getDimension(R.styleable.Radar6Chart_dotRadius, DpConverter.convertDpToPixel(context, 1));
            textColor = a.getColor(R.styleable.Radar6Chart_textColor, Color.BLACK);
            textSize = a.getDimension(R.styleable.Radar6Chart_textSize, DpConverter.convertDpToPixel(context, 9));
        } finally {
            a.recycle();
        }
        
        init();
    }
    
    // Setters are coming..
    
    public void setRatios(List<Float> ratios) {
        if (ratios.size() != 6)
            throw new IllegalArgumentException("list size must be 6.");
        
        needInvalidate(() -> this.ratios = ratios);
    }
    
    public void setLabels(List<String> labels) {
        if (labels.size() != 6)
            throw new IllegalArgumentException("list size must be 6.");
        
        needInvalidate(() -> this.labels = labels);
    }
    
    public void setPolygonForegroundDrawable(Drawable polygonForegroundDrawable) {
        needInvalidate(() -> this.polygonForegroundDrawable = polygonForegroundDrawable);
    }
    
    public void setPolygonBackgroundColor(@ColorInt int polygonBackgroundColor) {
        needInvalidate(() -> this.polygonBackgroundColor = polygonBackgroundColor);
    }
    
    public void setPolygonForegroundColor(@ColorInt int polygonForegroundColor) {
        needInvalidate(() -> this.polygonForegroundColor = polygonForegroundColor);
    }
    
    public void setDotColor(@ColorInt int dotColor) {
        needInvalidate(() -> this.dotColor = dotColor);
    }
    
    public void setDotStrokeColor(@ColorInt int dotStrokeColor) {
        needInvalidate(() -> this.dotStrokeColor = dotStrokeColor);
    }
    
    public void setDotRadius(float dotRadius) {
        needInvalidate(() -> this.dotRadius = dotRadius);
    }
    
    public void setDotWidth(float dotWidth) {
        needInvalidate(() -> this.dotWidth = dotWidth);
    }
    
    public void setTextColor(@ColorInt int textColor) {
        needInvalidate(() -> this.textColor = textColor);
    }
    
    public void setTextSize(float textSizeInPixel) {
        needInvalidate(() -> this.textSize = textSizeInPixel);
    }
    
    private void needInvalidate(Runnable runnable) {
        runnable.run();
        invalidate();
        requestLayout();
    }
    
    
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        
        chartWidth = getMeasuredWidth() * CHART_WIDTH_RATIO;
        chartHeight = getMeasuredHeight() * CHART_HEIGHT_RATIO;
        deltaY = chartHeight * DELTA_Y_RATIO;
        
        Log.e("asdf", "Chart width: " + chartWidth);
        Log.e("asdf", "Chart height: " + chartHeight);
        Log.e("asdf", "Delta y: " + deltaY);
    }
    
    private void init() {
        baseRect = new Rect();
        paint = new Paint();
        pathBuilder = new PathBuilder();
        ratios = defaultRatios;
        labels = defaultLabels;
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setPathBuilder();
        
        drawBackground(canvas, pathBuilder.makePath());
        drawForeground(canvas, pathBuilder.makePath(ratios));
        drawDots(canvas, pathBuilder.makePoints());
        drawLabels(canvas, pathBuilder.makeTextPositions(), labels);
    }
    
    private void setPathBuilder() {
        getDrawingRect(baseRect);
        pathBuilder.set(baseRect, deltaY, chartWidth, chartHeight);
    }
    
    private void drawBackground(Canvas canvas, Path path) {
        usePaint(paint -> {
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(polygonBackgroundColor);
            canvas.drawPath(path, paint);
        });
    }
    
    private void drawForeground(Canvas canvas, Path filledPath) {
        if (clipPathSupported()) {
            int save = canvas.save();
            
            canvas.clipPath(filledPath);
            polygonForegroundDrawable.setBounds(pathBuilder.chartRect());
            polygonForegroundDrawable.draw(canvas);
            
            canvas.restoreToCount(save);
        } else {
            usePaint(paint -> {
                // set
                paint.setStyle(Paint.Style.FILL);
                paint.setColor(polygonForegroundColor);
                canvas.drawPath(filledPath, paint);
            });
        }
    }
    
    private void drawDots(Canvas canvas, PointF[] points) {
        usePaint(paint -> {
            // stroke
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(dotStrokeColor);
            paint.setStrokeWidth(dotWidth);
            paint.setStrokeCap(Paint.Cap.ROUND);
            
            for (PointF point : points) {
                canvas.drawCircle(point.x, point.y, dotRadius, paint);
            }
            
            // fill
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(dotColor);
            
            for (PointF point : points) {
                canvas.drawCircle(point.x, point.y, dotRadius, paint);
            }
        });
    }
    
    private void drawLabels(Canvas canvas, PointF[] points, List<String> labels) {
        usePaint(paint -> {
            paint.setColor(textColor);
            paint.setTextSize(textSize);
            
            paint.setTextAlign(Paint.Align.CENTER);
            canvas.drawText(labels.get(0), points[0].x, points[0].y, paint);
            canvas.drawText(labels.get(3), points[3].x, points[3].y, paint);
            
            paint.setTextAlign(Paint.Align.LEFT);
            canvas.drawText(labels.get(1), points[1].x, points[1].y, paint);
            canvas.drawText(labels.get(2), points[2].x, points[2].y, paint);
            
            paint.setTextAlign(Paint.Align.RIGHT);
            canvas.drawText(labels.get(4), points[4].x, points[4].y, paint);
            canvas.drawText(labels.get(5), points[5].x, points[5].y, paint);
        });
    }
    
    // Ensure that the paint would not be dirty by others..?
    private void usePaint(Consumer<Paint> paintConsumer) {
        // save
        Paint.Style previous = paint.getStyle();
        int previousColor = paint.getColor();
        
        paintConsumer.accept(paint);
        
        // restore
        paint.setColor(previousColor);
        paint.setStyle(previous);
    }
    
    private boolean clipPathSupported() {
        return Build.VERSION.SDK_INT >= 18 && polygonForegroundDrawable != null;
    }
}
