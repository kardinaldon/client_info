package com.client.info.model.dto;

import com.client.info.model.enumeration.ContactType;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ContactDTO {

    @JsonProperty("contact_id")
    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "contact id", example = "123")
    private Long contactId;
    @JsonProperty("contact_type")
    @Schema(accessMode = Schema.AccessMode.READ_WRITE, description = "email or phone"
            , example = "email")
    private ContactType contactType;
    @JsonProperty("contact")
    @Schema(accessMode = Schema.AccessMode.READ_WRITE, description = "email or phone"
            , example = "ivan@mail.ru")
    private String contact;
    @JsonProperty("client_id")
    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "client id", example = "123")
    private Long clientId;
}
