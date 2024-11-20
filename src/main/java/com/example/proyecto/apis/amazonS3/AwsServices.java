package com.example.proyecto.apis.amazonS3;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.IOException;


@Service
public class AwsServices {

    private final S3Client s3Client;

    @Value("${aws.s3.bucket-name}")
    private String  bucketName;

    @Value("${aws.s3.region}")
    private String region;


    public AwsServices(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    public String uploadFile(String keyname, MultipartFile file) throws IOException {

        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("El archivo no puede estar vacío");
        }

        try {

            String fileName = keyname + "/" + file.getOriginalFilename();

            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)
                    .contentType(file.getContentType())
                    .build();

            s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));

            return "https://" + bucketName + ".s3." + region + ".amazonaws.com/" + fileName;

        } catch (S3Exception e) {
            throw new RuntimeException("Error al subir el archivo a S3: " + e.awsErrorDetails().errorMessage(), e);
        } catch (IOException e) {
            throw new RuntimeException("Error al leer el archivo: " + e.getMessage(), e);
        }

    }
    // Para editar se puede sobreescribir directamente

    public void DeleteFile(String keyname){
        try {

            DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                    .bucket(bucketName)
                    .key(keyname)
                    .build();

            s3Client.deleteObject(deleteObjectRequest);

        }catch( S3Exception e){
            throw new RuntimeException("Error al eliminar el archivo a S3: " + e.getMessage(), e);
        }
    }

    public boolean fileExists(String keyname) {
        try {
            HeadObjectRequest headObjectRequest = HeadObjectRequest.builder()
                    .bucket(bucketName)
                    .key(keyname)
                    .build();
            s3Client.headObject(headObjectRequest);
            return true;
        } catch (NoSuchKeyException e) {
            return false;
        } catch (S3Exception e) {
            throw new RuntimeException("Error al verificar si el archivo existe: " + e.awsErrorDetails().errorMessage(), e);
        }
    }

}
