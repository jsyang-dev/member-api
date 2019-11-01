package com.example.memberapi.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DbConfig {

    @Value("${dynamodb.endpoint}")
    private String END_POINT;

    @Value("${dynamodb.region}")
    private String REGION;

    @Bean
    public AmazonDynamoDB amazonDynamoDB(){
        AWSCredentials awsCredentials = new BasicAWSCredentials("key1", "key2");
        AWSCredentialsProvider awsCredentialsProvider = new AWSStaticCredentialsProvider(awsCredentials);
        AwsClientBuilder.EndpointConfiguration endpointConfiguration = new AwsClientBuilder.EndpointConfiguration(END_POINT, REGION);

        return AmazonDynamoDBClientBuilder.standard()
                .withCredentials(awsCredentialsProvider)
                .withEndpointConfiguration(endpointConfiguration).build();
    }

    @Bean
    public DynamoDBMapper dynamoDBMapper(AmazonDynamoDB amazonDynamoDB){
        return new DynamoDBMapper(amazonDynamoDB, DynamoDBMapperConfig.DEFAULT);
    }

}
