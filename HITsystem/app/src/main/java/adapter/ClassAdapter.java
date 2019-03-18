package adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhang.hitsystem.ClassManage;
import com.example.zhang.hitsystem.R;

import java.util.List;

import bean.Class;

public class ClassAdapter extends RecyclerView.Adapter <ClassAdapter.ViewHolder>{

    private List<Class> mClassList;

    private Context mContext;
    private MyItemClickListener mItemClickListener;

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private MyItemClickListener mListener;

        View classView;
        ImageView classImage;
        TextView className;

        public ViewHolder(View view, MyItemClickListener myItemClickListener){
            super(view);
            classView = view;
            classImage = (ImageView) view.findViewById(R.id.class_image);
            className = (TextView) view.findViewById(R.id.class_name);

            this.mListener = myItemClickListener;
            itemView.setOnClickListener(this);
        }

        /**
         * 实现OnClickListener接口重写的方法
         * @param v
         */
        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onItemClick(v, getPosition());
            }
        }

    }

    /**
     * 创建一个回调接口
     */
    public interface MyItemClickListener {
        void onItemClick(View view, int position);
    }

    /**
     * 在activity里面adapter就是调用的这个方法,将点击事件监听传递过来,并赋值给全局的监听
     *
     * @param myItemClickListener
     */
    public void setItemClickListener(MyItemClickListener myItemClickListener) {
        this.mItemClickListener = myItemClickListener;
    }


    public ClassAdapter(ClassManage classManage, List<Class> classList){
        mClassList = classList;
        mContext = classManage;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.class_item, parent, false);

        ViewHolder holder = new ViewHolder(view, mItemClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Class theclass = mClassList.get(position);
        holder.classImage.setImageResource(theclass.getImageId());
        holder.className.setText(theclass.getName());
    }

    @Override
    public int getItemCount() {
        return mClassList.size();
    }

}
