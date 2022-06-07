package ru.example.alfatest.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import ru.example.alfatest.AbstractTest;
import ru.example.alfatest.dto.GiphyImageDto;
import ru.example.alfatest.dto.GiphyImageType;
import ru.example.alfatest.dto.response.CurrenciesResponse;
import ru.example.alfatest.dto.response.ErrorResponse;
import ru.example.alfatest.dto.response.ImageResponse;
import ru.example.alfatest.infra.BadResponseGiphySearchImageException;
import ru.example.alfatest.infra.BadResponseOpenExchangeRatesException;
import ru.example.alfatest.service.GiphyService;
import ru.example.alfatest.service.OpenExchangeRatesService;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

/**
 * @see <a href="https://thepracticaldeveloper.com/guide-spring-boot-controller-tests/">Test a controller in Spring Boot</a>
 */

@AutoConfigureJsonTesters
@AutoConfigureMockMvc
class ImageControllerTest extends AbstractTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private OpenExchangeRatesService openExchangeRatesService;
    @MockBean
    private GiphyService giphyService;
    @MockBean
    private GiphyImageDto giphyImageDto;
    @Autowired
    private JacksonTester<CurrenciesResponse> jacksonCurrenciesResponse;
    @Autowired
    private JacksonTester<ErrorResponse> jacksonErrorResponse;
    @Autowired
    private JacksonTester<ImageResponse> jacksonImageResponse;

    @Test
    void givenCurrencies_whenRequestCurrencies_thenReturnOk() throws Exception {
        //given
        Map<String, String> currenciesMap = Map.of("Code", "Desc");
        CurrenciesResponse currenciesResponse = new CurrenciesResponse("USD", currenciesMap);
        Mockito.when(openExchangeRatesService.getCurrencies()).thenReturn(currenciesResponse);
        // when
        MockHttpServletResponse response = mockMvc.perform(
                        get("/image/currencies"))
                .andReturn().getResponse();
        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(jacksonCurrenciesResponse.write(currenciesResponse).getJson());
    }

    @Test
    void givenOERException_whenRequestCurrencies_thenStatusIsBadRequest() throws Exception {
        //given
        String message = "OpenExchangeRatesException";
        ErrorResponse errorResponse = new ErrorResponse(message, HttpStatus.BAD_REQUEST.value());
        Mockito.when(openExchangeRatesService.getCurrencies()).thenThrow(new BadResponseOpenExchangeRatesException(message));
        // when
        MockHttpServletResponse response = mockMvc.perform(
                        get("/image/currencies"))
                .andReturn().getResponse();
        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).isEqualTo(jacksonErrorResponse.write(errorResponse).getJson());
    }

    @Test
    void givenCurrenciesRate_whenRequestImage_thenReturnOk() throws Exception {
        //given
        ImageResponse imageResponse = new ImageResponse();
        imageResponse.setImageUrl("imageUrl");
        imageResponse.setImageDescription("imageDesc");
        Mockito.when(openExchangeRatesService.receiveImageType(anyString())).thenReturn(new GiphyImageDto(GiphyImageType.GIPHY_RICH));
        Mockito.when(giphyService.receiveRandomOriginalImage(any(GiphyImageType.class))).thenReturn(imageResponse);
        // when
        MockHttpServletResponse response = mockMvc.perform(
                        get("/image/AFN"))
                .andReturn().getResponse();
        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(jacksonImageResponse.write(imageResponse).getJson());
    }

    @Test
    void givenOERException_whenRequestImage_thenStatusIsBadRequest() throws Exception {
        //given
        String message = "OpenExchangeRatesException";
        ErrorResponse errorResponse = new ErrorResponse(message, HttpStatus.BAD_REQUEST.value());
        Mockito.when(openExchangeRatesService.receiveImageType(anyString())).thenThrow(new BadResponseOpenExchangeRatesException(message));
        // when
        MockHttpServletResponse response = mockMvc.perform(
                        get("/image/AFN"))
                .andReturn().getResponse();
        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).isEqualTo(jacksonErrorResponse.write(errorResponse).getJson());
    }

    @Test
    void givenGSIException_whenRequestImage_thenStatusIsBadRequest() throws Exception {
        //given
        String message = "GiphySearchImageException";
        ErrorResponse errorResponse = new ErrorResponse(message, HttpStatus.BAD_REQUEST.value());
        Mockito.when(openExchangeRatesService.receiveImageType(anyString())).thenThrow(new BadResponseGiphySearchImageException(message));
        // when
        MockHttpServletResponse response = mockMvc.perform(
                        get("/image/AFN"))
                .andReturn().getResponse();
        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).isEqualTo(jacksonErrorResponse.write(errorResponse).getJson());
    }

    @Test
    void whenRequestCurrencies_thenStatusIsOk() throws Exception {
        // when
        MockHttpServletResponse response = mockMvc.perform(
                        get("/image/currencies"))
                .andReturn().getResponse();
        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @ParameterizedTest
    @ValueSource(strings = {"/image/AFN", "/image/AfN", "/image/afn"})
    void whenRequestImage_thenStatusIsOk(String path) throws Exception {
        //given
        Mockito.when(giphyImageDto.getGiphyImageType()).thenReturn(GiphyImageType.GIPHY_BROKE);
        Mockito.when(openExchangeRatesService.receiveImageType("AFN")).thenReturn(giphyImageDto);
        Mockito.when(giphyService.receiveRandomOriginalImage(any(GiphyImageType.class))).thenReturn(new ImageResponse());
        // when
        MockHttpServletResponse response = mockMvc.perform(
                        get(path))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void whenRequestInvalid_thenStatusIsNotFound() throws Exception {
        // when
        MockHttpServletResponse response = mockMvc.perform(
                        get("/image/"))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }
}
