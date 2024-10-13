package com.example.proyecto.apis.amazonS3;
/*
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;
 */

import org.springframework.stereotype.Service;

@Service
public class S3Service {
/*
    private final S3Client s3Client;

    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    public S3Service(@Value("${aws.access-key-id}") String accessKeyId,
                     @Value("${aws.secret-access-key}") String secretAccessKey,
                     @Value("${aws.s3.region}") String region) {
        AwsBasicCredentials awsCreds = AwsBasicCredentials.create(accessKeyId, secretAccessKey);
        this.s3Client = S3Client.builder()
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .region(Region.of(region))
                .build();
    }

    public String subirArchivo(MultipartFile archivo) throws IOException {
        String nombreArchivo = UUID.randomUUID().toString() + "_" + archivo.getOriginalFilename();

        try {
            // Subir archivo al bucket S3
            s3Client.putObject(PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(nombreArchivo)
                    .build(), Paths.get(archivo.getOriginalFilename()));

            // Retornar la URL del archivo subido
            return "https://" + bucketName + ".s3.amazonaws.com/" + nombreArchivo;

        } catch (S3Exception e) {
            throw new RuntimeException("Error al subir el archivo a S3: " + e.getMessage());
        }
    }
 */
}

