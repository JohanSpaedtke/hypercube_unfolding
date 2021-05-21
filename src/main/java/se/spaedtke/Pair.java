package se.spaedtke;

public class Pair<S, T> {

    private final S s;
    private final T t;


    private Pair(S s, T t) {
        this.s = s;
        this.t = t;
    }

    public static <S, T> Pair of(S s, T t) {
        return new Pair(s, t);
    }

    public S left() {
        return s;
    }

    public T right() {
        return t;
    }

}
