package ru.example.alfatest.dto;

import lombok.Getter;

/**
 * Перечисление, используется в том числе, для текстового описания
 * переданой картинки, дающей представление о разнице курсов валют
 */
@Getter
public enum GiphyImageType {
    GIPHY_RICH("You are rich"),
    GIPHY_BROKE("You are broke");

    private final String giphyPhrase;

    GiphyImageType(String giphyPhrase) {
        this.giphyPhrase = giphyPhrase;
    }
}
