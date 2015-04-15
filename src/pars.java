import java.util.*;

/**
 * Created by User on 15.04.2015.
 */
public class pars {
    private Queue<Character> chars;

    public pars(String s) {
        chars = new ArrayDeque<>();
        for (char c : s.toCharArray()) {
            chars.add(c);
        }
        chars.add('&');
    }

    public Double calc(int r) {
        int rpb = r;
        char tmp;
        double result;
        result = nud(chars.poll());
        try {
            while (priority(chars.peek()) > rpb) {
                tmp = chars.poll();
                result = operators(result, tmp);
            }
            if (priority(chars.peek()) == -1) {
                chars.poll();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    private boolean isDigit(char c) {
        return (c >= '0' && c <= '9');
    }

    public double nud(char c) {
        switch (c) {
            case '(':
                return calc(0);
            default:
                String tmp = "" + c;
                try {
                    while (priority(c) == priority(chars.peek())) {
                        tmp += chars.poll();
                    }
                    if (isDigit(c)) {
                        return Double.parseDouble(tmp);
                    } else {
                        return functions(tmp);
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
        }
        return -1;
    }

    public double priority(char ch) {
        switch (ch) {
            case ('&'):
                return -2;
            case (')'):
                return -1;
            case ('('):
                return -1;
            case ('+'):
                return 1;
            case '-':
                return 1;
            case ('*'):
                return 2;
            case ('/'):
                return 2;
            case ('^'):
                return 3;
            default:
                return 0;
        }
    }

    public double functions(String s) throws Exception {
        switch (s) {
            case "sqrt":
                chars.poll();
                return Math.sqrt(calc(0));
            case "abs":
                chars.poll();
                return Math.abs(calc(0));
            default:
                throw new Exception("Unknown function");
        }
    }

    public double operators(double m, char ch) throws Exception {
        double result = 0;
        switch (ch) {
            case ('+'):
                result = m + calc(1);
                break;
            case ('-'):
                result = m - calc(1);
                break;
            case ('*'):
                result = m * calc(2);
                break;
            case ('/'):
                result = m / calc(2);
                break;
            case ('^'):
                int t = calc(3).intValue();
                for (int i = 1; i < t; i++) {
                    m *= m;
                }
                result = m;
                break;
            default:
                throw new Exception("Unknown operator");
        }
        return result;
    }
}