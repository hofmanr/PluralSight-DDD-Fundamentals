package pluralsightddd.sharedkernel.ddd.annotations;

import java.lang.annotation.*;

/**
 * Identifies a domain {@link DomainService}. A service is a significant process or transformation in the domain that is not a
 * natural responsibility of an entity or value object, add an operation to the model as a standalone interface declared
 * as a service. Define a service contract, a set of assertions about interactions with the service. (See assertions.)
 * State these assertions in the ubiquitous language of a specific bounded context. Give the service a name, which also
 * becomes part of the ubiquitous language.
 *
 * @author Christian Stettler
 * @author Henning Schwentner
 * @author Stephan Pirnbaum
 * @author Martin Schimak
 * @author Oliver Drotbohm
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface DomainService {
}
