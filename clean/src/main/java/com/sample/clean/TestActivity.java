package com.sample.clean;

import com.jcxavier.android.opengl.engine.EngineActivity;
import com.jcxavier.android.opengl.engine.RendererOptions;
import com.jcxavier.android.opengl.game.GameStage;
import com.jcxavier.android.opengl.game.SimpleGameStage;
import com.jcxavier.android.opengl.game.camera.OrthographicCamera;
import com.jcxavier.android.opengl.math.Vector3;

public class TestActivity extends EngineActivity {

    private SimpleGameStage mStage;

    @Override
    protected void onGameSetup() {
        // creates a basic test stage with a 2D, top-down default orthographic camera
        mStage = new TestStage(new OrthographicCamera());
    }

    @Override
    protected GameStage onGameStart() {
        return mStage;
    }

    @Override
    protected void onLoadAssets() {
        // assets can now be loaded if needed
    }

    @Override
    protected void onConfigureOptions(final RendererOptions options) {
        // configures the renderer with basic options
        options.setBackgroundColor(new Vector3(0.6f, 0.6f, 0.6f));
    }

    @Override
    protected void onGameFinished() {
        // game was finished, application is about to exit
    }
}
