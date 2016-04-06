package pl.com.bottega.commons.math.formatter;

import java.util.Arrays;

public class FormatterConsoleApp {
    
    public static void main(String[] args) {
        shouldCreateForLargeNumber(123124);
        shouldCreateForLargeNumber(12352432334576l);//L na końcu to typ long
        shouldCreateForLargeNumber(2154234634573457l);

        shouldCreateForLargeString("123124");
        shouldCreateForLargeString("12352432334576");
        shouldCreateForLargeString("2154234634573457");

        shouldFormatDigits();
/*
        shouldGetFirstNumber();
        shouldGetLastNumber();
        shouldFormatNumbers();*/

    }

    private static void shouldCreateForLargeNumber(long number) {
        Formatter formatter = new Formatter(number);
    }

    private static void shouldCreateForLargeString(String number) {
        Formatter formatter = new Formatter(number);
    }

    private static void shouldFormatDigits(){
        Formatter formatter = new Formatter(123456789123456789l);
        String[] digits = formatter.formatDigits("pl");
        System.out.println(Arrays.toString(digits));
    }

    private static void shouldFormatNumbers(){
        Formatter formatter = new Formatter("123456789123456789");
        String numbers = formatter.formatNumbers("pl");
    }


    private static void shouldGetLastNumber() {
        Formatter formatter = new Formatter(123456789123456789l);
        String digit = formatter.getLastDigit("pl");
    }

    private static void shouldGetFirstNumber() {
        Formatter formatter = new Formatter(123456789123456789l);
        String digit = formatter.getDigit(1, "pl");

    }



}