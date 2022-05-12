package draw_a_card_tests;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static io.restassured.RestAssured.get;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DrawingCardsWithPreCreatedDeckTests extends Common {

    private static int countOfCardsInDeck = defaultNumberOfCardsInDeck;
    private static int numberOfCardsToDraw = getRandomIntInRange(1, countOfCardsInDeck);
    private static JsonPath drawACardResponseAsJsonPath;

    @BeforeAll
    static void testsSetUp() {
        setUp();
        //create a new deck
        Response newDeckResponse = get("/new");
        assertEquals(newDeckResponse.statusCode(), successfulRequestStatusCode);
        String deckId = newDeckResponse.jsonPath().get("deck_id");

        Response response = get("/" + deckId + "/draw/?count=" + numberOfCardsToDraw);
        assertEquals(response.statusCode(), successfulRequestStatusCode);
        drawACardResponseAsJsonPath = response.jsonPath();
    }

    @Test
    public void expectedAmountOfCardsShouldBeDraw() {
        ArrayList<JsonPath> cardsList = drawACardResponseAsJsonPath.get("cards");
        assertEquals(cardsList.size(), numberOfCardsToDraw);
    }

    @Test
    public void expectedAmountOfCardsShouldRemainInDeck() {
        assertEquals(drawACardResponseAsJsonPath.getInt("remaining"), countOfCardsInDeck - numberOfCardsToDraw);
    }
}
