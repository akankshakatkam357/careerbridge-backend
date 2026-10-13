package com.example.academiaindustryportal.config;

import com.example.academiaindustryportal.entity.AssessmentQuestion;
import com.example.academiaindustryportal.repository.AssessmentQuestionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AssessmentQuestionSeeder implements CommandLineRunner {
    private final AssessmentQuestionRepository repository;
    public AssessmentQuestionSeeder(AssessmentQuestionRepository repository) { this.repository = repository; }
   @Override
public void run(String... args) {
    seedJava();
    seedReact();
    seedSql();
    seedPython();
    seedDataVisualization();
}
    private void seedJava() {
        if (repository.existsBySkillIgnoreCase("Java")) return;
        repository.save(new AssessmentQuestion("Java", "Which keyword is used to inherit a class in Java?", "implements", "extends", "inherits", "super", "B", "Beginner"));
        repository.save(new AssessmentQuestion("Java", "Which collection does not allow duplicate elements?", "ArrayList", "LinkedList", "HashSet", "Vector", "C", "Beginner"));
        repository.save(new AssessmentQuestion("Java", "Which is the correct method signature for the Java program entry point?", "public void main(String[] args)", "static public start(String[] args)", "public static void main(String[] args)", "private static void main(String args)", "C", "Beginner"));
        repository.save(new AssessmentQuestion("Java", "What does JVM stand for?", "Java Variable Method", "Java Virtual Machine", "Joint Virtual Memory", "Java Verified Module", "B", "Beginner"));
        repository.save(new AssessmentQuestion("Java", "Which keyword prevents a method from being overridden?", "static", "const", "final", "private", "C", "Intermediate"));
    }
    private void seedReact() {
        if (repository.existsBySkillIgnoreCase("React")) return;
        repository.save(new AssessmentQuestion("React", "Which hook is commonly used to manage local component state?", "useState", "useRoute", "useClass", "useFetchState", "A", "Beginner"));
        repository.save(new AssessmentQuestion("React", "What prop is used to help React identify list items?", "idOnly", "key", "indexKeyOnly", "ref", "B", "Beginner"));
        repository.save(new AssessmentQuestion("React", "What is a React component?", "A database table", "A reusable UI building block", "A CSS selector only", "A server port", "B", "Beginner"));
        repository.save(new AssessmentQuestion("React", "Which hook can run an effect after rendering?", "useMemo", "useEffect", "useKey", "useRenderNow", "B", "Beginner"));
        repository.save(new AssessmentQuestion("React", "Props are generally used to:", "Pass data from parent to child", "Replace the DOM", "Connect directly to MySQL", "Store server passwords", "A", "Intermediate"));
    }
    private void seedSql() {
        if (repository.existsBySkillIgnoreCase("SQL")) return;
        repository.save(new AssessmentQuestion("SQL", "Which SQL command retrieves rows from a table?", "GET", "OPEN", "SELECT", "FETCH TABLE", "C", "Beginner"));
        repository.save(new AssessmentQuestion("SQL", "Which clause filters rows before grouping?", "ORDER BY", "WHERE", "HAVING", "GROUP BY", "B", "Beginner"));
        repository.save(new AssessmentQuestion("SQL", "Which key uniquely identifies each row in a table?", "Foreign key", "Alternate label", "Primary key", "Duplicate key", "C", "Beginner"));
        repository.save(new AssessmentQuestion("SQL", "Which join returns matching rows from both tables?", "INNER JOIN", "CROSS JOIN", "FULL CARTESIAN", "UNION ALL", "A", "Intermediate"));
        repository.save(new AssessmentQuestion("SQL", "Which function counts rows in a result set?", "SUM(*)", "TOTAL()", "COUNT(*)", "NUMBER(*)", "C", "Beginner"));
    }
    
private void seedPython() {
    if (repository.existsBySkillIgnoreCase("Python")) return;

    repository.save(new AssessmentQuestion("Python",
        "Which keyword is used to define a function in Python?",
        "func", "def", "function", "define", "B", "Beginner"));

    repository.save(new AssessmentQuestion("Python",
        "Which data type stores True or False?",
        "int", "str", "bool", "list", "C", "Beginner"));

    repository.save(new AssessmentQuestion("Python",
        "What is the output of len([10, 20, 30])?",
        "2", "3", "4", "10", "B", "Beginner"));

    repository.save(new AssessmentQuestion("Python",
        "Which symbol starts a single-line comment in Python?",
        "//", "<!-- -->", "#", "/* */", "C", "Beginner"));

    repository.save(new AssessmentQuestion("Python",
        "Which collection stores key-value pairs?",
        "List", "Tuple", "Set", "Dictionary", "D", "Beginner"));

    repository.save(new AssessmentQuestion("Python",
        "What does range(3) produce when iterated?",
        "1, 2, 3", "0, 1, 2", "0, 1, 2, 3", "3, 2, 1", "B", "Beginner"));

    repository.save(new AssessmentQuestion("Python",
        "Which list method adds an item to the end?",
        "add()", "append()", "push()", "attach()", "B", "Beginner"));

    repository.save(new AssessmentQuestion("Python",
        "Which keyword handles an exception?",
        "catch", "error", "except", "final", "C", "Intermediate"));

    repository.save(new AssessmentQuestion("Python",
        "What is the result of 7 // 2?",
        "3", "3.5", "4", "1", "A", "Intermediate"));

    repository.save(new AssessmentQuestion("Python",
        "Which statement imports pandas using its common alias?",
        "import pandas as pd", "include pandas pd",
        "using pandas", "import pd from pandas", "A", "Intermediate"));
}

private void seedDataVisualization() {
    if (repository.existsBySkillIgnoreCase("Data Visualization")) return;

    repository.save(new AssessmentQuestion("Data Visualization",
        "Which chart compares values across categories?",
        "Bar chart", "Scatter plot", "Line chart", "Map only", "A", "Beginner"));

    repository.save(new AssessmentQuestion("Data Visualization",
        "Which chart is commonly used to show trends over time?",
        "Pie chart", "Line chart", "Box plot", "Treemap", "B", "Beginner"));

    repository.save(new AssessmentQuestion("Data Visualization",
        "Which chart shows the relationship between two numerical variables?",
        "Pie chart", "Bar chart", "Scatter plot", "Table only", "C", "Beginner"));

    repository.save(new AssessmentQuestion("Data Visualization",
        "What does a histogram show?",
        "Exact text values", "Frequency distribution of numerical data",
        "Database relationships", "Website navigation", "B", "Beginner"));

    repository.save(new AssessmentQuestion("Data Visualization",
        "Why should a chart have a clear title?",
        "To add data points", "To replace axis labels",
        "To explain what the chart represents", "To change values", "C", "Beginner"));

    repository.save(new AssessmentQuestion("Data Visualization",
        "Which Python library is widely used for plotting charts?",
        "Matplotlib", "Requests", "BeautifulSoup", "Flask only", "A", "Beginner"));

    repository.save(new AssessmentQuestion("Data Visualization",
        "What is the purpose of a chart legend?",
        "Identify data series or categories", "Remove outliers",
        "Calculate averages", "Sort a database", "A", "Beginner"));

    repository.save(new AssessmentQuestion("Data Visualization",
        "Which chart helps identify outliers and show quartiles?",
        "Pie chart", "Box plot", "Area chart only", "Donut chart", "B", "Intermediate"));

    repository.save(new AssessmentQuestion("Data Visualization",
        "What can a misleading axis scale do?",
        "Guarantee accurate conclusions", "Remove missing values",
        "Distort how differences are perceived", "Clean data automatically", "C", "Intermediate"));

    repository.save(new AssessmentQuestion("Data Visualization",
        "Which chart is useful for showing the distribution of a numerical variable?",
        "Histogram", "Network diagram", "Flowchart", "Gantt chart", "A", "Intermediate"));
}

}
