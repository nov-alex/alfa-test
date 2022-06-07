package ru.example.alfatest.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

/**
 * The GIF Objects are returned from most of GIPHY API`s Endpoints
 *
 * @see <a href="https://developers.giphy.com/docs/api/schema#gif-object">GIF Objects</a>
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class GiphyGifObject {
    private List<GiphyImageObject> data;
    private Map<String, String> pagination;
    private Map<String, String> meta;
}
