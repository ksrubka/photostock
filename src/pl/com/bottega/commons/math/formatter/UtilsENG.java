package pl.com.bottega.commons.math.formatter;

import java.util.ArrayList;

/**
 * Created by Beata Iłowiecka on 11.04.16.
 */
public class UtilsENG {

    private static final String[] TENS = {"", "", "twenty", "thirty", "fourty", "fifty", "sixty",
            "seventy", "eighty", "ninety"};

    private static final String[] SEVERALS = {"", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten",
            "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen",
            "seventeen", "eighteen", "nineteen"};

    private static final String[] BIG_NUMBERS =
            {"", "thousand,", "million,", "billion,", "trillion,", "quadrillion,", "quintillion,"};

    public static String formatBigNumber(ArrayList<ArrayList<Byte>> hundredContainer){
        StringBuilder result = new StringBuilder();
        int index = hundredContainer.size() - 1;

        for (ArrayList<Byte> byteArray : hundredContainer){
            String formattedHundred = formatHundred(byteArray);

            result.append(formattedHundred);

            // append name of a big number
            // if number is 0, don't write it's name
            if (byteArray.get(0) == 0 && byteArray.get(1) == 0 && byteArray.get(2) == 0) {
                index--;
                continue;
            }
            result.append(BIG_NUMBERS[index--]);
            result.append(" ");
        }
        return result.toString();
    }

    // in: {0,0,1}          out: one
    // in: {1,0,5}          out: one hundred five
    // in: {3,2,9}          out: three hundred twenty-nine
    public static String formatHundred(ArrayList<Byte> hundred){
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < 3; i++){
            byte currentDigit = hundred.get(i);

            if (currentDigit != 0){
                switch (i){
                    case 0:
                        result.append(SEVERALS[currentDigit]);
                        result.append(" hundred");
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
                            if (hundred.get(i + 1) != 0){
                                result.append("-");
                                continue;
                            }
                        }
                        break;
                    case 2:
                        result.append(SEVERALS[currentDigit]);
                        break;
                    default:
                        break;
                }
                result.append(" ");
            }
        }
        return result.toString();
    }
}
