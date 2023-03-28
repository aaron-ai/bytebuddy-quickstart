package io.github.aaronai.example1;

import io.github.aaronai.example0.Foo;
import java.lang.instrument.Instrumentation;
import java.lang.reflect.InvocationTargetException;
import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.matcher.ElementMatchers;

import static net.bytebuddy.matcher.ElementMatchers.isMethod;
import static net.bytebuddy.matcher.ElementMatchers.named;

public class Example {
    public static void premain(String arguments, Instrumentation instrumentation) {
        System.out.println("=================");
        // 使用 ByteBuddy 修改字节码并增强目标应用程序

        new AgentBuilder.Default()
            .type(named("io.github.aaronai.example1.Foobar")).transform(new AgentBuilder.Transformer.ForAdvice().advice(isMethod()
                .and(named("go")), Example.class.getName() + "$GoAdvice")).installOn(instrumentation);

    }

    public static class GoAdvice {
        @Advice.OnMethodEnter(suppress = Throwable.class)
        public static void onStart() {
            System.out.println("==========start==========");
        }
    }

    public static void main(
        String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // 在应用程序启动之前，调用 ByteBuddyAgent.install() 方法来安装代理类
        ByteBuddyAgent.install();
        final Instrumentation inst = ByteBuddyAgent.getInstrumentation();
        Class.forName("io.github.aaronai.example1.Example", true, ClassLoader.getSystemClassLoader())
            .getDeclaredMethod("premain", String.class, Instrumentation.class)
            .invoke(null, "", inst);
        System.out.println("++++++++++++++++++++");
        final Foobar foobar = new Foobar();
        foobar.go();
        // 执行应用程序的 main 方法
    }
}
