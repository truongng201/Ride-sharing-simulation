package me.truongng.journeymapapi.utils;

import java.util.*;

import me.truongng.journeymapapi.models.Location;

// lưu danh sách kề của đồ thị;
public class GraphOfSystem {
    static HashMap<Location, ArrayList<Location>> adj = new HashMap<Location, ArrayList<Location>>();

    public static void addUnDirectedEdge(Location from, Location to) {
        if (!adj.containsKey(from)) {
            adj.put(from, new ArrayList<>());
        }
        if (!adj.containsKey(to)) {
            adj.put(to, new ArrayList<>());
        }
        adj.get(from).add(to);
        adj.get(to).add(from); // Since it's undirected
    }

    // ToP là danh sách những Location có đường nối tới P
    // FromP là danh sách những Location từ P có đường nối tới
    public static void addLocationToMap(Location p, ArrayList<Location> ToP, ArrayList<Location> FromP) {
        adj.put(p, new ArrayList<Location>());
        if (ToP != null) {
            for (Location From : ToP)
                adj.get(From).add(p);
        }
        if (FromP != null) {
            for (Location To : FromP)
                adj.get(p).add(To);
        }
    }
}
