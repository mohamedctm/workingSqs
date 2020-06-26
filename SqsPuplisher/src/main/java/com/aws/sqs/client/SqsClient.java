package com.aws.sqs.client;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


import com.amazonaws.services.sqs.AmazonSQS;

import com.amazonaws.services.sqs.AmazonSQSClientBuilder;

import lombok.Getter;

@Component
public class SqsClient {

	@Value("AKIAT7EBP5SOJKTENNZN")
	private String AwsAccessKey;
	
	@Value("qMK7pHLrLWF2o7C2zcFiQvHJsYwhKEMemKZe01t0")
	private String AwsSecretKey;

	
	@Value("${region}")
	@Getter
	private String region;
	
	@Value("${url}")
	@Getter
	private String url;
	
	@Getter
	private AmazonSQS sqsClient;
	
	@PostConstruct
	public void init() {
		sqsClient = AmazonSQSClientBuilder.standard().withRegion(region)
				.build();
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public AmazonSQS getSqsClient() {
		return sqsClient;
	}

	public void setSqsClient(AmazonSQS sqsClient) {
		this.sqsClient = sqsClient;
	}
	
	
}
