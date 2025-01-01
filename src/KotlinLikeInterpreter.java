import java.util.*;

public class KotlinInterpreter {
    private final Map<String, Integer> variables = new HashMap<>();

    public void eval(String code) {
        List<String> lines = Arrays.asList(code.split("\n"));
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i).trim();
            if (line.isEmpty()) continue;

            // Check for if statement BEFORE checking for =
            if (line.startsWith("if")) {
                i = handleIf(lines, i);
            }
            else if (line.startsWith("while")) {
                i = handleWhile(lines, i);
            }
            else if (line.contains("=")) {
                handleAssignment(line);
            }
            else if (line.startsWith("println")) {
                handlePrint(line);
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

        String primeChecker = """
        val number = 13
        var isPrime = 1
        var i = 2
        var trueInstance = 1
        while (i < number) {
        val temp = number % i
        isPrime = isPrime * temp
        i = i+1
        }
        if(isPrime > 0) {
        println(trueInstance)
        }
        if(isPrime < 1) {
        println(isPrime)
        }                         // '1' for true, '0' for false
        
        
        """;
String largestDigit = """
val number = 957432
var largestDigit = 0
var tempNumber = number

while (tempNumber > 0) {
    val digit = tempNumber % 10
    if (digit > largestDigit) {
        largestDigit = digit
    }
    tempNumber = tempNumber / 10
}
println(largestDigit)
""";
String palindromeChecker = """
val number = 72627
var tempNumber = number
var reversedNumber = 0
var originalNumber = number
var isPalindrome = 1


while (tempNumber > 0) {
    val digit = tempNumber % 10
    reversedNumber = (reversedNumber * 10) + digit
    tempNumber = tempNumber / 10
}

// if the number is a palindrome the output will be '1'
if (originalNumber == reversedNumber) {
    println(isPalindrome)
}
""";

String multiTable = """
val number = 5
var i = 1

while (i <= 10) {
    val result = number * i
    println(result)
    i = i + 1
}
""";
String nthFibonacci = """
        val n = 11
        var a = 0
        var b = 1
        var i = 2
        while(i <= n) {
          var temp = a + b
          a = b
          b = temp
          i = i + 1
       }
       println(a)
       """;


        interpreter.eval(sumOf);
        interpreter.eval(factorialization);
        interpreter.eval(sumOfDigits);
        interpreter.eval(GCD);
        interpreter.eval(reverseNumber);
        interpreter.eval(primeChecker);
        interpreter.eval(largestDigit);
        interpreter.eval(palindromeChecker);
        interpreter.eval(multiTable);
        interpreter.eval(nthFibonacci);


    }
}



