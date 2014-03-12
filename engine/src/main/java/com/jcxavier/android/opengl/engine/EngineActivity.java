package com.jcxavier.android.opengl.engine;

import android.app.Activity;
import android.os.Bundle;
import com.jcxavier.android.opengl.engine.texture.TextureManager;
import com.jcxavier.android.opengl.file.FileManager;
import com.jcxavier.android.opengl.game.GameStage;

/**
 * Created on 31/01/2014.
 *
 * @author João Xavier <jcxavier@jcxavier.com>
 */
public abstract class EngineActivity extends Activity {

    private EngineView mView;

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // file manager setup
        FileManager.getInstance().setAssetManager(getAssets());

        // game setup
        onGameSetup();

        // view configuration
        mView = new EngineView(this);
        onConfigureOptions(mView.mRenderer);
        mView.initializeConfigChooser();
        mView.loadRenderer();

        setContentView(mView);
    }

    @Override
    protected final void onPause() {
        super.onPause();
        mView.onPause();
    }

    @Override
    protected final void onResume() {
        super.onResume();
        mView.onResume();
    }

    @Override
    protected final void onDestroy() {
        // clean up
        mView.mRenderer.clean();
        onGameFinished();
        FileManager.getInstance().setAssetManager(null);

        super.onDestroy();
    }

    void onGlContextLoad() {
        // read the maximum texture size from OpenGL
        TextureManager.getInstance().readMaxTextureSize();

        onLoadAssets();
        GameStage game = onGameStart();

        if (game == null) {
            throw new IllegalStateException("onGameStart() must return a non-null game");
        }

        mView.mRenderer.setGame(game);
    }

    protected abstract GameStage onGameStart();

    protected abstract void onGameSetup();

    protected abstract void onLoadAssets();

    protected abstract void onConfigureOptions(final RendererOptions options);

    protected abstract void onGameFinished();
}
