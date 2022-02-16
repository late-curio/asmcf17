package asmcf17.agent;

import com.google.common.io.ByteStreams;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.instrument.Instrumentation;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.objectweb.asm.Opcodes.ACC_PUBLIC;



public class Agent {
    //private static final String CLASS_DEF = "asmcf17/app/Simple";
    //private static final String CLASS_DEF = "java/util/concurrent/CompletableFuture";

    public static void premain(String agentArgs, Instrumentation instrumentation) {
        System.out.println("PREMAIN");
        System.out.println(agentArgs);
        instrumentation.addTransformer((loader, className, classBeingRedefined, protectionDomain, classfileBuffer) -> {
            //System.out.println("Checking..." + className);
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
                    Files.write(Paths.get("/Users/tcrone/temp/Original.class"), b1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ClassWriter cw = new ClassWriter(0);
                ClassVisitor addWeaveAnnotationAdapter = new AddAnnotationAdapter(cw, "Lasmcf17/agent/Weave;");
                ClassVisitor addInstrumentedClassAnnotationAdapter = new AddAnnotationAdapter(addWeaveAnnotationAdapter, "Lasmcf17/agent/InstrumentedClass;");
                ClassVisitor addFieldAdapter = new AddFieldAdapter(addInstrumentedClassAnnotationAdapter, ACC_PUBLIC, "aNewBooleanField", "");
                ClassReader cr = new ClassReader(b1);
                try {
                    cr.accept(addFieldAdapter, 0);
                } catch (Exception e) {
                    e.printStackTrace(System.err);
                }
                byte[] b2 = cw.toByteArray();
                try {
                    Files.write(Paths.get("/Users/tcrone/temp/Modified.class"), b2);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                System.out.println("AFTER: " + b2.length);
                return b2;
            }
            return classfileBuffer;
        });
    }
}
