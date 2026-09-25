package cm.kfokam48.backend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TableauIntegrationTest {

    @LocalServerPort int port;

    private HttpResponse<String> get(String path) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + port + path))
                .GET()
                .build();
        return client.send(req, HttpResponse.BodyHandlers.ofString());
    }

    @Test
    void tableauDoitRepondre200PourPromotionExistante() throws Exception {
        HttpResponse<String> resp = get("/api/tableau?promotionId=1");
        assertEquals(200, resp.statusCode());
        assertTrue(resp.body().contains("nom"));
    }

    @Test
    void tableauDoitRepondre404PourPromotionInconnue() throws Exception {
        HttpResponse<String> resp = get("/api/tableau?promotionId=9999");
        assertEquals(404, resp.statusCode());
        assertTrue(resp.body().contains("PROMOTION_INCONNUE"));
    }
}
