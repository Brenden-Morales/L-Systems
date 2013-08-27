package graphics;

import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class GameLine implements Shape, Shiftable{

	private Line2D MainLine;
	BigDecimal X1 = null;
	BigDecimal Y1 = null;
	BigDecimal X2 = null;
	BigDecimal Y2 = null;
	
	BigDecimal X1New = null;
	BigDecimal Y1New = null;
	BigDecimal X2New = null;
	BigDecimal Y2New = null;
	
	BigDecimal CurrentZoom = null;
	
	public GameLine(float x1, float y1, float x2, float y2){
		MainLine = new Line2D.Float(x1, y1, x2, y2);
		
		CurrentZoom = new BigDecimal("1");
		CurrentZoom = CurrentZoom.setScale(4, RoundingMode.HALF_UP);
		
		X1 = new BigDecimal(Float.toString(x1));
		X1 = X1.setScale(10, RoundingMode.HALF_UP);
		Y1 = new BigDecimal(Float.toString(y1));
		Y1 = Y1.setScale(10, RoundingMode.HALF_UP);
		
		X2 = new BigDecimal(Float.toString(x2));
		X2 = X2.setScale(10, RoundingMode.HALF_UP);
		Y2 = new BigDecimal(Float.toString(y2));
		Y2 = Y2.setScale(10, RoundingMode.HALF_UP);
		
		X1New = new BigDecimal(Float.toString(x1));
		X1New = X1New.setScale(10, RoundingMode.HALF_UP);
		Y1New = new BigDecimal(Float.toString(y1));
		Y1New = Y1New.setScale(10, RoundingMode.HALF_UP);
		
		X2New = new BigDecimal(Float.toString(x2));
		X2New = X2New.setScale(10, RoundingMode.HALF_UP);
		Y2New = new BigDecimal(Float.toString(y2));
		Y2New = Y2New.setScale(10, RoundingMode.HALF_UP);
	}
	
	@Override
	public void Shift(float xDelta, float yDelta, Point2D Center) {
		X1 = X1.subtract(new BigDecimal(Float.toString(xDelta)));
		Y1 = Y1.subtract(new BigDecimal(Float.toString(yDelta)));
		X2 = X2.subtract(new BigDecimal(Float.toString(xDelta)));
		Y2 = Y2.subtract(new BigDecimal(Float.toString(yDelta)));
		
		Zoom(CurrentZoom, Center);
	}


	@Override
	public void Zoom(BigDecimal Percent, Point2D Center) {
		CurrentZoom = Percent;
		
		//distance between first point and center
		BigDecimal X1Distance = new BigDecimal(Double.toString(Center.getX())).subtract(X1);
		BigDecimal Y1Distance = new BigDecimal(Double.toString(Center.getY())).subtract(Y1);
		//distance between second point and center
		BigDecimal X2Distance = new BigDecimal(Double.toString(Center.getX())).subtract(X2);
		BigDecimal Y2Distance = new BigDecimal(Double.toString(Center.getY())).subtract(Y2);
		
		X1Distance = X1Distance.multiply(Percent);
		Y1Distance = Y1Distance.multiply(Percent);
		X2Distance = X2Distance.multiply(Percent);
		Y2Distance = Y2Distance.multiply(Percent);
		
		X1New = new BigDecimal(Double.toString(Center.getX())).subtract(X1Distance);
		Y1New = new BigDecimal(Double.toString(Center.getY())).subtract(Y1Distance);
		X2New = new BigDecimal(Double.toString(Center.getX())).subtract(X2Distance);
		Y2New = new BigDecimal(Double.toString(Center.getY())).subtract(Y2Distance);
		
		MainLine = new Line2D.Double(X1New.doubleValue(),Y1New.doubleValue(), X2New.doubleValue(), Y2New.doubleValue());
		
	}
	
	
	//auto generated shit goes here
	@Override
	public boolean contains(Point2D arg0) {
		return MainLine.contains(arg0);
	}

	@Override
	public boolean contains(Rectangle2D arg0) {
		return MainLine.contains(arg0);
	}

	@Override
	public boolean contains(double arg0, double arg1) {
		return MainLine.contains( arg0,  arg1);
	}

	@Override
	public boolean contains(double arg0, double arg1, double arg2, double arg3) {
		return MainLine.contains(arg0,arg1,arg2,arg3);
	}

	@Override
	public Rectangle getBounds() {
		return MainLine.getBounds();
	}

	@Override
	public Rectangle2D getBounds2D() {
		return MainLine.getBounds2D();
	}

	@Override
	public PathIterator getPathIterator(AffineTransform arg0) {
		return MainLine.getPathIterator(arg0);
	}

	@Override
	public PathIterator getPathIterator(AffineTransform arg0, double arg1) {
		return MainLine.getPathIterator(arg0, arg1);
	}

	@Override
	public boolean intersects(Rectangle2D arg0) {
		return MainLine.intersects(arg0);
	}

	@Override
	public boolean intersects(double arg0, double arg1, double arg2, double arg3) {
		return MainLine.intersects(arg0,arg1,arg2,arg3);
	}


	

}
