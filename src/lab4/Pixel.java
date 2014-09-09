package lab4;

import java.awt.Point;

public class Pixel {
	Point position;
	double energyUpToNow;
	Pixel parent;
	
	public Pixel(int x, int y, double energy) {
		position = new Point(x, y);
		parent = null;
		energyUpToNow = energy;
	}
	
	public Pixel(int x, int y, double energy, Pixel parent) {
		position = new Point(x, y);
		this.parent = parent;
		energyUpToNow = energy;
	}
	
	public void updateParent(Pixel newParent) {
		parent = newParent;
	}
	
	public void updateEnergy(double energy) {
		energyUpToNow = energy;
	}
	
	public double getEnergy() {
		return energyUpToNow;
	}
	
	public Pixel getParent() {
		return parent;
	}
	
	public int getPositionX() {
		return position.x;
	}
	
	public int getPositionY() {
		return position.y;
	}
}
