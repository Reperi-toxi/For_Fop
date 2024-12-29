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
        String left = conditionParts[0].trim();
        String right = conditionParts[1].trim();

        int leftValue = variables.containsKey(left) ? variables.get(left) : Integer.parseInt(left);
        int rightValue = variables.containsKey(right) ? variables.get(right) : Integer.parseInt(right);

        return switch (operator) {
            case ">" -> leftValue > rightValue;
            case "<" -> leftValue < rightValue;
            case "==" -> leftValue == rightValue;
            default -> false;
        };
    }
}
