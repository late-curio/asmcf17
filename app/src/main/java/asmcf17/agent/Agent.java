package asmcf17.agent;

import java.lang.instrument.Instrumentation;

public class Agent {
    public static void premain(String agentArgs, Instrumentation instrumentation) {
        System.out.println("PREMAIN");
        instrumentation.addTransformer((loader, className, classBeingRedefined, protectionDomain, classfileBuffer) -> {
            if (className.startsWith("asmcf17")) {
                System.out.println(className);
            }
            return classfileBuffer;
        });
    }
}
