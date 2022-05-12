package draw_a_card_tests;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Tests for checking response data, not everything, just those that won't be covered in other tests
 */
public class ResponseVerificationTests extends Common{
    private static JsonPath drawACardResponseAsJsonPath;
    private static String deckId;

    @BeforeAll
    public static void createNewDeckOfCardsWithOneCardAvailableAndDrawACard(){
        setUp();
        //create a new deck
        Response newDeckResponse = get("/new/shuffle/?cards=AS");
        assertEquals(newDeckResponse.statusCode(), successfulRequestStatusCode);
        deckId = newDeckResponse.jsonPath().get(deckIdResponseJsonPath);
        //draw a card
        Response drawACardResponse = get("/" + deckId + "/draw/?count=1");
        assertEquals(drawACardResponse.statusCode(), successfulRequestStatusCode);
        drawACardResponseAsJsonPath = drawACardResponse.jsonPath();
    }

    @Test
    public void successShouldBeTrue(){
        assertTrue(drawACardResponseAsJsonPath.getBoolean(successResponseJsonPath));
    }

    @Test
    public void cardImageShouldBeAsExpected() {
        assertEquals("https://deckofcardsapi.com/static/img/AS.png", drawACardResponseAsJsonPath.get("cards[0].image"));
    }

    @Test
    public void cardValueShouldBeEqualToAce() {
        assertEquals("ACE", drawACardResponseAsJsonPath.get("cards[0].value"));
    }

    @Test
    public void cardSuitShouldBeEqualToSpades() {
        assertEquals("SPADES", drawACardResponseAsJsonPath.get("cards[0].suit"));
    }

    @Test
    public void cardCodeShouldBeEqualToAs() {
        assertEquals("AS", drawACardResponseAsJsonPath.get("cards[0].code"));
    }

    @Test
    public void deckIdShouldMatchCreatedDeckId() {
        assertEquals(deckId, drawACardResponseAsJsonPath.get(deckIdResponseJsonPath));
    }
}
