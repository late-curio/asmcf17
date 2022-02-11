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
    public static void premain(String agentArgs, Instrumentation instrumentation) {
        System.out.println("PREMAIN");
        instrumentation.addTransformer((loader, className, classBeingRedefined, protectionDomain, classfileBuffer) -> {
            //System.out.println(className);
            if (className.equals("java/util/concurrent/CompletableFuture")) {
                ClassPrinter printer = new ClassPrinter();
                ClassReader reader = null;
                try {
                    reader = new ClassReader("java.util.concurrent.CompletableFuture");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                reader.accept(printer, 0);

                InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("java/util/concurrent/CompletableFuture.class");
                if(is == null) {

                    return classfileBuffer;
                }
                byte[] b1 = new byte[0];
                try {
                    b1 = ByteStreams.toByteArray(is);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ClassWriter cw = new ClassWriter(0);
                ClassVisitor addFieldAdapter = new AddFieldAdapter(cw, ACC_PUBLIC, "aNewBooleanField", "");
                ClassReader cr = new ClassReader(b1);
                cr.accept(addFieldAdapter, 0);
                return cw.toByteArray();
            }
            return classfileBuffer;
        });
    }


}
