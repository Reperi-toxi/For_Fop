import java.util.*;

public class KotlinInterpreter {
    private final Map<String, Integer> variables = new HashMap<>();

    public void eval(String code) {
        List<String> lines = Arrays.asList(code.split("\n"));
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i).trim();
            if (line.isEmpty()) continue;

            if (line.contains("=")) {
                handleAssignment(line);
            }
            else if (line.startsWith("println")) {
                handlePrint(line);
            }
            else if (line.startsWith("if")) {
                i = handleIf(lines, i); // Changed to return new index after processing block
            } else if (line.startsWith("while")) {
                i = handleWhile(lines, i);
            }
        }
    }

    private void handleAssignment(String line) {
        if (line.startsWith("val") || line.startsWith("var")) {
            line = line.substring(3).trim();
        }

        String[] parts = line.split("=");
        String varName = parts[0].trim();
        int value = MathExpressions.getValue(parts, variables);

        variables.put(varName, value);
    }

    private void handlePrint(String line) {
        String varName = line.substring(line.indexOf('(') + 1, line.indexOf(')')).trim();
        System.out.println(variables.get(varName));
    }

    private int handleIf(List<String> lines, int currentIndex) {
        String line = lines.get(currentIndex);
        String condition = line.substring(line.indexOf('(') + 1, line.indexOf(')')).trim();

        IfCondition ifCondition = new IfCondition(condition);

        // block boundaries
        int openBraceIndex = currentIndex;
        while (!lines.get(openBraceIndex).contains("{")) {
            openBraceIndex++;
        }

        int closeBraceIndex = openBraceIndex;
        int braceCount = 1;

        while (braceCount > 0 && closeBraceIndex < lines.size() - 1) {
            closeBraceIndex++;
            String currentLine = lines.get(closeBraceIndex).trim();
            if (currentLine.contains("{")) braceCount++;
            if (currentLine.contains("}")) braceCount--;
        }

        // If condition is true, execute the block
        if (ifCondition.evaluateCondition(variables)) {
            for (int i = openBraceIndex + 1; i < closeBraceIndex; i++) {
                String blockLine = lines.get(i).trim();
                if (!blockLine.isEmpty() && !blockLine.equals("{") && !blockLine.equals("}")) {
                    if (blockLine.contains("=")) {
                        handleAssignment(blockLine);
                    }
                    else if (blockLine.startsWith("println")) {
                        handlePrint(blockLine);
                    }
                }
            }
        }

        return closeBraceIndex; // Return the index after the block
    }
    private int handleWhile(List<String> lines, int currentIndex) {
        String line = lines.get(currentIndex);
        String condition = line.substring(line.indexOf('(') + 1, line.indexOf(')')).trim();
        WhileLoop whileLoop = new WhileLoop(condition);

        int openBraceIndex = currentIndex;
        while (!lines.get(openBraceIndex).contains("{")) {
            openBraceIndex++;
        }

        int closeBraceIndex = openBraceIndex;
        int braceCount = 1;

        while (braceCount > 0 && closeBraceIndex < lines.size() - 1) {
            closeBraceIndex++;
            String currentLine = lines.get(closeBraceIndex).trim();
            if (currentLine.contains("{")) braceCount++;
            if (currentLine.contains("}")) braceCount--;
        }

        while (whileLoop.evaluateCondition(variables)) {
            for (int i = openBraceIndex + 1; i < closeBraceIndex; i++) {
                String blockLine = lines.get(i).trim();
                if (!blockLine.isEmpty() && !blockLine.equals("{") && !blockLine.equals("}")) {
                    if (blockLine.contains("=")) {
                        handleAssignment(blockLine);
                    }
                    else if (blockLine.startsWith("println")) {
                        handlePrint(blockLine);
                    }
                }
            }
        }

        return closeBraceIndex;
    }


    public static void main(String[] args) {
        KotlinInterpreter interpreter = new KotlinInterpreter();

        String program = """
            val sub = 45 - 15
            var modulo = 40 % 7
            val multi = 7*8
            val divs = 45/15
            var sum = 45 + 15
            println(sub)
            if (modulo > 3) {
                println(modulo)
            }
            if (divs > 5) {
                println(divs)  //this will not execute, doesn't meet the condition
            }
            while(multi < sum) {  //it handles variables on both sides now
                multi = multi + 3
                println(multi)
            }
        """;

        interpreter.eval(program);
    }
}


