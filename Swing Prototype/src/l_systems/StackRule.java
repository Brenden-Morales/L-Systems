package l_systems;

import java.awt.geom.Point2D;

public class StackRule {

	//the symbol
	private char Symbol;
	public char GetSymbol(){return Symbol;}
	//whether we Push or pop, 
	private boolean Push;
	public boolean GetPush(){return Push;}
	
	public StackRule (char sym, boolean push){
		Symbol = sym;
		Push = push;
	}
	
}
