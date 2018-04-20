#Author: your.email@your.domain.com
#Keywords Summary :
#Feature: List of scenarios.
#Scenario: Business rule through list of steps with arguments.
#Given: Some precondition step
#When: Some key actions
#Then: To observe outcomes or validation
#And,But: To enumerate more Given,When,Then steps
#Scenario Outline: List of steps for data-driven as an Examples and <placeholder>
#Examples: Container for s table
#Background: List of steps run before each of the scenarios
#""" (Doc Strings)
#| (Data Tables)
#@ (Tags/Labels):To group Scenarios
#<> (placeholder)
#""
## (Comments)
#Sample Feature Definition Template

Feature: Automated End2End Tests
Description: The purpose of this feature is to test End2End integration.

  
  Scenario: Customer places an order
    Given User is on Home Page
    And He search for "dress"
		And Choose to buy the first item
		And Moves to checkout from mini cart
		And Enter personal details on checkout page
		And Select same delivery address
		And Select payment method as "Check" payment
		And Place the orde
		Then Print the order No
		

