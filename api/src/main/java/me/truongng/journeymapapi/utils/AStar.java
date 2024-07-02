package me.truongng.journeymapapi.utils;

import java.util.*;

import me.truongng.journeymapapi.models.Location;

/*
	- AStar heuristics để giải quyết traffic jam +\
*/
public class AStarHeuristic {
	
	class Node implements Comparable<Node> {
		Location p;
		double g,h;
		// g là đến hiện tại, h là heuristic --> tính từ node hiện tại đến đích
		Node parent;
		
		public Node(Location p) {
			this.p = p;
			this.g = 0;
			this.h = 0;
			this.parent = null;
		}
		
		public double f() {
			return g + h;
		}
		
		@Override
		public int compareTo(Node other) {
			return Double.compare(this.f(), other.f());
		}
		
		@Override 
		public boolean equals(Object o) {
			if (this == o) return true; // cùng 1 object, hoặc cùng rỗng 
			if (o == null || getClass() != o.getClass()) return false;  // 1 cái rỗng hoặc khác loại class 
			Node x = (Node) o;
			return (p.equals(x.p)); //so giá trị
		}
		 @Override
		 public int hashCode() {
			 return Objects.hash(p.getX(), p.getY()); // hashCode theo giá trị
		 }
	}
	
	public double[][] trafficData;
	
	public AStarHeuristic(List<Location> TrafficJamLocations) {
		int rows = GenMap.getMap().length;
		int cols = GenMap.getMap()[0].length;
		trafficData = new double[rows][cols];
		for (int i = 0; i < rows; i++)
			for (int j = 0; j < cols; j++) {
				trafficData[i][j] = 1; // trạng thái bình thường
			}
		if (TrafficJamLocations != null)
		{
			for (Location p : TrafficJamLocations) {
				trafficData[(int)p.getX()][(int)p.getY()] = Double.POSITIVE_INFINITY;
			
				// những điểm tắc nghẽn
			}
		}
	}	
	
	private double timeHeuristic(Node node, Node goal) {
		//return Math.abs(node.p.getX() - goal.p.getX()) + Math.abs(node.p.getY() - goal.p.getY());
		return Math.sqrt(Math.pow(node.p.getX() - goal.p.getX(), 2) + Math.pow(node.p.getY() - goal.p.getY(), 2));
	}
	
	private List<Location> TraceBack(Node current){
		System.out.println(1);
		List<Location> path = new ArrayList<Location>();
		while (current != null) {
			path.add(current.p);
			current = current.parent;
		}
		Collections.reverse(path);
		return path;
	}
	public List<Location> findPath(Location start, Location goal){
		start = GenMap.modifiedLocation.get(start);
		goal = GenMap.modifiedLocation.get(goal);
		
		Node Start = new Node(start); 
		Node Goal = new Node(goal);
		
		PriorityQueue<Node> openList = new PriorityQueue<Node>(); 
		HashSet<Location> closedList = new HashSet<>();
		HashMap<Location, Double> LocationG = new HashMap<Location, Double>();
		
		Start.h = timeHeuristic(Start, Goal);
		LocationG.put(start, 0.0);
		openList.offer(Start);
		
		while (!openList.isEmpty()) {
			Node current = openList.poll();
			//System.out.println(current.p);
			if (LocationG.get(current.p) != current.g)
				continue;
			if (current.p.getX() == goal.getX() && current.p.getY() == goal.getY())
				return TraceBack(current);
			closedList.add(current.p);
			
			for (Location neighbor: GraphOfSystem.adj.get(current.p)) {
				if (closedList.contains(neighbor))
					continue;
				double tentativeG = current.g + trafficData[(int)neighbor.getX()][(int)neighbor.getY()];
				
				Node Neighbor = new Node(neighbor);
				if (!LocationG.containsKey(neighbor) || tentativeG < LocationG.get(neighbor)) {
					LocationG.put(neighbor, tentativeG);
                    Neighbor.parent = current;
                    Neighbor.g = tentativeG;
                    Neighbor.h = timeHeuristic(Neighbor, Goal);
                    openList.offer(Neighbor);
                }
			}
		}
		return new ArrayList<>(); // không có cách nào tránh đường tắc ?????????
	}
	
	public static void main(String[] args) {
		GenMap.genGraph();
		AStarHeuristic AStar = new AStarHeuristic(Arrays.asList(new Location(1, 0)));
		List<Location> answer = AStar.findPath(new Location(0, 0), new Location(2, 1));
		for (Location p : answer)
			System.out.println(p);
	}
}
