package pluralsightddd.sharedkernel.ddd.types;

public interface DomainEvent {
    interface IHandle<T extends DomainEvent> {

        void handle(T args);
    }
}
