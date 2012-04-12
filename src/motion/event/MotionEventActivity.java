package motion.event;

import motion.event.R;
import android.view.View.OnTouchListener;
import android.app.Activity;
import android.widget.RelativeLayout;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageButton;
import android.view.LayoutInflater;

public class MotionEventActivity extends Activity {
	Card c;
	Gesture g;
	int i = 0;

	private MediaPlayer mediaPlayer;
	SharedPreferences sp;
	public final String GUIDE_KEY = "GUIDE_KEY";
	private View guidanceView; // 添加向导
	Boolean result = false;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		final RelativeLayout main = (RelativeLayout) findViewById(R.id.main);
		Intent intent = getIntent();
		final int i = intent.getIntExtra("id", 0);

		sp = getPreferences(MODE_PRIVATE);
		result = sp.getBoolean(GUIDE_KEY, false);

		if (!result) {
			LayoutInflater inflater = (LayoutInflater) this
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			guidanceView = inflater.inflate(R.layout.guidance, null);
			guidanceView.setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					main.removeView(guidanceView);
					// TODO Auto-generated method stub
					return false;
				}
			});
			main.addView(guidanceView, LayoutParams.FILL_PARENT,
					LayoutParams.FILL_PARENT);
		}
		g = (Gesture) findViewById(R.id.viewFlipper);
		View v1 = new View(getBaseContext());
		v1.setBackgroundResource(Config.PICNAME[i]);
		g.addView(v1);
		g.setI(i);

		ImageButton startPlayerBtn = (ImageButton) findViewById(R.id.horn);
		c = (Card) findViewById(R.id.dot);
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

		startPlayerBtn.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					v.setBackgroundResource(R.drawable.horn1);
					v.invalidate();
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					v.setBackgroundResource(R.drawable.horn);// 添加声音图片
					v.invalidate();
				}
				return false;
			}
		});

		ImageButton eraserBtn = (ImageButton) findViewById(R.id.eraser);

		eraserBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				try {
					c.clean();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		eraserBtn.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					v.setBackgroundResource(R.drawable.clean1);
					v.invalidate();
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					v.setBackgroundResource(R.drawable.clean);// 添加垃圾桶图片
					v.invalidate();
				}
				return false;
			}
		});
	}

	private void playLocalAudio(int id) throws Exception {
		mediaPlayer = MediaPlayer.create(this, id);
		mediaPlayer.setVolume(1.0f, 1.0f);
		mediaPlayer.start();
	}

	@Override
	protected void onDestroy() {
		if (!result) {
			SharedPreferences.Editor editor = sp.edit();
			editor.putBoolean(GUIDE_KEY, true);
			editor.commit();
		}
		super.onDestroy();
	}
	//
	// private void killMediaPlayer(){
	// if(mediaPlayer!=null){
	// try{
	// mediaPlayer.release();
	// }catch(Exception e){
	// e.printStackTrace();
	// }
	// }
	// }

}
