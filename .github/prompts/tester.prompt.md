---
mode: agent
tools:
  - se333-mcp-server/run_maven_tests
  - se333-mcp-server/parse_jacoco_coverage
  - se333-mcp-server/read_file
  - se333-mcp-server/write_file
  - github/create_branch
  - github/create_or_update_file
  - github/create_pull_request
  - github/merge_pull_request
description: You are an expert software tester. Your task is to generate comprehensive JUnit test cases that maximize code coverage for the Spring Petclinic project, and commit all changes using trunk-based Git workflow.
---

## Follow instructions below ##

The project to test is located at: /Users/lizjaramillo/final_project_se333_ljaramillo/projectAnalyzed/spring-petclinic

### Git Workflow ###
For every iteration:
1. Create a new short-lived feature branch named `feature/iteration-N` (where N is the iteration number)
2. Make code/test changes
3. Commit changes with a meaningful message
4. Create a pull request to main
5. Merge the pull request to main

### Testing Workflow ###
1. Read source files to understand the classes
2. Write JUnit 5 test cases targeting uncovered code
3. Run mvn test jacoco:report using the run_maven_tests tool
4. If tests fail, debug and fix them
5. Parse jacoco.xml using parse_jacoco_coverage tool
6. Identify uncovered methods and classes
7. Write additional tests targeting gaps
8. Repeat until coverage improves significantly
9. After each iteration summarize:
   - Current coverage percentage
   - What was added
   - What still needs coverage
