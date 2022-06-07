package ru.example.alfatest.service.impl;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.example.alfatest.AbstractTest;
import ru.example.alfatest.browserless.OpenExchangeRatesClient;
import ru.example.alfatest.dto.GiphyImageDto;
import ru.example.alfatest.dto.GiphyImageType;
import ru.example.alfatest.model.OpenExchangeRatesRates;
import ru.example.alfatest.service.OpenExchangeRatesService;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;

class OpenExchangeRatesServiceImplTest extends AbstractTest {
    @MockBean
    private OpenExchangeRatesClient openExchangeRatesClient;
    @Autowired
    private OpenExchangeRatesService openExchangeRatesService;
    @Mock
    private OpenExchangeRatesRates latestRates;
    @Mock
    private OpenExchangeRatesRates historyRates;

    @Test
    void givenRichRates_whenComparingRates_thenReturnRich() {
        String currency = "TEST";
        Mockito.when(openExchangeRatesClient.getLatest(anyString(), anyString())).thenReturn(latestRates);
        Mockito.when(openExchangeRatesClient.getHistorical(anyString(), anyString(), anyString())).thenReturn(historyRates);
        Mockito.when(latestRates.getRates()).thenReturn(Map.of(currency, 89.065173D));
        Mockito.when(historyRates.getRates()).thenReturn(Map.of(currency, 88.065173D));

        GiphyImageDto giphyImageDto = openExchangeRatesService.receiveImageType(currency);
        assertThat(giphyImageDto.getGiphyImageType()).isEqualTo(GiphyImageType.GIPHY_RICH);
    }

    @Test
    void givenEqualsRates_whenComparingRates_thenReturnBroke() {
        String currency = "TEST";
        Mockito.when(openExchangeRatesClient.getLatest(anyString(), anyString())).thenReturn(latestRates);
        Mockito.when(openExchangeRatesClient.getHistorical(anyString(), anyString(), anyString())).thenReturn(historyRates);
        Mockito.when(latestRates.getRates()).thenReturn(Map.of(currency, 89.065173D));
        Mockito.when(historyRates.getRates()).thenReturn(Map.of(currency, 89.065173D));

        GiphyImageDto giphyImageDto = openExchangeRatesService.receiveImageType(currency);
        assertThat(giphyImageDto.getGiphyImageType()).isEqualTo(GiphyImageType.GIPHY_BROKE);
    }

    @Test
    void givenBrokeRates_whenComparingRates_thenReturnBroke() {
        String currency = "TEST";
        Mockito.when(openExchangeRatesClient.getLatest(anyString(), anyString())).thenReturn(latestRates);
        Mockito.when(openExchangeRatesClient.getHistorical(anyString(), anyString(), anyString())).thenReturn(historyRates);
        Mockito.when(latestRates.getRates()).thenReturn(Map.of(currency, 88.065173D));
        Mockito.when(historyRates.getRates()).thenReturn(Map.of(currency, 89.065173D));

        GiphyImageDto giphyImageDto = openExchangeRatesService.receiveImageType(currency);
        assertThat(giphyImageDto.getGiphyImageType()).isEqualTo(GiphyImageType.GIPHY_BROKE);
    }
}