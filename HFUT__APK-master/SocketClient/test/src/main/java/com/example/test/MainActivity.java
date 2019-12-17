

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;



import com.example.test.R;


import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.logging.Handler;
import java.util.logging.LogRecord;



class LoginActivity extends AppCompatActivity{

    private final int HANDLER_MSG_TELL_RECV = 0X124;
    private EditText user_ID, edt_password;
    private Button login;

    java.util.logging.Handler handler = new Handler() {
        @Override
        public void publish(LogRecord record) {

        }

        @Override
        public void flush() {

        }

        @Override
        public void close() throws SecurityException {

        }

       /* public void handleMessage(Message msg) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage("收到服务器数据" + msg.obj);

            builder.create().show();
        }*/
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initEvent();
    }




    /*login.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startNetThread();
        }
    });*/
    private void initEvent() {
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startNetThread();
            }
        });
    }

    private void initView() {
        user_ID = findViewById(R.id.user_ID);
        edt_password = findViewById(R.id.edt_password);
        login = findViewById(R.id.login);
    }

    private void startNetThread() {

        new Thread() {
            @Override
            public void run() {
                try {
                    final Socket socket = new Socket("192.168.1.252", Integer.valueOf("8066"));

                    String username = user_ID.getText().toString();
                    String password = edt_password.getText().toString();
                    final JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("USER_NAME", username);
                        jsonObject.put("PASSWORD", password);
                        jsonObject.put("TYPE", "PHONE");
                    } catch (Exception e) {

                    }

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                OutputStream os = socket.getOutputStream();
                                os.write(Integer.parseInt(jsonObject.toString() + "/n".getBytes("utf-8")));
                                os.flush();

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();

                } catch (Exception e) {

                }
            }
        }.start();
    }
}
