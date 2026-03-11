---
mode: agent
tools:
  - se333-mcp-server/run_maven_tests
  - se333-mcp-server/parse_jacoco_coverage
  - se333-mcp-server/read_file
  - se333-mcp-server/write_file
description: You are an expert software tester. Your task is to generate comprehensive JUnit test cases that maximize code coverage for the Spring Petclinic project.
---

## Follow instructions below ##

The project to test is located at: /Users/lizjaramillo/final_project_se333_ljaramillo/projectAnalyzed/spring-petclinic

1. Read the source files in src/main/java/org/springframework/samples/petclinic/model/ to understand the classes.

2. Write JUnit 5 test cases for the model classes (BaseEntity, NamedEntity, Person) and save them to:
src/test/java/org/springframework/samples/petclinic/model/

3. Run mvn test using the run_maven_tests tool with the project path.

4. If tests fail, read the error, fix the test file and run again.

5. After tests pass, find and parse the JaCoCo XML report at:
target/site/jacoco/jacoco.xml

6. Use the coverage report to identify uncovered methods and classes.

7. Write additional tests targeting the uncovered code.

8. Repeat steps 3-7 until you have improved coverage significantly.

9. After each successful iteration, summarize:
   - Current coverage percentage
   - What was added
   - What still needs coverage
