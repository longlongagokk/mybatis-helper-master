package club.yourbatis.hi.base.meta;

import club.yourbatis.hi.base.Primary;
import lombok.Getter;

@Getter
public class SimplePrimary<T> implements Primary<T> {
    private T value;
    private SimplePrimary(T value){
        this.value = value;
    }
    public static <T> Primary<T> valueOf(T value){
        return new SimplePrimary<>(value);
    }
}
