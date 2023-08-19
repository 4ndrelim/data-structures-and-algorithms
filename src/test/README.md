# Testing Implementation

This folder is for testing of implementation in **src/** folder.

Note to contributors: 
- Naming convention for test methods: featureUnderTest_testScenario_expectedBehaviour()
The above is just a guide, slight deviation is fine as long as its clear. eg. no need to specifically mention "ordinary array" if testing some sorting algor.
  - camelCase
  - eg. sortList_emptyList_shouldReturnNoChange(), test_mergeSort_shouldReturnSortedArray, test{algor}_emptyList_shouldReturnEmptyResult
