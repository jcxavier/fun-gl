package com.jcxavier.android.opengl;

import android.content.res.AssetManager;
import com.jcxavier.android.opengl.engine.EngineActivity;
import com.jcxavier.android.opengl.engine.RendererOptions;
import com.jcxavier.android.opengl.game.Game2D;

/**
 * Created on 11/03/2014.
 *
 * @author João Xavier <jcxavier@jcxavier.com>
 */
public class GameActivity extends EngineActivity {

    @Override
    protected Game2D onGameStart() {
        return null;
    }

    @Override
    protected void onGameSetup() {

    }

    @Override
    protected void onLoadAssets(final AssetManager assetManager) {

    }

    @Override
    protected void onConfigureOptions(final RendererOptions options) {

    }

    @Override
    protected void onGameFinished() {

    }
}
