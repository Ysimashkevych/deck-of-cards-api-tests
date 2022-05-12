package draw_a_card_tests;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.get;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DrawingCardsWithPreCreatedCustomDeckTests extends Common {

    private static final int countOfCardsInDeck = getRandomIntInRange(1, allAvailableCardsCodes.size());
    private static final List<String> cardsList = getRandomCardsSublist(countOfCardsInDeck);
    private static final int numberOfCardsToDraw = getRandomIntInRange(1, cardsList.size());
    private static JsonPath drawACardResponseAsJsonPath;

    @BeforeAll
    static void testsSetUp() {

        setUp();
        //create a new deck
        Response newDeckResponse = get("/new/shuffle/?cards=" + String.join(",", cardsList));
        assertEquals(successfulRequestStatusCode, newDeckResponse.statusCode());
        String deckId = newDeckResponse.jsonPath().get("deck_id");

        Response drawResponse = get("/" + deckId + "/draw/?count=" + numberOfCardsToDraw);
        assertEquals(successfulRequestStatusCode, drawResponse.statusCode());
        drawACardResponseAsJsonPath = drawResponse.jsonPath();
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
