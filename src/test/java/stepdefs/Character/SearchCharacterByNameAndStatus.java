package stepdefs.Character;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.*;

public class SearchCharacterByNameAndStatus {
    private String searchName;
    private String searchStatus;
    private Response response;

    private static final String BASE_URI = "https://rickandmortyapi.com/api/character/";

    @Given("The user searches for the character by name {string} and status {string}")
    public void the_user_searches_for_the_character_by_name_and_status(String name, String status) {
        this.searchName = name;
        this.searchStatus = status;
    }

    @Given("The user searches for characters by partial name {string} and status {string}")
    public void the_user_searches_for_characters_by_partial_name_and_status(String partialName, String status) {
        this.searchName = partialName;
        this.searchStatus = status;
    }

    @Given("The user searches for a character by name {string} and status {string}")
    public void the_user_searches_for_a_character_by_name_and_status(String name, String status) {
        this.searchName = name;
        this.searchStatus = status;
    }

    @When("The user sends the search request with params")
    public void the_user_sends_the_search_request_with_params() {
        response = given()
                .baseUri(BASE_URI)
                .queryParam("name", searchName)
                .queryParam("status", searchStatus)
                .when()
                .get();
    }

    @Then("the response code should be {int}")
    public void the_response_code_should_be(Integer statusCode) {
        assertEquals(statusCode.intValue(), response.getStatusCode());
    }

    @Then("the response should include a character with name {string} and status {string}")
    public void the_response_should_include_a_character_with_name_and_status(String name, String status) {
        List<Map<String, String>> results = response.jsonPath().getList("results");
        boolean characterFound = results.stream()
                .anyMatch(character -> name.equals(character.get("name")) && status.equals(character.get("status")));
        assertTrue(characterFound);
    }

    @Then("the response should include information about the character")
    public void the_response_should_include_information_about_the_character() {
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

    @Then("the response should include information about each character")
    public void the_response_should_include_information_about_each_character() {
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

    @Then("the response should include a list of characters that contain {string} in their names and have status {string}")
    public void the_response_should_include_a_list_of_characters_that_contain_in_their_names_and_have_status(String partialName, String partialStatus) {
        List<Map<String, Object>> results = response.jsonPath().getList("results");
        assertFalse(results.isEmpty());

        for (Map<String, Object> character : results) {
            String name = character.get("name").toString().toLowerCase();
            String status = character.get("status").toString().toLowerCase();
            assertTrue(name.contains(partialName.toLowerCase()));
            assertTrue(status.contains(partialStatus.toLowerCase()));
        }
    }

    @Then("the response should show an error message {string}")
    public void the_response_should_show_an_error_message(String errorMessage) {
        assertEquals(errorMessage, response.jsonPath().getString("error"));
    }
}
