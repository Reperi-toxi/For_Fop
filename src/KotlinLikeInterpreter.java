import java.util.*;

public class KotlinLikeInterpreter {
    private final Map<String, Integer> variables = new HashMap<>(); // Variable storage

    public void eval(String code) {
        // Remove extra newlines and trim code for Kotlin-like syntax
        String[] lines = code.split("\n"); // Split by line
        for (String line : lines) {
            line = line.trim();
            if (line.isEmpty()) continue;

            // Handle variable assignment
            if (line.contains("=")) {
                handleAssignment(line);
            }
            // Handle print statements
            else if (line.startsWith("println")) {
                handlePrint(line);
            }
        }
    }

    private void handleAssignment(String line) {
        // Check for Kotlin-like variable declaration (val/var)
        if (line.startsWith("val") || line.startsWith("var")) {
            line = line.substring(3).trim(); // Remove 'val' or 'var' part
        }

        String[] parts = line.split("=");
        String varName = parts[0].trim();
        String expression = parts[1].trim();
        String[] numbers = expression.split("\\+");
        int value = Integer.parseInt(numbers[0].trim()) + Integer.parseInt(numbers[1].trim());
        variables.put(varName, value);
    }

    private void handlePrint(String line) {
        String varName = line.substring(line.indexOf('(') + 1, line.indexOf(')')).trim();
        System.out.println(variables.get(varName));
    }

    public static void main(String[] args) {
        KotlinLikeInterpreter interpreter = new KotlinLikeInterpreter();

        // Example Kotlin-like program: Calculate and print the sum of 10 and 20
        String program = """
            val sum = 15 + 22
            println(sum)
        """;

        interpreter.eval(program);
    }
}
