package com.tornadoi.paytmpaymentgateway;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity  implements PaytmPaymentTransactionCallback {

    EditText edittext_order_id;
    EditText edittext_cust_id;
    EditText edittext_amount;
    Button button_pay;
    ApiInterface apiInterface;

    String marchant_id = "your merchant id";
    String callback_url = "https://pguat.paytm.com/paytmchecksum/paytmCallback.jsp";

    String cust_id;
    String amount;
    String order_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        apiInterface = RetrofitClient.getClient().create(ApiInterface.class);

        edittext_order_id = findViewById(R.id.edittext_order_id);
        edittext_cust_id = findViewById(R.id.edittext_cust_id);
        edittext_amount = findViewById(R.id.edittext_ammount);
        button_pay = findViewById(R.id.button_pay);

        button_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS}, 101);
                }
                generateCheckSum();

            }
        });
    }


    private void generateCheckSum(){

         order_id = edittext_order_id.getText().toString();
         cust_id = edittext_cust_id.getText().toString();
         amount = edittext_amount.getText().toString();

        apiInterface.generateCheckSumForPAytm(marchant_id,
                order_id,
                cust_id,
                "WAP",
                "50",
                "WEBSTAGING",
                "Retail",
                callback_url).enqueue(new Callback<CheckSumHashModel>() {
            @Override
            public void onResponse(Call<CheckSumHashModel> call, Response<CheckSumHashModel> response) {
                if (response.body()!=null){

                    CheckSumHashModel model = response.body();

                    String checkSum = model.getCHECKSUMHASH();

                    HashMap<String, String> paramMap = new HashMap<>();
                    paramMap.put("MID", marchant_id);
                    paramMap.put("ORDER_ID", order_id);
                    paramMap.put("CHANNEL_ID", "WAP");
                    paramMap.put("CUST_ID", cust_id);
                    paramMap.put("TXN_AMOUNT", amount);
                    paramMap.put("WEBSITE", "WEBSTAGING");
                    paramMap.put("INDUSTRY_TYPE_ID", "Retail");
                    paramMap.put("CALLBACK_URL", callback_url);
                    paramMap.put("CHECKSUMHASH", checkSum);

                    Log.d("test",":"+paramMap);

                    paytmTransaction(paramMap);


                }
            }

            @Override
            public void onFailure(Call<CheckSumHashModel> call, Throwable t) {

            }
        });
    }

    private void paytmTransaction(HashMap<String, String> map){
        PaytmPGService service = PaytmPGService.getStagingService();
        PaytmOrder Order = new PaytmOrder(map);
        service.initialize(Order, null);
        service.startPaymentTransaction(this,true,true,this);
    }


    @Override
    public void onTransactionResponse(Bundle inResponse) {
        Toast.makeText(this, "sucess", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this,NextActivity.class).putExtra("response","success"));

    }

    @Override
    public void networkNotAvailable() {
        Toast.makeText(this, "network not available", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this,NextActivity.class).putExtra("response","network"));


    }

    @Override
    public void clientAuthenticationFailed(String inErrorMessage) {
        Toast.makeText(this, "authentication failed", Toast.LENGTH_SHORT).show();

        startActivity(new Intent(this,NextActivity.class).putExtra("response","client"));

    }

    @Override
    public void someUIErrorOccurred(String inErrorMessage) {
        Toast.makeText(this, "ui-error", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this,NextActivity.class).putExtra("response","ui"));

    }

    @Override
    public void onErrorLoadingWebPage(int iniErrorCode, String inErrorMessage, String inFailingUrl) {
        Toast.makeText(this, "error loading web page", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this,NextActivity.class).putExtra("response","webpage"));


    }

    @Override
    public void onBackPressedCancelTransaction() {
        Toast.makeText(this, "error  on backpress", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this,NextActivity.class).putExtra("response","backpress" +
                ""));


    }

    @Override
    public void onTransactionCancel(String inErrorMessage, Bundle inResponse) {
        Toast.makeText(this, "transaction canecl", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this,NextActivity.class).putExtra("response","cancel"));


    }
}
