package com.example.FCAI.service.notificationV2;

import com.example.FCAI.api.model.Message;

public class ArabicShipmentMessage extends MessageFormatter{

    public ArabicShipmentMessage(){
    }


    @Override
    public Message formatMessage() {
        message.setMessage( "عزيزنا العميل " + customer + ", " + " لقد وصل طلبك ل " + getProductsMessage()
                + " تحت رقم " + getOrderId() + " لعنوان " + getOrderDistrict() + ", " + getOrderLocation()
                + "\nشكرا لاستخدامك متجرنا :)");
        return message;
    }
}
