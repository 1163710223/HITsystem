package com.example.zhang.hitsystem;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import tool.Md5;

public class ModifyPassword extends AppCompatActivity {

    EditText usrEdit;
    EditText oldpassEdit;
    EditText newpassEdit;
    EditText confirmnewpassEdit;
    String usrid;
    String oldpass;
    String newpass;
    String confirmnewpass;
    Handler mHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_password);
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                AlertDialog.Builder dialog;
                switch (msg.what){
                    case 1:
                        dialog = new AlertDialog.Builder(ModifyPassword.this);
                        dialog.setTitle("完成");
                        dialog.setMessage("密码已成功修改，请返回重新登录！");
                        dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                        dialog.show();
                        break;
                    case 2:
                        dialog = new AlertDialog.Builder(ModifyPassword.this);
                        dialog.setTitle("错误");
                        dialog.setMessage("密码修改失败，请联系管理员！");
                        dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                        dialog.show();
                        break;
                    case 3:
                        dialog = new AlertDialog.Builder(ModifyPassword.this);
                        dialog.setTitle("错误");
                        dialog.setMessage("旧密码输入错误！请重新输入！");
                        dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                        dialog.show();
                        break;
                    case 4:
                        dialog = new AlertDialog.Builder(ModifyPassword.this);
                        dialog.setTitle("错误");
                        dialog.setMessage("网络连接错误，请联系管理员");
                        dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                        dialog.show();
                        break;
                    case 5:
                        dialog = new AlertDialog.Builder(ModifyPassword.this);
                        dialog.setTitle("错误");
                        dialog.setMessage("两次密码输入不一致，请重新输入！");
                        dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                        dialog.show();
                        break;
                }
            }
        };
    }
    public void modifyPassword(View view){
        initview();
        initstring();
        String oldpassmd5 = Md5.md5(oldpass);
        String newpassmd5 = Md5.md5(newpass);
        String confirmnewpassmd5 = Md5.md5(confirmnewpass);
        if(!newpass.equals(confirmnewpass)){
            mHandler.sendEmptyMessage(5);
        }
        final boolean[] flag = new boolean[1];
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient client = new OkHttpClient();
                    FormBody.Builder builder = new FormBody.Builder();
                    Log.d("usrid&lodpass", "run: "+usrid+"1111111"+oldpass);
                    RequestBody requestBody = builder
                            .add("id", usrid)
                            .add("password", oldpass)
                            .build();
                    Request request = new Request.Builder()
                            .url("http://"+getString(R.string.ipdress)+"/login")
                            .post(requestBody)
                            .build();
                    //Log.d("LoginActivity", "emailandpassword"+usrid+oldpass);

                    Response response = client.newCall(request).execute();

                    String responseData = response.body().string();
                    Log.d("", "run: "+responseData);
                    JSONObject jsonObject = new JSONObject(responseData.toString());
                    String resflag = jsonObject.getString("status");

                    Log.d("LoginActivity", "run: "+responseData);
                    Log.d("LoginActivity", "0run: "+resflag);

                    if(resflag.equals("true")){
                        flag[0] =true;
                        FormBody.Builder builder0 = new FormBody.Builder();
                        RequestBody requestBody0 = builder0
                                .add("id", usrid)
                                .add("password", newpass)
                                .build();
                        request = new Request.Builder()
                                .url("http://"+getString(R.string.ipdress)+"/modifyPassword")
                                .post(requestBody0)
                                .build();
                        Log.d("LoginActivity", "http://"+getString(R.string.ipdress)+"/modifyPassword?"+"id="+usrid+"&password="+newpass+"&oldpass="+oldpass);

                        response = client.newCall(request).execute();

                        responseData = response.body().string();
                        jsonObject = new JSONObject(responseData.toString());
                        resflag = jsonObject.getString("status");
                        if(resflag.equals("true")){
                            mHandler.sendEmptyMessage(1);
                        }else{
                            mHandler.sendEmptyMessage(2);
                        }
                        Log.d("LoginActivity", "1run: "+flag[0]);
                    } else{
                        flag[0] =false;
                        mHandler.sendEmptyMessage(3);
                        Log.d("LoginActivity", "2run: "+flag[0]);
                    }
                    //parseJSONWithJSONObject(responseData);
                    Log.d("LoginActivity", "已接受服务端数据"+responseData);


                    //耗时操作，完成之后发送消息给Handler，完成UI更新；
                    //mHandler.sendEmptyMessage(0);
                    //需要数据传递，用下面方法；
                    //Message msg =new Message();
                    //msg.obj = flag[0];//可以是基本类型，可以是对象，可以是List、map等；
                    //mHandler.sendMessage(msg);

                }catch (Exception e){
                    e.printStackTrace();
                    mHandler.sendEmptyMessage(4);
                }
            }
        }).start();
    }
    public void initview(){
        usrEdit = (EditText)findViewById(R.id.modifyusrid);
        oldpassEdit = (EditText)findViewById(R.id.modifyoldpass);
        newpassEdit = (EditText)findViewById(R.id.modifynewpass);
        confirmnewpassEdit = (EditText)findViewById(R.id.modifyconfnewpass);
    }
    public void initstring(){
        usrid = usrEdit.getText().toString();
        oldpass = oldpassEdit.getText().toString();
        newpass = newpassEdit.getText().toString();
        confirmnewpass = confirmnewpassEdit.getText().toString();
    }
}
