package org.example;

import org.example.function.Func0;
import org.example.function.FuncX;
import org.example.io.SerializeX;
import org.example.reflect.ReflectX;

import java.io.Serializable;

public interface X {
    @SuppressWarnings("unchecked")
    static <T> T cast(Object obj) {
        return (T) obj;
    }

    static <T extends Throwable> T rethrow(Throwable e) throws T {
        throw X.<T>cast(e);
    }

    static <T extends RuntimeException> T throwrt(Throwable e) {
        throw X.<T>rethrow(e);
    }

    static void assertTrue(boolean expression) {
        assertTrue(expression, IllegalArgumentException::new);
    }

    static <T extends Throwable> void assertTrue(boolean expression, Func0<T> exception) {
        if (!expression) {
            throw X.throwrt(FuncX.sneaky(exception));
        }
    }

    static <T> T defaultIfNull(T obj, T def) {
        return obj != null ? obj : def;
    }

    static <T> T clone(T obj) {
        if (obj instanceof java.lang.Cloneable) {
            return ReflectX.invoke(obj, "clone");
        } else if (obj instanceof Serializable) {
            return cast(SerializeX.clone(cast(obj)));
        }
        throw new IllegalArgumentException();
    }

    static <T> T defaultOf(String typeName) {
        // @formatter:off
        switch (typeName) {
            case "boolean": return cast(false);
            case "char"   : return cast( '\0');
            case "byte"   : return cast((byte)  0);
            case "short"  : return cast((short) 0);
            case "int"    : return cast(0    );
            case "long"   : return cast(0L   );
            case "float"  : return cast(0F   );
            case "double" : return cast(0D   );
            default: return null;
        }
        // @formatter:on
    }

    static <T> Class<T> getPrimitiveClass(String wrapperName) {
        // @formatter:off
        switch (wrapperName) {
            case "Boolean"  : return cast(boolean.class );
            case "Character": return cast(char   .class );
            case "Byte"     : return cast(byte   .class );
            case "Short"    : return cast(short  .class );
            case "Integer"  : return cast(int    .class );
            case "Long"     : return cast(long   .class );
            case "Float"    : return cast(float  .class );
            case "Double"   : return cast(double .class );
            case "Void"     : return cast(void   .class );
            default: throw new IllegalArgumentException();
        }
        // @formatter:on
    }

    static <T> Class<T> getWrapperClass(String primitiveName) {
        // @formatter:off
        switch (primitiveName) {
            case "boolean": return cast(Boolean  .class );
            case "char"   : return cast(Character.class );
            case "byte"   : return cast(Byte     .class );
            case "short"  : return cast(Short    .class );
            case "int"    : return cast(Integer  .class );
            case "long"   : return cast(Long     .class );
            case "float"  : return cast(Float    .class );
            case "double" : return cast(Double   .class );
            case "void"   : return cast(Void     .class );
            default: throw new IllegalArgumentException();
        }
        // @formatter:on
    }
}