import java.util.Map;
class MathExpressions {
    public static int getValue(String[] parts, Map<String, Integer> variables) {
        String expression = parts[1].trim();
        return evaluateExpression(expression, variables);
    }

    private static int evaluateExpression(String expression, Map<String, Integer> variables) {
        // Support for parentheses
        if (expression.contains("(") && expression.contains(")")) {
            int start = expression.lastIndexOf("(");
            int end = expression.indexOf(")", start);
            String subExpr = expression.substring(start + 1, end);
            int result = evaluateExpression(subExpr, variables);
            String newExpr = expression.substring(0, start) + result + expression.substring(end + 1);
            return evaluateExpression(newExpr, variables);
        }

        // Support for basic operations
        if (expression.contains("+")) {
            String[] operands = expression.split("\\+");
            return getOperandValue(operands[0].trim(), variables) +
                    getOperandValue(operands[1].trim(), variables);
        } else if (expression.contains("-")) {
            String[] operands = expression.split("-");
            return getOperandValue(operands[0].trim(), variables) -
                    getOperandValue(operands[1].trim(), variables);
        } else if (expression.contains("*")) {
            String[] operands = expression.split("\\*");
            return getOperandValue(operands[0].trim(), variables) *
                    getOperandValue(operands[1].trim(), variables);
        } else if (expression.contains("/")) {
            String[] operands = expression.split("/");
            return getOperandValue(operands[0].trim(), variables) /
                    getOperandValue(operands[1].trim(), variables);
        } else if (expression.contains("%")) {
            String[] operands = expression.split("%");
            return getOperandValue(operands[0].trim(), variables) %
                    getOperandValue(operands[1].trim(), variables);
        }

        return getOperandValue(expression.trim(), variables);
    }

    static int getOperandValue(String operand, Map<String, Integer> variables) {
        if (variables.containsKey(operand)) {
            return variables.get(operand);
        }
        return Integer.parseInt(operand);
    }
}

