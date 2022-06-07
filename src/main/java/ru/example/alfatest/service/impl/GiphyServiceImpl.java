package ru.example.alfatest.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.example.alfatest.browserless.GiphyClient;
import ru.example.alfatest.dto.GiphyImageType;
import ru.example.alfatest.dto.response.ImageResponse;
import ru.example.alfatest.infra.BadResponseGiphySearchImageException;
import ru.example.alfatest.model.GiphyGifObject;
import ru.example.alfatest.model.GiphyImageObject;
import ru.example.alfatest.service.GiphyService;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class GiphyServiceImpl implements GiphyService {
    private static final Random random = new Random();
    @Autowired
    private final GiphyClient giphyClient;
    @Value("${giphy.api_key}")
    private String apiKey;
    @Value("${giphy.phrase.rich}")
    private String phraseRich;
    @Value("${giphy.phrase.broke}")
    private String phraseBroke;

    /**
     * Возвращает случайное число
     *
     * @param min минимальное значение (включая)
     * @param max максимальное значение (исключая)
     * @return Случайное число, включая min и исключая max
     */
    public static int getRandomNumber(int min, int max) {
        return random.ints(min, max)
                .findFirst().orElse(min);
    }

    /**
     * Выполняет запрос к сервису изображений на получение списка
     * изображений и выбирает случайной изображение из списка.
     *
     * @param giphyImageType {@link GiphyImageType}
     * @return {@link ImageResponse}
     * @throws {@link BadResponseGiphySearchImageException}
     */
    @Override
    public ImageResponse receiveRandomOriginalImage(GiphyImageType giphyImageType) throws BadResponseGiphySearchImageException {
        GiphyGifObject giphyGifObject;
        switch (giphyImageType) {
            case GIPHY_RICH -> giphyGifObject = giphyClient.getGiphySearch(apiKey, phraseRich);
            case GIPHY_BROKE -> giphyGifObject = giphyClient.getGiphySearch(apiKey, phraseBroke);
            default -> throw new BadResponseGiphySearchImageException("Unexpected value: " + giphyImageType);
        }
        int randomImageObjectId = getRandomNumber(0, giphyGifObject.getData().size());
        ImageResponse response = new ImageResponse();
        String originalImageUrl = getImageUrlByRenditionKey(giphyGifObject, randomImageObjectId, "original");
        response.setImageUrl(originalImageUrl);
        response.setImageDescription(giphyImageType.getGiphyPhrase());
        return response;
    }

    /**
     * Выдает url изображения из списка изображений полученных с сервиса изображений
     * объект с изображением.
     *
     * @param giphyGifObject   Объект {@link GiphyGifObject} со списком изображений {@link GiphyImageObject}
     * @param imageObjectIndex Задает индекс в списке изображений {@link GiphyGifObject}
     * @param renditionKey     Задает название Rendition Objects из {@link GiphyImageObject}
     * @return Url строку выбранного случайного изображения
     * @throws {@link BadResponseGiphySearchImageException}
     */
    private String getImageUrlByRenditionKey(
            GiphyGifObject giphyGifObject,
            int imageObjectIndex,
            String renditionKey) throws BadResponseGiphySearchImageException {

        try {
            return giphyGifObject
                    .getData().get(imageObjectIndex)
                    .getImages()
                    .get(renditionKey)
                    .get("url");
        } catch (Exception ex) {
            throw new BadResponseGiphySearchImageException("Unable to found requested RenditionKey=" + renditionKey);
        }
    }
}
