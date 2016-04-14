package pl.com.bottega.commons.math.formatter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Beata Iłowiecka on 11.04.16.
 */
public class UtilsPL {

    private static final String[] HUNDREDS = {"", "sto", "dwieście", "trzysta", "czterysta", "pięćset", "sześćset", "siedemset", "osiemset", "dziewięćset"};

    private static final String[] TENS = {"", "", "dwadzieścia", "trzydzieści", "czterdzieści", "pięćdziesiąt", "sześćdziesiąt",
            "siedemdziesiąt", "osiemdziesiąt", "dziewięćdziesiąt"};

    private static final String[] SEVERALS = {"", "jeden", "dwa", "trzy", "cztery", "pięć", "sześć", "siedem", "osiem", "dziewięć","dziesięć",
            "jedenaście", "dwanaście", "trzynaście", "czternaście", "piętnaście", "szesnaście",
            "siedemnaście", "osiemnaście", "dziewiętnaście"};

    private static final String[][] BIG_NUMBERS = {
            {"", "tysiąc", "milion", "miliard", "bilion", "biliard", "trylion"},
            {"", "tysiące", "miliony", "miliardy", "biliony", "biliardy", "tryliony"},
            {"", "tysięcy", "milionów", "miliardów", "bilionów", "biliardów", "trylionów"}
    };

    public static String formatBigNumber(List<List<Byte>> hundredContainer){
        StringBuilder result = new StringBuilder();
        int index = hundredContainer.size() - 1;
        List<Byte> lastHundred = hundredContainer.get(hundredContainer.size() - 1);

        for (List<Byte> hundred : hundredContainer){
            String formattedHundred = formatHundred(hundred);

            // if last digit of a number == 1, add only name of a BIG_NUMBER
            if ((hundred.get(0) == 0 && hundred.get(1) == 0 && hundred.get(2) == 1)) {
                result.append(BIG_NUMBERS[0][index--]);
                result.append(" ");
                continue;
            }
            result.append(formattedHundred);

            // append name of a big number
            // if number is 0, don't write it's name
            if (hundred.get(0) == 0 && hundred.get(1) == 0 && hundred.get(2) == 0) {
                index--;
                continue;
            }
            // if number is 1
            else if ((hundred.get(0) == 0 && hundred.get(1) == 0 && hundred.get(2) == 1)){
                result.append(BIG_NUMBERS[0][index--]);
            }
            else if (hundred.get(1) == 1) {
                result.append(BIG_NUMBERS[2][index--]);
            }
            else if ((hundred.get(2) == 2 || hundred.get(2) == 3 || hundred.get(2) == 4)){
                result.append(BIG_NUMBERS[1][index--]);
            }
            else {
                result.append(BIG_NUMBERS[2][index--]);
            }
            result.append(" ");

        }
        return result.toString();
    }

    // in: {0,0,1}          out: jeden
    // in: {1,0,5}          out: sto pięć
    // in: {3,2,9}          out: trzysta dwadzieścia dziewięć
    public static String formatHundred(List<Byte> hundred){
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < 3; i++){
            byte currentDigit = hundred.get(i);

            switch (i){
                case 0:
                    result.append(HUNDREDS[currentDigit]);
                    break;
                case 1:
                    if (currentDigit == 1) {
                        String tempResult = String.valueOf(1) + String.valueOf(hundred.get(i + 1));
                        int several = Integer.parseInt(tempResult);
                        result.append(SEVERALS[several]);
                        // break for loop
                        i = 2;
                    }
                    else {
                        result.append(TENS[currentDigit]);
                    }
                    break;
                case 2:
                    result.append(SEVERALS[currentDigit]);
                    break;
                default:
                    break;
            }
            if (currentDigit != 0) {
                result.append(" ");
            }
        }
        return result.toString();
    }
}
