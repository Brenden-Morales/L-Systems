package rendering;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

import graphics.*;

public class EnvironmentManager implements Shiftable {
	
	//the zoom level we're currently at
	private BigDecimal ZoomLevel = new BigDecimal("1.0");
	
	//list of game rectangles in the environment
	private List<GameRectangle> Rectangles = new ArrayList<GameRectangle>();
	private List<GameLine> Lines = new ArrayList<GameLine>();

	public EnvironmentManager(){
		//setting rounding behavior for zoom handling
		ZoomLevel = ZoomLevel.setScale(3, RoundingMode.HALF_UP);
	}
	
	@Override
	public void Shift(float xDelta, float yDelta, Point2D Center) {
		//for each of the currently existing environment variables, shift
		xDelta = xDelta / ZoomLevel.floatValue();
		yDelta = yDelta / ZoomLevel.floatValue();
		for(GameLine line : Lines){
			line.Shift(xDelta, yDelta, Center);
		}
		for(GameRectangle rectangle : Rectangles){
			rectangle.Shift(xDelta, yDelta, Center);
		}
		
	}

	@Override
	public void Zoom(BigDecimal Percent, Point2D Center) {
		//check to see if we're below minimum zoom
		if(ZoomLevel.equals(new BigDecimal("0.025"))) return;
		
		//set zoom level for the environment itself, in case we decide to load in new graphics, so we can scale them properly
		ZoomLevel = ZoomLevel.add(Percent);
		
		//for each of the currently existing environment variables, scale them
		for(GameLine line : Lines){
			line.Zoom(ZoomLevel, Center);
		}
		for(GameRectangle rectangle : Rectangles){
			rectangle.Zoom(ZoomLevel, Center);
		}
	}
	
	//adding rectangles to the environment
	public void AddRectangle(GameRectangle r){
		Rectangles.add(r);
	}
	//adding lines
	public void AddLine(GameLine l){
		Lines.add(l);
	}
	//clear all lines
	public void ClearLines(){
		Lines.clear();
	}
	
	//drawing the environment variables
	public void Draw(Graphics2D G2D){
		for(GameLine line : Lines){
			G2D.draw(line);
		}
		for(GameRectangle rectangle : Rectangles){
			G2D.draw(rectangle);
		}
	}
	
}
