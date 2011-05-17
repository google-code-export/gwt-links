package com.orange.links.client.menu;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.PopupPanel;
import com.orange.links.client.utils.LinksClientBundle;

public class ContextMenu extends PopupPanel {

    private MenuBar menu = new MenuBar(true);

    public ContextMenu() {
        super(true);
        setStyleName(LinksClientBundle.INSTANCE.css().connectionPopup());        
        add(menu);
        disableBrowserContextMenu(getElement());
    }
    
    public MenuItem addItem(MenuItem item) {
        return menu.addItem(item);
    }
    
    /**
     * Disable the context menu on a specified element
     * 
     * @param e the element where we want to disable the context menu
     */
    public static native void disableBrowserContextMenu(Element e) /*-{
        e.oncontextmenu = function() { return false; };
    }-*/;
    
    
}
