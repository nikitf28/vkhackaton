package ru.dolbak.vkkavkaton;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText email, pwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email = findViewById(R.id.email);
        pwd = findViewById(R.id.pwd);
    }

    public void send(View view){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String emailStr = String.valueOf(email.getText());
                String pwdStr = String.valueOf(pwd.getText());
                String response = null;
                try {
                    response = APIConnector.authorize(emailStr, pwdStr);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                final String toastStr = response;
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast toast = Toast.makeText(MainActivity.this, toastStr, Toast.LENGTH_LONG);
                        toast.show();
                    }
                });
            }
        }).start();
    }
}
