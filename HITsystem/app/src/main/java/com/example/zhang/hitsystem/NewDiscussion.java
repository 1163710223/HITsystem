package com.example.zhang.hitsystem;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import adapter.DiscussTopicsAdapter;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NewDiscussion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_discussion);
    }

    public void newdiscussion(View view){
        EditText biaoqianEdit = (EditText)findViewById(R.id.biaoqian);
        EditText mingchengEdit = (EditText)findViewById(R.id.mingcheng);
        final String biaoqian = biaoqianEdit.getText().toString();
        final String mingcheng = mingchengEdit.getText().toString();
        final boolean[] flag = new boolean[1];
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String thisurl = "http://" + getString(R.string.ipdress) + "/newDiscussion?topic=" + biaoqian + "&name=" + mingcheng;
                    Log.d("ACDicussTopics", "开启线程发送请求");
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder().url(thisurl).build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    Log.d("", "run: " + responseData);
                    JSONObject jsonObject = new JSONObject(responseData);
                    flag[0] = jsonObject.getBoolean("status");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(flag[0]){
                            AlertDialog.Builder dialog = new AlertDialog.Builder(NewDiscussion.this);
                            dialog.setTitle("添加成功");
                            dialog.setMessage("您已成功创建讨论话题");
                            dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            dialog.show();
                        }else{
                            AlertDialog.Builder dialog = new AlertDialog.Builder(NewDiscussion.this);
                            dialog.setTitle("添加失败");
                            dialog.setMessage("请检查网络连接后重试");
                            dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            });
                            dialog.show();
                        }
                    }
                });
            }
        }).start();
    }
}
