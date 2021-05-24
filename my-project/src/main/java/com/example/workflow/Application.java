package com.example.workflow;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.spring.boot.starter.event.PostDeployEvent;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;

import java.nio.charset.Charset;

@SpringBootApplication
@EnableRabbit
public class Application {

  public static void main(String... args) {
    SpringApplication.run(Application.class, args);
  }

  @Autowired
  private RuntimeService runtimeService;

  @EventListener
  private void processPostDeploy(PostDeployEvent event) {
    runtimeService.startProcessInstanceByKey("my-project-process");
  }



  @Value("${consumer.queueName}")
  private String queueName;

  @Value("${rabbitmq.exchangeGetName}")
  private String exchangeName;

  @Value("${rabbitmq.eventTopic}")
  private String Topic;

 /* @RabbitListener(queues = "testQueue")
  public void processMessage(byte[] message)
  {
    String msg= "";
    try
    {
      msg=  new String(message, Charset.defaultCharset());
      System.out.println("message received : "+msg);

    }catch (Exception e)
    {
      e.printStackTrace();
    }
  }

  */

  @Bean
  public Queue queue()
  {
      System.out.println("inside TopicExchange ");
    return new Queue(queueName);
  }

  @Bean
  public TopicExchange exchange()
  {
      System.out.println("inside TopicExchange ");
    return new TopicExchange(exchangeName);
  }

  @Bean
  public Binding binding(){
      System.out.println("inside TopicExchange ");
    return BindingBuilder.bind(queue()).to(exchange()).with(Topic);
  }

}