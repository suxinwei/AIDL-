package com.suxinwei.demo;

import android.content.Context;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * ClassName:FruitAdapter. <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Date:     2017/6/14 23:52 <br/>
 *
 * @author suxinwei
 */

public class FruitAdapter2 extends BaseAdapter {

    protected Context context;
    protected List<Fruit> data;

    public FruitAdapter2(Context context, List<Fruit> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public Fruit getItem(int position) {
        if (position >= data.size() || position < 0) {
            return null;
        }
        return data.get(position);
    }

    private void getItemView(int position, ViewHolder holder) {
        ImageView fruitImage = holder.getView(R.id.fruit_image);
        TextView fruitName = holder.getView(R.id.fruit_name);

        Fruit fruit = getItem(position);
        fruitName.setText(fruit.name);

        fruitImage.setImageResource(fruit.imageId);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (null == convertView) {
            convertView = View.inflate(context, R.layout.item_fruit, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        getItemView(position, holder);

        return convertView;
    }

    public class ViewHolder {
        private SparseArray<View> views = new SparseArray<View>();
        private View convertView;

        public ViewHolder(View convertView) {
            this.convertView = convertView;
        }

        @SuppressWarnings("unchecked")
        public <T extends View> T getView(int resId) {
            View v = views.get(resId);
            if (null == v) {
                v = convertView.findViewById(resId);
                views.put(resId, v);
            }
            return (T) v;
        }
    }
}
