package ru.example.alfatest.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * The Images Object found in the GIF Object contains a series of Rendition Objects.
 * These Rendition Objects includes the URLs and sizes for the many renditions
 * we offer for each GIF.
 * <p>
 * Состав полей Search Endpoint сокращен до миниально необходимых
 * для работы с GifObject содержащих объекты типа Images Object.
 *
 * @see <a href="https://developers.giphy.com/docs/api/schema#image-object">Images Object</a>
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class GiphyImageObject {
    private Map<String, Map<String, String>> images;
}
