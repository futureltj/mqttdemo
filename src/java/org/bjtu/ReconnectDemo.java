package org.bjtu;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class ReconnectDemo implements MqttCallbackExtended{
	private String topic        = "tp1";
	private String content      = "I am Java";
	private int qos             = 2;//最多一次（0）最少一次（1）只一次（2）
	private String broker       = "ssl://127.0.0.1:8883";
	private String clientId     = "JavaClient1";
	private MemoryPersistence persistence = new MemoryPersistence();
	private MqttClient sampleClient;
	private MqttConnectOptions connOpts ;
	public static void main(String[] args) {
		ReconnectDemo demo=new ReconnectDemo();
		demo.connect();
	}
	public void connect(){
		try {
			System.out.println("connecting..");
            this.sampleClient = new MqttClient(broker, clientId, persistence);
            this.connOpts= new MqttConnectOptions();
            this.connOpts.setCleanSession(true);
            this.connOpts.setAutomaticReconnect(true);         
            this.connOpts.setUserName("java");
            this.connOpts.setPassword("123456".toCharArray());
            //将整个过程中的事件注册到this
            this.sampleClient.setCallback(this);
            this.sampleClient.connect(connOpts);            
        }
		catch(Exception e){
			System.out.println("Exception occured while connecting to broker");
		}
	}
	@Override
	public void connectionLost(Throwable arg0) {
		// TODO Auto-generated method stub
		System.out.println("连接断开");
		
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken arg0) {
		// TODO Auto-generated method stub
		//System.out.println("发布到服务器成功");
	}

	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		// TODO Auto-generated method stub
		System.out.println(topic+":"+message.toString());
		
	}

	@Override
	public void connectComplete(boolean reconnect, String url) {
		// TODO Auto-generated method stub
		try {
			//reconnect判断是否是重连还是第一次连
			System.out.println("connected");
			sampleClient.subscribe(topic,qos);
			sampleClient.publish(topic, new MqttMessage(content.getBytes()));
		} catch (MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
