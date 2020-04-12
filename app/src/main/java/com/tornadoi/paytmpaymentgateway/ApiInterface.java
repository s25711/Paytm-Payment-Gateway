package com.tornadoi.paytmpaymentgateway;

import com.google.gson.JsonObject;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {

    @POST("Paytm/generateChecksum.php")
    @FormUrlEncoded
    Call<CheckSumHashModel> generateCheckSumForPAytm(@Field("MID") String MID,
                                                 @Field("ORDER_ID") String ORDER_ID,
                                                 @Field("CUST_ID") String CUST_ID,
                                                 @Field("CHANNEL_ID") String CHANNEL_ID,
                                                 @Field("TXN_AMOUNT") String TXN_AMOUNT,
                                                 @Field("WEBSITE") String WEBSITE,
                                                 @Field("INDUSTRY_TYPE_ID") String INDUSTRY_TYPE_ID,
                                                 @Field("CALLBACK_URL") String CALLBACK_URL);
}
