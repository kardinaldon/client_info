package com.client.info.service.impl;

import com.client.info.model.entity.Client;
import com.client.info.model.entity.Contact;
import com.client.info.model.enumeration.ContactType;
import com.client.info.repository.ClientRepository;
import com.client.info.repository.ContactRepository;
import com.client.info.service.IClientInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ClientInfoServiceImpl implements IClientInfoService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ContactRepository contactRepository;

    @Override
    public Optional<Client> clientById(Long id) {
        return clientRepository.findById(id);
    }

    @Override
    public Client newClient(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public List<Client> allClients() {
        List<Client> clients = clientRepository.findAll();
        return clients != null ? clients : Collections.emptyList();
    }

    @Override
    public Contact newContact(Contact contact) {
        return contactRepository.save(contact);
    }

    @Override
    public List<Contact> contactsByClientId(Client client) {
        List<Contact> contacts = contactRepository.findByClient(client);
        return contacts != null ? contacts : Collections.emptyList();
    }

    @Override
    public List<Contact> contactsByTypeAndClientId(ContactType contactType, Client client) {
        List<Contact> contacts = contactRepository.findByContactTypeAndClient(contactType, client);
        return contacts != null ? contacts : Collections.emptyList();
    }

}
