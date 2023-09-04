package com.client.info;

import com.client.info.controller.ContactController;
import com.client.info.model.dto.ContactDTO;
import com.client.info.model.entity.Client;
import com.client.info.model.entity.Contact;
import com.client.info.model.enumeration.ContactType;
import com.client.info.service.impl.ClientInfoServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(ContactController.class)
class ContactTests {

	@MockBean
	private ClientInfoServiceImpl clientInfoService;
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper mapper;
	private ContactDTO contactDTO;
	private Contact contact;
	private List<Contact> contacts;
	private Client client;
	@Value("${values.url.contact.controller}")
	private String urlContactController;
	@Value("${values.url.contact.new}")
	private String urlNewContact;
	@Value("${values.url.contact.by_client_id}")
	private String urlContactByClientId;
	@Value("${values.url.contact.by_type_and_client_id}")
	private String urlContactByTypeAndClientId;

	@Test
	void getByClientId() throws Exception {
		client = new Client();
		client.setClientId(1L);
		client.setName("Ivan");
		client.setPatronymic("Ivanovich");
		client.setSurname("Ivanov");
		contact = new Contact();
		mapper = new ObjectMapper();
		contact.setContactId(1L);
		contact.setClient(client);
		contact.setContactType(ContactType.EMAIL);
		contact.setContact("123@mail.ru");
		contacts = new ArrayList<>();
		contacts.add(contact);
		when(clientInfoService.clientById(any(Long.class))).thenReturn(Optional.of(client));
		when(clientInfoService.contactsByClientId(any(Client.class))).thenReturn(contacts);
		ResultActions response = mockMvc.perform(get("/"+urlContactController+"/"+urlContactByClientId)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.param("id", "1")
		);
		response.andDo(print()).
				andExpect(status().isOk());
	}

	@Test
	void getByTypeAndClientId() throws Exception {
		client = new Client();
		client.setClientId(1L);
		client.setName("Ivan");
		client.setPatronymic("Ivanovich");
		client.setSurname("Ivanov");
		contact = new Contact();
		mapper = new ObjectMapper();
		contact.setContactId(1L);
		contact.setClient(client);
		contact.setContactType(ContactType.EMAIL);
		contact.setContact("123@mail.ru");
		contacts = new ArrayList<>();
		contacts.add(contact);
		when(clientInfoService.clientById(any(Long.class))).thenReturn(Optional.of(client));
		when(clientInfoService.contactsByTypeAndClientId(any(ContactType.class),any(Client.class)))
				.thenReturn(contacts);
		ResultActions response = mockMvc.perform(get("/"+urlContactController+"/"+urlContactByTypeAndClientId)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.param("id", "1")
				.param("type", "email")
		);
		response.andDo(print()).
				andExpect(status().isOk());
	}

	@Test
	void addContact() throws Exception {
		client = new Client();
		client.setClientId(1L);
		client.setName("Ivan");
		client.setPatronymic("Ivanovich");
		client.setSurname("Ivanov");
		contactDTO = new ContactDTO();
		contactDTO.setContactId(1L);
		contactDTO.setClientId(1L);
		contactDTO.setContactType(ContactType.EMAIL);
		contactDTO.setContact("123@mail.ru");
		contact = new Contact();
		mapper = new ObjectMapper();
		contact.setContactId(1L);
		contact.setClient(client);
		contact.setContactType(ContactType.EMAIL);
		contact.setContact("123@mail.ru");
		when(clientInfoService.clientById(any(Long.class))).thenReturn(Optional.of(client));
		when(clientInfoService.newContact(any(Contact.class))).thenReturn(contact);
		ResultActions response = mockMvc.perform(post("/"+urlContactController+"/"+urlNewContact)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(mapper.writeValueAsString(contactDTO)));
		response.andDo(print()).
				andExpect(status().isOk())
				.andExpect(jsonPath("$.contact",
						is(contact.getContact())));
	}

}
