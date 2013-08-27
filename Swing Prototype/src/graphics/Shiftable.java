package graphics;

import java.awt.geom.Point2D;
import java.math.BigDecimal;

public interface Shiftable {
	void Shift(float xDelta, float yDelta, Point2D Center);
	void Zoom (BigDecimal Percent, Point2D Center);
}
