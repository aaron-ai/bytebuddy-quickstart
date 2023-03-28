package io.github.aaronai;

import java.lang.instrument.Instrumentation;
import java.util.Arrays;
import java.util.List;
import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.agent.builder.AgentBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(io.github.aaronai.Main.class);

    /**
     * Define a method that returns a list of TypeInstrumentation objects.
     */
    @SuppressWarnings("ArraysAsListWithZeroOrOneArgument")
    public static List<TypeInstrumentation> typeInstrumentations() {
        return Arrays.asList(new FoobarInstrumentation());
    }

    /**
     * Define a premain method that will be called when the Java agent is started.
     */
    public static void premain(String arguments, Instrumentation instrumentation) {
        logger.debug("start to execute premain");

        // Create a new AgentBuilder object
        final AgentBuilder.Default agentBuilder = new AgentBuilder.Default();

        // Iterate over the list of TypeInstrumentation objects
        for (TypeInstrumentation typeInstrumentation : typeInstrumentations()) {
            // Get the typeMatcher from the TypeInstrumentation object and use it to create an extendable identified AgentBuilder
            final AgentBuilder.Identified.Extendable extendable = agentBuilder.type(typeInstrumentation.typeMatcher())
                .transform(ConstantAdjuster.instance());
            final TypeTransformerImpl transformer = new TypeTransformerImpl(extendable);
            typeInstrumentation.transform(transformer);
            transformer.getAgentBuilder().installOn(instrumentation);
        }
    }

    public static void main(String[] args) {
        // Install the ByteBuddy agent
        ByteBuddyAgent.install();
        // Get the instrumentation from the ByteBuddy agent
        final Instrumentation inst = ByteBuddyAgent.getInstrumentation();
        // Call the premain method with an empty argument string and the obtained instrumentation
        Main.premain("", inst);
        final Foobar foobar = new Foobar();
        foobar.go();
    }
}
