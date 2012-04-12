package motion.event;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class Card extends View {

	public Canvas canvas;
	public Paint p;
	private Bitmap bitmap;
	float x, y;
	public Path path;

	public Card(Context context, AttributeSet attrs) {
		super(context, attrs);

		canvas = new Canvas();
		p = new Paint(Paint.DITHER_FLAG);
		p.setAntiAlias(true);
		p.setColor(Color.RED);
		p.setStrokeCap(Paint.Cap.ROUND);
		p.setPathEffect(new CornerPathEffect(50));
		p.setStrokeWidth(8);
		p.setStyle(Paint.Style.STROKE);
		path = new Path();

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int action = event.getAction();
		switch (action) {

		case MotionEvent.ACTION_DOWN:
			x = event.getX();
			y = event.getY();
			canvas.drawPoint(x, y, p);
			path.moveTo(x, y);
			invalidate();
			break;

		case MotionEvent.ACTION_MOVE:

			path.lineTo(x, y);
			canvas.drawPath(path, p);
			invalidate();
			break;
		case MotionEvent.ACTION_UP:
			path.rewind();
			break;
		case MotionEvent.ACTION_CANCEL:
			break;

		}
		// event.recycle();
		x = event.getX();
		y = event.getY();
		return true;
		// return super.onTouchEvent(event);
	}

	public void clean() {
		path.rewind();
		bitmap = Bitmap.createBitmap(this.canvas.getWidth(),
				this.canvas.getHeight(), Bitmap.Config.ARGB_8888);
		canvas.setBitmap(bitmap);
		this.canvas.drawBitmap(bitmap, 0, 0, null);
		invalidate();

	}

	@Override
	public void draw(Canvas c) {
		if (bitmap == null) {
			bitmap = Bitmap.createBitmap(c.getWidth(), c.getHeight(),
					Bitmap.Config.ARGB_8888);
			// canvas.drawRect(new Rect(c.getWidth()/2,0,0,0), p);
			// canvas=new Canvas();
			canvas.setBitmap(bitmap);

		}
		c.drawBitmap(bitmap, 0, 0, null);
	}

}