import java.util.Map;

public class WhileLoop {
    private final String condition;

    public WhileLoop(String condition) {
        this.condition = condition;
    }

    public boolean evaluateCondition(Map<String, Integer> variables) {
        if (condition.contains(">")) {
            return evaluateComparisonCondition(variables, ">");
        } else if (condition.contains("<")) {
            return evaluateComparisonCondition(variables, "<");
        } else if (condition.contains("==")) {
            return evaluateComparisonCondition(variables, "==");
        }
        return false;
    }

    private boolean evaluateComparisonCondition(Map<String, Integer> variables, String operator) {
        String[] conditionParts = condition.split("\\s*" + operator + "\\s*");
        if (conditionParts.length != 2) {
            throw new IllegalArgumentException("Invalid condition format");
        }

        String left = conditionParts[0].trim();
        String right = conditionParts[1].trim();

        // Get the value for left operand
        int leftValue;
        try {
            leftValue = variables.containsKey(left) ? variables.get(left) : Integer.parseInt(left);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid left operand: " + left);
        }

        // Get the value for right operand
        int rightValue;
        try {
            rightValue = variables.containsKey(right) ? variables.get(right) : Integer.parseInt(right);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid right operand: " + right);
        }

        return switch (operator) {
            case ">" -> leftValue > rightValue;
            case "<" -> leftValue < rightValue;
            case "==" -> leftValue == rightValue;
            default -> false;
        };
    }
}

