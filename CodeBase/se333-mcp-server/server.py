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
            pkg_name = package.get('name', '')
            for cls in package.findall('class'):
                class_name = cls.get('name', '')
                missed_lines = 0
                covered_lines = 0
                missed_branches = 0
                covered_branches = 0
                uncovered_methods = []
                
                for method in cls.findall('method'):
                    method_name = method.get('name', '')
                    for counter in method.findall('counter'):
                        if counter.get('type') == 'LINE':
                            m = int(counter.get('missed', 0))
                            c = int(counter.get('covered', 0))
                            if m > 0:
                                uncovered_methods.append(f"{method_name} (missed {m} lines)")
                
                for counter in cls.findall('counter'):
                    if counter.get('type') == 'LINE':
                        missed_lines = int(counter.get('missed', 0))
                        covered_lines = int(counter.get('covered', 0))
                    elif counter.get('type') == 'BRANCH':
                        missed_branches = int(counter.get('missed', 0))
                        covered_branches = int(counter.get('covered', 0))
                
                total_lines = missed_lines + covered_lines
                if total_lines > 0:
                    pct = round((covered_lines / total_lines) * 100, 1)
                    report.append(f"\nClass: {class_name}")
                    report.append(f"  Line Coverage: {pct}% ({covered_lines}/{total_lines})")
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

if __name__ == "__main__":
    mcp.run(transport="sse")
