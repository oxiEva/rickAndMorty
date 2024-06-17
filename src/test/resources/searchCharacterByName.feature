@aut-1 @character
Feature: Search character by name

  Scenario: Successful search by full name
    Given The user searches for the character by name "Rick Sanchez"
    When The user sends the search request
    Then the status code should be 200
    And the response should include a character with name "Rick Sanchez"
    And the response should include detailed information about the character

  Scenario: Successful search by partial name
    Given The user searches for characters by partial name "Rick"
    When The user sends the search request
    Then the status code should be 200
    And the response should include a list of characters that contain "Rick" in their names
    And the response should include detailed information about each character
    And the response count should be greater than 0
    And the response should include pagination details

  Scenario: Search for a specific character by name
    Given The user searches for a character by name "Mike"
    When The user sends the search request
    Then the status code should be 200
    And the response should include detailed information about each character
    And the response count should be 1
    And the pagination details should indicate a single page

  Scenario Outline: No characters found or search with invalid parameters
    Given The user searches for a character by name "<searchTerm>"
    When The user sends the search request
    Then the status code should be 404
    And the response should include an error message "<errorMessage>"

    Examples:
      | searchTerm           |  errorMessage            |
      | NonExistentCharacter |  There is nothing here   |
      | !@#$%                |  There is nothing here   |
