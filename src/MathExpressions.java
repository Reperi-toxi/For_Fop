public class MathExpressions {

    // Method to evaluate a mathematical expression
    private static int evaluateExpression(String expression) {
        int value = 0;
        if (expression.contains("+")) {
            String[] numbers = expression.split("\\+");
            value = Integer.parseInt(numbers[0].trim()) + Integer.parseInt(numbers[1].trim());
        } else if (expression.contains("-")) {
            String[] numbers = expression.split("-");
            value = Integer.parseInt(numbers[0].trim()) - Integer.parseInt(numbers[1].trim());
        } else if (expression.contains("*")) {
            String[] numbers = expression.split("\\*");
            value = Integer.parseInt(numbers[0].trim()) * Integer.parseInt(numbers[1].trim());
        } else if (expression.contains("/")) {
            String[] numbers = expression.split("/");
            value = Integer.parseInt(numbers[0].trim()) / Integer.parseInt(numbers[1].trim());
        } else if (expression.contains("%")) {
            String[] numbers = expression.split("%");
            value = Integer.parseInt(numbers[0].trim()) % Integer.parseInt(numbers[1].trim());
        }
        return value;
    }

    // Method to retrieve the value from parts
    public static int getValue(String[] parts) {
        String expression = parts[1].trim();
        return evaluateExpression(expression);
    }
}
