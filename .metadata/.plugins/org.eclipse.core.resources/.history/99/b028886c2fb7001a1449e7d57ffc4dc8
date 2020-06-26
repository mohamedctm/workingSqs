/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example;

import com.amazon.sqs.javamessaging.SQSConnection;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.AmazonSQSException;
import com.amazonaws.services.sqs.model.CreateQueueRequest;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazon.sqs.javamessaging.SQSConnectionFactory;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.internal.StaticCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.ReceiveMessageResult;
import com.amazonaws.services.sqs.model.SendMessageResult;
import com.amazonaws.services.sqs.model.SetQueueAttributesRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.JMSException;

/**
 *
 * @author Gustavo
 */
public class SqsService {
    
    private AmazonSQS sqs;
    
    public SqsService(){
        SQSConnectionFactory sqsConnectionFactory = SQSConnectionFactory.builder()
                .withRegion(Region.getRegion(Regions.CA_CENTRAL_1))
                .withAWSCredentialsProvider(new StaticCredentialsProvider(
                        new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY)))
                .build();
        //AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();
        SQSConnection sqsConnection = null;
        try {
            sqsConnection = sqsConnectionFactory.createConnection();
        } catch (JMSException ex) {
            Logger.getLogger(SqsService.class.getName()).log(Level.SEVERE, null, ex);
        }
        sqs = sqsConnection.getAmazonSQSClient();
    }
    
    public String sendSomething(String message, String queueName){

        //CreateQueueRequest create_request = new CreateQueueRequest("add-product-command-queue")
                //.addAttributesEntry("DelaySeconds", "60")
                //.addAttributesEntry("MessageRetentionPeriod", "86400");

        //try {
        //    sqs.createQueue(create_request);
        //} catch (AmazonSQSException e) {
        //    if (!e.getErrorCode().equals("QueueAlreadyExists")) {
        //        throw e;
        //    }
        //}
        
        SendMessageRequest send_msg_request = new SendMessageRequest()
        .withQueueUrl("https://sqs.ca-central-1.amazonaws.com/074339870987/" + queueName)
        .withMessageBody(message)
        .withDelaySeconds(5);
        SendMessageResult result = sqs.sendMessage(send_msg_request);
        return result.getMessageId();
        
    }
    
    public List<String> receiveSomething(String queueName){
        List<String> resultMessages = new ArrayList<String>();
        List<Message> messages = sqs.receiveMessage("https://sqs.ca-central-1.amazonaws.com/074339870987/" + queueName).getMessages();
        while(messages!=null && !messages.isEmpty()){
            messages.forEach((msg) -> {
                System.out.println(msg.getBody());
                resultMessages.add(msg.getBody());
            });

            for (Message m : messages) {
                sqs.deleteMessage("https://sqs.ca-central-1.amazonaws.com/074339870987/" + queueName, m.getReceiptHandle());
            }  
            
            messages = sqs.receiveMessage("https://sqs.ca-central-1.amazonaws.com/074339870987/" + queueName).getMessages();
        }
        return resultMessages;
    }
    
    public void receiveSomethingWithLongPolling(){
        
                // Enable long polling when creating a queue
        CreateQueueRequest create_request = new CreateQueueRequest()
                .withQueueName("add-product-command-queue")
                .addAttributesEntry("ReceiveMessageWaitTimeSeconds", "20");


        // Enable long polling on an existing queue
        SetQueueAttributesRequest set_attrs_request = new SetQueueAttributesRequest()
                .withQueueUrl("https://sqs.ca-central-1.amazonaws.com/074339870987/add-product-command-queue")
                .addAttributesEntry("ReceiveMessageWaitTimeSeconds", "20");
        sqs.setQueueAttributes(set_attrs_request);

        // Enable long polling on a message receipt
        ReceiveMessageRequest receive_request = new ReceiveMessageRequest()
                .withQueueUrl("https://sqs.ca-central-1.amazonaws.com/074339870987/add-product-command-queue")
                .withWaitTimeSeconds(20)
                .withMaxNumberOfMessages(10);

        ReceiveMessageResult result = sqs.receiveMessage(receive_request);
        
        List<Message> messages = result.getMessages();
        
        messages.forEach((msg) -> {
            System.out.println(msg.getBody());
        });
        
        for (Message m : messages) {
            sqs.deleteMessage("https://sqs.ca-central-1.amazonaws.com/074339870987/add-product-command-queue", m.getReceiptHandle());
        }
        
    }
}
