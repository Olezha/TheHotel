package ua.olezha.hotel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SecurityConfigurationIntegrationTest {

    private TestRestTemplate restTemplate;

    private URL baseUrl;

    @LocalServerPort
    private int port;

    @Before
    public void setUp() throws MalformedURLException {
        restTemplate = new TestRestTemplate();
        baseUrl = new URL("http://localhost:" + port);
    }

    @Test
    public void whenLoggedUserRequestsHomePage_ThenSuccess() throws IllegalStateException {
        ResponseEntity<String> response =
                restTemplate.getForEntity(baseUrl.toString(), String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(Objects.requireNonNull(response.getBody()).contains("Hotel"));
    }

    @Test
    public void whenUserWithWrongCredentials_thenUnauthorizedPage() {
        ResponseEntity<String> response =
                restTemplate.getForEntity(baseUrl.toString() + "/management", String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(Objects.requireNonNull(response.getBody()).contains("Login Page"));
    }
}
