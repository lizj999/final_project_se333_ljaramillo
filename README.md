# SE333 Final Project - AI-Powered Test Agent
**Student:** Liz Jaramillo  
**Course:** SE333 - Software Testing  
**Date:** March 2026

## Project Overview
This project implements an intelligent testing agent using the Model Context Protocol (MCP) that automatically generates, executes, and iterates on JUnit test cases to achieve maximum code coverage for the Spring Petclinic application.

**Note on IDE:**
This project uses Cursor IDE instead of VS Code. During environment setup, the `code` command was mapped to Cursor as the default editor. Since Cursor is built directly on VS Code and supports identical MCP configuration, it was used as a drop-in replacement. All VS Code instructions from the assignment apply directly to Cursor with no changes. Cursor also has native AI agent support which made MCP integration more seamless.

---

## Video Demonstration
[SE333 Final Project Presentation](https://depaul.hosted.panopto.com/Panopto/Pages/Viewer.aspx?id=9355a463-387d-4391-934c-b40b005f110c)

---

## MCP Tool / API Documentation

### MCP Server: `se333-mcp-server`
Built with FastMCP 3.1.0, running on `http://127.0.0.1:8000/sse`

#### Tools

**`add(a: int, b: int) -> int`**
- Description: Adds two numbers (used for initial MCP verification)
- Input: Two integers
- Output: Integer sum

**`run_maven_tests(project_path: str) -> str`**
- Description: Runs `mvn test` in the given project directory
- Input: Absolute path to Maven project
- Output: Full Maven stdout/stderr output

**`parse_jacoco_coverage(jacoco_xml_path: str) -> str`**
- Description: Parses JaCoCo XML report and returns per-class coverage
- Input: Absolute path to `jacoco.xml`
- Output: Coverage report with percentages and uncovered methods

**`read_file(file_path: str) -> str`**
- Description: Reads and returns file contents
- Input: Absolute file path
- Output: File contents as string

**`write_file(file_path: str, content: str) -> str`**
- Description: Writes content to a file
- Input: File path and content string
- Output: Success or error message

---

## Installation & Configuration Guide

### Prerequisites
- Python 3.13+
- Node.js 18+
- Java 11+
- Maven 3.6+
- Git
- Cursor (or VS Code)
- `uv` package manager

### Step 1: Clone the Repository
```bash
git clone https://github.com/lizj999/final_project_se333_ljaramillo.git
cd final_project_se333_ljaramillo
```

### Step 2: Set Up MCP Server
```bash
cd CodeBase/se333-mcp-server
uv venv
source .venv/bin/activate
uv add "mcp[cli]" httpx fastmcp
python server.py
```

### Step 3: Connect to Cursor
1. Open Cursor Settings → Tools & MCP
2. Click "Add Custom MCP"
3. Add the following to `mcp.json`:
```json
{
  "mcpServers": {
    "se333-mcp-server": {
      "url": "http://127.0.0.1:8000/sse",
      "type": "sse"
    },
    "github": {
      "command": "npx",
      "args": ["-y", "@modelcontextprotocol/server-github"],
      "env": {
        "GITHUB_PERSONAL_ACCESS_TOKEN": "YOUR_TOKEN_HERE"
      }
    }
  }
}
```

### Step 4: Run the Agent
1. Open `final_project_se333_ljaramillo` in Cursor
2. Open Cursor Chat (CMD+L)
3. Type: `#file:.github/prompts/tester.prompt.md`
4. Type: `Execute the tester prompt`

---

## Troubleshooting & FAQ

**Q: MCP server not connecting?**  
Make sure `python server.py` is running in a terminall before opening Cursor. The server must be running at `http://127.0.0.1:8000/sse`.

**Q: Maven build fails?**  
Run `mvn spring-javaformat:apply` first to fix formatting issues, then retry.

**Q: Agent not using tools?**  
Make sure Cursor is in Agent mode (not Ask mode) and tools are set to "Run Everything".

**Q: JaCoCo report not generated?**  
Run `mvn test jacoco:report` manually to generate the report at `target/site/jacoco/jacoco.xml`.

---

## Reflection Report

### Introduction
This project explores AI-assisted software testing through the Model Context Protocol (MCP). The goal was to build an intelligent agent that could automatically generate JUnit tests, execute them, analyze coverage gaps, and iteratively improve test coverage for the Spring Petclinic application — a real-world Java Spring Boot project.

### Methodology
The project was implemented in 5 phases:

1. **Environment Setup**: Configured FastMCP server with 5 custom tools (run_maven_tests, parse_jacoco_coverage, read_file, write_file, add)
2. **Agent Configuration**: Created a prompt-chained agent following the iterative loop from the course lecture: generate → run → parse → improve → repeat
3. **Iterative Testing**: Applied the agent across 5 iterations targeting different packages
4. **Git Automation**: Integrated GitHub MCP server for trunk-based workflow with feature branches and pull requests
5. **Coverage Analysis**: Used JaCoCo XML parsing to guide each iteration

### Results & Discussion

#### Coverage Improvement
| Iteration | Target | Coverage Achieved |
|-----------|--------|-------------------|
| Baseline | None | 0% |
| Iteration 1 | model package | 100% (BaseEntity, NamedEntity, Person) |
| Iteration 2 | vet package | 100% (Vet, Vets, Specialty) |
| Iteration 3 | owner package | 93.2% - 100% (Pet, PetType, Visit, PetValidator, Owner) |
| Iteration 4 | owner gap fix | Owner 100% (getAddress, getCity, getTelephone) |
| Iteration 5 | system package | WelcomeController/CrashController 100%, WebConfiguration 77.8%, CacheConfiguration 66.7% |

#### Insights from AI-Assisted Development
The most impressive aspect was the agent's ability to self-debug. When tests failed due to formatting violations or incorrect assertions, the agent automatically ran `mvn spring-javaformat:apply`, identified the root cause, and fixed the tests without help. This demonstrates the power of prompt chaining as described in our course lecture.

The JaCoCo XML parser MCP tool proved essential — by giving the agent structured coverage feedback, it could make targeted decisions about which methods to test next rather than generating random tests.

#### Recommendations for Future Enhancements
- Add integration test support using Spring's `@SpringBootTest`
- Implement mutation testing with PIT to verify test quality, not just coverage
- Add automatic PR merging with CI checks enforced before merge
- Extend to support Python projects using pytest and coverage.py

### Conclusion
AI-assisted testing through MCP significantly accelerates the test generation process. The iterative feedback loop generates, runs, analyzes, improves, and mirrors how an expert developer would approacch testing, but at machine speed. Human oversight was still needed for architectural decisions and handling Spring context complexities.
