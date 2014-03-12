package com.jcxavier.android.opengl.game.manager;

import com.jcxavier.android.opengl.game.manager.input.InputManager;

/**
 * Created on 12/03/2014.
 *
 * @author João Xavier <jcxavier@jcxavier.com>
 */
public interface GameManager {

    InputManager getInputManager();

    ScreenManager getScreenManager();
}
