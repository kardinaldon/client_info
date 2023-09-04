package com.client.info.model.enumeration;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Contact Type")
public enum ContactType {

    @JsonProperty("email")EMAIL, @JsonProperty("phone")PHONE
}
