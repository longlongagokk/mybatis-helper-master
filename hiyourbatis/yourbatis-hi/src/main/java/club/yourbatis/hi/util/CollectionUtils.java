package club.yourbatis.hi.util;

import java.util.Collection;

public abstract class CollectionUtils {
    public final static boolean isEmpty(Object[] array){
        return array == null || array.length == 0;
    }
    public final static boolean isNotEmpty(Object[] array){
        return !(isEmpty(array));
    }
    public final static boolean isEmpty(Collection<?> collection){
        return collection == null || collection.isEmpty();
    }
}
