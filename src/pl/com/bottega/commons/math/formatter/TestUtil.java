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

   /* public static byte[][] splitByteArray(byte[] byteArray){

    }*/

    public static byte[][] splitArray(byte[] array, byte chunkSize){
        byte numOfChunks = (byte) Math.ceil((double) array.length / chunkSize);
        byte[][] result = new byte[numOfChunks][];

        for (int i = 0; i < numOfChunks; i++){
            int start = i * chunkSize;
            int a = array.length - start;
            int length = Math.min(a, chunkSize);

            byte[] temp = new byte[chunkSize];
            System.arraycopy(array, start, temp, 0, length);
            result[i] = temp;
        }
        return result;
    }

    // in: {1,2,3,4,5},   out: {{0,1,2},{3,4,5}}
    //in: {9,8,7,6,5,4,3,2,1,0}   out:{{0,0,9},{8,7,6},{5,4,3},{2,1,0}}
    // in: {1}   out: {{0,0,1}}
    public static ArrayList<ArrayList<Byte>> chunkArray(ArrayList<Byte> array){
        byte chunkSize = 3;
        int numOfChunks = array.size() / chunkSize + 1;
        int remainingDigits = array.size() % chunkSize;
        ArrayList<ArrayList<Byte>> result = new ArrayList<ArrayList<Byte>>();
        ArrayList<Byte> tempArray = new ArrayList<>();

        switch (remainingDigits){
            case 1:
                tempArray.add((byte) 0);
                tempArray.add((byte) 0);
                tempArray.add((array.get(0)));
                result.add(tempArray);
                break;
            case 2:
                tempArray.add((byte) 0);
                tempArray.add((array.get(0)));
                tempArray.add((array.get(1)));
                result.add(tempArray);
                break;
            case 0:
                tempArray.add((array.get(0)));
                tempArray.add((array.get(1)));
                tempArray.add((array.get(2)));
                result.add(tempArray);
                break;
            default:
                break;
        }

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

    private static void insertFirstArray(ArrayList<Byte> tempArray, ArrayList<ArrayList<Byte>> result){

    }
}
