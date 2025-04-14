package com.sensedia.sample.consents.config.exception.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Schema(description = "Error response for a business rule violation")
public class ErrorMessage {
    @Schema(description = "Validation error type", example = "Invalid input")
    private String type;
    @Schema(description = "Error title", example = "Error creating consent")
    private String title;
    private List<ErrorInfo> errorInfos;

    public ErrorMessage(String type, String title) {
        this.type = type;
        this.title = title;
        this.errorInfos = new ArrayList<>();
    }

    public ErrorMessage(String type, String title, List<ErrorInfo> errorInfos) {
        this.type = type;
        this.title = title;
        this.errorInfos = errorInfos;
    }

    public void addError(String detail, String pointer){
        this.errorInfos.add(new ErrorInfo(detail, pointer));
    }
}
