package com.jcxavier.android.opengl.util;

import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created on 11/03/2014.
 *
 * @author João Xavier <jcxavier@jcxavier.com>
 */
public final class ReflectionUtils {

    private static final String TAG = ReflectionUtils.class.getSimpleName();

    private ReflectionUtils() {
        // can't be instantiated
    }

    /**
     * Attempts to invoke the given method with the given arguments in the given receiver object.
     *
     * @param method    the method to invoke
     * @param receiver  the receiver object
     * @param arguments the argument list to pass to the method
     */
    public static void invokeMethod(final Method method, final Object receiver, final Object... arguments) {
        try {
            method.invoke(receiver, arguments);
        } catch (IllegalAccessException | InvocationTargetException e) {
            Log.e(TAG, e.getMessage() + "");
        }
    }

    /**
     * Attempts to retrieve a public method in the given class from its name and parameter types.
     *
     * @param methodName     the name of the method
     * @param source         the class where to search for the method
     * @param parameterTypes a list with the class type of parameters to expect
     * @return the method representation as a {@link Method} object, or {@code null} if the method was not found
     */
    public static Method getMethodFromName(final String methodName, final Class<?> source, final Class<?>... parameterTypes) {
        try {
            return source.getDeclaredMethod(methodName, parameterTypes);
        } catch (NoSuchMethodException e) {
            Log.e(TAG, e.getMessage() + "");
        }

        return null;
    }

    /**
     * Attempts to create a class of the given type T, containing arguments of the type A. Can be used with Void
     * argument class types for constructors without parameters. Will only work in constructors that only take one type
     * of class as arguments. The constructor of the target class must be declared as public, otherwise it won't be
     * reachable. If the class to construct is an inner class, it needs to be static for it to be found.
     *
     * @param typeClass the class type of the class to instantiate
     * @param argClass  the class type of the argument of the constructor. If it is null or Void, a constructor with no
     *                  arguments is called, even if an argument list is passed.
     * @param arguments the list of arguments of type A (optional, or 1 element)
     * @param <T>       the type class to instantiate
     * @param <A>       the argument class to pass as parameter
     * @return the instantiated class, or null if an exception was thrown while trying to instantiate the class
     */
    @SafeVarargs
    public static <T, A> T makeObjectOfType(Class<T> typeClass, Class<A> argClass, A... arguments) {
        try {
            if (argClass == null || argClass == Void.class) {
                return typeClass.getDeclaredConstructor().newInstance();
            } else {
                return typeClass.getDeclaredConstructor(argClass).newInstance(arguments);
            }
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            Log.e(TAG, e.getMessage() + "");
        }

        return null;
    }
}
