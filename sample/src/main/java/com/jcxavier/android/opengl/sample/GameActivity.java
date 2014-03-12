package com.jcxavier.android.opengl.sample;

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

    private SimpleGameStage stage;

    @Override
    protected void onGameSetup() {
        // creates a basic test stage with a 2D, top-down default ortographic camera
        stage = new TestStage(new OrtographicCamera());
    }

    @Override
    protected GameStage onGameStart() {
        return stage;
    }

    @Override
    protected void onLoadAssets(final AssetManager assetManager) {

    }

    @Override
    protected void onConfigureOptions(final RendererOptions options) {
        // configures the renderer with basic options
        options.setBackgroundColor(new Vector3(0.1f, 0.2f, 0.3f));
        options.setDepthBufferSize(0);
        options.setStencilBufferSize(0);
        options.setSamples(0);
    }

    @Override
    protected void onGameFinished() {

    }
}
