import java.util.Map;
public class MathExpressions {
    // Method to evaluate a mathematical expression
    private static int evaluateExpression(String expression, Map<String, Integer> variables) {
        int value = 0;
        if (expression.contains("+")) {
            String[] operands = expression.split("\\+");
            value = getOperandValue(operands[0].trim(), variables) + getOperandValue(operands[1].trim(), variables);
        } else if (expression.contains("-")) {
            String[] operands = expression.split("-");
            value = getOperandValue(operands[0].trim(), variables) - getOperandValue(operands[1].trim(), variables);
        } else if (expression.contains("*")) {
            String[] operands = expression.split("\\*");
            value = getOperandValue(operands[0].trim(), variables) * getOperandValue(operands[1].trim(), variables);
        } else if (expression.contains("/")) {
            String[] operands = expression.split("/");
            value = getOperandValue(operands[0].trim(), variables) / getOperandValue(operands[1].trim(), variables);
        } else if (expression.contains("%")) {
            String[] operands = expression.split("%");
            value = getOperandValue(operands[0].trim(), variables) % getOperandValue(operands[1].trim(), variables);
        } else {
            // Handle single value (might be a variable or number)
            value = getOperandValue(expression.trim(), variables);
        }
        return value;
    }

    // Helper method to get value of an operand (either variable or number)
    private static int getOperandValue(String operand, Map<String, Integer> variables) {
        // If it's in our variables map, return the variable's value
        if (variables.containsKey(operand)) {
            return variables.get(operand);
        }
        // Otherwise, try to parse it as a number
        return Integer.parseInt(operand);
    }

    // Method to retrieve the value from parts
    public static int getValue(String[] parts, Map<String, Integer> variables) {
        String expression = parts[1].trim();
        return evaluateExpression(expression, variables);
    }
}
