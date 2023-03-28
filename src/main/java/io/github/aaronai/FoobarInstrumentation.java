package io.github.aaronai;

import net.bytebuddy.asm.Advice;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.matcher.ElementMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static net.bytebuddy.matcher.ElementMatchers.isMethod;
import static net.bytebuddy.matcher.ElementMatchers.named;

public class FoobarInstrumentation implements TypeInstrumentation {
    private static final Logger logger = LoggerFactory.getLogger(FoobarInstrumentation.class);

    @Override
    public ElementMatcher<TypeDescription> typeMatcher() {
        return named("io.github.aaronai.Foobar");
    }

    @Override
    public void transform(TypeTransformer transformer) {
        transformer.applyAdviceToMethod(isMethod().and(named("go")), FoobarInstrumentation.class.getName() + "$GoAdvice");
    }

    @SuppressWarnings("unused")
    public static class GoAdvice {
        @Advice.OnMethodEnter(suppress = Throwable.class)
        public static void onStart() {
            logger.info("This method will be invoked before Foobar#go");
        }

        @Advice.OnMethodExit(suppress = Throwable.class)
        public static void onExit() {
            logger.info("This method will be invoked after Foobar#go");
        }
    }
}
