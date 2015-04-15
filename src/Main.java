public class Main {
    public static void main(String[] args) {
        String s = "abs(0+-4)";
        pars p = new pars(s);
        System.out.println(p.calc(0));
    }
}
