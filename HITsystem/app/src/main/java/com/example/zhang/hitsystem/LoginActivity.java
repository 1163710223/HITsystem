package com.example.zhang.hitsystem;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import tool.HttpCon;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {


    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "1163710214:123456", "1163710255:123456"
    };


    // UI references.
    private AutoCompleteTextView usernameTextView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private SharedPreferences myPreference;
    private SharedPreferences.Editor editor;

    Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Set up the login form.
        usernameTextView = (AutoCompleteTextView) findViewById(R.id.account); //得到用户名的字符串格式
        mPasswordView = (EditText) findViewById(R.id.password); // 得到密码


        myPreference = getSharedPreferences("myPreference", Context.MODE_PRIVATE);
        editor = myPreference.edit();

        String email = myPreference.getString("email", "0");
        String password = myPreference.getString("password", "0");
        attemptLogin(email, password);

        //按回车键进行登陆的功能
        /*mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin(email, password);   //尝试登陆
                    return true;
                }
                return false;
            }
        });*/

        Button signInButton = (Button) findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                // Reset errors.
                usernameTextView.setError(null);
                mPasswordView.setError(null);
                // Store values at the time of the login attempt.
                String email = usernameTextView.getText().toString();
                String password = mPasswordView.getText().toString();
                attemptLogin(email, password);   //尝试登陆
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }



    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }

        return false;
    }



    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     * 尝试登陆
     */
    private void attemptLogin(final String email, final String password) {

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.检查密码是否合法
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid username .
        if (TextUtils.isEmpty(email)) {
            usernameTextView.setError(getString(R.string.error_field_required));
            focusView = usernameTextView;
            cancel = true;
        } else if (!isUsernameValid(email)) {
            usernameTextView.setError(getString(R.string.error_invalid_email));
            focusView = usernameTextView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            //当用户名密码正确时，进入主界面
            //下一行是出现转圈圈的代码，因调试需要将其取消，其实是不应该取消的
            //showProgress(true);
            UserLoginTask userLoginTask = new UserLoginTask(email,password);
            userLoginTask.doInBackground();
            mHandler = new Handler() {

                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    switch (msg.what) {
                        case 0:
                            //完成主界面更新,拿到数据
                            String data = (String)msg.obj;

                            Log.d("", "handleMessage: handler里面"+msg.obj);

                            Log.d("", "attemptLogin: 能不能行");
                            editor.clear();
                            editor.putString("email", email);
                            editor.putString("password", password);
                            editor.commit();

                            if (email.length()==8){
                                //进入老师界面
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.putExtra("id",email);
                                startActivity(intent);
                            }else{
                                //进入学生界面
                                Intent intent = new Intent(LoginActivity.this, stuMainActivity.class);
                                intent.putExtra("id",email);
                                startActivity(intent);
                            }
                            break;
                        case 1:
                            editor.clear();
                            editor.commit();
                            AlertDialog.Builder dialog = new AlertDialog.Builder(LoginActivity.this);
                            dialog.setTitle("错误");
                            dialog.setMessage("用户名或密码错误,请查证后再拨");
                            dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            });
                            dialog.show();
                            break;
                        default:
                            AlertDialog.Builder dia = new AlertDialog.Builder(LoginActivity.this);
                            dia.setTitle("错误");
                            dia.setMessage("网络链接错误，请联系管理员");
                            dia.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            });
                            dia.show();
                            break;
                    }
                }

            };
        }
    }

    /**
     * 判断用户名是否合法
     * @param username
     * @return
     */
    private boolean isUsernameValid(String username) {
        //TODO: Replace this with your own logic
        if(username.length()!=10&&username.length()!=8)    // 用户名应该是10位的，否则返回错误
            return false;
        else{
            return true;
        }
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        Log.d("", "showProgress: zhelia"+show);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }



    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(LoginActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        usernameTextView.setAdapter(adapter);
    }


    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private String mEmail;
        private String mPassword;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }



        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
            //判断是否和数据库中的用户名和密码相同
            final boolean[] flag = new boolean[1];
            flag[0]=false;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String responseData =  HttpCon.open(LoginActivity.this,"http://"+getString(R.string.ipdress)+"/login","id", mEmail, "password", mPassword);
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(responseData);
                        String resflag = jsonObject.getString("status");

                        Log.d("LoginActivity", "run: "+responseData);
                        Log.d("LoginActivity", "0run: "+resflag);

                        if(resflag.equals("true")){
                            flag[0] =true;
                            mHandler.sendEmptyMessage(0);
                            Log.d("LoginActivity", "1run: "+flag[0]);
                        } else{
                            flag[0] =false;
                            mHandler.sendEmptyMessage(1);
                            Log.d("LoginActivity", "2run: "+flag[0]);
                        }
                        //parseJSONWithJSONObject(responseData);
                        Log.d("LoginActivity", "已接受服务端数据"+responseData);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
            /*new Thread(new Runnable() {
                @Override
                public void run() {
                    try{
                        OkHttpClient client = new OkHttpClient();
                        FormBody.Builder builder = new FormBody.Builder();
                        RequestBody requestBody = builder
                                .add("id", mEmail)
                                .add("password", mPassword)
                                .build();

                        Request request = new Request.Builder()
                                .url("http://"+getString(R.string.ipdress)+"/login")
                                .post(requestBody)
                                .build();
                        Log.d("LoginActivity", "emailandpassword"+mEmail+mPassword);

                        Response response = client.newCall(request).execute();

                        String responseData = response.body().string();
                        JSONObject jsonObject = new JSONObject(responseData.toString());
                        String resflag = jsonObject.getString("status");

                        Log.d("LoginActivity", "run: "+responseData);
                        Log.d("LoginActivity", "0run: "+resflag);

                        if(resflag.equals("true")){
                            flag[0] =true;
                            mHandler.sendEmptyMessage(0);
                            Log.d("LoginActivity", "1run: "+flag[0]);
                        } else{
                            flag[0] =false;
                            mHandler.sendEmptyMessage(1);
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
                        Log.d("LoginActivity", "arun: "+flag[0]);
                        flag[0] =true;
                        mHandler.sendEmptyMessage(2);
                    }
                }
            }).start();*/
            Log.d("LoginActivity", "3run: "+flag[0]);
            return flag[0];
            /*try {
                // Simulate network access.
                String thisurl = "http://"+getString(R.string.ipdress)+"/login?id="+mEmail+"&password="+mPassword;
                Log.d("LoginActivity", "doInBackground: "+thisurl);
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url(thisurl).build();
                Response response = client.newCall(request).execute();
                String responseData = response.body().string();
                Log.d("LoginActivity", "doInBackground: "+responseData);
                if(responseData.equals("true\n")){
                    return true;
                }
            }catch (IOException e) {
                e.printStackTrace();
            }*/
        }

    }
    public void register(View view){
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }
    public void modifypass(View view){
        Intent intent = new Intent(LoginActivity.this, ModifyPassword.class);
        startActivity(intent);
    }
}

