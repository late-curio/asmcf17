/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package asmcf17.app;

import java.io.IOException;
import java.util.Arrays;

public class App {
    public static void main(String[] args) throws IOException {
        System.out.println("MAIN");
        Simple simple = new Simple();
        simple.setField("value");
        Arrays.stream(Simple.class.getDeclaredFields()).forEach((field -> System.out.println(field.getName())));
    }
}

