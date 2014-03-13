package com.jcxavier.android.opengl.util;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created on 13/03/2014.
 *
 * @author João Xavier <jcxavier@jcxavier.com>
 */
public final class BitmapUtils {

    private BitmapUtils() {
        // can't be instantiated
    }

    /**
     * Reads an InputStream into a byte array.
     *
     * @param inputStream the InputStream containing the binary data to read
     * @return the binary data read as a byte array
     * @throws java.io.IOException if a problem in reading from the buffer occurs
     */
    public static byte[] readInputStreamAsByteArray(InputStream inputStream) throws IOException {
        byte[] byteArr = new byte[0];
        byte[] buffer = new byte[8192];
        int len;
        int count = 0;

        while ((len = inputStream.read(buffer)) > -1) {
            if (len != 0) {
                if (count + len > byteArr.length) {
                    byte[] newbuf = new byte[(count + len) * 2];
                    System.arraycopy(byteArr, 0, newbuf, 0, count);
                    byteArr = newbuf;
                }

                System.arraycopy(buffer, 0, byteArr, count, len);
                count += len;
            }
        }

        return byteArr;
    }
}
