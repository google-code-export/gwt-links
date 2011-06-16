package com.orange.links.client.connection;

import com.orange.links.client.DiagramController;
import com.orange.links.client.shapes.Shape;

public class ConnectionFactory<C extends Connection> {
	
    public static ConnectionFactory<StraightConnection> STRAIGHT = new ConnectionFactory<StraightConnection>(ConnectionType.PLAIN);
    public static ConnectionFactory<StraightArrowConnection> ARROW = new ConnectionFactory<StraightArrowConnection>(ConnectionType.SINGLE_ARROW);
    
    public static enum ConnectionType { PLAIN, SINGLE_ARROW }
    
    private ConnectionType type;
    
    public ConnectionFactory(ConnectionType type) {
        this.type = type;
    }
    
    @SuppressWarnings("unchecked")
    public C create(DiagramController controller, Shape start, Shape end) {
        switch (type) {
        case PLAIN: 
                return (C) new StraightConnection(controller, start, end);
        case SINGLE_ARROW:
                return (C) new StraightArrowConnection(controller, start, end);
        default:
                throw new UnsupportedOperationException("undefined connection type: " + type);
        }
    }
}
