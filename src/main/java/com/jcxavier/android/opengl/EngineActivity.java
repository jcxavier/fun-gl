package com.jcxavier.android.opengl;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created on 31/01/2014.
 *
 * @author João Xavier <jcxavier@jcxavier.com>
 */
public class EngineActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new EngineView(this));
    }
}
