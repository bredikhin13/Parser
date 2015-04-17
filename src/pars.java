import java.util.*;

/**
 * Created by User on 15.04.2015.
 */
public class pars  {
    private Queue<Character> chars; //очередь, в которой хранитс€ наше выражение

    public pars(String s) {
        chars = new ArrayDeque<>();
        for (char c : s.toCharArray()) {
            chars.add(c);
        }
        chars.add('&');//специальный символ, обозначающий конец выражени€
    }

    public Double calc(int r) {
        int rpb = r;
        char tmp;
        double result = 0;
        try {
            result = nud(chars.poll());
            while (priority(chars.peek()) > rpb) {
                tmp = chars.poll();
                result = operators(result, tmp);
            }
            if (priority(chars.peek()) == -1) {
                chars.poll();
            }
        } catch (Exception e){
            System.err.println(e.getMessage());
        }
        return result;
    }

    private boolean isDigit(char c) { //проверка, €вл€етс€ ли данный символ цифрой
        return (c >= '0' && c <= '9');
    }

    private double nud(char c) { //функци€, определ€юща€ назначение символа в начале выражени€
        int minusFlag = 1;// унарный минус
        if(c == '-'){
           minusFlag= -1;
            c = chars.poll();
        }
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
                        return minusFlag * Double.parseDouble(tmp);
                    } else {
                        return minusFlag * functions(tmp);
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
        }
        return 0;
    }

    private double priority(char ch) { // функци€, возвращающа€ приоритет операции
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

    private double functions(String s) throws Exception {
        switch (s) {
            case "sqrt":
                chars.poll();
                return Math.sqrt(calc(0));
            case "abs":
                chars.poll();
                return Math.abs(calc(0));
            case "min":
                chars.poll();
                return getMin();
            default:
                throw new Exception("Unknown function: " + s);
        }
    }

    private double operators(double m, char ch) throws Exception {
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
                throw new Exception("Unknown operator: "+ ch);
        }
        return result;
    }

    private double getMin() throws Exception{
        String tmp = "";
        ArrayList<Double> arr = new ArrayList<>();
        Double result;
        while (chars.peek() != ')'){
            if(chars.peek()!=','){
                if(isDigit(chars.peek())) {
                    tmp += chars.poll();
                }
                else {
                    throw new Exception("Unexpected token:" + chars.poll());
                }
            }
            else {
                arr.add(Double.parseDouble(tmp));
                chars.poll();
                tmp = "";
            }
        }
        result = Double.parseDouble(tmp);
        for(Double d: arr){
            if(d<result) result = d;
        }
        return result;
    }

}