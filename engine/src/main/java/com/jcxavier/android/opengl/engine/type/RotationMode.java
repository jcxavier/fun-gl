/*
 * Copyright (c) 2003-2013 GameDuell GmbH, All Rights Reserved
 * This document is strictly confidential and sole property of GameDuell GmbH, Berlin, Germany
 */

package com.jcxavier.android.opengl.engine.type;

/**
 * Created on 13/03/2014.
 *
 * @author João Xavier <jcxavier@jcxavier.com>
 */
public enum RotationMode {

    /** Rotation is applied in the X, Y, Z order */
    XYZ,
    /** Rotation is applied in the X, Z, Y order */
    XZY,
    /** Rotation is applied in the Y, X, Z order */
    YXZ,
    /** Rotation is applied in the Y, Z, X order */
    YZX,
    /** Rotation is applied in the Z, X, Y order */
    ZXY,
    /** Rotation is applied in the Z, Y, X order */
    ZYX
}