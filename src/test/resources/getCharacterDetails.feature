@aut-2
Feature: Viewing Character Details

  Scenario: View character details by ID
    Given The user has the character ID 381
    When The user sends a GET request to get the character details
    Then the response status code should be 200
    And the response should include the character ID "381"
    And the response should include the character name "Woman Rick"
    And the response should include the character status "Alive"
    And the response should include the character species "Alien"
    And the response should include the character gender "Female"
    And the response should include the character origin "unknown"
    And the response should include the character location "unknown"
    And the response should include the character image URL "https://rickandmortyapi.com/api/character/avatar/381.jpeg"
    And the response should include the episode URL "https://rickandmortyapi.com/api/episode/10"

  Scenario Outline: View character details with invalid ID
    Given The user has the character ID <id>
    When The user sends a GET request to get the character details
    Then the response status code should be 404
    And the response should include the error message "<errorMessage>"

    Examples:
      | id   | errorMessage        |
      | 97843| Character not found |
      | -1   | Character not found |

  Scenario Outline: View character details with invalid ID type
    Given The user has the character string ID "<id>"
    When The user sends a request to get the character details
    Then the response status code should be 500
    And the response should include the error message "<errorMessage>"

    Examples:
      | id      | errorMessage                                   |
      | hi      | Hey! you must provide an id                    |
      | 86-7    | Cast to Number failed for value \"86-7\" at path \"id\" for model \"Character\"|
