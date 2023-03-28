package io.github.aaronai.example1;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.matcher.ElementMatcher;

public class TypeTransformerImpl implements TypeTransformer {
    private AgentBuilder.Identified.Extendable agentBuilder;

    TypeTransformerImpl(AgentBuilder.Identified.Extendable agentBuilder) {
        this.agentBuilder = agentBuilder;
    }

    @Override
    public void applyAdviceToMethod(
        ElementMatcher<? super MethodDescription> methodMatcher, String adviceClassName) {
        agentBuilder =
            agentBuilder.transform(
                new AgentBuilder.Transformer.ForAdvice()
                    .advice(methodMatcher, adviceClassName));
    }

    @Override
    public void applyTransformer(AgentBuilder.Transformer transformer) {
        agentBuilder = agentBuilder.transform(transformer);
    }

    AgentBuilder.Identified.Extendable getAgentBuilder() {
        return agentBuilder;
    }
}
