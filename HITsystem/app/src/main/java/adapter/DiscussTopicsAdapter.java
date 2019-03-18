package adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zhang.hitsystem.ACDicussTopics;
import com.example.zhang.hitsystem.R;

import java.util.List;

import bean.Discuss;


public class DiscussTopicsAdapter extends RecyclerView.Adapter <DiscussTopicsAdapter.ViewHolder>{

    private List<String> mTopicList;
    private Context mContext;
    private MyItemClickListener mItemClickListener;


    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView topic;
        public MyItemClickListener mListener;


        public ViewHolder(View view, MyItemClickListener myItemClickListener){
            super(view);
            topic = (TextView) view.findViewById(R.id.topic_name);
            this.mListener = myItemClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mListener != null) {
                mListener.onItemClick(view, getPosition());
            }
        }
    }

    public interface MyItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setItemClickListener(MyItemClickListener myItemClickListener) {
        this.mItemClickListener = myItemClickListener;
    }

    public DiscussTopicsAdapter(ACDicussTopics acDicussTopics,List<String> TopicList){
        mTopicList = TopicList;
        mContext = acDicussTopics;
    }



    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.topic_item, parent, false);
        ViewHolder holder = new ViewHolder(view,mItemClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String topics = mTopicList.get(position);
        holder.topic.setText(topics);
    }

    @Override
    public int getItemCount() {
        return mTopicList.size();
    }
}
