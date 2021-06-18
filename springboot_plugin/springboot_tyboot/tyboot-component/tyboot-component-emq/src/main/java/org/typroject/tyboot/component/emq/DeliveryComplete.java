package org.typroject.tyboot.component.emq;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;

public interface DeliveryComplete {


    void complete(IMqttDeliveryToken token);
}
