import java.util.Map;
public class IfCondition {
    private final String condition;

    public IfCondition(String condition) {
        this.condition = condition;
    }

    public boolean evaluateCondition(Map<String, Integer> variables) {
        // Evaluate condition such as 'sum > 20'
        if (condition.contains(">")) {
            return evaluateComparisonCondition(variables, ">");
        }
        // Evaluate condition such as 'sum < 20'
        else if (condition.contains("<")) {
            return evaluateComparisonCondition(variables, "<");
        }
        // Evaluate condition such as 'sum == 20'
        else if (condition.contains("==")) {
            return evaluateComparisonCondition(variables, "==");
        }
        return false;
    }

    private boolean evaluateComparisonCondition(Map<String, Integer> variables, String operator) {
        String[] conditionParts = condition.split("\\s" +operator);
        if (conditionParts.length > 1) {
            String left = conditionParts[0].trim();
            String right = conditionParts[1].trim();

            int leftValue = variables.getOrDefault(left, 0);
            int rightValue = Integer.parseInt(right);

            return switch (operator) {
                case ">" -> leftValue > rightValue;
                case "<" -> leftValue < rightValue;
                case "==" -> leftValue == rightValue;
                default -> false;
            };
        }
        return false;
    }
}
