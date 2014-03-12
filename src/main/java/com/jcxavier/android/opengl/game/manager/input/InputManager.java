package com.jcxavier.android.opengl.game.manager.input;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.view.MotionEvent;
import com.jcxavier.android.opengl.game.type.Touchable;
import com.jcxavier.android.opengl.util.WeakList;

/**
 * Created on 12/03/2014.
 *
 * @author João Xavier <jcxavier@jcxavier.com>
 */
public final class InputManager {

    private final Deque<MotionEvent> motionEventQueue;
    private final List<Touchable> touchableObjects;

    private final Set<Touchable> incomingTouchables;
    private final Set<Touchable> outgoingTouchables;

    public InputManager() {
        motionEventQueue = new ArrayDeque<>();
        touchableObjects = new WeakList<>();

        incomingTouchables = new HashSet<>();
        outgoingTouchables = new HashSet<>();
    }

    public void queueEvent(final MotionEvent event) {
        motionEventQueue.offer(event);
    }

    public void onUpdate() {
        handleIncomingOutgoingTouchables();

        while (!motionEventQueue.isEmpty()) {
            MotionEvent event = motionEventQueue.poll();

            for (Touchable touchable : touchableObjects) {
                if (touchable.canBeTouched() && touchable.isTouchedBy(event) && touchable.onTouch(event)) {
                    // touch was handled, don't proceed further
                    break;
                }
            }
        }
    }

    private void handleIncomingOutgoingTouchables() {
        incomingTouchables.removeAll(outgoingTouchables);

        for (Touchable touchable : incomingTouchables) {
            if (!touchableObjects.contains(touchable)) {
                touchableObjects.add(touchable);
            }
        }

        for (Touchable touchable : outgoingTouchables) {
            if (!touchableObjects.contains(touchable)) {
                touchableObjects.remove(touchable);
            }
        }

        incomingTouchables.clear();
        outgoingTouchables.clear();
    }

    public void addManagedObject(final Touchable touchable) {
        incomingTouchables.add(touchable);
    }

    public void removeManagedObject(final Touchable touchable) {
        outgoingTouchables.add(touchable);
    }

    public List<Touchable> getTouchedObjects(final MotionEvent event) {
        List<Touchable> touchedObjects = new ArrayList<>();

        for (Touchable touchable : touchableObjects) {
            if (touchable.canBeTouched() && touchable.isTouchedBy(event) && touchable.onTouch(event)) {
                touchedObjects.add(touchable);
            }
        }

        return touchedObjects;
    }
}
