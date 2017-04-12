package org.bjtu;

import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class Test {
    public static void main(String[] args) {
        String topic        = "tp1";
        String content      = "I am Java";
        int qos             = 2;//最多一次（0）最少一次（1）只一次（2）
        String broker       = "ssl://127.0.0.1:8883";
        String clientId     = "JavaClient1";
        MemoryPersistence persistence = new MemoryPersistence();
        try {
            MqttClient sampleClient = new MqttClient(broker, clientId, persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            connOpts.setAutomaticReconnect(true);
            connOpts.setUserName("java");
            connOpts.setPassword("123456".toCharArray());
            sampleClient.connect(connOpts);
            //连接成功
            //订阅tp1主题
            sampleClient.subscribe(topic,new IMqttMessageListener() {			
				@Override
				public void messageArrived(String arg0, MqttMessage arg1) throws Exception {
					// TODO Auto-generated method stub
					System.out.println("topic:"+arg0+";message:"+arg1);				
				}
			});
            //发布tp1主题的消息
            MqttMessage message = new MqttMessage(content.getBytes());
            message.setQos(qos);         
            sampleClient.publish(topic, message);
            //下面的是断开连接的方法，要保持长连接则注释掉
            //sampleClient.disconnect();
            //System.out.println("Disconnected");
            //System.exit(0);
        } catch(MqttException me) {
            System.out.println("reason "+me.getReasonCode());
            System.out.println("msg "+me.getMessage());
            System.out.println("loc "+me.getLocalizedMessage());
            System.out.println("cause "+me.getCause());
            System.out.println("excep "+me);
            me.printStackTrace();
        }
    }
}
