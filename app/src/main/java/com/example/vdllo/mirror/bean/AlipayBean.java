package com.example.vdllo.mirror.bean;

/**
 * Created by dllo on 16/4/19.
 */
public class AlipayBean {
    /**
     * msg :
     * data : {"str":"service=\"mobile.securitypay.pay\"&partner=\"2088021758262531\"&_input_charset=\"utf-8\"&notify_url=\"http%3A%2F%2Fapi.mirroreye.cn%2Findex.php%2Fali_notify\"&out_trade_no=\"146095206349I\"&subject=\"GENTLE MONSTER\"&payment_type=\"1\"&seller_id=\"2088021758262531\"&total_fee=\"450.00\"&body=\"GENTLE MONSTER\"&it_b_pay =\"30m\"&sign=\"bYZznNrdaie8k9QaIyAF4Bqs%2Bs0KqW%2BAVntnzGL2mPxU9%2B8ISMxRei6LeJgFCueUSGPx4OfQ3d2Yqsq2GHDu3aRTmqr168dokGt8G1W8keS7ka9p%2BC6Y4KUUSz7euBo193%2B82%2F96aUzC9WnWqhRLrcA3C6MsNFLV9OnfrE2jFMM%3D\"&sign_type=\"RSA\""}
     */
    private String msg;
    private DataEntity data;

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public DataEntity getData() {
        return data;
    }

    public class DataEntity {
        /**
         * str : service="mobile.securitypay.pay"&partner="2088021758262531"&_input_charset="utf-8"&notify_url="http%3A%2F%2Fapi.mirroreye.cn%2Findex.php%2Fali_notify"&out_trade_no="146095206349I"&subject="GENTLE MONSTER"&payment_type="1"&seller_id="2088021758262531"&total_fee="450.00"&body="GENTLE MONSTER"&it_b_pay ="30m"&sign="bYZznNrdaie8k9QaIyAF4Bqs%2Bs0KqW%2BAVntnzGL2mPxU9%2B8ISMxRei6LeJgFCueUSGPx4OfQ3d2Yqsq2GHDu3aRTmqr168dokGt8G1W8keS7ka9p%2BC6Y4KUUSz7euBo193%2B82%2F96aUzC9WnWqhRLrcA3C6MsNFLV9OnfrE2jFMM%3D"&sign_type="RSA"
         */
        private String str;

        public void setStr(String str) {
            this.str = str;
        }

        public String getStr() {
            return str;
        }
    }
}
