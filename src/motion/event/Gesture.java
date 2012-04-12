package motion.event;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ViewFlipper;
import motion.event.Card;

public class Gesture extends ViewFlipper implements
		android.view.GestureDetector.OnGestureListener {
	private MediaPlayer mediaPlayer;
	GestureDetector gd;
	View layout;
	View relativeLayout;
	int i = 0;

	public Gesture(Context context, AttributeSet attrs) {
		super(context, attrs);
		gd = new GestureDetector(this);

	}

	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		return this.gd.onTouchEvent(event);
	}

	@Override
	public boolean onDown(MotionEvent arg0) {
		// TODO Auto-generated method stub

		return true;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		// TODO Auto-generated method stub
		killMediaPlayer();
		layout = ((View) this.getParent());
		if ((e2.getX() - e1.getX()) > 10) {// Ïò×ó
			i -= 1;
			if (i < 0) {
				i = Config.PICNAME.length - 1;
			}
			this.setInAnimation(AnimationUtils.loadAnimation(getContext(),
					R.anim.push_right_in));
			this.setOutAnimation(AnimationUtils.loadAnimation(getContext(),
					R.anim.push_right_out));
		} else if ((e1.getX() - e2.getX()) > 10) {// ÏòÓÒ
			i += 1;
			if (i > (Config.PICNAME.length - 1)) {
				i = 0;
			}
			this.setInAnimation(AnimationUtils.loadAnimation(getContext(),
					R.anim.push_left_in));
			this.setOutAnimation(AnimationUtils.loadAnimation(getContext(),
					R.anim.push_left_out));
		}
		Card c = (Card) layout.findViewById(R.id.dot);
		c.clean();

		relativeLayout = (View) layout.getParent();
		ImageButton startPlayerBtn = (ImageButton) relativeLayout
				.findViewById(R.id.horn);
		startPlayerBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				try {
					playLocalAudio(Config.SOUNDNAME[i]);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		this.addView(addImageById(Config.PICNAME[i]));
		this.showNext();
		this.removeViewAt(0);

		return false;
	}

	public View addImageById(int id) {
		View iv = new View(getContext());
		iv.setBackgroundResource(id);
		return iv;
	}

	@Override
	public void onLongPress(MotionEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	private void playLocalAudio(int id) throws Exception {
		mediaPlayer = MediaPlayer.create(getContext(), id);
		mediaPlayer.start();
	}

	private void killMediaPlayer() {
		if (mediaPlayer != null) {
			try {
				mediaPlayer.release();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void setI(int j) {
		i = j;
	}

}