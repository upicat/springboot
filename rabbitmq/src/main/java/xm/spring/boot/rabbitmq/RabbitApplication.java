package xm.spring.boot.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;

@Configuration
@EnableAutoConfiguration
@ComponentScan
//@RestController
//@SpringBootApplication
public class RabbitApplication {

  @RequestMapping("/")
  public String home() {
    return "Hello World";
  }
  
  final static String queueName = "spring-boot";

  @Bean
  Queue queue() {
      return new Queue(queueName, false);
  }

  @Bean
  TopicExchange exchange() {
      return new TopicExchange("spring-boot-exchange");
  }

  @Bean
  Binding binding(Queue queue, TopicExchange exchange) {
      return BindingBuilder.bind(queue).to(exchange).with(queueName);
  }

  @Bean
  SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
          MessageListenerAdapter listenerAdapter) {
      SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
      container.setConnectionFactory(connectionFactory);
      container.setQueueNames(queueName);
      container.setMessageListener(listenerAdapter);
      return container;
  }

  @Bean
  MessageListenerAdapter listenerAdapter(Receiver receiver) {
      return new MessageListenerAdapter(receiver, "receiveMessage");
  }

  public static void main(String[] args) {
    SpringApplication.run(RabbitApplication.class, args);
  }

}