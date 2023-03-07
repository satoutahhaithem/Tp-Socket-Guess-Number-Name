import java.util.ArrayList;
import java.util.List;

public class Calculator {
    public double calculate(String input) {
        String[] tokens = input.split("\\s+");
        List<Double> operands = new ArrayList<>();
        List<Character> operators = new ArrayList<>();

        for (int i = 0; i < tokens.length; i++) {
            if (i % 2 == 0) {
                operands.add(Double.parseDouble(tokens[i]));
            } else {
                operators.add(tokens[i].charAt(0));
            }
        }

        double result = operands.get(0);
        for (int i = 0; i < operators.size(); i++) {
            char operator = operators.get(i);
            double operand = operands.get(i + 1);

            switch (operator) {
                case '+':
                    result += operand;
                    break;
                case '-':
                    result -= operand;
                    break;
                case '*':
                    result *= operand;
                    break;
                case '/':
                    if (operand == 0) {
                        throw new IllegalArgumentException("Division by zero.");
                    }
                    result /= operand;
                    break;
                default:
                    throw new IllegalArgumentException("Invalid operator.");
            }
        }

        return result;
    }
}
