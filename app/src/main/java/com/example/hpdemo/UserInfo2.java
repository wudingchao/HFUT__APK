package com.example.hpdemo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class UserInfo2 extends AppCompatActivity {

    @BindView(R.id.linear)
    LinearLayout linear;
    @BindView(R.id.image_user)
    ImageView imageUser;
    @BindView(R.id.linear_image)
    LinearLayout linearImage;
    @BindView(R.id.edt_user)
    EditText edtUser;
    @BindView(R.id.linear_user)
    LinearLayout linearUser;
    @BindView(R.id.edt_password)
    EditText edtPassword;
    @BindView(R.id.linear_password)
    LinearLayout linearPassword;
    @BindView(R.id.gab)
    View gab;
    @BindView(R.id.checkbox_remember_password)
    CheckBox checkboxRememberPassword;
    @BindView(R.id.btn_server)
    Button btnServer;
    @BindView(R.id.server)
    RelativeLayout server;
    @BindView(R.id.login)
    Button login;
    @BindView(R.id.login_offline)
    Button loginOffline;
    @BindView(R.id.forget_password)
    TextView forgetPassword;
    @BindView(R.id.register)
    TextView register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        ButterKnife.bind(this);
    }

    private void getUserInfo() {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .get()

                .url("http://192.168.1.252:8066")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("UserInfoActivity", "onFailure: 请求失败" + e.getLocalizedMessage());

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String json = response.body().string();

                    showUser(json);
                }

            }

            private void showUser(final String json) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject jsonObject = new JSONObject(json);
                            String id = jsonObject.optString("id");
                            String username = jsonObject.optString("username");
                            String head_url = jsonObject.optString("head_url");

                            //mTexUsername.setText(username);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    @OnClick(R.id.login)
    public void onViewClicked() {
        getUserInfo();
    }
}
