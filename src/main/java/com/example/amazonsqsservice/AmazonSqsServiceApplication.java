package com.example.amazonsqsservice;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.aws.autoconfigure.context.ContextStackAutoConfiguration;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication(exclude = {ContextStackAutoConfiguration.class})
@RestController
public class AmazonSqsServiceApplication {

    Logger logger = LoggerFactory.getLogger(AmazonSqsServiceApplication.class);
    @Autowired
    private QueueMessagingTemplate queueMessagingTemplate;

    @Value("${cloud.aws.end-point.uri}")
    private String endpoint;


    @SqsListener("myQueue")
    public void loadMessageFromSQS(String message)
    {
        logger.info("Message from SQS Queue { "+message+" }");
    }

    @GetMapping("/send/{message}")
    public String sendMessageToQueue(@PathVariable String message)
    {
        System.out.println("Message : "+message);
        queueMessagingTemplate.send(endpoint, MessageBuilder.withPayload(message).build());
        return "Message Sent Successfully";
    }

    public static void main(String[] args) {
        System.out.println("Hello Function");
        SpringApplication.run(AmazonSqsServiceApplication.class, args);
        System.out.println("Byy Function");
    }

}
