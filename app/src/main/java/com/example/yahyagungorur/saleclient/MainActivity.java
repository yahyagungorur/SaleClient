package com.example.yahyagungorur.saleclient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.net.Socket;
import java.io.IOException;


public class MainActivity extends AppCompatActivity {
    private Socket socket;
    private SocketHandler socketHandler;
    private static final int SERVER_PORT =5000;
    private static final String SERVER_IP = "192.168.154.2";
    private boolean connect =false;
    private Intent intent;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button btnCon = (Button) findViewById(R.id.BtnConnect);
        final EditText edtPort = (EditText)findViewById(R.id.EdtPort);
        final EditText edtIp = (EditText)findViewById(R.id.EdtIp);
        btnCon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = getIntent();
                connect = intent.getBooleanExtra("connect",false);
                if(!connect){
                    try {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try{
                                    String host = edtIp.getText().toString();
                                    int port = Integer.parseInt(edtPort.getText().toString());
                                    socket = new Socket(host,port);
                                    socketHandler.setSocket(socket);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                intent = new Intent(getApplicationContext(), Main2Activity.class);
                startActivity(intent);
            }
        });
    }
}

