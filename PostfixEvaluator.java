package cse214hw2;
//must use Operator enum

import java.util.Stack;

public class PostfixEvaluator implements Evaluator{

    @Override
    public double evaluate(String expressionString) {

        Stack<Double> evaluator = new Stack<>();
        char current;
        double operand1, operand2;

        for (int index = 0; index < expressionString.length(); index++) {
            current = expressionString.charAt(index);
            Converter.TokenBuilder operand = new Converter.TokenBuilder();
            if (!Operator.isOperator(current) && current != ' ') {
                while (Character.isDigit(current) || current == '.') {
                    operand.append(current);
                    current = expressionString.charAt(++index);
                }
                evaluator.push(Double.parseDouble(operand.build()));
            } else if (Operator.isOperator(current)) {
                operand2 = evaluator.pop();
                operand1 = evaluator.pop();
                if (current == Operator.ADDITION.getSymbol())
                    evaluator.push(operand1 + operand2);
                else if (current == Operator.SUBTRACTION.getSymbol())
                    evaluator.push(operand1 - operand2);
                else if (current == Operator.MULTIPLICATION.getSymbol())
                    evaluator.push(operand1 * operand2);
                else if (current == Operator.DIVISION.getSymbol())
                    evaluator.push(operand1 / operand2);
            }
        }
        return evaluator.pop();
    }
}
