package draw_a_card_tests;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DrawingCardsWithNewDeckTests extends Common {

    private static final int countOfCardsInDeck = defaultNumberOfCardsInDeck;
    private static final int numberOfCardsToDraw = getRandomIntInRange(1, countOfCardsInDeck);
    private static JsonPath drawACardResponseAsJsonPath;

    @BeforeAll
    static void testsSetUp() {
        setUp();
        Response response = get("/new/draw/?count=" + numberOfCardsToDraw);
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
