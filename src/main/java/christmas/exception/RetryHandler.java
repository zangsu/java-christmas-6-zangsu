package christmas.exception;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class RetryHandler implements ExceptionHandler {
    @Override
    public <T> T get(Supplier<T> logic, Consumer<IllegalArgumentException> handler) {
        while (true) {
            try {
                return logic.get();
            } catch (IllegalArgumentException e) {
                handler.accept(e);
            }
        }
    }
}
