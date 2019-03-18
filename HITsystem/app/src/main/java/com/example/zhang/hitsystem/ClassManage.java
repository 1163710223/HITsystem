package com.example.zhang.hitsystem;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import net.sf.json.JSON;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import adapter.ClassAdapter;
import bean.Class;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ClassManage extends AppCompatActivity {

    private List<Class> classList = new ArrayList<>();

    Handler mHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_manage);
        initClasses();

        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case 0:
                        RecyclerView recyclerView = findViewById(R.id.classmanage_recyclerview);
                        LinearLayoutManager layoutManager = new LinearLayoutManager(ClassManage.this);
                        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                        recyclerView.setLayoutManager(layoutManager);
                        final ClassAdapter adapter = new ClassAdapter(ClassManage.this,classList);
                        recyclerView.setAdapter(adapter);

                        adapter.setItemClickListener(new ClassAdapter.MyItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                //Toast.makeText(ClassManage.this, "点击了" + position, Toast.LENGTH_SHORT).show();
                                String classname = classList.get(position).getName();
                                String classid = classList.get(position).getId();

                                Intent intent = new Intent(ClassManage.this, Operate.class);
                                intent.putExtra("classname", classname);
                                intent.putExtra("classid", classid);
                                Log.d("classid", "onItemClick: "+classid);
                                startActivity(intent);
                            }
                        });
                        break;
                    default:
                        break;
                }
            }
        };

    }

    private void initClasses(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //String thisurl = "http://"+getString(R.string.ipdress)+"/reqallsubject";
                    SharedPreferences sharedPreferences = getSharedPreferences("myPreference", Context.MODE_PRIVATE);
                    String id = sharedPreferences.getString("email","0");
                    String thisurl = "http://"+getString(R.string.ipdress)+"/reqSubByTeaId?"+"teacherid="+id;

                    OkHttpClient client = new OkHttpClient();

                    Request request = new Request.Builder()
                            .url(thisurl)
                            .build();

                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    Log.d("aaaa", "run: "+responseData);

                    JSONObject jsonObject = new JSONObject(responseData);
                    String v = jsonObject.getString("subjects");
                    Class classa;

                    JSONArray array=new JSONArray(v);
                    //将字符串转成json数组
                    for(int i=0;i<array.length();i++){
                        JSONObject js = new JSONObject(array.getString(i));
                        classa = new Class(js.getString("subject_name"),js.getString("subject_id"),R.drawable.am);
                        String b0=array.getString(i);
                        //从数组里取出数组
                        Log.e("aaaa", b0);
                        classList.add(classa);
                    }
                    mHandler.sendEmptyMessage(0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void addcommit(View view){
        Intent intent = new Intent(ClassManage.this, AddClass.class);
        startActivity(intent);
    }



}
