@aut-2 @character
Feature: Viewing Character Details

  Scenario Outline: View character details by ID
    Given The user has the character ID <id>
    When The user sends a GET request to get the character details
    Then the response status code should be 200
    And the response should include the character details
      | id  | name   | status   | species   | gender   | origin  | location   | imageUrl   | episodeUrl   |
      | <id>| <name> | <status> | <species> | <gender> | <origin>| <location> | <imageUrl> | <episodeUrl> |

    Examples:
      | id  | name         | status | species | gender | origin        | location     | imageUrl                                            | episodeUrl                  |
      | 381 | Woman Rick   | Alive  | Alien   | Female | unknown       | unknown      | https://rickandmortyapi.com/api/character/avatar/381.jpeg | https://rickandmortyapi.com/api/episode/10 |
      | 17  | Annie        | Alive  | Human   | Female | Earth (C-137) | Anatomy Park | https://rickandmortyapi.com/api/character/avatar/17.jpeg  | https://rickandmortyapi.com/api/episode/3  |

  Scenario Outline: View character details with invalid ID
    Given The user has the character ID <id>
    When The user sends a GET request to get the character details
    Then the response status code should be 404
    And the response should include the error message "<errorMessage>"

    Examples:
      | id   | errorMessage        |
      | 97843| Character not found |
      | 10   | Character not found |

  Scenario Outline: View character details with invalid ID type
    Given The user has the character string ID "<id>"
    When The user sends a request to get the character details
    Then the response status code should be 500
    And the response should include the error message "<errorMessage>"

    Examples:
      | id      | errorMessage                                   |
      | hi      | Hey! you must provide an id                    |
      | 86-7    | Cast to Number failed for value \"86-7\" at path \"id\" for model \"Character\"|
