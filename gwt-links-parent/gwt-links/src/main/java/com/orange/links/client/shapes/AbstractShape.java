package com.orange.links.client.shapes;

import java.util.HashSet;
import java.util.Set;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Widget;
import com.orange.links.client.DiagramController;
import com.orange.links.client.connection.Connection;

public abstract class AbstractShape implements Shape {

    protected Widget widget;
    protected DiagramController controller;

    private int containerOffsetLeft = -1;
    private int containerOffsetTop = -1;
    private int offsetWidth = -1;
    private int offsetHeight = -1;
    private boolean sync;

    private DrawableSet<Connection> connections = new DrawableSet<Connection>();

    public AbstractShape(DiagramController controller, Widget widget) {
        this.widget = widget;
        this.controller = controller;
    }

    public boolean isSynchronized() {
        return sync;
    }

    public void setSynchronized(boolean sync) {
        this.sync = sync;
    }

    public int getLeft() {
        return widget.getAbsoluteLeft() - getContainerOffsetLeft();
    }

    protected int getContainerOffsetLeft() {
        if (containerOffsetLeft < 0) {
            Element parent = DOM.getParent(widget.getElement());
            while (parent != null) {
                if ("relative".equals(DOM.getStyleAttribute(parent, "position"))) {
                    containerOffsetLeft = DOM.getAbsoluteLeft(parent);
                }
                parent = DOM.getParent(parent);
            }
        }
        return containerOffsetLeft;
    }

    public int getTop() {
        return widget.getAbsoluteTop() - getContainerOffsetTop();
    }

    protected int getContainerOffsetTop() {
        if (containerOffsetTop < 0) {
            Element parent = DOM.getParent(widget.getElement());
            while (parent != null) {
                if ("relative".equals(DOM.getStyleAttribute(parent, "position"))) {
                    containerOffsetTop = DOM.getAbsoluteTop(parent);
                }
                parent = DOM.getParent(parent);
            }
        }
        return containerOffsetTop;
    }

    public int getWidth() {
        if (offsetWidth < 0) {
            offsetWidth = widget.getOffsetWidth();
        }
        return offsetWidth;
    }

    public int getHeight() {
        if (offsetHeight < 0) {
            offsetHeight = widget.getOffsetHeight();
        }
        return offsetHeight;
    }

    public boolean addConnection(Connection connection) {
        return connections.add(connection);
    }

    public boolean removeConnection(Connection connection) {
        return connections.remove(connection);
    }

    public Widget asWidget() {
        return widget;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((widget == null) ? 0 : widget.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        AbstractShape other = (AbstractShape) obj;
        if (widget == null) {
            if (other.widget != null)
                return false;
        } else if (!widget.equals(other.widget))
            return false;
        return true;
    }

    public DrawableSet<Connection> getConnections() {
        return connections;
    }

}
