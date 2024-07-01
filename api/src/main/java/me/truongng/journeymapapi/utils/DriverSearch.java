package me.truongng.journeymapapi.utils;

import java.util.*;

// từ đầu vào phân vùng cho tập tài xế luôn 
/*
 Tìm driver tối ưu khi có một khách hàng đặt xe, tìm tài xế trong vùng đó có (số km đã đi + số tiền kiếm được / 10k) min 
 ==> tức là vừa di chuyển ít và vừa có lợi nhuận ít
 -- giá trung bình 1km của grab là 10k vnd 
*/

import me.truongng.journeymapapi.models.Driver;
import me.truongng.journeymapapi.models.Location;
import me.truongng.journeymapapi.models.Customer;

public class DriverSearch {

    private Map<Driver, TreeSet<Driver>> clusterings;

    public DriverSearch(Map<Driver, List<Driver>> clusterings) {
        // tạo clusterings riêng cho driver search compare theo so sánh được viết ở trên
        this.clusterings = new HashMap<Driver, TreeSet<Driver>>();
        for (Driver centroids : clusterings.keySet()) {
            this.clusterings.put(centroids, new TreeSet<Driver>(new Comparator<Driver>() {
                @Override
                public int compare(Driver d1, Driver d2) {
                    if (Double.compare(d1.getKilometersDriven() + d1.getRevenue() / 1e4,
                            d2.getKilometersDriven() + d2.getRevenue() / 1e4) == 0)
                        return -1;
                    return Double.compare(d1.getKilometersDriven() + d1.getRevenue() / 1e4,
                            d2.getKilometersDriven() + d2.getRevenue() / 1e4);
                }
            }));
            for (Driver drivers : clusterings.get(centroids)) {
                this.clusterings.get(centroids).add(drivers);
            }
        }
    }

    public void addDriver(Driver driver) {
        // thêm lại tài xế vào danh sách tài xế đang free
        // tìm centroids mà gần thằng tài xế đó nhất sau đó add vào
        Driver nearestCentroids = new Driver();
        for (Driver centroids : clusterings.keySet()) {
            if (nearestCentroids == null ||
                    centroids.getLocation().distanceTo(driver.getLocation()) < nearestCentroids.getLocation()
                            .distanceTo(driver.getLocation()))
                nearestCentroids = centroids;
        }
        clusterings.get(nearestCentroids).add(driver);
    }

    public Driver findBestDriver(Customer customer) {
        // tìm tài xế tối ưu cho khách hàng
        Driver nearestCentroids = new Driver();
        for (Driver centroids : clusterings.keySet()) {
            if (nearestCentroids == null ||
                    centroids.getLocation().distanceTo(customer.getLocation()) < nearestCentroids.getLocation()
                            .distanceTo(customer.getLocation()))
                nearestCentroids = centroids;
        }
        // nếu trong clusterings không có thằng nào tức là không tìm được tài xế ở xung
        // quanh nó --> trả về null
        if (clusterings.isEmpty())
            return null;
        return clusterings.get(nearestCentroids).pollFirst();
    }

    // public static void main(String[] args) {
    // List<Driver> Drivers = Arrays.asList(
    // new Driver(new Location(1, 1)),
    // new Driver(new Location(2, 1)),
    // new Driver(new Location(4, 3)),
    // new Driver(new Location(5, 4)),
    // new Driver(new Location(10, 5)),
    // new Driver(new Location(7, 4)),
    // new Driver(new Location(6, 3)),
    // new Driver(new Location(4, 4)));
    // // 2 dòng đầu là để phân vùng tài xế
    // KMeansClustering KMeans = new KMeansClustering(3, Drivers);
    // KMeans.run(100, 0.01);
    // DriverSearch driverSearch = new DriverSearch(KMeans.getClusters());
    // // for (Driver Centroids : KMeans.getClusters().keySet()) {
    // // for (Driver driver : KMeans.getClusters().get(Centroids))
    // // System.out.println("Centroids " + Centroids.getLocation() + " Driver " +
    // // driver.getLocation());
    // // System.out.println(KMeans.getClusters().get(Centroids).size());
    // // }
    // System.out.println(KMeans.getClusters().keySet().size());
    // for (Driver Centroids : driverSearch.clusterings.keySet()) {
    // for (Driver driver : driverSearch.clusterings.get(Centroids))
    // System.out.println("Centroids " + Centroids.getLocation() + " Driver " +
    // driver.getLocation());
    // System.out.println(driverSearch.clusterings.get(Centroids).size());
    // }

    // }

}