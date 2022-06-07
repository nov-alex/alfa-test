package ru.example.alfatest.service.impl;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.example.alfatest.AbstractTest;
import ru.example.alfatest.browserless.GiphyClient;
import ru.example.alfatest.dto.GiphyImageType;
import ru.example.alfatest.dto.response.ImageResponse;
import ru.example.alfatest.model.GiphyGifObject;
import ru.example.alfatest.model.GiphyImageObject;
import ru.example.alfatest.service.GiphyService;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;

class GiphyServiceImplTest extends AbstractTest {
    @MockBean
    private GiphyClient giphyClient;
    @MockBean
    private GiphyGifObject giphyGifObject;
    @Autowired
    private GiphyService giphyService;

    @Test
    void givenImageType_whenImageRequest_thenValidUrl() {
        String url = "TestUrl";
        GiphyImageObject giphyImageObject = new GiphyImageObject();
        giphyImageObject.setImages(Map.of("original", Map.of("url", url)));
        giphyGifObject.setData(List.of(giphyImageObject));
        Mockito.when(giphyGifObject.getData()).thenReturn(List.of(giphyImageObject));
        Mockito.when(giphyClient.getGiphySearch(anyString(), anyString())).thenReturn(giphyGifObject);

        ImageResponse imageResponse = giphyService.receiveRandomOriginalImage(GiphyImageType.GIPHY_RICH);
        assertThat(imageResponse.getImageUrl()).isEqualTo(url);
        assertThat(imageResponse.getImageDescription()).isEqualTo(GiphyImageType.GIPHY_RICH.getGiphyPhrase());
    }
}