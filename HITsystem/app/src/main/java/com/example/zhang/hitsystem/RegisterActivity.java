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

public class RegisterActivity extends AppCompatActivity {

    EditText usrEdit;
    EditText newpassEdit;
    EditText confirmnewpassEdit;
    EditText yearEdit;
    EditText phoneEdit;
    EditText nameEdit;
    String usrid;
    String newpass;
    String confirmnewpass;
    String year;
    String phone;
    String name;
    Handler mHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                AlertDialog.Builder dialog;
                dialog = new AlertDialog.Builder(RegisterActivity.this);
                switch (msg.what){
                    case -2:
                        dialog.setTitle("错误");
                        dialog.setMessage("您输入的密码位数太少，请保证至少输入六位密码！");;
                        break;
                    case -1:
                        dialog.setTitle("错误");
                        dialog.setMessage("您输入的用户名不正确，请重新输入！");;
                        break;
                    case 0:
                        dialog.setTitle("错误");
                        dialog.setMessage("两次输入的密码不一致，请重新输入！");
                        break;
                    case 1:
                        dialog.setTitle("完成");
                        dialog.setMessage("密码已成功修改，请返回重新登录！");
                        break;
                    case 2:
                        dialog.setTitle("错误");
                        dialog.setMessage("注册失败！请联系管理员！");
                        break;
                    case 3:
                        dialog.setTitle("错误");
                        dialog.setMessage("注册失败，未知错误，请联系管理员！");
                        break;
                }
                dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                dialog.show();
            }
        };
    }
    public void register(View view){
        initview();
        initstring();
        if(usrid.length()!=10){
            mHandler.sendEmptyMessage(-1);
        }
        if(newpass.length()<6){
            mHandler.sendEmptyMessage(-2);
        }
        if (!newpass.equals(confirmnewpass)){
            mHandler.sendEmptyMessage(0);
            return;
        }
        newpass = Md5.md5(newpass);
        confirmnewpass = Md5.md5(confirmnewpass);
        final boolean[] flag = new boolean[1];
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient client = new OkHttpClient();
                    FormBody.Builder builder = new FormBody.Builder();
                    RequestBody requestBody = builder
                            .add("id", usrid)
                            .add("password", newpass)
                            .add("year",year)
                            .add("phone",phone)
                            .add("name",name)
                            .build();
                    Request request = new Request.Builder()
                            .url("http://"+getString(R.string.ipdress)+"/signup")
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
                        mHandler.sendEmptyMessage(1);
                    } else{
                        mHandler.sendEmptyMessage(2);
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
                    mHandler.sendEmptyMessage(3);
                }
            }
        }).start();
    }
    public void initview(){
        usrEdit = (EditText)findViewById(R.id.regusrid);
        newpassEdit = (EditText)findViewById(R.id.regnewpass);
        confirmnewpassEdit = (EditText)findViewById(R.id.regconfnewpass);
        phoneEdit = (EditText)findViewById(R.id.regphone);
        yearEdit = (EditText)findViewById(R.id.regyear);
        nameEdit = (EditText)findViewById(R.id.regname);
    }
    public void initstring(){
        usrid = usrEdit.getText().toString();
        newpass = newpassEdit.getText().toString();
        confirmnewpass = confirmnewpassEdit.getText().toString();
        phone = phoneEdit.getText().toString();
        year = yearEdit.getText().toString();
        name = nameEdit.getText().toString();
    }
}
