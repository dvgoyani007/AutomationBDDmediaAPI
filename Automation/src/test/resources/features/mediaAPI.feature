# This feature file is related to
# Author : 

@GETAPI
Feature: ottplatform/media API Test 

  Background: Load URL
    Given User loads the "testBaseURI"

  Scenario: Verify status code and response time
    When User hits "GET" request of "testEndpoint" api
    Then Response status code should be "200"
    And User verifies the response time should be below 1000

  Scenario: Verify id and segment_type in response
    When User hits "GET" request of "testEndpoint" api
    Then Response status code should be "200"
    And User verifies "id" field is never null or empty in response
    And User verifies value of response field "segment_type" should be "music"
    
  Scenario: Verify primary field  in response
    When User hits "GET" request of "testEndpoint" api
    Then Response status code should be "200"
    And User verifies "primary" field is never null or empty in response
    
  Scenario: Verify only one track in the list has "now_playing" field in "offset" as true in response
    When User hits "GET" request of "testEndpoint" api
    Then Response status code should be "200"
    And User verifies only one track in the list has "now_playing" field in "offset" as true in response
    
  Scenario: Verify date header value in response 
    When User hits "GET" request of "testEndpoint" api
    Then Response status code should be "200"
    And User verifies "Date" header as "Fri, 21 May" in response   
    