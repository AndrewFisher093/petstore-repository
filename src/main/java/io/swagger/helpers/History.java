package io.swagger.helpers;

import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.function.Consumer;

/**
 * The History class helps to collect entities for deletion after all tests are  complete in order not to overload the
 * database with fake records.
 *
 * @param <T> the type parameter
 */
public class History<T> {

    private final ConcurrentLinkedDeque<T> deque;

    public History() {
        this.deque = new ConcurrentLinkedDeque<>();
    }

    public void add(T entity) {
        this.deque.add(entity);
    }

    /**
     * Clear history using consumer function.
     *
     * @param function the function
     */
    public void clear(Consumer<T> function) {
        T entity;
        while ((entity = this.poll()) != null) {
            function.accept(entity);
        }
    }

    public T poll() {
        return deque.pollFirst();
    }
}