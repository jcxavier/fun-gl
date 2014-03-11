package com.jcxavier.android.opengl.engine;

import android.app.Activity;
import android.content.res.AssetManager;
import android.os.Bundle;
import com.jcxavier.android.opengl.game.Game2D;

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

        onGameSetup();

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
        onGameFinished();
        super.onDestroy();
    }

    void onGlContextLoad() {
        onLoadAssets(getAssets());
        Game2D game = onGameStart();

        if (game == null) {
            throw new IllegalStateException("onGameStart() must return a non-null game");
        }

        mView.mRenderer.setGame(game);
    }

    protected abstract Game2D onGameStart();

    protected abstract void onGameSetup();

    protected abstract void onLoadAssets(final AssetManager assetManager);

    protected abstract void onConfigureOptions(final RendererOptions options);

    protected abstract void onGameFinished();
}
