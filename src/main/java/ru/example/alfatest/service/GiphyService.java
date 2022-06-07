package ru.example.alfatest.service;

import ru.example.alfatest.dto.GiphyImageType;
import ru.example.alfatest.dto.response.ImageResponse;

public interface GiphyService {
    /**
     * Выполняет запрос к сервису изображений на получение списка
     * изображений и выбирает случайной изображение из списка.
     */
    ImageResponse receiveRandomOriginalImage(GiphyImageType giphyImageType);
}

