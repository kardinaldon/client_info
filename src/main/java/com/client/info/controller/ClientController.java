package com.client.info.controller;

import com.client.info.model.entity.Client;
import com.client.info.model.exception.ClientBadRequestException;
import com.client.info.model.exception.ClientNotFoundException;
import com.client.info.service.impl.ClientInfoServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "#{'${values.url.client.controller}'}")
@Validated
public class ClientController {

    private static final Logger LOG = LoggerFactory.getLogger(ClientController.class);

    private ClientInfoServiceImpl clientInfoService;

    @Autowired
    public ClientController(ClientInfoServiceImpl clientInfoService) {
        this.clientInfoService = clientInfoService;
    }

    @GetMapping(produces = "application/json", path = "#{'${values.url.client.by_id}'}")
    public ResponseEntity<Client> getById(@RequestParam(required = true) @NonNull long id){
        if(id != 0){
            Optional<Client> optionalClient = clientInfoService.clientById(id);
            if(optionalClient.isPresent()){
                return ResponseEntity.ok()
                        .body(optionalClient.get());
            }
            else {
                LOG.warn(" Client by id  "+ id + " not found");
                throw new ClientNotFoundException();
            }
        }
        else {
            LOG.warn(" client id 0 or empty");
            throw new ClientBadRequestException("client id 0 or empty");
        }
    }

    @GetMapping(produces = "application/json", path = "#{'${values.url.client.all}'}")
    public ResponseEntity<List<Client>> getAll(){
        return ResponseEntity.ok()
                        .body(clientInfoService.allClients());
    }

    @PostMapping(consumes = "application/json",  produces = "application/json"
            , path = "#{'${values.url.client.new}'}")
    public ResponseEntity<Client> newClient(
            @RequestBody(required = true)
            @NonNull
            Client client){
        if(client.getName() != null && !client.getName().isBlank()
        && client.getPatronymic() != null && !client.getPatronymic().isBlank()
        && client.getSurname() != null && !client.getSurname().isBlank()
        ){
            return ResponseEntity.ok().body(clientInfoService.newClient(client));
        }
        else {
            LOG.warn("parameter name, patronymic or surname are missing in the request");
            throw new ClientBadRequestException("parameter name, patronymic or surname are missing in the request");
        }
    }


}
