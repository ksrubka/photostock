package pl.com.bottega.photostock.fun;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Created by Beata Iłowiecka on 06.06.16.
 */
public class NonEmptyList<T> implements FunList<T> {

    private T head;
    private FunList<T> tail;

    public NonEmptyList(T el){
        head = el;
        tail = new EmptyList<>();
    }

    @Override
    public FunList add(T el) {
        return  new NonEmptyList<>(head, tail.add(el));
    }
    public NonEmptyList(T head, FunList<T> tail){
        this.head = head;
        this.tail = tail;
    }

    @Override
    public FunList remove(T el) {
        return this;
    }

    @Override
    public FunList<T> filter(Predicate<T> predicate) {
        return null;
    }

    @Override
    public void each(Consumer<T> consumer) {

    }

    @Override
    public FunList<T> concat(FunList<T> other) {
        return null;
    }

    @Override
    public FunList<T> sublist(int startIndex, int endIndex) {
        return null;
    }

    @Override
    public boolean contains(T el) {
        return head.equals(el) || tail.contains(el);
    }

    @Override
    public int size() {
        return 1 +tail.size();
    }

    @Override
    public T find(Predicate<T> predicate) {
        if (predicate.test(head))
            return head;
        else
            return tail.find(predicate);
    }

    @Override
    public T get(int i) {
        if(i == 0)
            return head;
        else
            return tail.get(i-1);
    }
    public String toString(){
        if(tail.empty())
            return head.toString();
        else
            return head.toString() + ", " +tail.toString();
    }
    public boolean empty(){
        return false;
    }

    @Override
    public <R> FunList<R> map(Function<T, R> mapper) {
        return new NonEmptyList<R>(mapper.apply(head), tail.map(mapper));
    }

    //R -
    //T -
    //R -
    @Override
    public <R> R reduce(R initial, BiFunction<R, T, R> reductor) {
        R partialResult = reductor.apply(initial, head);
        return tail.reduce(partialResult, reductor);
    }

    public boolean equals(Object other) {
        if (!(other instanceof NonEmptyList))
            return false;
        NonEmptyList otherList = (NonEmptyList) other;
        return head.equals(otherList.head) && tail.equals(otherList.tail);
    }
}
