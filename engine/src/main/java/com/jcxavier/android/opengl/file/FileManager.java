package com.jcxavier.android.opengl.file;

import android.content.res.AssetManager;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created on 12/03/2014.
 *
 * @author João Xavier <jcxavier@jcxavier.com>
 */
public final class FileManager {

    private static FileManager sFileManager = null;

    private AssetManager mAssetManager;

    private FileManager() {
    }

    public static FileManager getInstance() {
        if (sFileManager == null) {
            sFileManager = new FileManager();
        }

        return sFileManager;
    }

    public void setAssetManager(final AssetManager assetManager) {
        mAssetManager = assetManager;
    }

    public InputStream readFile(final String filename) throws IOException {
        if (mAssetManager == null) {
            throw new IllegalStateException("There is no valid asset manager from where to read the file.");
        }

        return mAssetManager.open(filename);
    }
}
