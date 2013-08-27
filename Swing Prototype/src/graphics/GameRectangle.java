package graphics;

import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class GameRectangle implements Shape, Shiftable{

	Rectangle2D MainRectangle;
	BigDecimal X;
	BigDecimal Y;
	BigDecimal xDistance;
	BigDecimal yDistance;
	
	
	
	BigDecimal xRender;
	BigDecimal yRender;
	BigDecimal yDistanceRender;
	BigDecimal xDistanceRender;
	
	
	BigDecimal CurrentZoom = null;
	
	public GameRectangle(float x1, float y1, float x2, float y2){
		
		CurrentZoom = new BigDecimal("1");
		CurrentZoom = CurrentZoom.setScale(4, RoundingMode.HALF_UP);
		
		MainRectangle = new Rectangle2D.Float(x1,y1,x2,y2);
		xDistance = new BigDecimal(Float.toString(x2));
		yDistance = new BigDecimal(Float.toString(y2));
		xDistance = xDistance.setScale(10, RoundingMode.HALF_UP);
		yDistance = yDistance.setScale(10, RoundingMode.HALF_UP);
		
		xDistanceRender = new BigDecimal(Float.toString(x2));
		yDistanceRender = new BigDecimal(Float.toString(y2));
		xDistanceRender = xDistance.setScale(10, RoundingMode.HALF_UP);
		yDistanceRender = yDistance.setScale(10, RoundingMode.HALF_UP);
		
		X = new BigDecimal(Float.toString(x1));
		Y = new BigDecimal(Float.toString(y1));
		X = X.setScale(10, RoundingMode.HALF_UP);
		Y = Y.setScale(10, RoundingMode.HALF_UP);
		
		xRender = new BigDecimal(Float.toString(x1));
		yRender = new BigDecimal(Float.toString(y1));
		xRender = xRender.setScale(10, RoundingMode.HALF_UP);
		yRender = yRender.setScale(10, RoundingMode.HALF_UP);
	}
	
	@Override
	public void Shift(float xDelta, float yDelta, Point2D Center) {
		X = X.subtract(new BigDecimal(Float.toString(xDelta)));
		Y = Y.subtract(new BigDecimal(Float.toString(yDelta)));
		Zoom(CurrentZoom, Center);
	}

	@Override
	public void Zoom(BigDecimal Percent, Point2D Center) {
		//System.out.println(Percent);
		//set zoom level so we can re-build the image if we shift
		CurrentZoom = Percent;
		
		//find the distance between the X and Y values and the center
		BigDecimal XCenterDistance = new BigDecimal(Double.toString(Center.getX())).subtract(X);
		BigDecimal YCenterDistance = new BigDecimal(Double.toString(Center.getY())).subtract(Y);
		XCenterDistance = XCenterDistance.setScale(10, RoundingMode.HALF_UP);
		YCenterDistance = YCenterDistance.setScale(10, RoundingMode.HALF_UP);
		
		//where the new X is going to be rendered
		xRender = XCenterDistance.multiply(Percent);
		xRender = new BigDecimal(Double.toString(Center.getX())).subtract(xRender);
		//where the new Y is going to be rendered
		yRender = YCenterDistance.multiply(Percent);
		yRender = new BigDecimal(Double.toString(Center.getY())).subtract(yRender);
		
		//the new draw distances
		xDistanceRender = xDistance.multiply(Percent);
		yDistanceRender = yDistance.multiply(Percent);
		
		MainRectangle.setRect(xRender.doubleValue(), yRender.doubleValue(), xDistanceRender.doubleValue(), yDistanceRender.doubleValue());
		//System.out.println(xRender.doubleValue() + "," + yRender.doubleValue() + ",");
		
	}
	
	//auto generated shit
	@Override
	public boolean contains(Point2D p) {
		return MainRectangle.contains(p);
	}

	@Override
	public boolean contains(Rectangle2D r) {
		return MainRectangle.contains(r);
	}

	@Override
	public boolean contains(double x, double y) {
		return MainRectangle.contains(x,y);
	}

	@Override
	public boolean contains(double x, double y, double w, double h) {
		return MainRectangle.contains(x,y,w,h);
	}

	@Override
	public Rectangle getBounds() {
		return MainRectangle.getBounds();
	}

	@Override
	public Rectangle2D getBounds2D() {
		return MainRectangle.getBounds2D();
	}

	@Override
	public PathIterator getPathIterator(AffineTransform at) {
		return MainRectangle.getPathIterator(at);
	}

	@Override
	public PathIterator getPathIterator(AffineTransform at, double flatness) {
		return MainRectangle.getPathIterator(at, flatness);
	}

	@Override
	public boolean intersects(Rectangle2D r) {
		return MainRectangle.intersects(r);
	}

	@Override
	public boolean intersects(double x, double y, double w, double h) {
		return intersects(x,y,w,h);
	}

	

	
	
}
