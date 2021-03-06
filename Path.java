import java.awt.Point;
import java.util.ArrayList;

public class Path {
	private ArrayList<Point> path = new ArrayList<Point>();
	//A set of points describing the ECG path
	private int length;
	//The total length of one ECG cycle (determines blank space)
	public Path(String s)
	//String s is the type of ECG cycle to show
	{
		Point[] pWave = null, qrsComplex = null, tWave = null;
		if (s.equals("sinus rhythm"))
		{
			//Set up pWave, qrsComplex, and tWave
			Point[] p = {new Point(0,0), new Point(25,5), new Point(32,0)};
			Point[] qrs = {new Point(35,0), new Point(38,-5), new Point(42,46), 
					new Point(44,-11), new Point(48,0)};
			Point[] t = {new Point(61,0), new Point(71,8), new Point(78,0)};
			
			pWave = p;
			qrsComplex = qrs;
			tWave = t;
			this.length = 100;
		}
		
		//Applies a curve to the otherwise triangular pWave and tWave (see below)
		pWave = curve(pWave);
		tWave = curve(tWave);
		
		//Finalizes (see below)
		finalize(pWave, qrsComplex, tWave);
		
	}
	
	/**
	 * @return a list of points representing the path
	 */
	public ArrayList<Point> getPath()
	{
		return path;
	}
	
	/**
	 * @return the length of one ECG cycle 
	 */
	public int getLength()
	{
		return length;
	}
	
	/**
	 * @return the points in the path printed to the console
	 * @category debug
	 */
	public void print()
	{
		for(Object a: path)
			System.out.print(a + " ");
		System.out.println();
	}
	
	/**
	 * @param a the first value
	 * @param b the second value
	 * @param n the amount to interpolate
	 * @return the linear interpolation of two input values by a given amount
	 * @see https://docs.yoyogames.com/source/dadiospice/002_reference/maths/real%20valued%20functions/lerp.html
	 */
	public static int lerp(int a, int b, double n)
	{
		if (a < b)
			return (int)(Math.abs(a - b) * n) + a;
		else
			return (int)(Math.abs(a - b) * n) + b;
	}
	
	/**
	 * @param arr an array of Points to curve
	 * @return a curved array of Points
	 */
	public static Point[] curve(Point[] arr)
	{
		//creates an ArrayList because arrays have a fixed length
		ArrayList<Point> points = new ArrayList<Point>();

		for (int i = 0; i < arr.length; i++)
		{
			//adds the current element
			points.add(arr[i]);
			//conditional avoids out of bounds exception
			if (i + 1 < arr.length){
				//gets the midpoint of the x values
				int x = (int)(arr[i].getX() + arr[i + 1].getX()) / 2;
				//gets 3/4 between the y values closer to the larger side for an upwards curve
				int y = (int)(3 * Math.abs(arr[i].getY() - arr[i+1].getY())) / 4;
				//add the lower y value being compared
				if (arr[i].getY() <= arr[i+1].getY())
					y += arr[i].getY();
				else
					y += arr[i + 1].getY();
				//adds a new Point with that x and y to the ArrayList
				Point p = new Point(x,y);
				points.add(p);
			}
			

		}
		//creates a destination array and transfers the ArrayList to the array
		Point[] result = {};
		return points.toArray(result);
	}
	
	/**
	 * 
	 */
	public void finalize(Point[] pWave, Point[] qrsComplex, Point[] tWave)
	{
		ArrayList<Point> temp = new ArrayList<Point>();
		//adds waves and complexes to path
		for (int i = 0; i < pWave.length; i++)
		{
			temp.add(pWave[i]);
		}
		for (int i = 0; i < qrsComplex.length; i++)
		{
			temp.add(qrsComplex[i]);
		}
		for (int i = 0; i < tWave.length; i++)
		{
			temp.add(tWave[i]);
		}
		temp.add(new Point((int)pWave[0].getX() + length, (int)pWave[0].getY()));
		
		if (Main.lerpEnabled)
		{
			int j = 0;
			while (j + 1 < temp.size())
			{
				//Get the percentage of a part of the path of the total length
				double k, n = Math.abs(temp.get(j).getX() - temp.get(j + 1).getX()) / length;
				n *= 100;
				for (k = 0; k <= 1; k += 1 / n)
				{
					//Rounds k up to 1.0 when k is greater than 0.99999 (if k is affected by imprecise double operations) 
					k = k > 0.99999 ? 1.0 : k;
					//Adds a new point to the path using the linear interpolation (lerp) method
					Point a;
					if (temp.get(j).getY() < temp.get(j + 1).getY())
						a = new Point(
							lerp((int)temp.get(j).getX(), (int)temp.get(j + 1).getX(), k),
							lerp((int)temp.get(j).getY(), (int)temp.get(j + 1).getY(), k)
							);
					else
					//Inverts the interpolation factor (k) if the path is going down (negative slope)
						a = new Point(
								lerp((int)temp.get(j).getX(), (int)temp.get(j + 1).getX(), k),
								lerp((int)temp.get(j).getY(), (int)temp.get(j + 1).getY(), 1 - k)
								);
					path.add(a);
				}
				++j;
			}
		}
		else
		{
			path = temp;
		}
	}
}
