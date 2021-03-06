package pl.com.bottega.commons.math.formatter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Beata Iłowiecka on 10.04.16.
 */
public class TestUtil {

    public static void main(String[] args) {

        List<Byte> a = new ArrayList<>();
        a.add((byte)7);
        a.add((byte)0);
        a.add((byte)1);

        List<Byte> b = new ArrayList<>();
        b.add((byte)0);
        b.add((byte)0);
        b.add((byte)1);

        List<Byte> c = new ArrayList<>();
        c.add((byte)8);
        c.add((byte)0);
        c.add((byte)1);

        List<Byte> d = new ArrayList<>();
        d.add((byte)0);
        d.add((byte)0);
        d.add((byte)0);

        List<Byte> e = new ArrayList<>();
        e.add((byte)5);
        e.add((byte)1);
        e.add((byte)5);

        List<List<Byte>> result1 = new ArrayList<>();
        result1.add(a);
        result1.add(b);
        result1.add(c);
        result1.add(d);
        result1.add(e);

        System.out.println(UtilsPL.formatBigNumber(result1));
    }
}
