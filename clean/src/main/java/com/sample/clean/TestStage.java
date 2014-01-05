package com.sample.clean;

import android.graphics.Point;
import com.jcxavier.android.opengl.game.SimpleGameStage;
import com.jcxavier.android.opengl.game.camera.Camera;

class TestStage extends SimpleGameStage {

    public TestStage(final Camera camera) {
        super(camera);

        // create native objects here
    }

    @Override
    public void onLoad() {
        super.onLoad();

        // create game objects here
    }

    @Override
    public void onLayout(Point screenSize) {
        super.onLayout(screenSize);

        // set positions here
    }

    @Override
    public void onUpdate(double dt) {
        super.onUpdate(dt);

        // implement your logic here
    }
}
