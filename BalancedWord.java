package cse214hw2;

public class BalancedWord {
    private final String word;
    public BalancedWord(String word) {
        if (isBalanced(word))
            this.word = word;
        else
            throw new IllegalArgumentException(String.format("%s is not a balanced word.", word));
    }

    // can i use () or do i need to use Operator enum
    private static boolean isBalanced(String word) {
        int leftPar = 0, rightPar = 0;

        //using Operator enum
        for (int i = 0; i < word.length(); i++) {
            if (Operator.LEFT_PARENTHESIS.getSymbol() == word.charAt(i)) {
                leftPar++;
            } else if (Operator.RIGHT_PARENTHESIS.getSymbol() == word.charAt(i)) {
                rightPar++;
            }
        }

        // using actual char
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == '(') { leftPar++; }
            else if (word.charAt(i) == ')') { rightPar++; }
        }
        return leftPar == rightPar;
    }
    public String getWord() { return word; }
}
