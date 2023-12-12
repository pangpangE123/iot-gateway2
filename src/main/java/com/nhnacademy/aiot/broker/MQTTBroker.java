package com.nhnacademy.aiot.broker;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/*
 * MQTT 클라이언트 간의 메세지를 조정하는 클래스입니다. 
 */
public interface MQTTBroker {
    /**
     * MQTTCLient가 subscribe하도록 합니다.
     * 
     * @param topicFilter     subscribe할 topic (application name)
     * @param messageListener 수신된 메세지를 처리할 callback
     */
    public void subscribe(String topicFilter, IMqttActionListener messageListener);

    /**
     * MQTTClient가 publish하도록 합니다.
     * 
     * @param topic   메세지를 보낼 topic
     * @param message 보낼 메세지
     */
    public void publish(String topic, MqttMessage message);
}
