package l_systems;

import graphics.GameLine;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Float;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class LSystem {
	
	public void System(){};
	
	//the fundamental components of the L system
	private List<Character> Variables = new ArrayList<Character>();
	private List<Character> Constants = new ArrayList<Character>();
	private char Start;
	private List<ProductionRule> Rules = new ArrayList<ProductionRule>();
	private List<DrawingRule> DrawRules = new ArrayList<DrawingRule>();
	private List<StackRule> StackRules = new ArrayList<StackRule>();
	
	//keeping track of the angle to drawr in
	private float CurrentAngle = 0;
	
	public void AddVariable (char ch) throws Exception{
		//check to see if variable already exists as a variable OR as a constant
		if(Variables.contains(ch) || Constants.contains(ch))
			throw new Exception("Variable \"" + ch +"\" already Exists");
		else
			Variables.add(ch);
	}
	
	public void AddConstant (char ch) throws Exception{
		//check to see if constant already exists as a constant OR as a variable
		if(Constants.contains(ch) || Variables.contains(ch))
			throw new Exception("Constant \"" + ch + "\" already Exists");
		else
			Constants.add(ch);
		
	}
	
	public void AddRule(ProductionRule Rule) throws Exception{
		//check to see if the rule already exists
		if(Rules.contains(Rule))
			throw new Exception("Rule: " + Rule.toString() + " Already Exists");
		else
			Rules.add(Rule);
	}
	
	//create drawing rule with existing variables and constants
	public void DefineDrawingRule(char ch, boolean Draw, boolean Rotate, float Degrees) throws Exception{
		//check to make sure symbol exists
		if(Variables.contains(ch) || Constants.contains(ch)){
			DrawRules.add(new DrawingRule(ch,Draw,Rotate,Degrees));
		}
		else throw new Exception("Variable does not exist");
	}
	
	//create stack rule with existing variables and constants
	public void DefineStackRule(char ch, boolean Push) throws Exception{
		//check to make sure symbol exists
		if(Variables.contains(ch) || Constants.contains(ch)){
			StackRules.add(new StackRule(ch, Push));
		}
		else throw new Exception("Variable does not exist");
	}
	
	//sanity checking
	public String toString(){
		String ReturnString = "";
		
		ReturnString += "Variables: ";
		for(char v : Variables){
			ReturnString += v + " ";
		}ReturnString += "\n";
		
		ReturnString += "Constants: ";
		for(char c : Constants){
			ReturnString += c + " ";
		}ReturnString += "\n";
		
		ReturnString += "Start: " + Start + "\n";
		
		ReturnString += "Rules: ";
		for(ProductionRule R : Rules){
			ReturnString += R.toString() + " ";
		}
		
		return ReturnString;
	}
	
	//apply the Production Rules to a string and get the resulting string
	public String ApplyRules(String InitialString){
		String ReturnString = "";
		
		
		while(InitialString.length() != 0){
			System.out.println("applying Rules");
			
			//if we found a match for a rule at the beginning of the string
			boolean found = false;
			
			//loop through the rules and check to see if there's one we can apply to the beginning of the string,
			for(ProductionRule Rule : Rules){
				
				//check for a rule
				if(InitialString.startsWith(Rule.getPredecessor())){
					//add the successor to the return string
					ReturnString += Rule.getSuccessor();
					//remove the Predecessor from the Initial String
					InitialString = InitialString.substring(Rule.getPredecessor().length());
				}
				
			}
			
			//copy any constant over
			for(char ch : Constants){
				
				if(InitialString.startsWith(Character.toString(ch))){
					ReturnString += ch;
					InitialString = InitialString.substring(1);
				}
			}
			
		}//big while loop
		return ReturnString;
	}
	
	//apply the drawing rules to a string
	public List<GameLine> ApplyDraw(String DrawString, float x, float y, float Length, float deg){
		
		//the current angle we're drawing at, 0 is to the right, 180 is to the left
		double CurrentDegrees = deg;
		
		//return array of lines
		List<GameLine> Lines = new ArrayList<GameLine>();
		
		//lifo queue for queueing rules
		Stack PositionStack = new Stack();
		
		
		//process the string character by character
		while(DrawString.compareTo("") != 0){
			System.out.println("Drawring");
			
			//if we found a rule to apply
			boolean RuleFound = false;
			
			//check for a drawing rule
			for(DrawingRule Rule : DrawRules){
				
				if(DrawString.startsWith(Character.toString(Rule.getSymbol()))){
					
					//we've found a rule
					RuleFound = true;
					
					if(Rule.getDraw()){
						//the first point is just the current X and Y Values
						Point2D P1 = new Point2D.Float(x, y);
						
						//now we need to find the delta x and delta y for the second point
						Point2D P2 = new Point2D.Float(x,y);
						
						//first handle all the 90 degree intervals, they are special cases
						if(CurrentDegrees % 90 == 0){
							if(CurrentDegrees == 0)P2.setLocation(x + Length, y);
							else if (CurrentDegrees == 90)P2.setLocation(x, y - Length);
							else if (CurrentDegrees == 180)P2.setLocation(x - Length, y);
							else if (CurrentDegrees == 270)P2.setLocation(x, y + Length);
						}
						//otherwise handle any other angle
						else{
							//between 0 and 90
							if(CurrentDegrees > 0 && CurrentDegrees < 90){
								float xDelta = (float) (Math.cos(Math.toRadians(CurrentDegrees)) * Length);
								float yDelta = (float) (Math.sin(Math.toRadians(CurrentDegrees)) * Length);
								P2.setLocation(x + xDelta, y - yDelta);
							}
							//between 90 and 180
							else if(CurrentDegrees > 90 && CurrentDegrees < 180){
								float Degrees = (float) (180- CurrentDegrees);
								float xDelta = (float) (Math.cos(Math.toRadians(Degrees)) * Length);
								float yDelta = (float) (Math.sin(Math.toRadians(Degrees)) * Length);
								P2.setLocation(x - xDelta, y - yDelta);
							}
							//between 180 and 270
							else if(CurrentDegrees > 180 && CurrentDegrees < 270){
								float Degrees = (float) (270- CurrentDegrees);
								//notice swapped 
								float yDelta = (float) (Math.cos(Math.toRadians(Degrees)) * Length);
								float xDelta = (float) (Math.sin(Math.toRadians(Degrees)) * Length);
								P2.setLocation(x - xDelta, y + yDelta);
							}
							//between 270 and 360
							else if(CurrentDegrees > 270 && CurrentDegrees < 360){
								float Degrees = (float) (360- CurrentDegrees);
								float xDelta = (float) (Math.cos(Math.toRadians(Degrees)) * Length);
								float yDelta = (float) (Math.sin(Math.toRadians(Degrees)) * Length);
								P2.setLocation(x + xDelta, y + yDelta);
							}
						}
						
						//create the line, add it to the array, set new starting location
						Lines.add(new GameLine((float)P1.getX(), (float)P1.getY(),(float)P2.getX(), (float)P2.getY()));
						x = (float) P2.getX();
						y = (float) P2.getY();
						
						DrawString = DrawString.substring(1);
						
						
					}
					//otherwise, we change the target angle
					else if(Rule.getRotate()){
						//add to the rotation
						CurrentDegrees += Rule.getDegrees();
						while(CurrentDegrees < 0) CurrentDegrees += 360;
						while(CurrentDegrees > 360) CurrentDegrees -= 360;
						CurrentDegrees = CurrentDegrees % 360;
						DrawString = DrawString.substring(1);
					}
					
				}//if we've found an applicable drawing rule
				
				
			}//check for a drawing rule
			
			//if we haven't found a drawing rule, look for a stack rule
			if(!RuleFound){
				boolean StackRuleFound = false;
				
				for(StackRule Rule : StackRules){
					
					if(DrawString.startsWith(Character.toString(Rule.GetSymbol()))){
						StackRuleFound = true;
						
						//if this is a push
						if(Rule.GetPush()){
							PositionStack.push(new Point2D.Float(x,y));
							PositionStack.push(CurrentDegrees);
						}
						//this is a pop
						else{
							double Degrees = (double) PositionStack.pop();
							Point2D point = (Point2D) PositionStack.pop();
							CurrentDegrees = Degrees;
							x = (float) point.getX();
							y = (float) point.getY();
						}
						DrawString = DrawString.substring(1);
					}//we've found a stack rule
					
				}//looping through stack rules
				
				//we found nothing
				if(!RuleFound && !StackRuleFound) DrawString = DrawString.substring(1);
			}
			
			
			
		}//looping through the string to draw
		
		return Lines;
	}
	
}
