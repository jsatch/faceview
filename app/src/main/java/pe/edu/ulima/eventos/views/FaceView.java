package pe.edu.ulima.eventos.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

public class FaceView extends View{
    private int mAncho, mAlto;
    private int mSonrisa = 0;
    private RectF mRect;

    private Paint mPaint;
    private Paint mPaintSonrisa;

    private ScaleGestureDetector mDetector;

    public FaceView(Context context) {
        super(context);
        mDetector = new ScaleGestureDetector(this.getContext(),new GestosListener());
        init();
    }

    public FaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mDetector = new ScaleGestureDetector(this.getContext(),new GestosListener());
        init();
    }

    public FaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mDetector = new ScaleGestureDetector(this.getContext(),new GestosListener());
        init();
    }

    private void init(){
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLACK);

        mPaintSonrisa = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintSonrisa.setColor(Color.BLACK);
        mPaintSonrisa.setStyle(Paint.Style.STROKE);
        mPaintSonrisa.setStrokeWidth(10);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mSonrisa == 0){
            mSonrisa = mAncho/8;
        }

        int diametroOjo = Math.min(mAncho, mAlto) / 8;
        // Ojo izquierdo
        canvas.drawCircle(mAncho / 4, mAlto / 3, diametroOjo, mPaint);
        // Ojo derecho
        canvas.drawCircle((mAncho / 4) * 3, mAlto / 3, diametroOjo, mPaint);

        mRect.left = mAncho/4;
        mRect.top = (mAlto/3)*2 - (mSonrisa/2);
        mRect.right =  (mAncho/4)*3;
        mRect.bottom = (mAlto/3)*2 + (mSonrisa/2);

        canvas.drawArc(mRect, 0, 180, false, mPaintSonrisa );
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mRect = new RectF();

        mAncho = w;
        mAlto = h;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDetector.onTouchEvent(event);
        return true;
    }

    private class GestosListener extends ScaleGestureDetector.SimpleOnScaleGestureListener{
        @Override
        public boolean onScale(ScaleGestureDetector detector) {

            float scale = detector.getScaleFactor();
            mSonrisa *= scale;
            invalidate();
            return true;
        }

    }

}
