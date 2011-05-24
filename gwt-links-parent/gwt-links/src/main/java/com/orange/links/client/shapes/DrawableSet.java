package com.orange.links.client.shapes;

import java.util.Collection;
import java.util.HashSet;

/**
 * Drawable composite
 */
@SuppressWarnings("serial")
public class DrawableSet<D extends Drawable> extends HashSet<D> implements Drawable {

    public DrawableSet() {
        super();
    }

    public DrawableSet(Collection<D> c) {
        super(c);
    }

    public DrawableSet(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    public DrawableSet(int initialCapacity) {
        super(initialCapacity);
    }

    @Override
    public void setSynchronized(boolean sync) {
        for (Drawable drawable : this) {
            drawable.setSynchronized(sync);
        }
    }

    @Override
    public boolean isSynchronized() {
        for (Drawable drawable : this) {
            if (!drawable.isSynchronized()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void draw() {
        for (Drawable drawable : this) {
            drawable.draw();
        }
    }

    @Override
    public void drawHighlight() {
        for (Drawable drawable : this) {
            drawable.drawHighlight();
        }
    }

    @Override
    public void allowSynchronized(boolean allowSynchronized) {
        for (Drawable drawable : this) {
            drawable.allowSynchronized(allowSynchronized);
        }
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public DrawableSet<D> getUnsynchronizedDrawables(){
		DrawableSet unsynchronizedSet = new DrawableSet<Drawable>();
    	for (Drawable drawable : this) {
            if(!drawable.isSynchronized())
            	unsynchronizedSet.add(drawable);
        }
    	return unsynchronizedSet;
    }
    

}
