package com.sensedia.sample.consents;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class ConsentIntegrationTest {
    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    @DynamicPropertySource
    static void overrideProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "update");
    }

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldCreateConsent() throws Exception {
        String json = """
                {
                  "cpf": "067.793.060-70",
                  "expirationDateTime": "13/04/2025 23:15:00",
                  "additionalInfo": "I do consent ..."
                }
                """;

        mockMvc.perform(post("/consents")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.cpf").exists())
                .andExpect(jsonPath("$.cpf").value("067.***.***-70"));
    }


    @Test
    void shouldntCreateConsent_InvalidCpf() throws Exception {
        String json = """
                {
                  "cpf": "111.111.111-11",
                  "expirationDateTime": "11/04/2025 23:15:00",
                  "additionalInfo": "I do consent ..."
                }
                """;

        mockMvc.perform(post("/consents")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.type").exists())
                .andExpect(jsonPath("$.type").value("Invalid input"))
                .andExpect(jsonPath("$.title").exists())
                .andExpect(jsonPath("$.title").value("Error creating consent"))
                .andExpect(jsonPath("$.errorInfos[0].detail").exists())
                .andExpect(jsonPath("$.errorInfos[0].detail").value("CPF must have 11 digits and cannot be repeated!"))
                .andExpect(jsonPath("$.errorInfos[0].pointer").exists())
                .andExpect(jsonPath("$.errorInfos[0].pointer").value("#/cpf"));
    }


    @Test
    void shouldFindConsentById() throws Exception {
        String json = """
                {
                  "cpf": "067.793.060-70",
                  "expirationDateTime": "13/04/2025 23:15:00",
                  "additionalInfo": "I do consent ..."
                }
                """;

        String response = mockMvc.perform(post("/consents")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.cpf").exists())
                .andExpect(jsonPath("$.cpf").value("067.***.***-70"))
                .andReturn()
                .getResponse()
                .getContentAsString();
        String id = response.split("\"id\":\"")[1].split("\"")[0];

        mockMvc.perform(get("/consents/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cpf").exists())
                .andExpect(jsonPath("$.cpf").value("067.***.***-70"))
                .andExpect(jsonPath("$.id").value(id));

        mockMvc.perform(get("/consents/abc"))
                .andExpect(jsonPath("$.type").exists())
                .andExpect(jsonPath("$.type").value("Invalid parameter"))
                .andExpect(jsonPath("$.title").exists())
                .andExpect(jsonPath("$.title").value("Error searching for consent by id"))
                .andExpect(jsonPath("$.errorInfos[0].detail").exists())
                .andExpect(jsonPath("$.errorInfos[0].detail").value("Consent not found with provided id"))
                .andExpect(jsonPath("$.errorInfos[0].pointer").exists())
                .andExpect(jsonPath("$.errorInfos[0].pointer").value("?id"));
    }

    @Test
    void shouldSearchConsent() throws Exception {
        String json = """
                {
                  "cpf": "067.793.060-70",
                  "expirationDateTime": "13/04/2025 23:15:00",
                  "additionalInfo": "I do consent ..."
                }
                """;

        String response = mockMvc.perform(post("/consents")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.cpf").exists())
                .andExpect(jsonPath("$.cpf").value("067.***.***-70"))
                .andReturn()
                .getResponse()
                .getContentAsString();
        String id1 = response.split("\"id\":\"")[1].split("\"")[0];

        //Tests orderBy
        mockMvc.perform(get("/consents")
                        .param("status", "EXPIRED")
                        .param("orderBy", "id")
                        .param("direction", "DESC"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").exists())
                .andExpect(jsonPath("$.content[0].cpf").value("067.***.***-70"));

        //Tests filters
        mockMvc.perform(get("/consents")
                        .param("id", id1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").exists())
                .andExpect(jsonPath("$.content[0].cpf").value("067.***.***-70"));

        //Tests order by
        mockMvc.perform(get("/consents")
                        .param("orderBy", "id")
                        .param("direction", "ASC"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").exists())
                .andExpect(jsonPath("$.content[0].cpf").value("067.***.***-70"));

        //Tests filters with empty result
        mockMvc.perform(get("/consents")
                        .param("status", "ACTIVE"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isEmpty());
    }

    @Test
    void shouldChangeConsent() throws Exception {
        String json = """
                {
                  "cpf": "067.793.060-70",
                  "expirationDateTime": "13/04/2025 23:15:00",
                  "additionalInfo": "I do consent ..."
                }
                """;

        String response = mockMvc.perform(post("/consents")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.cpf").exists())
                .andExpect(jsonPath("$.cpf").value("067.***.***-70"))
                .andReturn()
                .getResponse()
                .getContentAsString();
        String id1 = response.split("\"id\":\"")[1].split("\"")[0];

        String jsonChange = """
                {
                  "cpf": "067.793.060-70",
                  "status": "ACTIVE",
                  "expirationDateTime": "13/04/2025 23:15:00",
                  "additionalInfo": "I do consent ..."
                }
                """;
        mockMvc.perform(put("/consents/" + id1)
                        .contentType(APPLICATION_JSON)
                        .content(jsonChange))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cpf").exists())
                .andExpect(jsonPath("$.cpf").value("067.***.***-70"))
                .andExpect(jsonPath("$.id").value(id1))
                .andExpect(jsonPath("$.expirationDateTime").value("13/04/2025 23:15:00"))
                .andExpect(jsonPath("$.additionalInfo").value("I do consent ..."));

        mockMvc.perform(put("/consents/aa")
                        .contentType(APPLICATION_JSON)
                        .content(jsonChange))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.type").exists())
                .andExpect(jsonPath("$.type").value("Invalid parameter"))
                .andExpect(jsonPath("$.title").exists())
                .andExpect(jsonPath("$.title").value("Error changing consent by id"))
                .andExpect(jsonPath("$.errorInfos[0].detail").exists())
                .andExpect(jsonPath("$.errorInfos[0].detail").value("Consent not found with provided id"))
                .andExpect(jsonPath("$.errorInfos[0].pointer").exists())
                .andExpect(jsonPath("$.errorInfos[0].pointer").value("?id"));

        jsonChange = """
                {
                  "cpf": "111.111.111-11",
                  "status": "ACTIVE",
                  "expirationDateTime": "13/04/2025 23:15:00",
                  "additionalInfo": "I do consent ..."
                }
                """;
        mockMvc.perform(put("/consents/" + id1)
                        .contentType(APPLICATION_JSON)
                        .content(jsonChange))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.type").exists())
                .andExpect(jsonPath("$.type").value("Invalid input"))
                .andExpect(jsonPath("$.title").exists())
                .andExpect(jsonPath("$.title").value("Error changing consent by id"))
                .andExpect(jsonPath("$.errorInfos[0].detail").exists())
                .andExpect(jsonPath("$.errorInfos[0].detail").value("CPF must have 11 digits and cannot be repeated!"))
                .andExpect(jsonPath("$.errorInfos[0].pointer").exists())
                .andExpect(jsonPath("$.errorInfos[0].pointer").value("#/cpf"));
    }

    @Test
    void shouldRevokeConsent() throws Exception {
        String json = """
                {
                  "cpf": "067.793.060-70",
                  "expirationDateTime": "13/04/2025 23:15:00",
                  "additionalInfo": "I do consent ..."
                }
                """;

        String response = mockMvc.perform(post("/consents")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.cpf").exists())
                .andExpect(jsonPath("$.cpf").value("067.***.***-70"))
                .andReturn()
                .getResponse()
                .getContentAsString();
        String id1 = response.split("\"id\":\"")[1].split("\"")[0];

        mockMvc.perform(delete("/consents/" + id1))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$.cpf").exists())
                .andExpect(jsonPath("$.cpf").value("067.***.***-70"))
                .andExpect(jsonPath("$.id").value(id1))
                .andExpect(jsonPath("$.status").value("REVOKED"))
                .andExpect(jsonPath("$.expirationDateTime").value("13/04/2025 23:15:00"))
                .andExpect(jsonPath("$.additionalInfo").value("I do consent ..."));

        mockMvc.perform(delete("/consents/aa"))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.type").exists())
                .andExpect(jsonPath("$.type").value("Invalid parameter"))
                .andExpect(jsonPath("$.title").exists())
                .andExpect(jsonPath("$.title").value("Error deleting consent by id"))
                .andExpect(jsonPath("$.errorInfos[0].detail").exists())
                .andExpect(jsonPath("$.errorInfos[0].detail").value("Consent not found with provided id"))
                .andExpect(jsonPath("$.errorInfos[0].pointer").exists())
                .andExpect(jsonPath("$.errorInfos[0].pointer").value("?id"));
    }
}
