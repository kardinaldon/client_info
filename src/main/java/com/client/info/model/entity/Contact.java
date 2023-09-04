package com.client.info.model.entity;

import com.client.info.model.enumeration.ContactType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="contact")
public class Contact {

    @JsonProperty("contact_id")
    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "contact id", example = "123")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contact_id")
    private Long contactId;

    @JsonProperty("contact_type")
    @Schema(accessMode = Schema.AccessMode.READ_WRITE, description = "email or phone"
            , example = "email")
    @Column(name = "contact_type")
    private ContactType contactType;

    @JsonProperty("contact")
    @Schema(accessMode = Schema.AccessMode.READ_WRITE, description = "email or phone"
            , example = "ivan@mail.ru")
    @Column(name = "contact")
    private String contact;

    @Schema(accessMode = Schema.AccessMode.READ_WRITE
            , description = "client_id", example = "123")
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="client_id")
    private Client client;
}
