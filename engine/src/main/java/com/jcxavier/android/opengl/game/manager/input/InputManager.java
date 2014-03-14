package com.jcxavier.android.opengl.game.manager.input;

import android.view.MotionEvent;
import com.jcxavier.android.opengl.game.type.Touchable;
import com.jcxavier.android.opengl.util.WeakList;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * Created on 12/03/2014.
 *
 * @author João Xavier <jcxavier@jcxavier.com>
 */
public final class InputManager {

    private final Deque<MotionEvent> motionEventQueue;
    private final List<Touchable> touchableObjects;

    private final Deque<Touchable> incomingTouchables;
    private final Deque<Touchable> outgoingTouchables;

    public InputManager() {
        motionEventQueue = new ArrayDeque<>();
        touchableObjects = new WeakList<>();

        incomingTouchables = new ArrayDeque<>();
        outgoingTouchables = new ArrayDeque<>();
    }

    public void queueEvent(final MotionEvent event) {
        motionEventQueue.offer(event);
    }

    public void onUpdate() {
        handleIncomingOutgoingTouchables();

        while (!motionEventQueue.isEmpty()) {
            MotionEvent event = motionEventQueue.poll();

            // reverse iteration, as we want to get the first element in Z-order
            for (int i = touchableObjects.size() - 1; i >= 0; i--) {
                Touchable touchable = touchableObjects.get(i);

                if (touchable.canBeTouched() && touchable.isTouchedBy(event) && touchable.onTouch(event)) {
                    // touch was handled, don't proceed further
                    break;
                }
            }
        }
    }

    private void handleIncomingOutgoingTouchables() {
        // add all pending touchables first
        while (!incomingTouchables.isEmpty()) {
            Touchable touchable = incomingTouchables.poll();

            if (!touchableObjects.contains(touchable)) {
                touchableObjects.add(touchable);
            }
        }

        // remove all pending touchables afterwards. If an object is added and removed in the same frame, it will
        // be removed
        while (!outgoingTouchables.isEmpty()) {
            Touchable touchable = outgoingTouchables.poll();

            if (!touchableObjects.contains(touchable)) {
                touchableObjects.remove(touchable);
            }
        }
    }

    public void addManagedObject(final Touchable touchable) {
        incomingTouchables.offer(touchable);
    }

    public void removeManagedObject(final Touchable touchable) {
        outgoingTouchables.offer(touchable);
    }

    public List<Touchable> getTouchedObjects(final MotionEvent event) {
        List<Touchable> touchedObjects = new ArrayList<>();

        for (int i = touchableObjects.size() - 1; i >= 0; i--) {
            Touchable touchable = touchableObjects.get(i);

            if (touchable.canBeTouched() && touchable.isTouchedBy(event) && touchable.onTouch(event)) {
                touchedObjects.add(touchable);
            }
        }

        return touchedObjects;
    }
}
