package com.example.proyecto.apis.amazonS3;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.IOException;


@Service
public class AwsServices {



    private final S3Client s3Client;

    public AwsServices(S3Client s3client){
        this.s3Client = s3client;
    }

    public String uploadFile(MultipartFile file) throws IOException {

        try{
            String fileName=file.getOriginalFilename();
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket("proyecto-dbp")
                    .key(fileName)
                    .build();
            s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));

            String fileUrl = "https://proyecto-dbp.s3.us-east-1.amazonaws.com/" + fileName;

            return  fileUrl;

        }catch( Exception e ){
            throw new IOException(e.getMessage());
        }

    }

}
