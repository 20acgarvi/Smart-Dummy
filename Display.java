import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

public class Display extends Canvas{
	private static final long serialVersionUID = 8274011568777903027L;
	//a Path Object to display
	private Path ecg;
	//the width and height of the display 
	//baseline is the y coordinate where the ECG will be drawn
	//offset is so that multiple ECG cycles can be shown
	private int width, height, baseline, offset = 0;
	//the starting index of the cycle
	private int index = 0;
	
	
	//scales the image of the path
	private double scale = 1;
	//the length of the moving line
	private int length = 10;
	
	
	public Display(int width, int height, Path ecg)
	{
		this.width = width;
		this.height = height;
		this.ecg = ecg;
		//ecg.print();
		
		//the ECG will always be printed in the middle
		baseline = height / 2;
		//sets the size of the window
		this.setSize(width,height);
		
		//timer that animates the moving line
		 int delay = 10; //milliseconds
		  ActionListener taskPerformer = new ActionListener() {
		      public void actionPerformed(ActionEvent e) {
		    	  index++;
		          repaint();
		      }
		  };
		  new Timer(delay, taskPerformer).start();
	}
	@Override
	//displays the ECG
	public void paint(Graphics g)
	{
		//TODO fix awkward jump at end of one animation cycle
		
		//creates a set of lines equal to length
		for (int i = 0; i < length; i++)
		{
			int x1 = (int)(ecg.getPath().get(index + i).getX() * scale) + offset;
			int y1 = (int)(ecg.getPath().get(index + i).getY() * scale);
			if (ecg.getPath().size() <= index + 1 + i)
			{
				//increases the offset and repeats the cycle
				offset += ecg.getLength() * scale;
				index = -1;
			}
			int x2 = (int)(ecg.getPath().get(index + 1 + i).getX() * scale) + offset;
			int y2 = (int)(ecg.getPath().get(index + 1 + i).getY() * scale);
			if (x2 > width)
			{
				offset = 0;
			}
			else
			{
				g.drawLine(x1, height - (y1 + baseline), x2, height - (y2 + baseline));
			}
		}		
	}
}
