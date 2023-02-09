package cse214hw2;

import java.util.Stack;

public class ToPostfixConverter implements Converter {
    @Override
    public String convert(ArithmeticExpression expression) {
        Stack<Character> operatorStack = new Stack<>();
        String infix = expression.getExpression();
        StringBuilder postfix = new StringBuilder();
        char current;
        int index = 0;

        while (index < infix.length()) {
            TokenBuilder newToken = new TokenBuilder();
            current = infix.charAt(index++);
            while (!Operator.isOperator(current) && (current != Operator.LEFT_PARENTHESIS.getSymbol()) &&
                    (current != Operator.RIGHT_PARENTHESIS.getSymbol())) {
                newToken.append(current);
                if (index < infix.length())
                    current = infix.charAt(index++);
                else break;
            }
                postfix.append(newToken.build()).append(' ');

            if (Operator.LEFT_PARENTHESIS.getSymbol() == current)
                operatorStack.push(current);
            else if (Operator.RIGHT_PARENTHESIS.getSymbol() == current) {
                while (operatorStack.peek() != Operator.LEFT_PARENTHESIS.getSymbol())
                    postfix.append(operatorStack.pop()).append(' ');
                operatorStack.pop();
            } else if (Operator.isOperator(current)) {
                if (operatorStack.isEmpty() || operatorStack.peek() == Operator.LEFT_PARENTHESIS.getSymbol()) {
                    operatorStack.push(current);
                } else {
                    int currentRank = Operator.of(current).getRank();
                    int stackRank = Operator.of(operatorStack.peek()).getRank();

                    while (currentRank > stackRank) {
                        postfix.append(operatorStack.pop()).append(' ');
                        if (operatorStack.isEmpty())
                            break;
                        stackRank = Operator.of(operatorStack.peek()).getRank(); ;
                    }
                    if (stackRank == 0 || operatorStack.isEmpty()) operatorStack.push(current);
                    else if (currentRank < stackRank) { //create stack rank method -
                        operatorStack.push(current);
                    } else {
                        postfix.append(operatorStack.pop()).append(' ');
                        operatorStack.push(current);
                    }
                }
            }
        }
        while (!operatorStack.isEmpty())
            postfix.append(operatorStack.pop()).append(' ');

        return postfix.toString();
    }

    @Override
    public String nextToken(String s, int start) {
        TokenBuilder newToken = new TokenBuilder();
        int index = start;

        if (!isOperand(String.valueOf(s.charAt(start))))
            return String.valueOf(s.charAt(start));

        while (isOperand(String.valueOf(s.charAt(index)))) {
            char current = s.charAt(index++);
            newToken.append(current);
        }
        return newToken.build();
    }

    @Override
    public boolean isOperand(String s) {
        int decimal = 0;
        boolean flag = false;
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (ch == '.') {
                decimal++;
                if (decimal > 1)
                    throw new IllegalArgumentException("Invalid operand");
            }
            flag = Character.isDigit(ch) || ch == '.';
        }
        return flag;
    }
}
