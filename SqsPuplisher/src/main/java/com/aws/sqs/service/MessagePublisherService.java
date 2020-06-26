package com.aws.sqs.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.amazonaws.services.sqs.model.SendMessageResult;
import com.aws.sqs.client.SqsClient;


@Component
public class MessagePublisherService {
	
	private static final Logger LOG = LoggerFactory.getLogger(MessagePublisherService.class);

	private int  count = 0;
	@Autowired 
	private SqsClient client;
	
	@Scheduled(fixedRate = 1000)
	private void sendMessage() {
		SendMessageResult result = client.getSqsClient().
				sendMessage(client.getUrl(),"message sent from s-boot" + count);
		LOG.info("Published Message {}", result.getMessageId());
		count++;
	}
}
