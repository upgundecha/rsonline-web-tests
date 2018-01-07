Feature: Search Filters

  Background: Search for a category
    Given user is on home page
    When he search for a category
      | category | Processor & Microcontroller Development Kits |
    Then results page should list products from the searched category
      | products_count | 1407 |

  Scenario: Search filters should be available for user to select
    Then results page should also display filters
      | Brand                 |
      | Classification        |
      | Name                  |
      | Technology            |
      | Device Core           |
      | Processor Family Name |
      | Processor Part Number |
      | Processor Type        |
      | Revision              |

  Scenario: Apply a filter
    When user applies a filter
      | filter | value        |
      | Brand  | Raspberry Pi |
    Then results should be narrowed to selected filters
      | products_count | 17 |

  Scenario: Apply multiple filters
    When user applies a filter
      | filter | value        |
      | Brand  | Raspberry Pi |
    And user applies additional filter
      | filter      | value         |
      | Device Core | ARM Cortex A7 |
    Then results should be narrowed to selected filters
      | products_count | 2 |

  Scenario: Apply a filter with multiple values
    When user applies a filter
      | filter | value        |
      | Brand  | Raspberry Pi |
    And user applies additional filter
      | filter      | value                |
      | Device Core | ARM Cortex A7, ARM11 |
    Then results should be narrowed to selected filters
      | products_count | 6 |

  Scenario: Disabled filters
    When user applies a filter
      | filter | value        |
      | Brand  | Raspberry Pi |
    And user applies additional filter
      | filter         | value          |
      | Classification | Computer Board |
    And user applies additional filter
      | filter      | value         |
      | Device Core | ARM Cortex A7 |
    Then results should be narrowed to selected filters
      | products_count | 2 |
    And following filters should be disabled
      | Name                  |
      | Processor Part Number |
      | Processor Type        |
      | Revision              |