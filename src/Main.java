import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String s = "";
        System.out.println("Write expression");
        Scanner scanner = new Scanner(System.in);
        s = scanner.nextLine();
        pars p = new pars(s);   //разбор выражений методом Вогана Пратта
        System.out.println(p.calc(0));
        scanner.close();
    }
}
