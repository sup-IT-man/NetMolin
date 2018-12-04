package com.lml.molin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lml.molin.net.NetMolin;
import com.lml.molin.net.NetworkConfiguration;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NetworkConfiguration configuration = new NetworkConfiguration
                .Builder()
                .setIsToast(true)
                .build();
        NetMolin.getInstance().setConfiguration(configuration).register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        NetMolin.getInstance().unRegister(this);
    }
}
