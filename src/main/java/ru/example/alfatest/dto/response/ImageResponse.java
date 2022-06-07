package ru.example.alfatest.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * Объект для сериализации при запросе изображения, которое
 * визуально демонстриует разницу курса выбранной валюты
 *
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ImageResponse {
    private String imageUrl;
    private String imageDescription;
}
