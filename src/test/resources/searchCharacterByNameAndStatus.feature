@aut-8
Feature: Search character by name and status

  Scenario: Successful search by full name and status
    Given The user searches for the character by name "Rick Sanchez" and status "Alive"
    When The user sends the search request with params
    Then the response code should be 200
    And the response should include a character with name "Rick Sanchez" and status "Alive"
    And the response should include information about the character

  Scenario Outline: Successful search by partial name and status
    Given The user searches for characters by partial name "<partialName>" and status "<partialStatus>"
    When The user sends the search request with params
    Then the response code should be 200
    And the response should include a list of characters that contain "<partialName>" in their names and have status "<partialStatus>"
    And the response should include information about each character

    Examples:
      | partialName | partialStatus |
      | ric         | Alive         |
      | ric         | dead          |
      | ric         | unknown       |
      | Ric         | ali           |
      | orty        |               |

  Scenario Outline: No characters found or search with invalid parameters
    Given The user searches for a character by name "<searchTerm>" and status "<status>"
    When The user sends the search request with params
    Then the response code should be 404
    And the response should show an error message "There is nothing here"

    Examples:
      | searchTerm           | status   |
      | alexander            | alive    |
      | !@#$%                | dead     |