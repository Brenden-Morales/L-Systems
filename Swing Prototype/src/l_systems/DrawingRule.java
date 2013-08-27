package l_systems;

public class DrawingRule {

	private char Symbol;
	public char getSymbol(){return Symbol;}
	
	private boolean Draw;
	public boolean getDraw(){return Draw;}
	
	private boolean Rotate;
	public boolean getRotate(){return Rotate;}
	
	private float Degrees;
	public float getDegrees(){return Degrees;}
	
	//constructor for drawing rules
	public DrawingRule(char symbol,boolean draw, boolean rotate, float degrees) throws Exception{
		//this is a draw rule
		if(draw){
			if(rotate)throw new Exception("Cannot be both draw and rotation rule");
			Draw = draw;
		}
		//if this is a rotation rule
		else if (rotate){
			if(draw)throw new Exception("Cannot be both draw and rotation rule");
			Rotate = rotate;
			Degrees = degrees;
		}
		Symbol = symbol;
	}
	
	
	
	
}
