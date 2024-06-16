package stepdefs;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.*;

public class SearchCharacterByName {
    private String searchName;
    private Response response;

    private static final String BASE_URI = "https://rickandmortyapi.com/api/character/";

    @Given("The user searches for the character by name {string}")
    public void the_user_searches_for_the_character_by_name(String name) {
        this.searchName = name;
    }

    @Given("The user searches for characters by partial name {string}")
    public void the_user_searches_for_characters_by_partial_name(String partialName) {
        this.searchName = partialName;
    }

    @Given("The user searches for a character by name {string}")
    public void the_user_searches_for_a_character_by_name(String name) {
        this.searchName = name;
    }

    @When("The user sends the search request")
    public void the_user_sends_the_search_request() {
        response = given()
                .baseUri(BASE_URI)
                .queryParam("name", searchName)
                .when()
                .get();
    }

    @Then("the status code should be {int}")
    public void the_status_code_should_be(Integer statusCode) {
        assertEquals(statusCode.intValue(), response.getStatusCode());
    }

    @Then("the response should include a character with name {string}")
    public void the_response_should_include_a_character_with_name(String name) {
        List<Map<String, String>> results = response.jsonPath().getList("results");
        boolean nameFound = results.stream()
                .anyMatch(character -> name.equals(character.get("name")));
        assertTrue(nameFound);
    }

    @Then("the response should include detailed information about the character")
    public void the_response_should_include_detailed_information_about_the_character() {
        List<Map<String, Object>> results = response.jsonPath().getList("results");
        assertFalse(results.isEmpty());

        Map<String, Object> character = results.get(0);
        assertTrue(character.containsKey("id"));
        assertTrue(character.containsKey("name"));
        assertTrue(character.containsKey("status"));
        assertTrue(character.containsKey("species"));
        assertTrue(character.containsKey("gender"));
        assertTrue(character.containsKey("origin"));
        assertTrue(character.containsKey("location"));
        assertTrue(character.containsKey("image"));
        assertTrue(character.containsKey("episode"));
    }

    @Then("the response should include detailed information about each character")
    public void the_response_should_include_detailed_information_about_each_character() {
        List<Map<String, Object>> results = response.jsonPath().getList("results");
        assertFalse(results.isEmpty());

        for (Map<String, Object> character : results) {
            assertTrue(character.containsKey("id"));
            assertTrue(character.containsKey("name"));
            assertTrue(character.containsKey("status"));
            assertTrue(character.containsKey("species"));
            assertTrue(character.containsKey("gender"));
            assertTrue(character.containsKey("origin"));
            assertTrue(character.containsKey("location"));
            assertTrue(character.containsKey("image"));
            assertTrue(character.containsKey("episode"));
        }
    }

    @Then("the response should include a list of characters that contain {string} in their names")
    public void the_response_should_include_a_list_of_characters_that_contain_in_their_names(String partialName) {
        List<Map<String, Object>> results = response.jsonPath().getList("results");
        assertFalse(results.isEmpty());

        for (Map<String, Object> character : results) {
            assertTrue(character.get("name").toString().contains(partialName));
        }
    }

    @Then("the response count should be greater than 0")
    public void the_response_count_should_be_greater_than_0() {
        int count = response.jsonPath().getInt("info.count");
        assertTrue(count > 0);
    }

    @Then("the response count should be {int}")
    public void the_response_count_should_be(int expectedCount) {
        int count = response.jsonPath().getInt("info.count");
        assertEquals(expectedCount, count);
    }

    @Then("the response should include pagination details")
    public void the_response_should_include_pagination_details() {
        int pages = response.jsonPath().getInt("info.pages");
        assertTrue(pages > 1);

        String next = response.jsonPath().getString("info.next");
        assertNotNull(next);

        String prev = response.jsonPath().getString("info.prev");
        assertTrue(prev == null || !prev.isEmpty());
    }

    @Then("the pagination details should indicate a single page")
    public void the_pagination_details_should_indicate_a_single_page() {
        int pages = response.jsonPath().getInt("info.pages");
        assertEquals(1, pages);

        String next = response.jsonPath().getString("info.next");
        assertNull(next);

        String prev = response.jsonPath().getString("info.prev");
        assertNull(prev);
    }

    @Then("the response should include an error message {string}")
    public void the_response_should_include_an_error_message(String errorMessage) {
        assertEquals(errorMessage, response.jsonPath().getString("error"));
    }
}
