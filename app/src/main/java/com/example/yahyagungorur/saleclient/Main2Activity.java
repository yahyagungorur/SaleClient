package com.example.yahyagungorur.saleclient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {
    private Intent intent;
    private Socket socket;
    private  SocketHandler socketHandler;
    private BufferedReader in;
    ArrayList<String> arrProducts=new ArrayList<>();
    private Button btncDownloadProduct,btnUploadSales;
    private DecimalFormat df = new DecimalFormat("#.##");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        socket = socketHandler.getSocket();

        btncDownloadProduct= (Button)findViewById(R.id.btnDownloadProduct);
        btncDownloadProduct.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               new Thread(new Runnable() {
                   @Override
                   public void run() {
                       DataOutputStream dout = null;
                       try {
                           dout = new DataOutputStream(socket.getOutputStream());
                           dout.write("GetProducts".getBytes());
                            dout.flush();
                           in =new BufferedReader (new InputStreamReader(socket.getInputStream()));
                           String message_distant;
                           while((message_distant = in.readLine())!= null){
                               if(message_distant.contains("END"))
                                   break;
                               arrProducts.add(message_distant);
                           }

                       } catch (IOException e) {
                          e.printStackTrace();

                       }
                   }
               }).start();


           }
       });

        btnUploadSales = (Button)findViewById(R.id.btnUploadSales);
        btnUploadSales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double salesTotal = 0;
                intent =getIntent();
                final ArrayList<Integer> Sales = intent.getIntegerArrayListExtra("sales");
                final String count = String.valueOf(intent.getIntExtra("count",0));
                final String cash = String.valueOf(intent.getDoubleExtra("cash",0));
                final String credit = String.valueOf(intent.getDoubleExtra("credit",0));
                final String total = df.format(Double.valueOf(cash)+Double.valueOf(credit));
                final String query="("+count+","+total+","+cash+","+credit+");";
                salesTotal = Double.valueOf(total);
                if(salesTotal !=0){
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            DataOutputStream dout = null;
                            try {
                                dout = new DataOutputStream(socket.getOutputStream());
                                dout.write("Sales".getBytes());
                                dout.writeBytes(query);
                                int id=1;
                                for(int sl:Sales){
                                    dout.writeBytes("-"+String.valueOf(id)+" "+String.valueOf(sl));
                                    id++;
                                }

                            } catch (IOException e) {
                                e.printStackTrace();

                            }
                        }
                    }).start();
                    Toast.makeText(getApplicationContext(),"SALES DONE : "+total+" TL",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(),"NO SALES",Toast.LENGTH_SHORT).show();
                }

            }
        });

        Button btnNext = (Button)findViewById(R.id.btnNext);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent= new Intent(getApplicationContext(), UploadSales.class);
                intent.putExtra("ProductArray",arrProducts);
                startActivity(intent);
                arrProducts.clear();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        intent= new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("connect",true);
        startActivity(intent);
        arrProducts.clear();
    }
}
