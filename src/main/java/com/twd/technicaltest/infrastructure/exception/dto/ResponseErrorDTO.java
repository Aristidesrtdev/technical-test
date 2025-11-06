package com.twd.technicaltest.infrastructure.exception.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ResponseErrorDTO {

    @JsonProperty("message")
    private String message;
}
