/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
        // Apply advice to the "go" method of "io.github.aaronai.Foobar"
        transformer.applyAdviceToMethod(isMethod().and(named("go")), FoobarInstrumentation.class.getName() + "$GoAdvice");
    }

    // Define the advice to be applied to the "go" method
    @SuppressWarnings("unused")
    public static class GoAdvice {

        // This method will be invoked before the "go" method
        @Advice.OnMethodEnter(suppress = Throwable.class)
        public static void onStart() {
            logger.info("This method will be invoked before Foobar#go");
        }

        // This method will be invoked after the "go" method
        @Advice.OnMethodExit(suppress = Throwable.class)
        public static void onExit() {
            logger.info("This method will be invoked after Foobar#go");
        }
    }
}
