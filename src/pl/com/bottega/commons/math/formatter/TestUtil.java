package pl.com.bottega.commons.math.formatter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Beata Iłowiecka on 10.04.16.
 */
public class TestUtil {

    public static byte[][] bytes = {
            {5,6,5}, {9,1,3}, {3,2,8}
    };

    public static byte[] bytes1 = {6,5,9,1,3,3,2,8};

    public static void main(String[] args) {


        /*ArrayList<ArrayList<Byte>> result = new ArrayList<ArrayList<Byte>>();
        ArrayList<Byte> a = new ArrayList<>();
        a.add((byte) 1);
        result.add(a);
        System.out.println(result.size());*/

        /*Formatter formatter = new Formatter("565912328");
        String[] result = new String[formatter.digits.size()];
        System.out.println(doSth());*/

        /*ArrayList<Byte> bytes2 = new ArrayList<>();
        for (byte b : bytes1){
            bytes2.add(b);
        }*/
        ArrayList<Byte> a = new ArrayList<>();
        a.add((byte)9);
        a.add((byte)8);
        a.add((byte)8);
        a.add((byte)8);
        a.add((byte)8);
        a.add((byte)8);
        a.add((byte)8);
        a.add((byte)9);
        a.add((byte)8);
        a.add((byte)8);
        a.add((byte)8);
        a.add((byte)8);
        a.add((byte)8);
        a.add((byte)8);

        ArrayList<ArrayList<Byte>> byteArray = chunkArray(a);

        for (ArrayList<Byte> byteAr : byteArray){
            System.out.println();
            for (byte b : byteAr){
                System.out.print(b);
            }
        }

    }
    public static String doSth(){

        for (byte[] list : bytes){
            for (int index = 0; index < 3; index++){
                if (index == 0){

                }
                if (index == 1){
                    if (list[index] == 1){
                        String tempResult;
                        tempResult = String.valueOf(list[index]) + String.valueOf(list[index+1]);
                        return tempResult;
                    }
                }
            }
        }
        return "a";
    }


    // Splits an array into equal 3-elements arrays. If number of elements is not dividable by 3,
    // additional zero's are inserted into the first array to make it contain 3-elements.
    // in: {1}                      out: {{0,0,1}}
    // in: {0,1,2,3}                out: {{0,0,0},{1,2,3}}
    // in: {1,2,3,4}                out: {{0,0,1},{2,3,4}}
    // in: {1,2,3,4,5},             out: {{0,1,2},{3,4,5}}
    // in: {9,8,7,6,5,4,3,2,1,0}    out:{{0,0,9},{8,7,6},{5,4,3},{2,1,0}}
    public static ArrayList<ArrayList<Byte>> chunkArray(ArrayList<Byte> array){
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

    private static ArrayList<Byte> getFirstArray(int remainingDigits, ArrayList<Byte> array){
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
