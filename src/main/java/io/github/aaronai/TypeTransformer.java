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
