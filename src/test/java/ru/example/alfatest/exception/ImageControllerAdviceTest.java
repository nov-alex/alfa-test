package ru.example.alfatest.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.example.alfatest.AbstractTest;
import ru.example.alfatest.controller.ImageController;
import ru.example.alfatest.dto.response.ErrorResponse;
import ru.example.alfatest.infra.BadResponseOpenExchangeRatesException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureJsonTesters
class ImageControllerAdviceTest extends AbstractTest {
    private MockMvc mockMvc;
    @Mock
    private ImageController imageController;
    @Autowired
    private ImageControllerAdvice imageControllerAdvice;
    @Mock
    private ErrorResponse errorResponse;
    @Autowired
    private JacksonTester<ErrorResponse> jacksonErrorResponse;

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(imageController)
                .setControllerAdvice(imageControllerAdvice)
                .build();
    }

    @Test
    void whenException_thenReturnErrorMessage() throws Exception {
        String message = "Test";
        Mockito.when(imageController.getImage(anyString())).thenThrow(new BadResponseOpenExchangeRatesException(message));
        Mockito.when(errorResponse.getError()).thenReturn(HttpStatus.BAD_REQUEST.value());
        Mockito.when(errorResponse.getMessage()).thenReturn(message);

        mockMvc.perform(get("/image/AFN"))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
                .andExpect(result -> assertThat(result.getResponse().getContentAsString()).isEqualTo(jacksonErrorResponse.write(errorResponse).getJson()))
                .andReturn();
    }
}