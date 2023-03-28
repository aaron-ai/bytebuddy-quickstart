package io.github.aaronai.example1;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.matcher.ElementMatcher;

public interface TypeTransformer {
    /**
     * Apply the advice class named {@code adviceClassName} to the instrumented type methods that
     * match {@code methodMatcher}.
     */
    void applyAdviceToMethod(
        ElementMatcher<? super MethodDescription> methodMatcher, String adviceClassName);

    /**
     * Apply a custom ByteBuddy {@link AgentBuilder.Transformer} to the instrumented type. Note that
     * since this is a completely custom transformer, muzzle won't be able to scan for references or
     * helper classes.
     */
    void applyTransformer(AgentBuilder.Transformer transformer);
}
