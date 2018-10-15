package topic;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.junit.Test;

import javax.jms.*;

/**
 * @author: sqt
 * @Description:
 * @Date: Created in 10:19 ${Date}
 */
public class TopicProducer {
   /* public static void main(String[] args) {
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
            destination = session.createTopic("topic");
            //创建消息生产者
            messageProducer = session.createProducer(destination);
            //发送消息
            TextMessage message = session.createTextMessage("topic发送的消息1");
            messageProducer.send(message);

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

    }*/

       @Test
       public void send() throws Exception{
           // 1.创建一个连接工厂 （Activemq的连接工厂）参数：指定连接的activemq的服务
           ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("admin", "admin", "tcp://ip:61616");
           // 2.获取连接
           Connection connection = connectionFactory.createConnection();
           // 3.开启连接
           connection.start();
           // 4.根据连接对象创建session
           // 第一个参数：表示是否使用分布式事务（JTA）
           // 第二个参数：如果第一个参数为false,第二个参数才有意义；表示使用的应答模式 ：自动应答，手动应答.这里选择自动应答。
           Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
           // 5.根据session创建Destination(目的地，queue topic,这里使用的是topic)
           Topic topic = session.createTopic("topic");//---------------------
          // 6.创建生产者
           MessageProducer producer = session.createProducer(topic);
           // 7.构建消息对象，（构建发送消息的内容） 字符串类型的消息格式（TEXTMessage）
           TextMessage textMessage = new ActiveMQTextMessage();
           textMessage.setText("发送消息123");// 消息的内容
           // 8.发送消息
           producer.send(textMessage);
           // 9.关闭资源
           producer.close();
           session.close();
           connection.close();

   }


}