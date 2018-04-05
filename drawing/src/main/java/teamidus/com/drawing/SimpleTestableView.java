package teamidus.com.drawing;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by nyh0111 on 2018-04-05.
 *
 * RoundCornerProgressBar with Section
 */

public class SimpleTestableView extends View {
    
    public SimpleTestableView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    
    public SimpleTestableView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    
    
    private Paint sectionPaint;
    private Path sectionPath;
    
    private int sectionColor = Color.BLACK;
    
    private void init() {
        sectionPaint = new Paint();
        sectionPaint.setColor(sectionColor);
        sectionPaint.setStyle(Paint.Style.FILL);
        
        sectionPath = new Path();
    }
    
    public void setSectionColor(int sectionColor) {
        this.sectionColor = sectionColor;
        sectionPaint.setColor(sectionColor);
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
    
        Log.e("asdf", "Measured width by function: " + getMeasuredWidth());
        Log.e("asdf", "Measured height by function: " + getMeasuredHeight());
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        
        sectionPath.moveTo(10, 10);
        sectionPath.lineTo(25, 25);
        sectionPath.lineTo(40, 10);
        sectionPath.close();
        
        canvas.drawPath(sectionPath, sectionPaint);
        canvas.drawCircle(50, 50, 20, sectionPaint);
    }
}
