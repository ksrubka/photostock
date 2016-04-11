package pl.com.bottega.commons.math.formatter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Beata Iłowiecka on 11.04.16.
 */
public class UtilsPL {

    public static final String[] HUNDREDS = {"", "sto", "dwieście", "trzysta", "czterysta", "pięćset", "sześćset", "siedemset", "osiemset", "dziewięćset"};

    public static final String[] TENS = {"", "", "dwadzieścia", "trzydzieści", "czterdzieści", "pięćdziesiąt", "sześćdziesiąt",
            "siedemdziesiąt", "osiemdziesiąt", "dziewięćdziesiąt"};

    public static final String[] SEVERALS = {"", "jeden", "dwa", "trzy", "cztery", "pięć", "sześć", "siedem", "osiem", "dziewięć","dziesięć",
            "jedenaście", "dwanaście", "trzynaście", "czternaście", "piętnaście", "szesnaście",
            "siedemnaście", "osiemnaście", "dziewiętnaście"};

    public static final String[][] BIG_NUMBERS = {
            {"", "tysiąc", "milion", "miliard", "bilion", "biliard", "trylion"},
            {"", "tysiące", "miliony", "miliardy", "biliony", "biliardy", "tryliony"},
            {"", "tysięcy", "milionów", "miliardów", "bilionów", "biliardów", "trylionów"}
    };

    public static String formatBigNumber(ArrayList<ArrayList<Byte>> arrayOfArrays){
        StringBuilder result = new StringBuilder();
        int index = arrayOfArrays.size() - 1;

        for (ArrayList<Byte> byteArray : arrayOfArrays){
            String formattedHundred = formatHundred(byteArray);

            // if every digit in 3-element array == 0, add only name of a BIG_NUMBER
            if ((byteArray.get(0) == 0 && byteArray.get(1) == 0 && byteArray.get(2) == 1) && index != 0 ) {
                result.append(BIG_NUMBERS[0][index--]);
                result.append(" ");
                continue;
            }
            result.append(formattedHundred);

            // append name of a big number
            if (byteArray.get(0) == 0 && byteArray.get(1) == 0 && byteArray.get(2) == 0) {
                index--;
                continue;
            }
            else if ((byteArray.get(0) == 0 && byteArray.get(1) == 0 && byteArray.get(2) == 1)){
                result.append(BIG_NUMBERS[0][index--]);
            }
            else if ((byteArray.get(2) == 2 || byteArray.get(2) == 3 || byteArray.get(2) == 4)){
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
    public static String formatHundred(ArrayList<Byte> byteArray){
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < 3; i++){
            byte currentDigit = byteArray.get(i);

            switch (i){
                case 0:
                    result.append(HUNDREDS[currentDigit]);
                    break;
                case 1:
                    byte secondDigit = byteArray.get(i);
                    if (secondDigit == 1) {
                        String tempResult = String.valueOf(1) + String.valueOf(byteArray.get(i + 1));
                        int several = Integer.parseInt(tempResult);
                        result.append(SEVERALS[several]);
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

    // Splits an array into equal 3-element arrays. If number of elements is not dividable by 3,
    // additional zero's are inserted into the first array to make it contain 3-elements.
    // in: {1}                      out: {{0,0,1}}
    // in: {0,1,2,3}                out: {{0,0,0},{1,2,3}}
    // in: {1,2,3,4}                out: {{0,0,1},{2,3,4}}
    // in: {1,2,3,4,5},             out: {{0,1,2},{3,4,5}}
    // in: {9,8,7,6,5,4,3,2,1,0}    out:{{0,0,9},{8,7,6},{5,4,3},{2,1,0}}
    public static ArrayList<ArrayList<Byte>> chunkArray(List<Byte> array){
        byte chunkSize = 3;
        int numOfChunks = array.size() / chunkSize + 1;
        int remainingDigits = array.size() % chunkSize;
        ArrayList<ArrayList<Byte>> result = new ArrayList<>();

        result.add(getFirstArray(remainingDigits, array));

        int start = remainingDigits;
        for (int i = 1; i < numOfChunks; i++){
            ArrayList<Byte> tempArray1 = new ArrayList<>();
            for (int j = 0; j < chunkSize; j++) {
                tempArray1.add(array.get(start));
                start++;
            }
            result.add(tempArray1);
        }
        return result;
    }

    private static ArrayList<Byte> getFirstArray(int remainingDigits, List<Byte> array){
        ArrayList<Byte> tempArray = new ArrayList<>();

        switch (remainingDigits){
            case 1:
                tempArray.add((byte) 0);
                tempArray.add((byte) 0);
                tempArray.add((array.get(0)));
                break;
            case 2:
                tempArray.add((byte) 0);
                tempArray.add((array.get(0)));
                tempArray.add((array.get(1)));
                break;
            case 0:
                tempArray.add((array.get(0)));
                tempArray.add((array.get(1)));
                tempArray.add((array.get(2)));
                break;
            default:
                break;
        }
        return tempArray;
    }
}
