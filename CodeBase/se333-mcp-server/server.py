from fastmcp import FastMCP
import xml.etree.ElementTree as ET
import subprocess
import os

mcp = FastMCP("se333-mcp-server 🚀")

@mcp.tool
def add(a: int, b: int) -> int:
    """Add two numbers"""
    return a + b

@mcp.tool
def run_maven_tests(project_path: str) -> str:
    """Run mvn test in the given project directory and return output"""
    try:
        result = subprocess.run(
            ["mvn", "test"],
            cwd=project_path,
            capture_output=True,
            text=True,
            timeout=300
        )
        return result.stdout + result.stderr
    except Exception as e:
        return f"Error running tests: {str(e)}"

@mcp.tool
def parse_jacoco_coverage(jacoco_xml_path: str) -> str:
    """Parse JaCoCo XML report and return coverage summary with gaps"""
    try:
        tree = ET.parse(jacoco_xml_path)
        root = tree.getroot()
        report = []
        report.append("=== JACOCO COVERAGE REPORT ===\n")
        for package in root.findall('.//package'):
            for cls in package.findall('class'):
                class_name = cls.get('name', '')
                missed_lines = 0
                covered_lines = 0
                missed_branches = 0
                uncovered_methods = []
                for method in cls.findall('method'):
                    method_name = method.get('name', '')
                    for counter in method.findall('counter'):
                        if counter.get('type') == 'LINE':
                            m = int(counter.get('missed', 0))
                            if m > 0:
                                uncovered_methods.append(f"{method_name} (missed {m} lines)")
                for counter in cls.findall('counter'):
                    if counter.get('type') == 'LINE':
                        missed_lines = int(counter.get('missed', 0))
                        covered_lines = int(counter.get('covered', 0))
                    elif counter.get('type') == 'BRANCH':
                        missed_branches = int(counter.get('missed', 0))
                total = missed_lines + covered_lines
                if total > 0:
                    pct = round((covered_lines / total) * 100, 1)
                    report.append(f"\nClass: {class_name}")
                    report.append(f"  Line Coverage: {pct}% ({covered_lines}/{total})")
                    if missed_branches > 0:
                        report.append(f"  Missed Branches: {missed_branches}")
                    if uncovered_methods:
                        report.append(f"  Uncovered Methods: {', '.join(uncovered_methods)}")
        return '\n'.join(report)
    except Exception as e:
        return f"Error parsing JaCoCo report: {str(e)}"

@mcp.tool
def read_file(file_path: str) -> str:
    """Read and return the contents of a file"""
    try:
        with open(file_path, 'r') as f:
            return f.read()
    except Exception as e:
        return f"Error reading file: {str(e)}"

@mcp.tool
def write_file(file_path: str, content: str) -> str:
    """Write content to a file"""
    try:
        os.makedirs(os.path.dirname(file_path), exist_ok=True)
        with open(file_path, 'w') as f:
            f.write(content)
        return f"Successfully wrote to {file_path}"
    except Exception as e:
        return f"Error writing file: {str(e)}"

@mcp.tool
def generate_coverage_summary(jacoco_xml_path: str, iteration: int) -> str:
    """Generate a markdown coverage summary with overall percentage and recommendations for next iteration"""
    try:
        tree = ET.parse(jacoco_xml_path)
        root = tree.getroot()
        fully_covered = []
        needs_work = []
        total_covered = 0
        total_lines = 0
        for package in root.findall('.//package'):
            for cls in package.findall('class'):
                class_name = cls.get('name', '').split('/')[-1]
                missed = 0
                covered = 0
                for counter in cls.findall('counter'):
                    if counter.get('type') == 'LINE':
                        missed = int(counter.get('missed', 0))
                        covered = int(counter.get('covered', 0))
                total = missed + covered
                if total == 0:
                    continue
                pct = round((covered / total) * 100, 1)
                total_covered += covered
                total_lines += total
                if pct == 100.0:
                    fully_covered.append(class_name)
                else:
                    needs_work.append((class_name, pct, missed))
        overall = round((total_covered / total_lines) * 100, 1) if total_lines > 0 else 0.0
        needs_work.sort(key=lambda x: x[1])
        lines = []
        lines.append(f"# Coverage Summary - Iteration {iteration}")
        lines.append(f"\n## Overall Coverage: {overall}% ({total_covered}/{total_lines} lines)\n")
        lines.append("## Fully Covered Classes (100%)")
        for c in fully_covered:
            lines.append(f"  - {c}")
        lines.append("\n## Classes Needing More Tests")
        for name, pct, missed in needs_work:
            lines.append(f"  - {name}: {pct}% ({missed} lines uncovered)")
        lines.append("\n## Recommendations for Next Iteration")
        if needs_work:
            for name, pct, missed in needs_work[:3]:
                lines.append(f"  - {name} ({missed} uncovered lines) - priority target")
        else:
            lines.append("  - All classes at 100%! Consider adding branch/integration tests.")
        return '\n'.join(lines)
    except Exception as e:
        return f"Error generating summary: {str(e)}"

if __name__ == "__main__":
    mcp.run(transport="sse")
