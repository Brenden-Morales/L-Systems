package input;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.HashMap;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.MouseInputListener;

public class InputManager implements KeyListener, MouseInputListener, MouseWheelListener, ActionListener, ChangeListener{

	//the keys that we're keeping track of
	private HashMap<Character, Boolean> KeyMap = new HashMap<Character, Boolean>();
	//add a key to the KeyMap
	public void addKey(char ch) throws Exception{
		if(KeyMap.containsKey(ch)) throw new Exception ("Key already mapped");
		else KeyMap.put(ch, false);
	}
	//check the status of a key
	public boolean checkKey(char ch) throws Exception{
		if(!KeyMap.containsKey(ch)) throw new Exception("Key is not mapped");
		else return KeyMap.get(ch);
	}
	
	//keeping track of mouse stuff
	private float[] CurrentMousePosition = new float[2];
	public float[] GetMousePosition(){return CurrentMousePosition;}
	private float[] MouseDelta = new float[2];
	public float[] GetMouseDelta(){return MouseDelta;}
	private boolean[] Buttons = new boolean[3];
	public boolean GetMouseButton (int i){return Buttons[i];}
	
	//mouse wheel shit
	private int MouseWheelDelta = 0;
	public int GetMouseWheelDelta(){
		int MWD = MouseWheelDelta;
		MouseWheelDelta = 0;
		return MWD;
	}
	
	//button stuff
	private boolean ButtonHandled = true;
	private String LSystem = "";
	public boolean CheckButton(){return ButtonHandled;}
	public String HandleButton(){
		ButtonHandled = true;
		return LSystem;
	}
	
	//slider stuff
	private int SliderValue = 3;
	public int GetSliderValue(){return SliderValue;}
	
	
	
	
	//keyboard shit
	@Override
	public void keyPressed(KeyEvent arg0){
		if(KeyMap.containsKey(arg0.getKeyChar())){
			KeyMap.put(arg0.getKeyChar(), true);
			System.out.println(arg0.getKeyChar() + " Down");
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		if(KeyMap.containsKey(arg0.getKeyChar())){
			KeyMap.put(arg0.getKeyChar(), false);
		}
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// not doing anything with this one
		
	}
	
	
	//mouse shit
	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println("Mouse Button:" + e.getButton());
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getButton() < 2) Buttons[e.getButton()] = true;
		CurrentMousePosition[0] = e.getX();
		CurrentMousePosition[1] = e.getY();
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.getButton() < 2) Buttons[e.getButton()] = false;
		CurrentMousePosition[0] = e.getX();
		CurrentMousePosition[1] = e.getY();
		
	}
	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseMoved(MouseEvent arg0) {
		MouseDelta[0] = arg0.getX() - CurrentMousePosition[0];
		MouseDelta[1] = arg0.getY() - CurrentMousePosition[1];
		CurrentMousePosition[0] = arg0.getX();
		CurrentMousePosition[1] = arg0.getY();
		
	}
	
	//mouse wheel shit
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		if(e.getScrollType() == MouseWheelEvent.WHEEL_UNIT_SCROLL){
			//System.out.println("Wheel Scrolled");
			//System.out.println("Amount:" + e.getPreciseWheelRotation());
			MouseWheelDelta += e.getPreciseWheelRotation();
		}
		
	}
	
	//for button presses
	@Override
	public void actionPerformed(ActionEvent arg0) {
		ButtonHandled = false;
		if(arg0.getActionCommand() == "Dragon") LSystem = "Dragon";
		if(arg0.getActionCommand() == "Sierpinski") LSystem = "Sierpinski";
		if(arg0.getActionCommand() == "Plant") LSystem = "Plant";
		
	}
	//for slider changes
	@Override
	public void stateChanged(ChangeEvent arg0) {
		JSlider Source = (JSlider)arg0.getSource();
		System.out.println(Source.getValue());
		SliderValue = Source.getValue();
		
	}


}
