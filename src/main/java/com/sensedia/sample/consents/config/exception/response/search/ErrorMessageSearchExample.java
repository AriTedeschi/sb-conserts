package com.sensedia.sample.consents.config.exception.response.search;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Schema(description = "Error response for a not found registry")
public class ErrorMessageSearchExample {
    @Schema(description = "Validation error type", example = "Invalid parameter")
    private String type;
    @Schema(description = "Error title", example = "Error searching for consent by id")
    private String title;
    private List<ErrorInfoSearchExample> errorInfos;

    public ErrorMessageSearchExample(String type, String title) {
        this.type = type;
        this.title = title;
        this.errorInfos = new ArrayList<>();
    }

    public ErrorMessageSearchExample(String type, String title, List<ErrorInfoSearchExample> errorInfos) {
        this.type = type;
        this.title = title;
        this.errorInfos = errorInfos;
    }

    public void addError(String detail, String pointer){
        this.errorInfos.add(new ErrorInfoSearchExample(detail, pointer));
    }
}
