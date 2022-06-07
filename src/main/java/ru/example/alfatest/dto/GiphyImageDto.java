package ru.example.alfatest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Объект, который использует контроллер для взаимодейтсвием между
 * сервисами курса валют и изображений
 */
@Getter
@AllArgsConstructor
public class GiphyImageDto {
    private final GiphyImageType giphyImageType;
}
