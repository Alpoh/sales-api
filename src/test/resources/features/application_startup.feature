Feature: Application startup

  Scenario: The Spring context starts successfully
    Given the application context is starting
    When the context finishes loading
    Then the context is available
