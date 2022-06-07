package ru.example.alfatest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.example.alfatest.dto.GiphyImageDto;
import ru.example.alfatest.dto.response.CurrenciesResponse;
import ru.example.alfatest.dto.response.ImageResponse;
import ru.example.alfatest.infra.BadResponseOpenExchangeRatesException;
import ru.example.alfatest.service.GiphyService;
import ru.example.alfatest.service.OpenExchangeRatesService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/image")
public class ImageController {

    @Autowired
    private final OpenExchangeRatesService openExchangeRatesService;
    @Autowired
    private final GiphyService giphyService;

    /**
     * Выдает список доступных валют
     *
     * @return {@link CurrenciesResponse}
     */
    @GetMapping(value = "/currencies")
    public CurrenciesResponse getCurrencies() {
        return openExchangeRatesService.getCurrencies();
    }

    /**
     * Выдает изображение, описывающее динамику базовой и выбранной валют на сегодня и вчера
     *
     * @return {@link ImageResponse}
     */
    @GetMapping("/{currency:[a-zA-Z]{3}}")
    public ImageResponse getImage(@PathVariable("currency") String currency) throws BadResponseOpenExchangeRatesException {
        GiphyImageDto dto = openExchangeRatesService.receiveImageType(currency.toUpperCase());
        return giphyService.receiveRandomOriginalImage(
                dto.getGiphyImageType());
    }
}
