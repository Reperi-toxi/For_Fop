import java.util.Map;
public class IfCondition {
    private final String condition;

    public IfCondition(String condition) {
        this.condition = condition;
    }

    public boolean evaluateCondition(Map<String, Integer> variables) {
        return BooleanEvaluator.evaluate(condition, variables);
    }
}
