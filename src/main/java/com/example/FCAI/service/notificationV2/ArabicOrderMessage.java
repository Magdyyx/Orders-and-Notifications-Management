package com.example.FCAI.service.notificationV2;

import com.example.FCAI.api.model.Message;

public class ArabicOrderMessage extends MessageFormatter {
    public ArabicOrderMessage(){
    }


    @Override
    public Message formatMessage() {
        message.setMessage( "عزيزنا العميل " + customer + ", " + "لقد تم تأكيد شرائك ل " + getProductsMessage()
                + "تحت رقم" + getOrderId() + "\nشكرا لاستخدامك متجرنا :)");
        return message;
    }
}
