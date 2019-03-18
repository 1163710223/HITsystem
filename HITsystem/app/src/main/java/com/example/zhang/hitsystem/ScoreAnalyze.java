package com.example.zhang.hitsystem;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import bean.Score;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class ScoreAnalyze extends AppCompatActivity {

    static int Anum;
    static int Bnum;
    static int Cnum;
    static int Dnum;
    static int Enum;
    String classid;
    String classname;
    Handler mHandler;
    TextView zhu;
    TextView yuan;

    ArrayList<Integer> scorelist=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scorequery);

        Intent intent = getIntent();
        classid = intent.getStringExtra("classid");
        classname = intent.getStringExtra("classname");

        scorelist=initList();

        zhu =(TextView)findViewById(R.id.zhu);
        yuan =(TextView)findViewById(R.id.yuan);

        mHandler = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 0:
                        Anum= cal(scorelist).get(0);
                        Bnum= cal(scorelist).get(1);
                        Cnum= cal(scorelist).get(2);
                        Dnum= cal(scorelist).get(3);
                        Enum= cal(scorelist).get(4);
                        break;
                    default:
                        break;
                }
            }

        };
        zhu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ScoreAnalyze.this,TriDraw.class);
                startActivity(intent);
            }
        });

        yuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ScoreAnalyze.this,CirDraw.class);
                startActivity(intent);
            }
        });


    }

    public ArrayList<Integer> cal(ArrayList<Integer> list){
        int a=0;
        int b=0;
        int c=0;
        int d=0;
        int e=0;
        ArrayList<Integer> re=new ArrayList<>();
        for(int i = 0;i < list.size(); i ++){
            if (list.get(i)<=100 && list.get(i)>=90) { a++; }
            else if (list.get(i)<90 && list.get(i)>=80) { b++; }
            else if (list.get(i)<80 && list.get(i)>=70) { c++; }
            else if (list.get(i)<70 && list.get(i)>=60) { d++; }
            else if (list.get(i)<60) { e++; }
        }
        re.add(a);
        re.add(b);
        re.add(c);
        re.add(d);
        re.add(e);
        return re;
    }

    public ArrayList<Integer> initList(){
        final ArrayList<Integer> list = new ArrayList<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String url = new String("http://"+getString(R.string.ipdress)+"/reqsubscore?subjectId="+classid);
                    Log.d("", "urlurl: "+url);
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder().url(url).build();
                    Response response = client.newCall(request).execute();
                    String responsedata = response.body().string();
                    Log.d("initstudnets", "run: "+responsedata);
                    JSONObject jsonObjectre = new JSONObject(responsedata);
                    JSONArray jsonArray = new JSONArray(jsonObjectre.get("subjects").toString());
                    String score;
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject = new JSONObject(jsonArray.get(i).toString());
                        int num = (new Double(jsonObject.getDouble("score"))).intValue();
                        list.add(num);
                        System.out.println(num);
                    }
                    mHandler.sendEmptyMessage(0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        /*for(int i=0;i<10;i++){
            list.add(55+i*5);
        }*/
        return list;

    }
}

