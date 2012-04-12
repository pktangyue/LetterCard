package motion.event;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class Gridview extends Activity {
	ArrayList<Integer> blockPosition = new ArrayList<Integer>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initblockPosition();
		setContentView(R.layout.gridview);
		GridView gv = (GridView) findViewById(R.id.GridView1);
		// 为GridView设置适配器
		gv.setAdapter(new MyAdapter(this));
		// 注册监听事件
		gv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				// Toast.makeText(Gridview.this, "pic" + position,
				// Toast.LENGTH_SHORT).show();
				if (blockPosition.indexOf(position) == -1) {
					Intent intent = new Intent(
							"motion.event.MotionEventActivity");
					intent.putExtra("id", position);
					startActivity(intent);
				}
			}
		});
	}

	private void initblockPosition() {
		blockPosition.add(36);
		blockPosition.add(38);
		blockPosition.add(46);
		blockPosition.add(47);
		blockPosition.add(48);
	}

}

// 自定义适配器
class MyAdapter extends BaseAdapter {
	// 上下文对象
	private Context context;

	// 图片数组

	MyAdapter(Context context) {
		this.context = context;
	}

	public int getCount() {
		return Config.PICTHUMBNAME.length;
	}

	public Object getItem(int item) {
		return item;
	}

	public long getItemId(int id) {
		return id;
	}

	// 创建View方法
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView imageView;
		if (convertView == null) {
			imageView = new ImageView(context);
			imageView.setLayoutParams(new GridView.LayoutParams(75, 75));// 设置ImageView对象布局
			imageView.setAdjustViewBounds(false);// 设置边界对齐
			imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);// 设置刻度的类型
			imageView.setPadding(8, 2, 2, 2);// 设置间距
		} else {
			imageView = (ImageView) convertView;
		}
		imageView.setImageResource(Config.PICTHUMBNAME[position]);// 为ImageView设置图片资源
		return imageView;
	}
}