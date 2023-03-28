package io.github.aaronai;

import java.security.ProtectionDomain;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.asm.TypeConstantAdjustment;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.utility.JavaModule;

/**
 * This {@link AgentBuilder.Transformer} ensures that class files of a version previous to Java 5 do
 * not store class entries in the generated class's constant pool.
 *
 * @see ConstantAdjuster The ASM visitor that does the actual work.
 */
public class ConstantAdjuster implements AgentBuilder.Transformer {
    private static final ConstantAdjuster INSTANCE = new ConstantAdjuster();

    static AgentBuilder.Transformer instance() {
        return INSTANCE;
    }

    private ConstantAdjuster() {
    }

    @Override
    public DynamicType.Builder<?> transform(
        DynamicType.Builder<?> builder,
        TypeDescription typeDescription,
        ClassLoader classLoader,
        JavaModule javaModule,
        ProtectionDomain protectionDomain) {
        return builder.visit(TypeConstantAdjustment.INSTANCE);
    }
}