package asmcf17.agent;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Type;

import static org.objectweb.asm.Opcodes.ASM4;

public class AddFieldAdapter extends ClassVisitor {
    private final int fAcc;
    private final String fName;
    private final String fDesc;
    private boolean isFieldPresent;

    public AddFieldAdapter(ClassVisitor classVisitor, int fAcc, String fName, String fDesc) {
        super(ASM4, classVisitor);
        this.fAcc = fAcc;
        this.fName = fName;
        this.fDesc = fDesc;
    }

    @Override
    public FieldVisitor visitField(int access, String name, String descriptor, String signature, Object value) {
        if (name.equals(fName)) {
            isFieldPresent = true;
        }
        return cv.visitField(access, name, descriptor, signature, value);
    }

    @Override
    public void visitEnd() {
        if (!isFieldPresent) {
            FieldVisitor fv = cv.visitField(fAcc, fName, Type.BOOLEAN_TYPE.toString(), null, null);
            if (fv != null) {
                fv.visitEnd();
            }
        }
        super.visitEnd();
    }
}
