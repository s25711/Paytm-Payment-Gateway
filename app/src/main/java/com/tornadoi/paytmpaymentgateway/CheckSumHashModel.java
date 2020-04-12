package com.tornadoi.paytmpaymentgateway;

public class CheckSumHashModel {
    private String ORDER_ID;

    private String payt_STATUS;

    private String CHECKSUMHASH;

    public String getORDER_ID ()
    {
        return ORDER_ID;
    }

    public void setORDER_ID (String ORDER_ID)
    {
        this.ORDER_ID = ORDER_ID;
    }

    public String getPayt_STATUS ()
    {
        return payt_STATUS;
    }

    public void setPayt_STATUS (String payt_STATUS)
    {
        this.payt_STATUS = payt_STATUS;
    }

    public String getCHECKSUMHASH ()
    {
        return CHECKSUMHASH;
    }

    public void setCHECKSUMHASH (String CHECKSUMHASH)
    {
        this.CHECKSUMHASH = CHECKSUMHASH;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [ORDER_ID = "+ORDER_ID+", payt_STATUS = "+payt_STATUS+", CHECKSUMHASH = "+CHECKSUMHASH+"]";
    }
}
