package com.yufei.infoExtractor.extractor.entity;



public class Point implements Comparable<Point>{
	
	/**
	 * 
	 */


	private Float x;
	public Point(Float x, Float y) {
		super();
		this.x = x;
		this.y = y;
	}


	public Float getX() {
		return x;
	}


	public void setX(Float x) {
		this.x = x;
	}


	public Float getY() {
		return y;
	}


	public void setY(Float y) {
		this.y = y;
	}


	private Float y;


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