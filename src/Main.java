public class Main {
    public static void main(String[] args) {
        String s = "";
        pars p = new pars(s);   //разбор выражений методом Вогана Пратта
        System.out.println(p.calc(0));
    }
}
