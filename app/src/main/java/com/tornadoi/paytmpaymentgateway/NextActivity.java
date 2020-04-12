package com.tornadoi.paytmpaymentgateway;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class NextActivity extends AppCompatActivity {
    TextView status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);

        status = findViewById(R.id.status);

        Intent intent = getIntent();
        if (intent!=null){

            String response = intent.getStringExtra("response");
            if (response.equals("success"))
            {
                status.setText("Payment Sucess");
            } else if (response.equals("cancel")) {

                status.setText("Transaction cancelled");
            } else if (response.equals("ui")) {

                status.setText("UI error");
            } else if (response.equals("client")) {

                status.setText("Transaction failed due to client authentication");
            }else if (response.equals("webpage")){
                status.setText("Error in webpage loading...");
            }
        }
    }
}
