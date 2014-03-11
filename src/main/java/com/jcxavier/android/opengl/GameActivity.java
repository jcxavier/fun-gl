package com.jcxavier.android.opengl;

import android.content.res.AssetManager;
import com.jcxavier.android.opengl.engine.EngineActivity;
import com.jcxavier.android.opengl.engine.RendererOptions;
import com.jcxavier.android.opengl.game.GameStage;
import com.jcxavier.android.opengl.game.SimpleGameStage;
import com.jcxavier.android.opengl.game.camera.OrtographicCamera;
import com.jcxavier.android.opengl.math.Vector3;

/**
 * Created on 11/03/2014.
 *
 * @author João Xavier <jcxavier@jcxavier.com>
 */
public class GameActivity extends EngineActivity {

    @Override
    protected GameStage onGameStart() {
        return new SimpleGameStage(new OrtographicCamera());
    }

    @Override
    protected void onGameSetup() {

    }

    @Override
    protected void onLoadAssets(final AssetManager assetManager) {

    }

    @Override
    protected void onConfigureOptions(final RendererOptions options) {
        options.setBackgroundColor(new Vector3(0.1f, 0.2f, 0.3f));
        options.setDepthBufferSize(0);
        options.setStencilBufferSize(0);
        options.setSamples(0);
    }

    @Override
    protected void onGameFinished() {

    }
}
