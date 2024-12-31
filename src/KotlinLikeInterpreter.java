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

        String sumOf = """
            var number = 5
            var sum = 0
        
             while (number > 0) {
              sum = sum + number
              number = number - 1
             }
        
             println(sum)
        """;
        String sumOfDigits = """
            var num = 12345
            var sum = 0
        
            while (num > 0) {
                sum = sum + (num % 10)
                num = num / 10
            }
        
            println(sum)
        """;

        String factorialization = """
           var number1 = 5
           var factorial = 1
             while (number1 > 0) {
               factorial = number1 * factorial
               number1 = number1 - 1
             }
        
             println(factorial)
        """;
        String GCD = """
                  val num1 = 48
                  val num2 = 18
                  
                  var a = num1
                  var b = num2
                              
                  while (b > 0) {
                     val temp = b
                     b = a % b
                     a = temp
                    }
                
                        println(a)
                """;

        String reverseNumber = """          
                        var n = 1234
                        var reversed = 0
                
                        while (n > 0) {
                            val digit = n % 10
                            reversed = reversed * 10
                            reversed = reversed + digit
                            n = n / 10
                        }
                
                        println(reversed)
                """;

        interpreter.eval(sumOf);
        interpreter.eval(factorialization);
        interpreter.eval(sumOfDigits);
        interpreter.eval(GCD);
        interpreter.eval(reverseNumber);
    }
}


