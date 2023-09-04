package com.client.info.controller;

import com.client.info.model.dto.ContactDTO;
import com.client.info.model.entity.Client;
import com.client.info.model.entity.Contact;
import com.client.info.model.enumeration.ContactType;
import com.client.info.model.exception.*;
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
@RequestMapping(path = "#{'${values.url.contact.controller}'}")
@Validated
public class ContactController {

    private static final Logger LOG = LoggerFactory.getLogger(ClientController.class);

    private ClientInfoServiceImpl clientInfoService;

    @Autowired
    public ContactController(ClientInfoServiceImpl clientInfoService) {
        this.clientInfoService = clientInfoService;
    }

    @PostMapping(consumes = "application/json",  produces = "application/json"
            , path = "#{'${values.url.contact.new}'}")
    public ResponseEntity<Contact> newContact(
            @RequestBody(required = true)
            @NonNull
            ContactDTO contactDTO){
        if(contactDTO.getContact() != null && !contactDTO.getContact().isBlank()
                && contactDTO.getContactType() != null
                && contactDTO.getClientId() != null
        ){
            Optional<Client> optionalClient = clientInfoService.clientById(contactDTO.getClientId());
            if(optionalClient.isPresent()){
                Contact contact = new Contact();
                contact.setClient(optionalClient.get());
                contact.setContactType(contactDTO.getContactType());
                contact.setContact(contactDTO.getContact());
                return ResponseEntity.ok().body(clientInfoService.newContact(contact));
            }
            else {
                LOG.warn(" Client by id  "+ contactDTO.getClientId() + " not found");
                throw new ClientNotFoundException();
            }
        }
        else {
            LOG.warn("parameter contact type, client id or contact is missing in the request");
            throw new ContactBadRequestException("parameter contact type, client id or contact is missing in the request");
        }
    }

    @GetMapping(produces = "application/json", path = "#{'${values.url.contact.by_client_id}'}")
    public ResponseEntity<List<Contact>> getByClientId(@RequestParam(required = true) @NonNull long id){
        if(id != 0){
            Optional<Client> optionalClient = clientInfoService.clientById(id);
            if(optionalClient.isPresent()){
                List<Contact> contacts = clientInfoService.contactsByClientId(optionalClient.get());
                if (!contacts.isEmpty()){
                    return ResponseEntity.ok().body(contacts);
                }
                else {
                    LOG.warn(" Contacts by client id  "+ id + " not found");
                    throw new ContactNotFoundException();
                }
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

    @GetMapping(produces = "application/json", path = "#{'${values.url.contact.by_type_and_client_id}'}")
    public ResponseEntity<List<Contact>> getByTypeAndClientId(
            @RequestParam(required = true) @NonNull long id,
            @RequestParam(required = true) @NonNull String type
    ){
        ContactType contactType;
        if(type.equalsIgnoreCase(ContactType.EMAIL.name())){
            contactType = ContactType.EMAIL;
        }
        else if(type.equalsIgnoreCase(ContactType.PHONE.name())){
            contactType = ContactType.PHONE;
        }
        else {
            LOG.warn(" invalid Contact Type");
            throw new ContactTypeBadRequestException("invalid Contact Type");
        }
        if(id != 0){
            Optional<Client> optionalClient = clientInfoService.clientById(id);
            if(optionalClient.isPresent()){
                List<Contact> contacts = clientInfoService
                        .contactsByTypeAndClientId(contactType, optionalClient.get());
                if (!contacts.isEmpty()){
                    return ResponseEntity.ok().body(contacts);
                }
                else {
                    LOG.warn(" Contacts by client id  "+ id + " and type " + contactType + " not found");
                    throw new ContactNotFoundException();
                }

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
}
