package adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.zhang.hitsystem.R;

import java.util.List;

import bean.Score;

public abstract class AddAdapter extends RecyclerView.Adapter <AddAdapter.ViewHolder>{
    private Context mContent;
    private List<Score> mScoreList;

    SaveEditListener saveEditListener;

    public interface SaveEditListener{
        void SaveEdit(int position, String snum, String sscore);
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView addnum;
        EditText addstunum;
        EditText addstuscore;

        public ViewHolder(View view){
            super(view);
            addnum = (TextView) view.findViewById(R.id.addnum1);
            addstunum = (EditText) view.findViewById(R.id.addstunum1);
            addstuscore = (EditText) view.findViewById(R.id.addstuscore1);
        }
    }

    public AddAdapter(List<Score> scoreList, Context context){
        mContent = context;
        mScoreList = scoreList;
        Log.d("zonggongyoujici", "AddAdapter: "+mScoreList.size());
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.add_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("zheshidijici", "onBindViewHolder: ");
        holder.addnum.setText((position+1)+"");
        holder.addstunum.setText("");
        holder.addstuscore.setText("");

        //添加editText的监听事件
        holder.addstunum.addTextChangedListener(new numTextSwitcher(holder));
        //通过设置tag，防止position紊乱
        holder.addstunum.setTag(position);

        //添加editText的监听事件
        holder.addstuscore.addTextChangedListener(new scoreTextSwitcher(holder));
        //通过设置tag，防止position紊乱
        holder.addstuscore.setTag(position);


    }

    @Override
    public int getItemCount() {
        return mScoreList.size();
    }

    //自定义EditText的监听类
    public class numTextSwitcher implements TextWatcher {

        private ViewHolder mHolder;

        public numTextSwitcher(ViewHolder mHolder) {
            this.mHolder = mHolder;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            //用户输入完毕后，处理输入数据，回调给主界面处理
            SaveEditListener listener = (SaveEditListener) mContent;
            if(s!=null){
                String snum = mHolder.addstunum.getText().toString();
                String sscore = mHolder.addstuscore.getText().toString();
                listener.SaveEdit(Integer.parseInt(mHolder.addstunum.getTag().toString()),snum,sscore);
            }
        }
    }

    //自定义EditText的监听类
    public class scoreTextSwitcher implements TextWatcher {

        private ViewHolder mHolder;

        public scoreTextSwitcher(ViewHolder mHolder) {
            this.mHolder = mHolder;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            //用户输入完毕后，处理输入数据，回调给主界面处理
            SaveEditListener listener = (SaveEditListener) mContent;
            if(s!=null){
                String snum = mHolder.addstunum.getText().toString();
                String sscore = mHolder.addstuscore.getText().toString();
                listener.SaveEdit(Integer.parseInt(mHolder.addstuscore.getTag().toString()),snum,sscore);
            }
        }
    }


}
