/*
 * Copyright 2020-2024 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package pluralsightddd.sharedkernel.architecture.clean;

import java.lang.annotation.*;

/**
 * Identifies the DomainServiceRing in a clean architecture. The domain service ring defines the interfaces
 * needed for e.g. storing and retrieving data, i.e. repository interfaces. However, it does not provide the technical
 * implementations. These will be provided by the InfrastructureRing. Thus, the domain service ring supports the
 * execution of business use cases.
 *
 * @author Christian Stettler
 * @author Henning Schwentner
 * @author Stephan Pirnbaum
 * @author Martin Schimak
 * @author Oliver Drotbohm
 * @see <a href="https://jeffreypalermo.com/2008/07/the-onion-architecture-part-1/">The Onion Architecture : part 1
 * (Palermo)</a>
 */
@Retention(RetentionPolicy.CLASS)
@Target({ ElementType.PACKAGE, ElementType.TYPE })
@Documented
public @interface DomainServiceRing {
}
