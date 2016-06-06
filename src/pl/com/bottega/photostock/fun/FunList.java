package pl.com.bottega.photostock.fun;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Created by Beata Iłowiecka on 06.06.16.
 */
public interface FunList<T> {

    FunList<T> add(T el);

    boolean contains(T el);

    int size();

    T find(Predicate<T> predicate);

    T get(int i);

    boolean empty();

    <R> FunList<R> map(Function<T, R> mapper);

    //1 param - wartosc cząstkowa - akumulator - np. element do którego dodajemy
    //2 param - element dodawany
    <R> R reduce(R initial, BiFunction<R, T, R> reducor);

    static <T> FunList<T> create() {
        return new EmptyList<>();
    }

    //todo implement + testy

    FunList<T> remove(T el);

    //finds all elem on the list matching predicate
    FunList<T> filter(Predicate<T> predicate);

    //iterates over all elements and calls consumer with each element
    //interfejs funkcyjny z jedną funkcją
    void each(Consumer<T> consumer);
    //tylko wewnętrznie przetwarza elementy

    //can be not unique elements
    FunList<T> concat(FunList<T> other);

    //* - return sublist of elements starting at startIndex, ends at endIndex
    FunList<T> sublist(int startIndex, int endIndex);
}
