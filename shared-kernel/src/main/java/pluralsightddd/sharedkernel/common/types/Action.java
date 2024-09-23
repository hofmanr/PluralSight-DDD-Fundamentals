package pluralsightddd.sharedkernel.common.types;

@FunctionalInterface
public interface Action {
    void invoke();
}
