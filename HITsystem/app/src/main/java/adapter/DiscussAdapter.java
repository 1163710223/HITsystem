package adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhang.hitsystem.ACDiscuss;
import com.example.zhang.hitsystem.ClassManage;
import com.example.zhang.hitsystem.Operate;
import com.example.zhang.hitsystem.OperateModify;
import com.example.zhang.hitsystem.R;

import java.util.List;

import bean.Discuss;


public class DiscussAdapter extends RecyclerView.Adapter <DiscussAdapter.ViewHolder>{

    private List<Discuss> mDiscussList;

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView discussContent;
        TextView discusserID;
        TextView dicusserName;


        public ViewHolder(View view){
            super(view);
            discussContent = (TextView) view.findViewById(R.id.discuss_content);
            discusserID = (TextView)view.findViewById(R.id.discusser_id);
            dicusserName = (TextView) view.findViewById(R.id.discusser_name);
        }
    }



    public DiscussAdapter(List<Discuss> discussList){
        mDiscussList = discussList;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.discusscontent_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Discuss thediscuss = mDiscussList.get(position);
        if (thediscuss.getTeanum()!=null && !thediscuss.getTeanum().equals("")){
            holder.discusserID.setText(thediscuss.getTeanum());
            holder.discussContent.setText(thediscuss.getTeadetail());
        } else{
            //Log.d("zhelijinlailema", "onBindViewHolder: zhelijinlailema");
            holder.discusserID.setText(thediscuss.getStunum());
            holder.discussContent.setText(thediscuss.getStudetail());
        }
    }

    @Override
    public int getItemCount() {
        return mDiscussList.size();
    }

}
