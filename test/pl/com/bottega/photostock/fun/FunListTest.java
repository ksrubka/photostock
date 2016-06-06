package pl.com.bottega.photostock.fun;

import org.junit.Test;
import pl.com.bottega.photostock.sales.model.Money;
import pl.com.bottega.photostock.sales.model.Product;
import pl.com.bottega.photostock.sales.model.products.Picture;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Created by Beata Iłowiecka on 06.06.16.
 */
public class FunListTest {

    @Test
    public void shouldGetElement() {
        FunList<String> l = FunList.create();
        l = l.add("first").add("second").add("third").add("fifth");
        assertNull(l.get(-1));
        assertNull(l.get(10000000));
        assertEquals("second", l.get(1));
    }

    @Test
    public void shouldFindElement() {
        FunList<Integer> l = FunList.create();
        l = l.add(100).add(50).add(300).add(400);
        Predicate<Integer> t = new Predicate<Integer>() {
            @Override
            public boolean test(Integer integer) {
                return false;
            }
        };
        int number = l.find(new Predicate<Integer>() {
            @Override
            public boolean test(Integer i) {
                return i > 200;
            }
        });
        assertEquals(300, number);
        assertNull(l.find(new Predicate<Integer>() {
            @Override
            public boolean test(Integer i) {
                return i == 500;
            }
        }));
        Predicate<Integer> p = (i) -> i > 200;
        int number2 = l.find(i -> i == 400);
        assertEquals(400, number2);
        assertEquals(50, (int) l.find(i -> i == 50));
        assertNull(l.find(x -> {
            System.out.println("Calling predicate");
            return x > 1000;
        }));

        Predicate<Integer> pred = i -> {
            int x = 20;
            int y = 50;
            String s = " 400";
            s = s.trim();
            return x + y == 70;
        };
    }

    @Test
    public void shouldMapElements() {
        //given
        FunList<String> list = FunList.create();
        list = list.add("1").add("2").add("3").add("4").add("5").add("100");
        //when
        //z lambdą
        FunList<Integer> mapped = list.map(s -> Integer.valueOf(s));
        //method reference
        FunList<Integer> maped2 = list.map(Integer::valueOf);
        //z anonimową klsą wew
        FunList<Integer> mappedAnotherWay = list.map(new Function<String, Integer>() {
            @Override
            public Integer apply(String s) {
                return Integer.valueOf(s);
            }
        });
        //then
        assertTrue(mapped.contains(1));
        assertTrue(mapped.contains(3));
        assertTrue(mappedAnotherWay.contains(4));
        assertTrue(mappedAnotherWay.contains(100));
        //potrzebna metoda equals w
        FunList<Integer> expected = FunList.create();
        expected = expected.add(1).add(2).add(3).add(4).add(5).add(100);
        assertEquals(expected, mapped);
        assertEquals(expected, mappedAnotherWay);
    }

    @Test
    public void shouldReduceElements() {
        //given
        Picture p1 = new Picture("nr1", new Money(200, "USD"), new String[]{"tag1", "tag2", "tag3"}, true);
        Picture p2 = new Picture("nr2", new Money(100, "USD"), new String[]{"tag1", "tag4"}, false);
        Picture p3 = new Picture("nr3", new Money(50, "USD"), new String[]{"tag2"}, true);
        FunList<Picture> l = FunList.create();
        l = l.add(p1).add(p2).add(p3);

        //when
        int availableCount = l.reduce(
                0, (accumulator, product) -> accumulator + (product.isAvailable() ? 1 : 0));
        int availableCount2 = availableCount2 = l.reduce(0, (accumulator, product) -> {
            if (product.isAvailable())
                return accumulator + 1;
            else
                return accumulator;
        });
        Money total = l.reduce(new Money(0, "USD"), (accumulator, product) -> accumulator.add(product.getPrice()));
        FunList<String> tags = l.reduce(FunList.create(), (accumulator, product) -> {
            for (String tag : product.getTags())
                if (!accumulator.contains(tag))
                    accumulator = accumulator.add(tag);
            return accumulator;
        });
        FunList<String> expectedTags = FunList.create();
        expectedTags = expectedTags.add("tag1").add("tag2").add("tag3").add("tag4");

        //then
        assertEquals(2, availableCount);
        assertEquals(2, availableCount2);
        assertEquals(new Money(350, "USD"), total);
        assertEquals(expectedTags, tags);
    }
}
