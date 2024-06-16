package stepdefs;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GetCharacterDetails {
    private int characterId;
    private String characterStringId;
    private Response response;
    private static final String BASE_URI = "https://rickandmortyapi.com/api/character/";

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
                .baseUri(BASE_URI)
                .pathParam("id", characterId)
                .when()
                .get("/{id}");
    }

    @When("The user sends a request to get the character details")
    public void the_user_sends_a_request_to_get_the_character_details() {
        response = given()
                .baseUri(BASE_URI)
                .pathParam("id", characterStringId)
                .when()
                .get("/{id}");
    }

    @Then("the response status code should be {int}")
    public void the_response_status_code_should_be(Integer statusCode) {
        assertEquals(statusCode.intValue(), response.getStatusCode());
    }

    @Then("the response should include the character details")
    public void the_response_should_include_the_character_details(DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> row : rows) {
            assertEquals(row.get("id"), String.valueOf(response.jsonPath().getInt("id")));
            assertEquals(row.get("name"), response.jsonPath().getString("name"));
            assertEquals(row.get("status"), response.jsonPath().getString("status"));
            assertEquals(row.get("species"), response.jsonPath().getString("species"));
            assertEquals(row.get("gender"), response.jsonPath().getString("gender"));
            assertEquals(row.get("origin"), response.jsonPath().getString("origin.name"));
            assertEquals(row.get("location"), response.jsonPath().getString("location.name"));
            assertEquals(row.get("imageUrl"), response.jsonPath().getString("image"));
            assertTrue(response.jsonPath().getList("episode").contains(row.get("episodeUrl")));
        }
    }

    @Then("the response should include the error message {string}")
    public void the_response_should_include_the_error_message(String errorMessage) {
        assertEquals(errorMessage, response.jsonPath().getString("error"));
    }
}
