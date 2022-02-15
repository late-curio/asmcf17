package asmcf17.agent;

import com.google.common.io.ByteStreams;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

import java.io.IOException;
import java.io.InputStream;
import java.lang.instrument.Instrumentation;

import static org.objectweb.asm.Opcodes.ACC_PUBLIC;



public class Agent {
    //private static final String CLASS_DEF = "asmcf17/app/Simple";
    //private static final String CLASS_DEF = "java/util/concurrent/CompletableFuture";

    public static void premain(String agentArgs, Instrumentation instrumentation) {
        System.out.println("PREMAIN");
        System.out.println(agentArgs);
        instrumentation.addTransformer((loader, className, classBeingRedefined, protectionDomain, classfileBuffer) -> {
            if(className.equals(agentArgs)) {
                System.out.println(className);
                InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(agentArgs + ".class");
                if(is == null) {
                    return classfileBuffer;
                }
                byte[] b1 = new byte[0];
                try {
                    b1 = ByteStreams.toByteArray(is);
                    System.out.println("BEFORE:" + b1.length);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ClassWriter cw = new ClassWriter(0);
                ClassVisitor addFieldAdapter = new AddFieldAdapter(cw, ACC_PUBLIC, "aNewBooleanField", "");
                //ClassVisitor addAnnotationAdapter = new AddAnnotationAdapter(addFieldAdapter, "Lasmcf17/agent/InstrumentedClass;");
                ClassReader cr = new ClassReader(b1);
                cr.accept(addFieldAdapter, 0);
                byte[] b2 = cw.toByteArray();
                System.out.println("AFTER: " + b2.length);
                return b2;
            }
            return classfileBuffer;
        });
    }


}
