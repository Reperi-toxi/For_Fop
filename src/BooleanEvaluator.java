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

        // Need to check for compound operators first
        String[] parts;
        if (condition.contains(">=")) {
            parts = splitCondition(condition, ">=");
            return compareValues(parts, variables, ">=");
        }
        if (condition.contains("<=")) {
            parts = splitCondition(condition, "<=");
            return compareValues(parts, variables, "<=");
        }
        if (condition.contains("==")) {
            parts = splitCondition(condition, "==");
            return compareValues(parts, variables, "==");
        }
        if (condition.contains("!=")) {
            parts = splitCondition(condition, "!=");
            return compareValues(parts, variables, "!=");
        }
        if (condition.contains(">")) {
            parts = splitCondition(condition, ">");
            return compareValues(parts, variables, ">");
        }
        if (condition.contains("<")) {
            parts = splitCondition(condition, "<");
            return compareValues(parts, variables, "<");
        }
        return false;
    }

    private static String[] splitCondition(String condition, String operator) {
        // Use indexOf instead of split to handle the operator correctly
        int index = condition.indexOf(operator);
        if (index == -1) return new String[0];

        String left = condition.substring(0, index).trim();
        String right = condition.substring(index + operator.length()).trim();

        return new String[]{left, right};
    }

    private static boolean compareValues(String[] parts, Map<String, Integer> variables, String operator) {
        if (parts.length != 2) return false;

        int leftValue = MathExpressions.getOperandValue(parts[0].trim(), variables);
        int rightValue = MathExpressions.getOperandValue(parts[1].trim(), variables);

        return switch (operator) {
            case "==" -> leftValue == rightValue;
            case "!=" -> leftValue != rightValue;
            case "<=" -> leftValue <= rightValue;
            case ">=" -> leftValue >= rightValue;
            case ">" -> leftValue > rightValue;
            case "<" -> leftValue < rightValue;
            default -> false;
        };
    }

}

