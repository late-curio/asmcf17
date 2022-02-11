ASM CompleteableFuture on Java 17
=================================

Sample project to test out modifying CompleteableFuture bytecode with ASM in Java 17.

Requires SDK man being installed and a couple exported values for `JDK8` and `JDK17`:

e.g.
```
export JDK8='8.0.292.hs-adpt'
export JDK17='17-open'
```

`./run.sh` - builds and runs with Java 8 and see `aBooleanField` gets added successfully
`./runWithJava17.sh` - builds with Java 8 and runs with Java 17, the field does not appear to be added

