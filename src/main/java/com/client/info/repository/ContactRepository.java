package com.client.info.repository;

import com.client.info.model.entity.Client;
import com.client.info.model.entity.Contact;
import com.client.info.model.enumeration.ContactType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {

    List<Contact> findByClient(Client client);

    List<Contact> findByContactTypeAndClient(ContactType contactType, Client client);

}
