@aut-9 @episode
Feature: Search episode by ID

  Scenario: Successful search by episode ID
    Given the user has the episode ID 28
    When the user sends a GET request to get the episode details by ID
    Then response code should be 200
    And the response should include the episode details with ID 28, name "The Ricklantis Mixup", air date "September 10, 2017", episode "S03E07", and a list of character URLs
    And the response should include other relevant information

  Scenario: Search with a non-existent episode ID
    Given the user has the episode ID 999999999
    When the user sends a GET request to get the episode details by ID
    Then response code should be 404
    And response should show an error message "Episode not found"

  Scenario: Search with an invalid episode string ID
    Given the user has the episode string ID "*&^%$"
    When the user sends a GET request to get the episode details by string ID
    Then response code should be 500
    And response should show an error message "Hey! you must provide an id"
