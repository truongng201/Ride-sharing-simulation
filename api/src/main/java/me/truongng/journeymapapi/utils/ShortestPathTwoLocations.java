package me.truongng.journeymapapi.utils;

// tìm đường đi ngắn nhất giữa 2 locations --> Dijkstra Algorithm cải tiến dùng D'Esopo-Pape 

import java.util.*;

import me.truongng.journeymapapi.models.Location;

class Dijkstra {
    static HashMap<Location, Double> dist;

    static class Data implements Comparable<Data> {
        Location u;
        double dist;

        public Data(Location u, double dist) {
            this.u = u;
            this.dist = dist;
        }

        @Override
        public int compareTo(Data o) {
            if (dist > o.dist)
                return 1;
            if (dist < o.dist)
                return -1;
            return 0;
        }
    }

    static double getDistance(Location From, Location To) {
        dist = new HashMap<Location, Double>();
        PriorityQueue<Data> pq = new PriorityQueue<Data>();
        dist.put(From, 0.0);
        pq.offer(new Data(From, 0));
        while (!pq.isEmpty()) {
            Data x = pq.poll();
            if (x.dist != dist.get(x.u))
                continue;
            if (x.u.equals(To))
                return x.dist;

            if (GraphOfSystem.adj.containsKey(x.u)) {
                for (Location v : GraphOfSystem.adj.get(x.u)) {
                    if (!dist.containsKey(v) || dist.get(v) > x.dist + x.u.distanceTo(v)) {
                        dist.put(v, x.dist + x.u.distanceTo(v));
                        pq.offer(new Data(v, dist.get(v)));
                    }
                }
            }
        }
        return Double.MAX_VALUE; // don't have path from : From ---> To
    }

    static List<Location> getPath(Location From, Location To) {
        dist = new HashMap<Location, Double>();
        PriorityQueue<Data> pq = new PriorityQueue<Data>();
        HashMap<Location, Location> trace = new HashMap<Location, Location>();
        dist.put(From, 0.0);
        pq.offer(new Data(From, 0));
        while (!pq.isEmpty()) {
            Data x = pq.poll();
            if (x.dist != dist.get(x.u))
                continue;
            if (x.u.equals(To))
                break;

            if (GraphOfSystem.adj.containsKey(x.u)) {
                for (Location v : GraphOfSystem.adj.get(x.u)) {
                    if (!dist.containsKey(v) || dist.get(v) > x.dist + x.u.distanceTo(v)) {
                        dist.put(v, x.dist + x.u.distanceTo(v));
                        trace.put(v, x.u);
                        pq.offer(new Data(v, dist.get(v)));
                    }
                }
            }
        }
        Stack<Location> st = new Stack<Location>();
        Location u = To;
        while (!u.equals(From)) {
            st.push(u);
            u = trace.get(u);
        }
        st.push(From);
        List<Location> paths = new ArrayList<Location>();
        while (st.size() > 0)
            paths.add(st.pop());
        return paths;
    }
}

// còn thời gian thì implement
class DEsopoPape {

}

public class ShortestPathTwoLocations {

    public static double getDistance(Location From, Location To) {
        return Dijkstra.getDistance(From, To);
    }

    public static List<Location> getPath(Location From, Location To) {
        return Dijkstra.getPath(From, To);
    }

    // public static void main(String[] args) {
    // // testing
    // // new GraphOfSystem(); // init new GraphOfSystem() --> đầu chương trình khởi
    // // tạo cho nó phát
    // GraphOfSystem.addLocationToMap(new Location(0, 1), null, null);
    // GraphOfSystem.addLocationToMap(new Location(0, 2), null, null);
    // GraphOfSystem.addLocationToMap(new Location(0, 5), new
    // ArrayList<Location>(Arrays.asList(new Location(0, 1))),
    // new ArrayList<Location>(Arrays.asList(new Location(0, 2))));
    // GraphOfSystem.addLocationToMap(new Location(0, 4), new
    // ArrayList<Location>(Arrays.asList(new Location(0, 1))),
    // new ArrayList<Location>(Arrays.asList(new Location(0, 2))));
    // System.out.println(getDistance(new Location(0, 1), new Location(0, 2)));
    // }

}
