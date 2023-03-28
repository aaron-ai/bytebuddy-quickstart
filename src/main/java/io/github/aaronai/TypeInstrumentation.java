package io.github.aaronai;

import static net.bytebuddy.matcher.ElementMatchers.any;

import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.matcher.ElementMatchers;

public interface TypeInstrumentation {
    /**
     * An optimization to short circuit matching in the case where the instrumented library is not
     * even present on the class path.
     *
     * <p>Most applications have only a small subset of libraries on their class path, so this ends up
     * being a very useful optimization.
     *
     * <p>Some background on type matcher performance:
     *
     * <p>Type matchers that only match against the type name are fast, e.g. {@link
     * ElementMatchers#named(String)}.
     *
     * <p>All other type matchers require some level of bytecode inspection, e.g. {@link
     * ElementMatchers#isAnnotatedWith(ElementMatcher)}.
     *
     * @return A type matcher that rejects classloaders that do not contain desired interfaces or base
     * classes.
     */
    default ElementMatcher<ClassLoader> classLoaderOptimization() {
        return any();
    }

    /**
     * Returns a type matcher defining which classes should undergo transformations defined in the
     * {@link #transform(TypeTransformer)} method.
     */
    ElementMatcher<TypeDescription> typeMatcher();

    /**
     * Define transformations that should be applied to classes matched by {@link #typeMatcher()}, for
     * example: apply advice classes to chosen methods ({@link
     * TypeTransformer#applyAdviceToMethod(ElementMatcher, String)}.
     */
    void transform(TypeTransformer transformer);
}
