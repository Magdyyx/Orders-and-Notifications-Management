package com.example.FCAI.service.notificationService.MessageFacility;

public class ArabicShipmentMessage extends MessageFormatter{

    public ArabicShipmentMessage(){
    }


    @Override
    public void stringFormat() {
        message.setMessage( "عزيزنا العميل " + customer + ", " + " لقد وصل طلبك ل " + getProductsMessage()
                + " تحت رقم " + getOrderId() + " لعنوان " + getOrderDistrict() + ", " + getOrderLocation()
                + "\nشكرا لاستخدامك متجرنا :)");
    }

    @Override
    public void setMessageType() {
        message.setMessageType("shipment");

    }

    @Override
    public void setMessageLanguage() {
        message.setMessageLanguage("arabic");
    }
}
