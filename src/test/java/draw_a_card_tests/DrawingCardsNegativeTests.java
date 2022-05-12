package draw_a_card_tests;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static io.restassured.RestAssured.get;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class DrawingCardsNegativeTests extends Common {
    private final String errorMessageResponseJsonPath = "error";
    private static String partialRequestURL;

    @BeforeAll
    static void testsSetUp() {
        setUp();
        //create a new deck
        Response newDeckResponse = get("/new");
        assertEquals(newDeckResponse.statusCode(), successfulRequestStatusCode);
        partialRequestURL = "/" + newDeckResponse.jsonPath().get("deck_id") + "/draw/?count=";
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 0})
    public void cannotDrawErrorMessageShouldBeReturned(int numberOfCardsToDraw) {
        Response response = get(partialRequestURL + numberOfCardsToDraw);
        assertEquals(response.statusCode(), successfulRequestStatusCode);
        JsonPath jsonPath = response.jsonPath();
        assertFalse(jsonPath.getBoolean(successResponseJsonPath));
        assertEquals(jsonPath.get(errorMessageResponseJsonPath), "Cannot draw " + numberOfCardsToDraw + " cards from deck"); //made up message
    }

    @Test
    public void notEnoughCardsToDrawErrorMessageShouldBeReturned(){
        int numberOfCardsToDraw = defaultNumberOfCardsInDeck + 1;
        Response response = get(partialRequestURL + numberOfCardsToDraw );
        assertEquals(response.statusCode(), successfulRequestStatusCode);
        JsonPath jsonPath = response.jsonPath();
        assertFalse(jsonPath.getBoolean(successResponseJsonPath));
        assertEquals(jsonPath.get(errorMessageResponseJsonPath), "Not enough cards remaining to draw " + numberOfCardsToDraw + " additional");
    }
}
