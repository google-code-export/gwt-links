package com.orange.links.client.menu;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.PopupPanel;
import com.orange.links.client.utils.LinksClientBundle;

public class ContextMenu extends PopupPanel {

    private MenuBar menu = new MenuBar(true);

    public ContextMenu() {
        super(true);
        addStyleName(LinksClientBundle.INSTANCE.css().connectionPopup());        
        add(menu);
        disableBrowserContextMenu(getElement());
    }
    
    /** 
     * Add a MenuItem with the GWT-Links style.
     */
    public MenuItem addItem(MenuItem item) {
        item.addStyleName(LinksClientBundle.INSTANCE.css().connectionPopupItem());
        return menu.addItem(item);
    }

    public MenuItem addItem(String text, Command command) {
        MenuItem item = new MenuItem(text, command);
        return addItem(item);
    }
    
    /**
     * Disable the context menu on a specified element
     * 
     * @param e the element where we want to disable the context menu
     */
    public static native void disableBrowserContextMenu(Element e) /*-{
        e.oncontextmenu = function() { return false; };
    }-*/;

    /**
     * Expose the underlying MenuBar for modification.
     */
    public MenuBar getMenu() {
        return menu;
    }
    
}
