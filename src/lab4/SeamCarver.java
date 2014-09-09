package lab4;

import java.awt.Color;
import java.awt.Point;

import lab4.edu.neumont.ui.Picture;

public class SeamCarver {

	private Picture pic;
	public SeamCarver(Picture pic) {
		this.pic = pic;
	}
	
	public Picture getPicture() {
		return pic;
	}
	
	public int width() {
		return pic.width();
	}
	
	public int height() {
		return pic.height();
	}
	
	public double energy(int x, int y) {
		if (x < 0 || x >= width())  throw new IndexOutOfBoundsException("x must be between 0 and " + (width()-1));
        if (y < 0 || y >= height()) throw new IndexOutOfBoundsException("y must be between 0 and " + (height()-1));
		
		double totalEnergy = 0, horizontalEnergy = 0, verticalEnergy = 0;
		int leftX = x - 1, rightX = x + 1, topY = y - 1, bottomY = y + 1;
		
		if(x == 0)
			leftX = width() - 1;
		if(x == width() - 1)
			rightX = 0;
		if(y == 0)
			topY = height() - 1;
		if(y == height() - 1)
			bottomY = 0;
		
		Color left = pic.get(leftX, y);
		Color right = pic.get(rightX, y);
		Color top = pic.get(x, topY);
		Color bottom = pic.get(x, bottomY);
		
		double red   = Math.pow(left.getRed() - right.getRed(), 2);
		double green = Math.pow(left.getGreen() - right.getGreen(), 2);
		double blue  = Math.pow(left.getBlue() - right.getBlue(), 2);
		horizontalEnergy = red + green + blue;
		
		red   = Math.pow(top.getRed() - bottom.getRed(), 2);
		green = Math.pow(top.getGreen() - bottom.getGreen(), 2);
		blue  = Math.pow(top.getBlue() - bottom.getBlue(), 2);
		verticalEnergy = red + green + blue;
		
		totalEnergy = horizontalEnergy + verticalEnergy;
		return totalEnergy;
	}
	
	public int[] findHorizontalSeam() {
		Pixel[][] energyValues = new Pixel[width()][height()];
		int[] selectedSeam = new int[width()];
		for(int j = 0; j < width() - 1; j ++) {
			for(int i = 0; i < height(); i++) {
				if(j == 0)
					energyValues[j][i] = new Pixel(j, i, energy(j, i));
				
				int nextColumn = j + 1;
				int bot = i + 1, top = i - 1;
				if(i > 0) {
					double energyLeft = energy(nextColumn, top);
					if(energyValues[nextColumn][top] == null)
						energyValues[nextColumn][top] = new Pixel(nextColumn, top, energyLeft + energyValues[j][i].getEnergy(), energyValues[j][i]);
					else {
						double totalEnergy = energyLeft + energyValues[j][i].getEnergy(); 
						if(totalEnergy < energyValues[nextColumn][top].getEnergy()) {
							energyValues[nextColumn][top].updateEnergy(totalEnergy);
							energyValues[nextColumn][top].updateParent(energyValues[j][i]);
						}
					}
						
				}
				
				double energy = energy(nextColumn, i);
				if(energyValues[nextColumn][i] == null)
					energyValues[nextColumn][i] = new Pixel(nextColumn, i, energy + energyValues[j][i].getEnergy(), energyValues[j][i]);
				else {
					double totalEnergy = energy + energyValues[j][i].getEnergy();
					if(totalEnergy < energyValues[j][i].getEnergy()) {
						energyValues[nextColumn][i].updateEnergy(totalEnergy);
						energyValues[nextColumn][i].updateParent(energyValues[j][i]);
					}
				}
				
				if(i < height() - 1) {
					double energyRight = energy(nextColumn, bot);
					if(energyValues[nextColumn][bot] == null)
						energyValues[nextColumn][bot] = new Pixel(nextColumn, bot, energyRight + energyValues[j][i].getEnergy(), energyValues[j][i]);
					else {
						double totalEnergy = energyRight + energyValues[j][i].getEnergy();
						if(totalEnergy < energyValues[nextColumn][bot].getEnergy()) {
							energyValues[nextColumn][bot].updateEnergy(totalEnergy);
							energyValues[nextColumn][bot].updateParent(energyValues[j][i]);
						}
					}
				}
			}
		}
		Pixel lowestEnergy = energyValues[width() - 1][0];
		for(int i = 1; i < height(); i++) {
			if(energyValues[width() - 1][i].getEnergy() < lowestEnergy.getEnergy())
				lowestEnergy = energyValues[width() - 1][i];
		}
		int index = width() - 1;
		while(index >= 0) {
			selectedSeam[index] = lowestEnergy.getPositionY();
			lowestEnergy = lowestEnergy.getParent();
			index--;
		}
		
		return selectedSeam;
	}
	
	public int[] findVerticalSeam() {
		Pixel[][] energyValues = new Pixel[width()][height()];
		int[] selectedSeam = new int[height()];
		for(int i = 0; i < height() - 1; i ++) {
			for(int j = 0; j < width(); j++) {
				if(i == 0)
					energyValues[j][i] = new Pixel(j, i, energy(j, i));
				
				int nextRow = i + 1;
				int left = j - 1, right = j + 1;
				if(j > 0) {
					double energyLeft = energy(left, nextRow);
					if(energyValues[left][nextRow] == null)
						energyValues[left][nextRow] = new Pixel(left, nextRow, energyLeft + energyValues[j][i].getEnergy(), energyValues[j][i]);
					else {
						double totalEnergy = energyLeft + energyValues[j][i].getEnergy(); 
						if(totalEnergy < energyValues[left][nextRow].getEnergy()) {
							energyValues[left][nextRow].updateEnergy(totalEnergy);
							energyValues[left][nextRow].updateParent(energyValues[j][i]);
						}
					}
						
				}
				
				double energy = energy(j, nextRow);
				if(energyValues[j][nextRow] == null)
					energyValues[j][nextRow] = new Pixel(j, nextRow, energy + energyValues[j][i].getEnergy(), energyValues[j][i]);
				else {
					double totalEnergy = energy + energyValues[j][i].getEnergy();
					if(totalEnergy < energyValues[j][nextRow].getEnergy()) {
						energyValues[j][nextRow].updateEnergy(totalEnergy);
						energyValues[j][nextRow].updateParent(energyValues[j][i]);
					}
				}
				
				if(j < width() - 1) {
					double energyRight = energy(right, nextRow);
					if(energyValues[right][nextRow] == null)
						energyValues[right][nextRow] = new Pixel(right, nextRow, energyRight + energyValues[j][i].getEnergy(), energyValues[j][i]);
					else {
						double totalEnergy = energyRight + energyValues[j][i].getEnergy();
						if(totalEnergy < energyValues[right][nextRow].getEnergy()) {
							energyValues[right][nextRow].updateEnergy(totalEnergy);
							energyValues[right][nextRow].updateParent(energyValues[j][i]);
						}
					}
				}
			}
		}
		Pixel lowestEnergy = energyValues[0][height() - 1];
		for(int i = 1; i < width(); i++) {
			if(energyValues[i][height() - 1].getEnergy() < lowestEnergy.getEnergy())
				lowestEnergy = energyValues[i][height() - 1];
		}
		int index = height() - 1;
		while(index >= 0) {
			selectedSeam[index] = lowestEnergy.getPositionX();
			lowestEnergy = lowestEnergy.getParent();
			index--;
		}
		
		return selectedSeam;
	}
	
	public void removeHorizontalSeam(int[] indices) {
		if(indices.length != width() || width() == 1) throw new IllegalArgumentException("There was a problem");
		Picture newPicture = new Picture(width(), height() - 1);
		
		for(int x = 0; x < width(); x++) {
			int offset = 0;
			for(int y = 0; y < height(); y++) {
				if (indices[x] < 0 || indices[x] >= height())  throw new IndexOutOfBoundsException("Cannot remove out of bounds pixel");
				
				if(indices[x] != y) 
					newPicture.set(x, y - offset, pic.get(x,  y));
				else {
					offset++;
					
					if(x > 0) {
						Point one = new Point(indices[x], x);
						Point two = new Point(indices[x - 1], x - 1);
						double distance = Math.sqrt(Math.pow(one.x - two.x, 2) + Math.pow(one.y - two.y, 2));
						if(distance != 1 && distance != Math.sqrt(2)) throw new IllegalArgumentException("Pixels are too far apart to be removed.");
					}
				}
			}
		}
		
		pic = newPicture;
		newPicture.save("modified2.png");
	}
	
	public void removeVerticalSeam(int[] indices) {
		if(indices.length != height() || height() == 1) throw new IllegalArgumentException("There was a problem");
		Picture newPicture = new Picture(width() - 1, height());
		
		for(int y = 0; y < height(); y++) {
			int offset = 0;
			for(int x = 0; x < width(); x++) {
				if (indices[y] < 0 || indices[y] >= width())  throw new IndexOutOfBoundsException("Cannot remove out of bounds pixel");
				
				if(indices[y] != x) 
					newPicture.set(x - offset, y, pic.get(x,  y));
				else {
					offset++;
					
					if(y > 0) {
						Point one = new Point(indices[y], y);
						Point two = new Point(indices[y - 1], y - 1);
						double distance = Math.sqrt(Math.pow(one.x - two.x, 2) + Math.pow(one.y - two.y, 2));
						if(distance != 1 && distance != Math.sqrt(2)) throw new IllegalArgumentException("Pixels are too far apart to be removed.");
					}
				}
			}
		}
		
		pic = newPicture;
		newPicture.save("modified2.png");
	}
}
