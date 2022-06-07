package ru.example.alfatest.browserless;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.example.alfatest.model.GiphyGifObject;

@FeignClient(name = "giphy-client", url = "${giphy.path}")
public interface GiphyClient {

    /**
     * Выполняет запрос на поиск изображения по фразе
     *
     * @param apiKey       ключ для авторизации GIPHY API Key
     * @param searchPhrase поиск изображений соответсвующих искомой фразе
     * @return Объект типа {@link GiphyGifObject}
     * <p>
     * Часть необязательных параметром в запросе не используется
     * @see <a href="https://developers.giphy.com/docs/api/endpoint#search">Search Endpoint</a>
     */
    @GetMapping(value = "/search")
    GiphyGifObject getGiphySearch(
            @RequestParam("api_key") String apiKey,
            @RequestParam("q") String searchPhrase
    );
}
