package me.truongng.journeymapapi.utils;

// build a delivery jounrney for shipper
/*
Input : Set of Location và điểm bắt đầu nhận hàng của Shipper  --> Output : Thứ tự giao hàng cho Shipper

Bài toán TSP 
Với số location <= 20 --> đúng 100%
Với location > 20: Dùng Local Seach 


--> cho location của driver và location các tọa độ mà driver đó cần giao hàng --> đưa ra thứ tự giao hàng tối ưu
 */

import java.util.*;

import me.truongng.journeymapapi.models.Location;

public class Journey {
    Location DriverLocation; // luôn bắt đầu tại DriverLocation(chỗ tổng kho nhận hàng, lấy hàng ở tổng kho
                             // xong đi giao cho bọn kia)

    public Journey(Location DriverLocation) {
        this.DriverLocation = DriverLocation;
    }

    static void swap(int a[], int i, int j) {
        int tmp = a[j];
        a[j] = a[i];
        a[i] = tmp;
    }

    public ArrayList<Location> OrderOfLocations(ArrayList<Location> setOfLocations) {
        ArrayList<Location> orderOfLocations = new ArrayList<Location>();
        int n = setOfLocations.size();
        if (n > 20) { // nho sua cai nay
            // DP bitmask
            double cost[][] = new double[n][n];
            for (int i = 0; i < n; i++)
                for (int j = 0; j < n; j++)
                    if (i != j) // i == j --> Khoảng cách = 0
                        cost[i][j] = ShortestPathTwoLocations.getDistance(setOfLocations.get(i), setOfLocations.get(j));
            double dp[][] = new double[1 << n][n];
            int trace[][] = new int[1 << n][n];
            for (int mask = 0; mask < (1 << n); mask++)
                for (int i = 0; i < n; i++)
                    dp[mask][i] = Double.MAX_VALUE;

            for (int i = 0; i < n; i++) {
                dp[1 << i][i] = ShortestPathTwoLocations.getDistance(DriverLocation, setOfLocations.get(i));
            }

            for (int mask = 0; mask < (1 << n); mask++) {
                ArrayList<Integer> OnBit = new ArrayList<Integer>(), OffBit = new ArrayList<Integer>();
                for (int i = 0; i < n; i++)
                    if (((mask >> i) & 1) == 1)
                        OnBit.add(i);
                    else
                        OffBit.add(i);
                for (int u : OnBit) {
                    if (dp[mask][u] == Double.MAX_VALUE)
                        continue;
                    for (int v : OffBit) {
                        if (dp[mask | (1 << v)][v] > dp[mask][u] + cost[u][v]) {
                            dp[mask | (1 << v)][v] = dp[mask][u] + cost[u][v];
                            trace[mask | (1 << v)][v] = u;
                        }
                    }
                }
            }
            // phải trace back để in ra thứ tự
            // find min solution and trace back
            int u = 0, status = (1 << n) - 1;
            System.out.println(status);
            for (int i = 1; i < n; i++)
                if (dp[status][i] < dp[status][u])
                    u = i;
            System.out.println(dp[status][u]);
            ArrayList<Integer> Order = new ArrayList<Integer>();
            while (status != 0) {
                Order.add(u);
                int tmp = status ^ (1 << u);
                u = trace[status][u];
                status = tmp;
            }
            // System.out.println(n + " " + status);
            // for (int x : Order)
            // System.out.println(x);
            for (int i = n - 1; i >= 0; i--)
                orderOfLocations.add(setOfLocations.get(Order.get(i)));
            // System.out.println(Order.get(i));
        } else {
            // Local Search
            // greedy thứ tự ban đầu
            int Order[] = new int[n + 1];
            boolean visited[] = new boolean[n + 1];
            setOfLocations.add(DriverLocation);
            double cost[][] = new double[n + 1][n + 1];
            for (int i = 0; i <= n; i++)
                for (int j = 0; j <= n; j++)
                    if (i != j)
                        cost[i][j] = ShortestPathTwoLocations.getDistance(setOfLocations.get(i), setOfLocations.get(j));
            visited[n] = true;
            Order[0] = n; // DriverLocation
            for (int i = 1; i <= n; i++) {
                // find Order[i]
                int minPos = -1;
                for (int u = 0; u < n; u++)
                    if (!visited[u]) {
                        if (minPos == -1)
                            minPos = u;
                        else {
                            if (cost[Order[i - 1]][u] < cost[Order[i - 1]][minPos])
                                minPos = u;
                        }
                    }
                visited[minPos] = true;
                Order[i] = minPos;
            }

            // local search + simulated annealing
            double Temperature = 1e15;
            double coolingRate = 0.99999;
            Random random = new Random();

            while (Temperature > 1) {
                // System.out.println(u + " " + v);
                for (int u = 1; u <= n - 2; u++) {
                    for (int v = n - 1; v > u; v--)
                        if (Temperature > 1) {
                            double t1 = cost[Order[u - 1]][Order[u]] + cost[Order[v]][Order[v + 1]]; // currentState
                            double t2 = cost[Order[u - 1]][Order[v]] + cost[Order[u]][Order[v + 1]]; // nextState
                            if (t1 > t2) {
                                for (int i = u, j = v; i <= j; i++, j--) {
                                    swap(Order, i, j);
                                }
                            } else {
                                double deltaE = t1 - t2;
                                double probability = Math.exp(deltaE / Temperature);
                                // System.out.println(random.nextDouble() + " " + probability);
                                if (random.nextDouble() < probability) {
                                    for (int i = u, j = v; i <= j; i++, j--) {
                                        swap(Order, i, j);
                                    }
                                }
                            }
                            Temperature *= coolingRate;
                        }
                }
            }
            // normal local search
            while (true) {
                // optimize
                boolean stop = true;
                for (int u = 1; u <= n - 2; u++) {
                    for (int v = n - 1; v > u; v--) {
                        double t1 = cost[Order[u - 1]][Order[u]] + cost[Order[v]][Order[v + 1]]; // currentState
                        double t2 = cost[Order[u - 1]][Order[v]] + cost[Order[u]][Order[v + 1]]; // nextState
                        if (t1 > t2) {
                            for (int i = u, j = v; i <= j; i++, j--) {
                                swap(Order, i, j);
                            }
                            stop = false;
                        }
                    }
                }
                if (stop)
                    break;

            }

            // answer
            for (int i = 1; i <= n; i++)
                orderOfLocations.add(setOfLocations.get(Order[i]));
        }
        return orderOfLocations;
    }

    // public static void main(String[] args) {
    // GenMap.genGraph();
    // ArrayList<Location> setOfLocations = new ArrayList<Location>();
    // setOfLocations.add(new Location(8, 6));
    // Journey Journey = new Journey(new Location(0, 0));
    // ArrayList<Location> Order = Journey.OrderOfLocations(setOfLocations);
    // for (Location p : Order)
    // System.out.println(p.getX() + " " + p.getY());
    // }
}
