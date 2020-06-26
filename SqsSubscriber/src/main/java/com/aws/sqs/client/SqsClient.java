package com.aws.sqs.client;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


import com.amazonaws.services.sqs.AmazonSQS;

import com.amazonaws.services.sqs.AmazonSQSClientBuilder;

import lombok.Getter;

@Component
@Scope("singleton")
public class SqsClient {
	@Value("US_EAST_1")
	@Getter
	private String region;
	
	@Value("${url}")
	@Getter
	private String url;
	
	@Value("${AWS_ACCESS_KEY}")
	@Getter
	private String access_key;
	
	@Value("${AWS_SECRET_ACCESS_KEY}")
	@Getter
	private String secret_key;
	
	@Getter
	private AmazonSQS amazonSqsClient;
	
	@PostConstruct
	public void init() {
		amazonSqsClient = AmazonSQSClientBuilder.standard().withRegion(region)
				.build();
	}
	
	
	public String getAccess_key() {
		return access_key;
	}


	public void setAccess_key(String access_key) {
		this.access_key = access_key;
	}


	public String getSecret_key() {
		return secret_key;
	}


	public void setSecret_key(String secret_key) {
		this.secret_key = secret_key;
	}


	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public AmazonSQS getAmazonSqsClient() {
		return amazonSqsClient;
	}

	public void setAmazonSqsClient(AmazonSQS amazonSqsClient) {
		this.amazonSqsClient = amazonSqsClient;
	}

	
	
	

	
	
}