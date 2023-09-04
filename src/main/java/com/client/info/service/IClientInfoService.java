package com.client.info.service;

import com.client.info.model.entity.Client;
import com.client.info.model.entity.Contact;
import com.client.info.model.enumeration.ContactType;

import java.util.List;
import java.util.Optional;

public interface IClientInfoService {

    Optional<Client> clientById(Long id);

    Client newClient(Client client);

    List<Client> allClients();

    Contact newContact(Contact contact);

    List<Contact> contactsByClientId(Client client);

    List<Contact> contactsByTypeAndClientId(ContactType contactType, Client client);


}
