import java.util.Map;
public class WhileLoop {
    private final String condition;

    public WhileLoop(String condition) {
        this.condition = condition;
    }

    public boolean evaluateCondition(Map<String, Integer> variables) {
        return BooleanEvaluator.evaluate(condition, variables);
    }
}
