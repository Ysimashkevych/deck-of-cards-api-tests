package draw_a_card_tests;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static io.restassured.RestAssured.*;

public class Common {

    public static final List<String> allAvailableCardsCodes = Arrays.asList("AS", "2S", "3S", "4S", "5S", "6S", "7S", "8S", "9S", "0S", "JS", "QS", "KS",
            "AD", "2D", "3D", "4D", "5D", "6D", "7D", "8D", "9D", "0D", "JD", "QD", "KD",
            "AC", "2C", "3C", "4C", "5C", "6C", "7C", "8C", "9C", "0C", "JC", "QC", "KC",
            "AH", "2H", "3H", "4H", "5H", "6H", "7H", "8H", "9H", "0H", "JH", "QH", "KH");

    public static final int successfulRequestStatusCode = 200;
    public static final int defaultNumberOfCardsInDeck = 52;
    public static final String successResponseJsonPath = "success";
    public static void setUp() {
        baseURI = "https://deckofcardsapi.com/api/deck";
    }

    public static int getRandomIntInRange(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public static List<String> getRandomCardsSublist(int newSize) {
        ArrayList<String> list = new ArrayList<>(allAvailableCardsCodes);
        Collections.shuffle(list);
        return list.subList(0, newSize);
    }
}
