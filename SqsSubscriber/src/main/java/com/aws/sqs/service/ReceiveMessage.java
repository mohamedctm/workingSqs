package com.aws.sqs.service;



import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.DeleteMessageRequest;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.ReceiveMessageResult;
import com.aws.sqs.client.SqsClient;


//
@Component
public class ReceiveMessage implements Runnable {
	
	private static final Logger LOG = LoggerFactory.getLogger(ReceiveMessage.class);
	
	
	private SqsClient client;
	
	public ReceiveMessage(SqsClient client) {
		this.client = client;
		
	}
	
	//start here
	
		AWSCredentialsProvider awsCredentialsProvider = new AWSStaticCredentialsProvider(
		        new BasicAWSCredentials(client.getAccess_key(), client.getSecret_key())
		);

		AmazonSQS amazonSQS = AmazonSQSClientBuilder.standard().withCredentials(awsCredentialsProvider).build();
		//end here
		
		final ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest(client.getUrl())
		        .withMaxNumberOfMessages(1)
		        .withWaitTimeSeconds(3);
		
		private void deleteMessage(Message messageObject) {

		    final String messageReceiptHandle = messageObject.getReceiptHandle();
		    amazonSQS.deleteMessage(new DeleteMessageRequest(client.getUrl(), messageReceiptHandle));

		}
		
//		 public void startListeningToMessages() {
//
//		        final ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest(client.getUrl())
//		                .withMaxNumberOfMessages(1)
//		                .withWaitTimeSeconds(3);
//
//		        while (true) {
//
//		            final List<Message> messages = amazonSQS.receiveMessage(receiveMessageRequest).getMessages();
//
//		            for (Message messageObject : messages) {
//		                String message = messageObject.getBody();
//
//		                LOG.info("Received message: " + message);
//
//		                deleteMessage(messageObject);
//		            }
//		        }
//		    }
	@Override
	@Scheduled(fixedRate = 1000)
	public void run() {
		final ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest(client.getUrl())
                .withMaxNumberOfMessages(1)
                .withWaitTimeSeconds(3);
		
//		 while (true) {

	            final List<Message> messages = amazonSQS.receiveMessage(receiveMessageRequest).getMessages();

	            for (Message messageObject : messages) {
	                String message = messageObject.getBody();

	                LOG.info("Received message: " + message);

	                deleteMessage(messageObject);
	            }
//	        }
//		ReceiveMessageResult result = client.getAmazonSqsClient()
//				.receiveMessage(client.getUrl());
//		LOG.info("start listening", result);
//		if(null == result)
//			LOG.info("Receive Message{}", result);
//		
//		if(null != result.getMessages() && !result.getMessages()
//				.isEmpty()) {
//			LOG.info("delete message", result.getMessages().get(0).getReceiptHandle());
//			client.getAmazonSqsClient().deleteMessage(client.getUrl(),
//					result.getMessages().get(0).getReceiptHandle());
//		}
	}
}
