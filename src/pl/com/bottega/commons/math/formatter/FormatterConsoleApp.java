package pl.com.bottega.commons.math.formatter;

/**
 * Created by Beata Iłowiecka on 06.04.16.
 */
public class FormatterConsoleApp {

    public static void main(String[] args) {

        Formatter f = new Formatter(1234567891);

        String[] digits = f.formatDigits(Formatter.FormattingLanguage.ENG);

        for (String digit : digits){
            System.out.println(digit);
        }
    }

}
