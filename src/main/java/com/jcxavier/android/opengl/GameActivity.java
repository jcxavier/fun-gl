package com.jcxavier.android.opengl;

import android.content.res.AssetManager;
import android.graphics.Point;
import com.jcxavier.android.opengl.engine.EngineActivity;
import com.jcxavier.android.opengl.engine.RendererOptions;
import com.jcxavier.android.opengl.game.Game2D;
import com.jcxavier.android.opengl.math.Vector3;

/**
 * Created on 11/03/2014.
 *
 * @author João Xavier <jcxavier@jcxavier.com>
 */
public class GameActivity extends EngineActivity {

    @Override
    protected Game2D onGameStart() {
        return new Game2D() {
            @Override
            public void onLoad() {

            }

            @Override
            public void onLayout(final Point screenSize) {

            }

            @Override
            public void onUpdate(final double dt) {

            }

            @Override
            public void onDraw() {

            }
        };
    }

    @Override
    protected void onGameSetup() {

    }

    @Override
    protected void onLoadAssets(final AssetManager assetManager) {

    }

    @Override
    protected void onConfigureOptions(final RendererOptions options) {
        options.setBackgroundColor(new Vector3(1.0f, 0.0f, 0.0f));
        options.setDepthBufferSize(0);
        options.setStencilBufferSize(0);
        options.setSamples(0);
    }

    @Override
    protected void onGameFinished() {

    }
}
