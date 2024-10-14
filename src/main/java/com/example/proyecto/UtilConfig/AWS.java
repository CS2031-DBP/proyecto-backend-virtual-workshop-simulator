package com.example.proyecto.UtilConfig;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class AWS {

    @Value("${aws.access-key-id}")
    private String accessKey;

    @Value("${aws.secret-access-key}")
    private String secretKey;

    @Bean
    public S3Client s3cliente(){
        Region region = Region.US_EAST_1;
        AwsCredentials credentials = AwsBasicCredentials.create(accessKey, secretKey);

        S3Client s3Client = S3Client.builder()
                .region(region)
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .build();

        return s3Client;
    }


}
