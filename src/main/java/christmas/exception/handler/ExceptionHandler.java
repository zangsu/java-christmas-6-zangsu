package christmas.exception.handler;

import java.util.function.Consumer;
import java.util.function.Supplier;

public interface ExceptionHandler {
    <T> T get(Supplier<T> logic, Consumer<IllegalArgumentException> handler);
}
