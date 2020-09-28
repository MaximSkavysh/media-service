package com.lobster.ecommerce.media.dto;

import lombok.Data;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class RequestDto {
    @NotNull
    @NumberFormat(style = NumberFormat.Style.NUMBER)
    private Long product_id;
    MultipartFile[] files;
}
