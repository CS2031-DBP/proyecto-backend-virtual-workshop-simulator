package com.example.proyecto.material.dto;

import com.example.proyecto.material.domail.Tipo;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class MaterialRequestDto {

    private String nombre;
    private Tipo tipo;
    private MultipartFile archivo;

}
