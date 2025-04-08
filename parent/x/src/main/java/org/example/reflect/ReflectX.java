package org.example.reflect;

import org.example.X;
import org.example.function.ActionX;
import org.example.function.FuncX;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public interface ReflectX {
    static Field getDeclaredField(Class<?> cls, String name) {
        return FuncX.sneaky(Class::getDeclaredField, cls, name);
    }

    static Method getDeclaredMethod(Class<?> cls, String name, Class<?>... actualClasses) {
        return FuncX.sneaky(Class::getDeclaredMethod, cls, name, actualClasses);
    }

    static <T> Constructor<T> getDeclaredConstructor(Class<T> cls, Class<?>... actualClasses) {
        return FuncX.sneaky(Class::getDeclaredConstructor, cls, actualClasses);
    }

    static Field getField(Class<?> cls, String name) {
        Field field = FuncX.ignore(Class::getDeclaredField, cls, name);
        if (field != null) {
            return field;
        }
        Class<?> parent;
        for (Class<?> child = cls; child != null; child = parent) {
            parent = child.getSuperclass();
            if (parent != null) {
                field = FuncX.ignore(Class::getDeclaredField, parent, name);
                if (field != null) {
                    return field;
                }
            }
            for (Class<?> item : child.getInterfaces()) {
                field = FuncX.ignore(Class::getDeclaredField, item, name);
                if (field != null) {
                    return field;
                }
            }
        }
        throw X.throwrt(new NoSuchFieldException(name));
    }

    static Method getMethod(Class<?> cls, String name, Class<?>... actualClasses) {
        Method method = FuncX.ignore(Class::getDeclaredMethod, cls, name, actualClasses);
        if (method != null) {
            return method;
        }
        Class<?> parent;
        for (Class<?> child = cls; child != null; child = parent) {
            parent = child.getSuperclass();
            if (parent != null) {
                method = FuncX.ignore(Class::getDeclaredMethod, parent, name, actualClasses);
                if (method != null) {
                    return method;
                }
            }
            for (Class<?> item : child.getInterfaces()) {
                method = FuncX.ignore(Class::getDeclaredMethod, item, name, actualClasses);
                if (method != null) {
                    return method;
                }
            }
        }
        throw X.throwrt(new NoSuchMethodException(name));
    }

    static <T> T getValue(Object obj, Field field) {
        return X.cast(FuncX.sneaky(Field::get, setAccessible(field), obj));
    }

    static <T> T getValue(Object obj, String filedName) {
        return getValue(obj, getField(obj.getClass(), filedName));
    }

    static <T> T setValue(Object obj, Field field, T newValue) {
        T oldValue = getValue(obj, field);
        ActionX.sneaky(Field::set, field, obj, newValue);
        return oldValue;
    }

    static <T> T setValue(Object obj, String fieldName, T newValue) {
        return setValue(obj, getField(obj.getClass(), fieldName), newValue);
    }

    static <T> T invoke(Object obj, Method method, Object... actualArgs) {
        return X.cast(FuncX.sneaky(Method::invoke, setAccessible(method), obj, actualArgs));
    }

    static <T> T invoke(Object obj, String methodName, Object... args) {
        return invoke(obj, getMethod(obj.getClass(), methodName, getActualClasses(args)), getActualArgs(args));
    }

    static <T> T invokeSpecial(Object obj, Class<?> callerClass, Method method, Object... actualArgs) {
        MethodHandle handle = FuncX.sneaky(MethodHandles.Lookup::unreflectSpecial, getLookup(callerClass), method, callerClass);
        return X.cast(FuncX.sneaky(MethodHandle::invokeWithArguments, handle.bindTo(obj), actualArgs));
    }

    static <T> T invokeSpecial(Object obj, Class<?> callerClass, String methodName, Object... args) {
        return invokeSpecial(obj, callerClass, getMethod(callerClass, methodName, getActualClasses(args)), getActualArgs(args));
    }

    static <T> T newInstance(Constructor<T> constructor, Object... actualArgs) {
        return FuncX.sneaky(Constructor::newInstance, setAccessible(constructor), actualArgs);
    }

    static <T> T newInstance(Class<T> cls, Object... args) {
        return newInstance(getDeclaredConstructor(cls, getActualClasses(args)), getActualArgs(args));
    }

    static MethodHandles.Lookup getLookup(Class<?> callerClass) {
        int modes = MethodHandles.Lookup.PUBLIC | MethodHandles.Lookup.PROTECTED | MethodHandles.Lookup.PRIVATE | MethodHandles.Lookup.PACKAGE;
        return newInstance(MethodHandles.Lookup.class, callerClass, TypeHolderX.of(modes, int.class));
    }

    static <T extends AccessibleObject> T setAccessible(T obj) {
        if (!obj.isAccessible()) {
            obj.setAccessible(true);
        }
        return obj;
    }

    static Object[] getActualArgs(Object... args) {
        Object[] actual = new Object[args.length];
        for (int i = 0; i < args.length; i++) {
            Object arg = args[i];
            if (arg instanceof TypeHolder<?>) {
                actual[i] = X.<TypeHolder<?>>cast(arg).getValue();
            } else {
                actual[i] = arg;
            }
        }
        return actual;
    }

    static Class<?>[] getActualClasses(Object... args) {
        Class<?>[] actual = new Class<?>[args.length];
        for (int i = 0; i < args.length; i++) {
            Object arg = args[i];
            if (arg instanceof TypeHolder<?>) {
                actual[i] = X.<TypeHolder<?>>cast(arg).getType();
            } else {
                actual[i] = arg.getClass();
            }
        }
        return actual;
    }

    static Class<?> getCallerClass() {
        return FuncX.sneaky(Class::forName, Thread.currentThread().getStackTrace()[2].getClassName());
    }
}