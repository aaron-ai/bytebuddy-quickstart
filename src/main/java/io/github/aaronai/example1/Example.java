package io.github.aaronai.example1;

import java.lang.instrument.Instrumentation;
import java.lang.reflect.InvocationTargetException;
import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.asm.Advice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static net.bytebuddy.matcher.ElementMatchers.isMethod;
import static net.bytebuddy.matcher.ElementMatchers.named;

public class Example {
    private static final Logger log = LoggerFactory.getLogger(Example.class);

    public static void premain(String arguments, Instrumentation instrumentation) {
        log.debug("start to execute premain");
        new AgentBuilder.Default()
            .type(named("io.github.aaronai.example1.Foobar")).transform(new AgentBuilder.Transformer.ForAdvice().advice(isMethod()
                .and(named("go")), Example.class.getName() + "$GoAdvice"))
            .installOn(instrumentation);

    }

    public static class GoAdvice {
        @Advice.OnMethodEnter(suppress = Throwable.class)
        public static void onStart() {
            log.info("enter go method");
        }
    }

    public static void main(
        String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        ByteBuddyAgent.install();
        final Instrumentation inst = ByteBuddyAgent.getInstrumentation();
        Class.forName("io.github.aaronai.example1.Example", true, ClassLoader.getSystemClassLoader())
            .getDeclaredMethod("premain", String.class, Instrumentation.class)
            .invoke(null, "", inst);
        final Foobar foobar = new Foobar();
        foobar.go();
    }
}
