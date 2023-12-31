package com.example.FCAI.service.notificationV2;

import com.example.FCAI.api.model.Message;

public class ArabicOrderMessage extends MessageFormatter {
    public ArabicOrderMessage(){
    }

    @Override
    public void stringFormat() {
        message.setMessage( "عزيزنا العميل " + customer + ", " + "لقد تم تأكيد شرائك ل " + getProductsMessage()
                + " تحت رقم " + getOrderId() + "\nشكرا لاستخدامك متجرنا :)");
    }

    @Override
    public void setMessageType() {
        message.setMessageType("order");
    }

    @Override
    public void setMessageLanguage() {
        message.setMessageLanguage("arabic");

    }
}
