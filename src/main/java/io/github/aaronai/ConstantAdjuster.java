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