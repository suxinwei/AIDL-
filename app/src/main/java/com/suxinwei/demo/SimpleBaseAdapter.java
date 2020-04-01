package com.suxinwei.demo;

import android.content.Context;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dephy
 * created at 2017/4/18 14:48
 * @ClassName: com.mosant.mosantim.adapter.SimpleBaseAdapter
 * @Description:
 **/

public abstract class SimpleBaseAdapter<T> extends BaseAdapter {

    protected Context context;
    protected List<T> data;

    private OnDataChangeLisener mOnDataChangeLisener;
    public interface OnDataChangeLisener{
           void onDataChange(int size);
    }

    public void setmOnDataChangeLisener(OnDataChangeLisener mOnDataChangeLisener) {
        this.mOnDataChangeLisener = mOnDataChangeLisener;
    }

    public SimpleBaseAdapter(Context context, List<T> data) {
        this.context = context;
        this.data = data == null ? new ArrayList<T>() : new ArrayList<>(data);
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public T getItem(int position) {
        if (position >= data.size() || position < 0)
            return null;
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 该方法需要子类实现，需要返回item布局的resource id
     *
     * @return
     */
    public abstract int getItemResource();

    /**
     * 使用该getItemView方法替换原来的getView方法，需要子类实现
     *
     * @param position
     * @param convertView
     * @param holder
     * @return
     */
    public abstract View getItemView(int position, View convertView, ViewHolder holder);

    @SuppressWarnings("unchecked")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (null == convertView) {
            convertView = View.inflate(context, getItemResource(), null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        return getItemView(position, convertView, holder);
    }

    /**
     *
     */
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

    public void setData(List<T> elem) {
        this.data=elem;
//        this.data.clear();
//        if (elem != null) {
//            this.data.addAll(elem);
//        }
        notifyDataSetChanged();
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        if (mOnDataChangeLisener!=null){
            if (data==null||data.size()==0){
                mOnDataChangeLisener.onDataChange(0);
            }else {
                mOnDataChangeLisener.onDataChange(data.size());
            }
        }
    }

    public List<T> getData() {
        return data;
    }

    public void setDataWithoutNotify(List<T> elem) {
        this.data = elem;
    }

    public void addAll(List<T> elem) {
        if (data == null) data = new ArrayList<>();
        data.addAll(elem);
        notifyDataSetChanged();
    }

    public void remove(T elem) {
        if (data == null) data = new ArrayList<>();
        data.remove(elem);
        notifyDataSetChanged();
    }

    public void removeAll(List<T> elems) {
        if (data == null) data = new ArrayList<>();
        data.removeAll(elems);
        notifyDataSetChanged();
    }

    public void clear() {
        if (data != null) {
            data.clear();
            notifyDataSetChanged();
        }
    }

    public void remove(int index) {
        if (data == null) data = new ArrayList<>();
        if (index >= getCount()) return;
        data.remove(index);
        notifyDataSetChanged();
    }

    public void replaceAll(List<T> elem) {
        if (data == null) data = new ArrayList<>();
        data.clear();
        data.addAll(elem);
        notifyDataSetChanged();
    }
}