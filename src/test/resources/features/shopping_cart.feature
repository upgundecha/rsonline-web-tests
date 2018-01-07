Feature: Shopping Cart

  Scenario: Add a popular product
    Given user is on home page
    When he selects a top brand from the home page
      | brand | Raspberry Pi |
    And selects a popular product from the brand page
      | product  | Raspberry Pi 3 Model B SBC |
      | price    | £29.99                     |
      | quantity | 1                          |
    And adds the product to shopping cart
    Then shopping cart should contain the selected product

  Scenario: Search and add a product
    Given user is on home page
    When he search for a product
      | product  | Official Raspberry Pi 7in Capacitive Touch Screen for Raspberry Pi |
      | price    | £50.39                                                             |
      | quantity | 1                                                                  |
    And adds the product to shopping cart from results page
    Then shopping cart should contain the selected product