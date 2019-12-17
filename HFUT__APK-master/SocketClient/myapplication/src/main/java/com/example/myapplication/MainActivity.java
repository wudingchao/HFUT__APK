package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {
    private final int HANDLER_MSG_TELL_RECV = 0X124;
    public static final int SHOW_RESPONSE = 0;
    private EditText client_host_ip, client_port,client_username,client_password;
    private Button client_submit;
    private TextView responseText;
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage("登录成功:" );
            builder.create().show();
            switch (msg.what){
                case SHOW_RESPONSE:

                    String response =(String)msg.obj;
                    responseText.setText(response);
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initEvent();
    }

    private void initEvent() {
        client_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String host = client_host_ip.getText().toString();
                String port = client_port.getText().toString();
                String username =client_username.getText().toString();
                String password =client_password.getText().toString();
                //byte[] content = client_content.getText().;
                byte[] content = {0x03, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
                int lenth1 = 100;//0x6E,   //110,代表字数，这里要改，100+用户名长度+密码长度
                byte[] content2={
                        0x00, 0x01, 0x00, 0x00, 0x13, 0x00, 0x03,
                        0x00, 0x07, 0x43, 0x6D, 0x64, 0x54, 0x79,
                        0x70, 0x65, 0x06, 0x00, 0x00, 0x27, 0x11,
                        0x00, 0x0D, 0x54, 0x72, 0x61, 0x6E, 0x73,
                        0x61, 0x63, 0x74, 0x69, 0x6F, 0x6E, 0x49,
                        0x64, 0x11, 0x00, 0x0D, 0x6C, 0x6F, 0x67,
                        0x69, 0x6E, 0x61, 0x63, 0x74, 0x69, 0x76,
                        0x69, 0x74, 0x79, 0x00, 0x06, 0x4F, 0x62,
                        0x6A, 0x65, 0x63, 0x74, 0x13, 0x00, 0x03,
                        0x00, 0x09,    //，00是空字符null，09是水平定位符号
                        0x55, 0x53, 0x45, 0x52, 0x5F, 0x4E, 0x41, 0x4D, 0x45,  //USER_NAME
                        0x11,           //11是设备控制1，此处可改
                        0x00};
                int lenth2;//0x08  //04是传输结束，此处要改,此处为用户名长度


                //byte[] username={0x6D, 0x69, 0x6D, 0x61};                  //mima    //这里要改
                byte[] content3={0x00, 0x08,     //08是退格，此处不可改
                        0x50, 0x41, 0x53, 0x53, 0x57, 0x4F, 0x52, 0x44,         //PASSWORD
                        0x11,           //此处11不可改
                        0x00, 0x06} ; //06是确认回应
                //String password="123456";//123456    这里要改
                byte[] content4={0x00, 0x04, 0x54, 0x59, 0x50, 0x45,            // TYPE
                        0x11, 0x00, 0x05,   //05是请求，此处可改
                        0x50, 0x48, 0x4F, 0x4E, 0x45 };           //PHONE

                //byte[] content3;
                lenth1=lenth1+username.length()+password.length();
                lenth2=username.length();
                startNetThread(host, Integer.parseInt(port), content,lenth1,content2,lenth2,username,content3,password,content4);
            }
        });
    }

    private void initViews() {
        client_host_ip = findViewById(R.id.client_host_ip);
        client_port = findViewById(R.id.client_host_port);
        client_submit = findViewById(R.id.client_submit);
        client_username = findViewById(R.id.user_name);
        client_password = findViewById(R.id.pass);
        responseText = findViewById(R.id.responseText);

    }

    private void startNetThread(final String host, final int port, final byte[] data1,final int len,final byte[] data2 ,final  int len2,final String username,final byte[] data3,
                                final String pass,final byte[] data4) {
        new Thread() {
            @Override
            public void run() {
                try {
                    Socket socket = new Socket(host, port);
                    OutputStream outputStream = socket.getOutputStream();
                    outputStream.write(data1);
                    outputStream.write(len);
                    outputStream.write(data2);
                    outputStream.write(len2);
                    outputStream.write(username.getBytes());
                    outputStream.write(data3);
                    outputStream.write(pass.getBytes());
                    outputStream.write(data4);
                    outputStream.flush();
                    System.out.println("congtent:" + socket);
                    InputStream is = socket.getInputStream();

                    //想办法把服务器发给客户端的数据读出来
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                    String line = reader.readLine();
                    StringBuilder response= new StringBuilder();            //这里内存会炸掉，暂时无法将服务器发过来的东西读出来
                                    //猜测服务器发过来的可能是图片什么的
                                        //Throwing OutOfMemoryError "Failed to allocate a 191179624 byte allocation with 16777216 free bytes and 129MB until OOM"
                   /* while ((line!=null)){
                        response.append(line);
                    }*/
                    Message message = new Message();
                    message.what=SHOW_RESPONSE;  //将服务器返回的结果放到Message中
                    message.obj =response.toString();
                    handler.sendMessage(message);

                   /* byte[] bytes = new byte[1024];

                    int n = is.read();*/

                   // System.out.println(new String(bytes, 0, n));

                    //Message msg = handler.obtainMessage(HANDLER_MSG_TELL_RECV, new String(bytes, 0, n));
                   // msg.sendToTarget();
                    is.close();
                    socket.close();
                } catch (Exception e) {
                }
            }
        }.start();
    }
}
