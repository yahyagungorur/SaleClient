package com.example.yahyagungorur.saleclient;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.net.Socket;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class UploadSales extends AppCompatActivity {
    private SocketHandler socketHandler;
    private Socket socket;
    private Context context=this;
    private ListView lvProducts;
    private CustomAdapter customAdapter;
    private ArrayList<String> productNames;
    private ArrayList<String> productVatRates;
    private ArrayList<String> productUnitPrice;
    private ArrayList<Integer> productSales;
    double Cash=0,Credit=0;
    int count =0;
    private TextView txtTotal;
    private DecimalFormat df = new DecimalFormat("#.##");




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_sales);

        socket =socketHandler.getSocket();

        Intent intent = getIntent();
        final ArrayList<String> products = intent.getStringArrayListExtra("ProductArray");

        productNames=  new ArrayList<>();
        productVatRates=new ArrayList<>();
        productUnitPrice=new ArrayList<>();
        productSales=new ArrayList<>();

        for(int i=0;i< products.size();i++){
            if(i%3==0)
                productNames.add(products.get(i));
            if(i%3==1)
                productVatRates.add(products.get(i));
            if(i%3==2)
                productUnitPrice.add(products.get(i));

        }

        for(String st:productNames)
            productSales.add(0);


        lvProducts =(ListView)findViewById(R.id.lvproducts);
        customAdapter = new CustomAdapter(productNames,productUnitPrice,context);
        lvProducts.setAdapter(customAdapter);


        txtTotal= (TextView)findViewById(R.id.total);
        lvProducts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                double Total = Double.valueOf(txtTotal.getText().toString())+Double.valueOf(productUnitPrice.get(i));
                txtTotal.setText( df.format(Total));
                productSales.set(i,productSales.get(i)+1);


            }
        });
        Button btnCash = (Button)findViewById(R.id.btnCash);
        btnCash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                        count++;
                        Cash = Double.valueOf(txtTotal.getText().toString())+Cash;
                        txtTotal.setText("0");
                        Toast.makeText(getApplicationContext(),"PAYMENT DONE ! TOTAL CASH ="+Cash,Toast.LENGTH_SHORT).show();

            }
        });


    Button btnCredit = (Button)findViewById(R.id.btnCredit);
    btnCredit.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {


                    count++;
                    Credit = Double.valueOf(txtTotal.getText().toString())+Credit;
                    txtTotal.setText("0");
                    Toast.makeText(getApplicationContext(),"PAYMENT DONE ! TOTAL CREDIT ="+Credit+" TL",Toast.LENGTH_SHORT).show();
        }
    });

        Button btnSync = (Button)findViewById(R.id.btnSync);
        btnSync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent= new Intent(getApplicationContext(), Main2Activity.class);
               intent.putExtra("count",count);
               intent.putExtra("cash",Cash);
               intent.putExtra("credit",Credit);
               intent.putIntegerArrayListExtra("sales",productSales);
               startActivity(intent);
               count=0;
               Cash=0;
               Credit=0;
               productSales.clear();
            }
        });

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Toast.makeText(getApplicationContext(),"ALL PAYMENT CANCELED",Toast.LENGTH_SHORT).show();
        Intent intent= new Intent(getApplicationContext(), Main2Activity.class);
        startActivity(intent);
        count=0;
        Cash=0;
        Credit=0;
        productSales.clear();

    }

}
