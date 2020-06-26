package com.aws.sqs.service;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import com.aws.sqs.client.SqsClient;

@Component
@RestController
public class MessageSubscriberService {
	
	@Autowired
	private  SqsClient client;

	@Value("6")
	private int pollingThreads;

	@Value("1000")
	private int pollingRate;

	private ScheduledThreadPoolExecutor executor;


	@PostConstruct
	private void startSqsPoll() {
		executor = new ScheduledThreadPoolExecutor(pollingThreads);
		for(int worker = 0; worker < pollingThreads; worker++) {
			executor.scheduleAtFixedRate(new ReceiveMessage(client),
				0, pollingRate, TimeUnit.MILLISECONDS);
		}
	}
}
