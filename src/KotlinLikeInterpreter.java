import java.util.*;
public class KotlinInterpreter {
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
        if (line.startsWith("val") || line.startsWith("var")) {
            line = line.substring(3).trim(); // Remove 'val' or 'var' part
        }

        String[] parts = line.split("=");
        String varName = parts[0].trim();
        int value = getValue(parts);
        // More operators can be added here

        variables.put(varName, value);
    }

    private static int getValue(String[] parts) {
        String expression = parts[1].trim();

        // Handling addition or subtraction
        int value = 0;
        if (expression.contains("+")) {
            String[] numbers = expression.split("\\+");
            value = Integer.parseInt(numbers[0].trim()) + Integer.parseInt(numbers[1].trim());
        } else if (expression.contains("-")) {
            String[] numbers = expression.split("-");
            value = Integer.parseInt(numbers[0].trim()) - Integer.parseInt(numbers[1].trim());
        } else if (expression.contains("*")) {
            String[] numbers = expression.split("\\*");
            value = Integer.parseInt(numbers[0].trim()) * Integer.parseInt(numbers[1].trim());
        } else if (expression.contains("/")) {
            String[] numbers = expression.split("/");
            value = Integer.parseInt(numbers[0].trim()) / Integer.parseInt(numbers[1].trim());
        }
        return value;
    }


    private void handlePrint(String line) {
        String varName = line.substring(line.indexOf('(') + 1, line.indexOf(')')).trim();
        System.out.println(variables.get(varName));
    }

    public static void main(String[] args) {
        KotlinInterpreter interpreter = new KotlinInterpreter();

        // Example Kotlin-like program: Calculate and print the sum of 10 and 20
        String program = """
            val sum = 15 + 45
            val sub = 45 - 15
            val multi = 6*6
            val divs = 45/15
            println(sum)
            println(sub)
            println(multi)
            println(divs)
        """;

        interpreter.eval(program);
    }
}


