import java.util.Map;
public class BooleanEvaluator {
    public static boolean evaluate(String condition, Map<String, Integer> variables) {
        if (condition.contains("&&")) {
            String[] conditions = condition.split("&&");
            return evaluateSimpleCondition(conditions[0].trim(), variables) &&
                    evaluateSimpleCondition(conditions[1].trim(), variables);
        } else if (condition.contains("||")) {
            String[] conditions = condition.split("\\|\\|");
            return evaluateSimpleCondition(conditions[0].trim(), variables) ||
                    evaluateSimpleCondition(conditions[1].trim(), variables);
        }
        return evaluateSimpleCondition(condition, variables);
    }

    private static boolean evaluateSimpleCondition(String condition, Map<String, Integer> variables) {
        if (condition.contains(">=")) return evaluateComparison(condition, variables, ">=");
        if (condition.contains("<=")) return evaluateComparison(condition, variables, "<=");
        if (condition.contains(">")) return evaluateComparison(condition, variables, ">");
        if (condition.contains("<")) return evaluateComparison(condition, variables, "<");
        if (condition.contains("==")) return evaluateComparison(condition, variables, "==");
        if (condition.contains("!=")) return evaluateComparison(condition, variables, "!=");
        return false;
    }

    private static boolean evaluateComparison(String condition, Map<String, Integer> variables, String operator) {
        String[] parts = condition.split("\\s*" + operator + "\\s*");
        if (parts.length != 2) return false;

        int leftValue = MathExpressions.getOperandValue(parts[0].trim(), variables);
        int rightValue = MathExpressions.getOperandValue(parts[1].trim(), variables);

        return switch (operator) {
            case ">=" -> leftValue >= rightValue;
            case "<=" -> leftValue <= rightValue;
            case ">" -> leftValue > rightValue;
            case "<" -> leftValue < rightValue;
            case "==" -> leftValue == rightValue;
            case "!=" -> leftValue != rightValue;
            default -> false;
        };
    }
}
