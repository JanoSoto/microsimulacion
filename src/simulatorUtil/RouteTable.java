/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulatorUtil;

import java.util.HashMap;
import processors.Processor;

/**
 *
 * @author JAno
 */
public class RouteTable {

    public HashMap<String, Processor> routeTable;

    public RouteTable(HashMap<String, Processor> routeTable) {
        this.routeTable = routeTable;
    }

    public RouteTable() {
        this.routeTable = null;
    }

    public HashMap<String, Processor> getRouteTable() {
        return routeTable;
    }

    public void setRouteTable(HashMap<String, Processor> routeTable) {
        this.routeTable = routeTable;
    }

}
