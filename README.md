# CS320-Portfolio-Artifacts

How can I ensure that my code, program, or software is functional and secure?

- I ensure functionality and security by validating input data, writing unit tests to cover both expected and unexpected use cases, and using assertions to verify behavior. In the Contact class, for example, I prevent invalid data such as null IDs or incorrectly formatted phone numbers, which enhances both data integrity and security.

How do I interpret user needs and incorporate them into a program?

- I rely on clearly defined requirements and translate them into validation rules and test cases. For example, when the client required that phone numbers be exactly 10 digits, I implemented regex checks and verified them through JUnit tests. Understanding these user needs helps me make intentional design decisions that meet expectations.

How do I approach designing software?

- I begin by understanding the problem and identifying key objects and services. I use object oriented programming principles to separate responsibilities and write clean, modular code. I follow up with testing to ensure the design holds under different inputs. Throughout this course, I have created one service per object and wrote targeted unit tests for each behavior.

<!-- Add this section to the end of the existing README.md in this repo -->

## CS 499 Milestone Two — Enhancement One: Software Design and Engineering

The `enhanced/` folder contains the CS 499 capstone enhancement of this artifact. The original `ContactServices`-based project owned its storage directly as a private `HashMap` inside each service, which made the services hard to test against anything but that one implementation. This enhancement introduces a `Repository` interface (`findById`, `save`, `existsById`, `deleteById`, `findAll`) for each of the three entities, so `ContactService`, `TaskService`, and `AppointmentService` now depend on that abstraction instead of a concrete map. The original in-memory behavior remains available as `InMemory*Repository`; a new `File*Repository` implementation was added so data actually persists across restarts, which also made real integration tests possible for the first time.

Alongside the planned enhancement, three smaller bugs found during the code review were fixed: an invalid-regex bug in `Contact`'s phone validation, missing existence checks on `ContactService`/`TaskService` update methods (previously threw an unchecked `NullPointerException` on an unknown ID), and a leftover JUnit 4 import mixed into JUnit 5 test classes. The project was also consolidated from three separate Eclipse projects into a single Maven project with a GitHub Actions CI workflow (`.github/workflows/ci.yml`) that runs the full test suite on every push.

Full narrative: see `Jones_Aaron_Milestone_Two_Narrative.pdf` on the [ePortfolio site](https://aaron-jones8.github.io/).

### Running the enhanced project

```
cd enhanced
mvn test
```
