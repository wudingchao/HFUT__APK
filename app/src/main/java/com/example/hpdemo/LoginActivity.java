package com.example.hpdemo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.helpsoft.Variant;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;
import static com.example.hpdemo.R2.id.password;
import static com.example.hpdemo.R2.id.username;

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.username)
    EditText mEtxtUsername;
    @BindView(R.id.password)
    EditText mEtxtPassword;

    @BindView(R.id.loading)
    ProgressBar loading;
    @BindView(R.id.container)
    ConstraintLayout container;



    private OkHttpClient httpClient;

    private Map<String, Variant> m_map = null;
    private byte m_type = (byte)1;


    public void addValue(String paramString, int paramInt) {
        this.m_type = (byte)19;
        if (this.m_map == null)
            this.m_map = new LinkedHashMap<String, Variant>();
        this.m_map.put(paramString, new Variant(paramInt));

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        httpClient = new OkHttpClient();
        Button login = findViewById(R.id.login2);
        login.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(LoginActivity.this,"你点击了",Toast.LENGTH_SHORT).show();

                String username = mEtxtUsername.getText().toString().trim();
                String password = mEtxtPassword.getText().toString().trim();

              /*  Variant variant = new Variant();
                variant.addValue("USER_NAME", username);
                variant.addValue("PASSWORD", password);
                variant.addValue("TYPE", "PHONE");*/
                loginWithForm(username,password);
            }
        });



    }


    private void loginWithForm(String username ,String password) {

        String url = "http://192.168.1.252:8066";


        RequestBody body =new FormBody.Builder()
                .add("CmdType","TransactionId")
                .add("loginactivity","Object")
                .add("USER_NAME",username)
                .add("PASSWORD",password)
                .add("TYPE","PHONE")
                .build();


        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("LoginActivity", "服务器出差: ");

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){
                    String json = response.body().string();
                    try {
                        JSONObject jsonObject = new JSONObject(json);

                       final String message = jsonObject.optString("message");
                        final int success = jsonObject.optInt("success");

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(success==1)
                                    Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                                    else
                                        Toast.makeText(LoginActivity.this,message,Toast.LENGTH_SHORT).show();

                            }
                        });

                }catch (JSONException e){
                       // e.printStackTrace();
                    }
                }

            }
        });
    }
}
