package point2point;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;

import javax.jms.*;
import java.net.URI;

/**
 * @author: sqt
 * @Description:
 * @Date: Created in 10:25 ${Date}
 */
public class ActiveMQProducer {
   /* 生产者：生产消息，发送端。
    第一步：创建ConnectionFactory对象，需要指定服务端ip及端口号。
    第二步：使用ConnectionFactory对象创建一个Connection对象。
    第三步：开启连接，调用Connection对象的start方法。
    第四步：使用Connection对象创建一个Session对象。
    第五步：使用Session对象创建一个Destination对象（topic、queue），此处创建一个Queue对象。
    第六步：使用Session对象创建一个Producer对象。
    第七步：创建一个Message对象，创建一个TextMessage对象。
    第八步：使用Producer对象发送消息。
    第九步：关闭资源。*/
   public static void main(String[] args) {
       //连接工厂
       ConnectionFactory connectionFactory;
       //连接
       Connection connection = null;
       //会话 接受或者发送消息的线程
       Session session;
       //消息的目的地
       Destination destination;
       //消息生产者
       MessageProducer messageProducer;
       //实例化连接工厂
       connectionFactory = new ActiveMQConnectionFactory("admin", "admin", "tcp://ip:61616");

       try {
           //通过连接工厂获取连接
           connection = connectionFactory.createConnection();
           //启动连接
           connection.start();
           //创建session
           session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
           //创建一个名称为HelloWorld的消息队列
           destination = session.createQueue("这是我创建的消息队列");
           //创建消息生产者
           messageProducer = session.createProducer(destination);
           //发送消息
           sendMessage(session, messageProducer);

           session.commit();

       } catch (Exception e) {
           e.printStackTrace();
       }finally{
           if(connection != null){
               try {
                   connection.close();
               } catch (JMSException e) {
                   e.printStackTrace();
               }
           }
       }

   }
    /**
     * 发送消息
     * @param session
     * @param messageProducer  消息生产者
     * @throws Exception
     */
    public static void sendMessage(Session session,MessageProducer messageProducer) throws Exception{
        for (int i = 0; i < 5; i++) {
            //创建一条文本消息
            TextMessage message = session.createTextMessage("ActiveMQ 发送消息" +i);
            System.out.println("发送消息：Activemq 发送消息" + i);
            //通过消息生产者发出消息
            messageProducer.send(message);
        }

    }

}
