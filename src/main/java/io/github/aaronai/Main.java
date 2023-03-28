package io.github.aaronai;

import java.lang.instrument.Instrumentation;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.agent.builder.AgentBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(io.github.aaronai.Main.class);

    @SuppressWarnings("ArraysAsListWithZeroOrOneArgument")
    public static List<TypeInstrumentation> typeInstrumentations() {
        return Arrays.asList(new FoobarInstrumentation());
    }

    public static void premain(String arguments, Instrumentation instrumentation) {
        logger.debug("start to execute premain");
        final AgentBuilder.Default agentBuilder = new AgentBuilder.Default();
        for (TypeInstrumentation typeInstrumentation : typeInstrumentations()) {
            final AgentBuilder.Identified.Extendable extendable = agentBuilder.type(typeInstrumentation.typeMatcher()).transform(ConstantAdjuster.instance());
            final TypeTransformerImpl transformer = new TypeTransformerImpl(extendable);
            typeInstrumentation.transform(transformer);
            transformer.getAgentBuilder().installOn(instrumentation);
        }
    }

    public static void main(String[] args) {
        ByteBuddyAgent.install();
        final Instrumentation inst = ByteBuddyAgent.getInstrumentation();
        Main.premain("", inst);
        final Foobar foobar = new Foobar();
        foobar.go();
    }
}
