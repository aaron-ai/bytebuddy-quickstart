# Byte Buddy Demo

This is a sample project demonstrating how to use ByteBuddy for bytecode manipulation without requiring an external Java
agent. This project was inspired
by [opentelemetry-java-instrumentation](https://github.com/open-telemetry/opentelemetry-java-instrumentation).

The Foobar class is the target of the bytecode enhancement.

## Requirements

This project requires Java 8 or higher.

## License

This project is licensed under the Apache License, Version 2.0.

## Usage

To use ByteBuddy for bytecode manipulation, first create a **TypeInstrumentation** implementation for each class you
wish to manipulate. In this example, we have created the **FoobarInstrumentation** class.

Then, in the premain method of the **Main** class, create a **TypeTransformer** implementation for each 
**TypeInstrumentation** and install it on the instrumentation instance.

Finally, run your application and the **Foobar** class will be enhanced by the GoAdvice advice.
