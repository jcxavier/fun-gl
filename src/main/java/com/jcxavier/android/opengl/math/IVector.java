package com.jcxavier.android.opengl.math;

/**
 * Created on 11/03/2014.
 *
 * @author João Xavier <jcxavier@jcxavier.com>
 */
public interface IVector<T extends IVector<T>> {

    /**
     * Sets this vector from the given vector
     *
     * @param _v The vector
     * @return This vector for chaining
     */
    T set(T _v);
}
