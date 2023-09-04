package com.client.info.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name="client")
public class Client {

    @JsonProperty("client_id")
    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "client id", example = "123")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_id")
    private Long clientId;

    @JsonProperty("name")
    @Schema(accessMode = Schema.AccessMode.READ_WRITE, description = "name", example = "Ivan")
    @Column(name = "name")
    private String name;

    @JsonProperty("patronymic")
    @Schema(accessMode = Schema.AccessMode.READ_WRITE, description = "patronymic", example = "Ivanovich")
    @Column(name = "patronymic")
    private String patronymic;

    @JsonProperty("surname")
    @Schema(accessMode = Schema.AccessMode.READ_WRITE, description = "surname", example = "Ivanov")
    @Column(name = "surname")
    private String surname;

    @Schema(accessMode = Schema.AccessMode.READ_WRITE
            , description = "List of user contacts")
    @JsonProperty("contacts")
    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Contact> contacts = new ArrayList<>();
}
