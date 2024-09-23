package pluralsightddd.sharedkernel.common.types;

import java.util.List;

public interface ObjectMapper<T> {

    <U> U map(T source);

    default List<Object> transformList(List<T> source) {
        return source.stream().map(this::map).toList();
    }
}
