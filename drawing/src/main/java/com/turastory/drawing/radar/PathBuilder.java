package com.turastory.drawing.radar;

import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;

import java.util.List;

/**
 * Created by nyh0111 on 2018-04-10.
 */

public class PathBuilder {
    
    private Rect baseRect;
    private Rect chartRect;
    
    private float deltaY;
    private float chartWidth;
    private float chartHeight;
    
    private Path path;
    
    public PathBuilder() {
        path = new Path();
    }
    
    public void set(Rect baseRect, float deltaY, float chartWidth, float chartHeight) {
        this.baseRect = baseRect;
        this.deltaY = deltaY;
        this.chartWidth = chartWidth;
        this.chartHeight = chartHeight;
        
        this.chartRect = new Rect(
            (int) (baseRect.centerX() - halfWidth()),
            (int) (baseRect.centerY() - halfWidth()),
            (int) (baseRect.centerX() + halfWidth()),
            (int) (baseRect.centerY() + halfWidth()));
    }
    
    public Rect chartRect() {
        return chartRect;
    }
    
    public Rect baseRect() {
        return baseRect;
    }
    
    public Path makePath() {
        int cx = baseRect.centerX();
        int cy = baseRect.centerY();
        
        path.reset();
        path.moveTo(cx, cy + topY(1));
        path.lineTo(cx + rightX(1), cy + topMiddle(1));
        path.lineTo(cx + rightX(1), cy + bottomMiddle(1));
        path.lineTo(cx, cy + bottomY(1));
        path.lineTo(cx + leftX(1), cy + bottomMiddle(1));
        path.lineTo(cx + leftX(1), cy + topMiddle(1));
        path.close();
        
        return path;
    }
    
    public Path makePath(List<Float> ratios) {
        int cx = baseRect.centerX();
        int cy = baseRect.centerY();
        
        path.reset();
        path.moveTo(cx, cy + topY(ratios.get(0)));
        path.lineTo(cx + rightX(ratios.get(1)), cy + topMiddle(ratios.get(1)));
        path.lineTo(cx + rightX(ratios.get(2)), cy + bottomMiddle(ratios.get(2)));
        path.lineTo(cx, cy + bottomY(ratios.get(3)));
        path.lineTo(cx + leftX(ratios.get(4)), cy + bottomMiddle(ratios.get(4)));
        path.lineTo(cx + leftX(ratios.get(5)), cy + topMiddle(ratios.get(5)));
        path.close();
        
        return path;
    }
    
    public PointF[] makePoints() {
        float cx = baseRect.centerX();
        float cy = baseRect.centerY();
        
        return new PointF[]{
            new PointF(cx, cy + topY(1)),
            new PointF(cx + rightX(1), cy + topMiddle(1)),
            new PointF(cx + rightX(1), cy + bottomMiddle(1)),
            new PointF(cx, cy + bottomY(1)),
            new PointF(cx + leftX(1), cy + bottomMiddle(1)),
            new PointF(cx + leftX(1), cy + topMiddle(1))
        };
    }
    
    public PointF[] makeTextPositions() {
        float cx = baseRect.centerX();
        float cy = baseRect.centerY();
        
        return new PointF[]{
            new PointF(cx, cy + topY(1.15f)),
            new PointF(cx + rightX(1.2f), cy + topMiddle(1)),
            new PointF(cx + rightX(1.2f), cy + bottomMiddle(1)),
            new PointF(cx, cy + bottomY(1.25f)),
            new PointF(cx + leftX(1.2f), cy + bottomMiddle(1)),
            new PointF(cx + leftX(1.2f), cy + topMiddle(1))
        };
    }
    
    private float topY(float ratio) {
        return -halfHeight() * ratio;
    }
    
    private float bottomY(float ratio) {
        return halfHeight() * ratio;
    }
    
    private float leftX(float ratio) {
        return -halfWidth() * ratio;
    }
    
    private float rightX(float ratio) {
        return halfWidth() * ratio;
    }
    
    private float topMiddle(float ratio) {
        return (-halfHeight() + deltaY) * ratio;
    }
    
    private float bottomMiddle(float ratio) {
        return (halfHeight() - deltaY) * ratio;
    }
    
    private float halfHeight() {
        return chartHeight / 2f;
    }
    
    private float halfWidth() {
        return chartWidth / 2f;
    }
}
