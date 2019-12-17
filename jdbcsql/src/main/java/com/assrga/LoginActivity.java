package com.assrga;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.username)
    EditText username;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.login2)
    Button login2;
    @BindView(R.id.loading)
    ProgressBar loading;
    @BindView(R.id.container)
    ConstraintLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);


        loginConnection();

    }

    private void loginConnection() {
        Connection conn;//连接对象
        Statement stmt;//将SQL语句发送到数据库的对象
        ResultSet rs;//查询结果集对象
        String url = "jdbc:sqlserver://192.168.1.252:1433;DatabaseName=HelpBMTCC;";
        //数据库服务器地址和表名
        String sql;//SQL命令
        sql = "select * from Users;";//填写SQL语句
        try {
            // 连接数据库,传送数据库账号密码
            conn = DriverManager.getConnection(url, "sa", "5329625");
            // 建立Statement对象.发送SQL
            stmt = conn.createStatement();
            /**
             * Statement createStatement() 创建一个 Statement 对象来将 SQL 语句发送到数据库。
             */
            // 执行数据库查询语句
            rs = stmt.executeQuery(sql);
            /**
             * ResultSet executeQuery(String sql) throws SQLException 执行给定的 SQL
             * 语句，该语句返回单个 ResultSet 对象
             */
            while (rs.next()) {

                String name = rs.getString("UserName");
                String pass = rs.getString("Password");
                //int id = rs.getInt("Sno");
                //int age = rs.getInt("Sage");
                //分别获取查询数据项
                System.out.println("UserName:" + name + "\tPassword:" + pass );
            }
            if (rs != null) {
                rs.close();
                rs = null;
            }
            if (stmt != null) {
                stmt.close();
                stmt = null;
            }
            if (conn != null) {
                conn.close();
                conn = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("数据库连接失败");
        }
    }
}
