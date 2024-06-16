package stepdefs;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GetCharacterDetails {
    private int characterId;
    private String characterStringId;
    private Response response;

    @Given("The user has the character ID {int}")
    public void the_user_has_the_character_id(int characterId) {
        this.characterId = characterId;
    }

    @Given("The user has the character string ID {string}")
    public void the_user_has_the_character_string_id(String characterStringId) {
        this.characterStringId = characterStringId;
    }

    @When("The user sends a GET request to get the character details")
    public void the_user_sends_a_GET_request_to_get_the_character_details() {
        response = given()
                .baseUri("https://rickandmortyapi.com/api/character/")
                .pathParam("id", characterId)
                .when()
                .get("{id}");
    }

    @When("The user sends a request to get the character details")
    public void the_user_sends_a_request_to_get_the_character_details() {
        response = given()
                .baseUri("https://rickandmortyapi.com/api/character/")
                .pathParam("id", characterStringId)
                .when()
                .get("{id}");
    }

    @Then("the response status code should be {int}")
    public void the_response_status_code_should_be(Integer statusCode) {
        assertEquals(statusCode.intValue(), response.getStatusCode());
    }

    @Then("the response should include the character ID {string}")
    public void the_response_should_include_the_character_id(String id) {
        assertEquals(id, response.jsonPath().getString("id"));
    }

    @Then("the response should include the character name {string}")
    public void the_response_should_include_the_character_name(String name) {
        assertEquals(name, response.jsonPath().getString("name"));
    }

    @Then("the response should include the character status {string}")
    public void the_response_should_include_the_character_status(String status) {
        assertEquals(status, response.jsonPath().getString("status"));
    }

    @Then("the response should include the character species {string}")
    public void the_response_should_include_the_character_species(String species) {
        assertEquals(species, response.jsonPath().getString("species"));
    }

    @Then("the response should include the character gender {string}")
    public void the_response_should_include_the_character_gender(String gender) {
        assertEquals(gender, response.jsonPath().getString("gender"));
    }

    @Then("the response should include the character origin {string}")
    public void the_response_should_include_the_character_origin(String origin) {
        assertEquals(origin, response.jsonPath().getString("origin.name"));
    }

    @Then("the response should include the character location {string}")
    public void the_response_should_include_the_character_location(String location) {
        assertEquals(location, response.jsonPath().getString("location.name"));
    }

    @Then("the response should include the character image URL {string}")
    public void the_response_should_include_the_character_image_url(String imageUrl) {
        assertEquals(imageUrl, response.jsonPath().getString("image"));
    }

    @Then("the response should include the episode URL {string}")
    public void the_response_should_include_the_episode_url(String episodeUrl) {
        assertTrue(response.jsonPath().getList("episode").contains(episodeUrl));
    }

    @Then("the response should include the error message {string}")
    public void the_response_should_include_the_error_message(String errorMessage) {
        assertEquals(errorMessage, response.jsonPath().getString("error"));
    }
}
