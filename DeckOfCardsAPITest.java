import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;

import org.testng.annotations.Test;
public class DeckOfCardsAutomation {
private String baseUrl = &quot;https://deckofcardsapi.com&quot;;
@BeforeClass
public void setup() {
RestAssured.baseURI = baseUrl;
}
@Test
public void testDeckOfCardsAPI() {
// Step 1: Navigate to https://deckofcardsapi.com/
// Step 2: Confirm the site is up
Response response = RestAssured.get(&quot;/&quot;);
Assert.assertEquals(response.getStatusCode(), 200, &quot;The site is not up.&quot;);
// Step 3: Get a new deck
response = RestAssured.get(&quot;/api/deck/new/&quot;);
Assert.assertEquals(response.getStatusCode(), 200, &quot;Failed to get a new deck.&quot;);
String deckId = response.jsonPath().getString(&quot;deck_id&quot;);
// Step 4: Shuffle the deck
response = RestAssured.get(&quot;/api/deck/{deckId}/shuffle/&quot;, deckId);
Assert.assertEquals(response.getStatusCode(), 200, &quot;Failed to shuffle the deck.&quot;);
// Step 5: Deal three cards to each of two players
response = RestAssured.get(&quot;/api/deck/{deckId}/draw/?count=6&quot;, deckId);
Assert.assertEquals(response.getStatusCode(), 200, &quot;Failed to draw cards.&quot;);
String[] cards = response.jsonPath().getString(&quot;cards.value&quot;).toArray(new String[0]);
// Step 6: Check whether either has blackjack
boolean player1HasBlackjack = checkBlackjack(cards[0], cards[1], cards[2]);
boolean player2HasBlackjack = checkBlackjack(cards[3], cards[4], cards[5]);
// Step 7: If either has, write out which one does
if (player1HasBlackjack &amp;&amp; player2HasBlackjack) {
System.out.println(&quot;Both players have blackjack.&quot;);
} else if (player1HasBlackjack) {
System.out.println(&quot;Player 1 has blackjack.&quot;);
} else if (player2HasBlackjack) {
System.out.println(&quot;Player 2 has blackjack.&quot;);
} else {
System.out.println(&quot;Neither player has blackjack.&quot;);

}
}
private boolean checkBlackjack(String card1, String card2, String card3) {
return (card1.equals(&quot;ACE&quot;) &amp;&amp; (card2.equals(&quot;10&quot;) || card2.equals(&quot;JACK&quot;) ||
card2.equals(&quot;QUEEN&quot;) || card2.equals(&quot;KING&quot;)))
|| (card2.equals(&quot;ACE&quot;) &amp;&amp; (card1.equals(&quot;10&quot;) || card1.equals(&quot;JACK&quot;) ||
card1.equals(&quot;QUEEN&quot;) || card1.equals(&quot;KING&quot;)))
|| (card3.equals(&quot;ACE&quot;) &amp;&amp; (card1.equals(&quot;10&quot;) || card1.equals(&quot;JACK&quot;) ||
card1.equals(&quot;QUEEN&quot;) || card1.equals(&quot;KING&quot;)));
}
}
