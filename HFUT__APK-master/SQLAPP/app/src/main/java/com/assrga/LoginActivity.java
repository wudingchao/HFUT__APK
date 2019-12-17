package com.assrga;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.multidex.MultiDex;

import com.assrga.activity.BaseActivity;

public class LoginActivity extends BaseActivity {


    Handler handler = new Handler();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final EditText mEmailView = (EditText) findViewById(R.id.username);//1.用户
        //populateAutoComplete();//动态获取PERMISSION_GRANTED(通讯录权限)

        final EditText mPasswordView = (EditText) findViewById(R.id.password);//2.密码
        //setOnEditorActionListener：编辑完之后点击软键盘上的回车键才会触发
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {//当用户处在输入密码编辑框，软键盘打开，输入确定按钮(EditorInfo.IME_NUL)时尝试登录
                // 按下完成按钮，这里IME_ACTION_DONE和.xml中imeOptions对应
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    //attemptLogin();
                    return true;//返回true，保留软键盘。false，隐藏软键盘
                }
                return false;
            }
        });
//3.登陆（看这里>>>>>>>>>>
        Button login = (Button) findViewById(R.id.login);//找到按钮

        //mEmailSignInButton.setOnClickListener(this.);//绑定监听器
        WorkThread wt=new WorkThread();
        wt.start();//调动子线程
//看到这就可以了>>>>>>>>>
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s= mEmailView.getText().toString();//获取页面密码
                String sy=mPasswordView.getText().toString();//获取页面用户名
                Message m=handler.obtainMessage();//获取事件
                Bundle b=new Bundle();
                b.putString("pass",s);//以键值对形式放进 Bundle中
                b.putString("name",sy);
                m.setData(b);
                handler.sendMessage(m);//把信息放到通道中


            }
        });

        //mLoginFormView = findViewById(R.id.login_form);//滑动视图
        //mProgressView = findViewById(R.id.login_progress);//进度条

    }

    class WorkThread extends  Thread{
        @Override
        public  void run(){
            Looper.prepare();
             handler=new Handler(){
                @Override
                public  void handleMessage(Message m){
                    super.handleMessage(m);
                    Bundle b= m.getData();//得到与信息对用的Bundle
                    String name=b.getString("name");//根据键取值
                    String pass=b.getString("pass");
                    //System.out.println(name+","+pass);
                    DBUtil db=new DBUtil(pass,name);//调用数据库查询类
                    String ret=db.QuerySQL();//得到返回值
                    if (ret.equals("1"))//为1，页面跳转，登陆成功
                    {
                        Intent localIntent = new Intent();
                        localIntent.setClass(LoginActivity.this, MainActivity.class);
                        LoginActivity.this.startActivity(localIntent);
                        Toast.makeText(LoginActivity.this, "登录成功",Toast.LENGTH_SHORT).show();//显示提示框
                        return;
                    }
                    Toast.makeText(LoginActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();

                }
            };
            Looper.loop();//Looper循环，通道中有数据执行，无数据堵塞
        }
    }



}
