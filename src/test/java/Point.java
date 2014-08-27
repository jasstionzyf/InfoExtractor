


public class Point implements Comparable<Point>{
	
	/**
	 * 
	 */
	public Point( int x, int y) {
		super();
		
		this.x = x;
		this.y = y;
	}
	private int x;
	private int y;
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		
		result = prime * result + x;
		result = prime * result + y;
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
		Point other = (Point) obj;


		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}


	@Override
	public int compareTo(Point o) {
		// TODO Auto-generated method stub
		if(x>o.getX()){
			return 1;
		}
		if(x<o.getX()){
			return -1;
			
		}
		else{
			if(y>o.getY()){
				return 1;
			}
			else{
				return -1;	
			}
			
		}


	}
	
	
}