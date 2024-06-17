package stepdefs.Episode;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.*;

public class SearchEpisodeById {
    private int episodeId;
    private String episodeStringId;
    private Response response;

    private static final String BASE_URI = "https://rickandmortyapi.com/api/episode/";

    @Given("the user has the episode ID {int}")
    public void the_user_has_the_episode_id(int episodeId) {
        this.episodeId = episodeId;
    }

    @Given("the user has the episode string ID {string}")
    public void the_user_has_the_episode_string_id(String episodeStringId) {
        this.episodeStringId = episodeStringId;
    }

    @Given("the user searches for episodes without specifying an ID")
    public void the_user_searches_for_episodes_without_specifying_an_ID() {
    }

    @When("the user sends a GET request to get the episode details by ID")
    public void the_user_sends_a_GET_request_to_get_the_episode_details_by_ID() {
        response = given()
                .baseUri(BASE_URI)
                .pathParam("id", episodeId)
                .when()
                .get("/{id}");
    }

    @When("the user sends a GET request to get the episode details by string ID")
    public void the_user_sends_a_GET_request_to_get_the_episode_details_by_string_ID() {
        response = given()
                .baseUri(BASE_URI)
                .pathParam("id", episodeStringId)
                .when()
                .get("/{id}");
    }

    @When("the user sends a GET request to get all episodes")
    public void the_user_sends_a_GET_request_to_get_all_episodes() {
        response = given()
                .baseUri(BASE_URI)
                .when()
                .get();
    }

    @Then("response code should be {int}")
    public void the_response_code_should_be(Integer statusCode) {
        assertEquals(statusCode.intValue(), response.getStatusCode());
    }

    @Then("the response should include the episode details with ID {int}, name {string}, air date {string}, episode {string}, and a list of character URLs")
    public void the_response_should_include_the_episode_details(int id, String name, String airDate, String episode) {
        assertEquals(id, response.jsonPath().getInt("id"));
        assertEquals(name, response.jsonPath().getString("name"));
        assertEquals(airDate, response.jsonPath().getString("air_date"));
        assertEquals(episode, response.jsonPath().getString("episode"));
        assertFalse(response.jsonPath().getList("characters").isEmpty());
    }

    @Then("the response should include other relevant information")
    public void the_response_should_include_other_relevant_information() {
        assertNotNull(response.jsonPath().getString("url"));
        assertNotNull(response.jsonPath().getString("created"));
    }

    @Then("response should show an error message {string}")
    public void the_response_should_show_an_error_message(String errorMessage) {
        assertEquals(errorMessage, response.jsonPath().getString("error"));
    }

    @Then("the response should include a list of all episodes")
    public void the_response_should_include_a_list_of_all_episodes() {
        assertFalse(response.jsonPath().getList("results").isEmpty());
    }
}
