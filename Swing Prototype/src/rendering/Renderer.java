package rendering;

import graphics.GameLine;
import graphics.GameRectangle;
import input.InputManager;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.geom.Point2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import l_systems.LSystem;
import l_systems.ProductionRule;

public class Renderer implements Runnable {

	// the stuff we need passed in for drawring
	private GraphicsConfiguration MainGraphicsConfiguration = null;
	private BufferStrategy MainBuffer = null;
	private Canvas MainCanvas = null;
	
	//input manager passed in
	InputManager MainInputManager = null;
	
	//environment manager passed in
	EnvironmentManager Environment = null;

	// stuff we need for drawring
	BufferedImage MainImage = null;
	Graphics MainGraphics = null;
	Graphics2D G2D = null;

	// whether we want the main render loop running
	private boolean Run = true;
	public void StopRunning() {Run = false;}

	// whether we want to render
	private boolean doRender = false;
	public void StartRender() {doRender = true;}
	public void StopRender() {doRender = false;}
	public boolean CheckRendering() {return doRender;}

	// frame timer
	private RenderTimer MainTimer = new RenderTimer();

	// desired frames per second
	private float DesiredFPS = 240;
	private float ActualFPS = 0;

	//constructor passes in the needed graphics vars
	public Renderer(GraphicsConfiguration gc, BufferStrategy buff, Canvas canv, InputManager manager, EnvironmentManager env) {
		MainGraphicsConfiguration = gc;
		MainBuffer = buff;
		MainCanvas = canv;
		MainInputManager = manager;
		Environment = env;
	}

	@Override
	public void run() {
		// set the timer
		MainTimer.Set();

		// frame counter for fps
		float Frames = 0;
		float Milliseconds = 0;
		
		//L-System test
		LSystem Dragon = new LSystem();
		List<GameLine> DragonLines = null;
		try{
			Dragon.AddVariable('X');
			Dragon.AddVariable('Y');
			Dragon.AddConstant('+');
			Dragon.AddConstant('-');
			Dragon.AddConstant('F');
			Dragon.DefineDrawingRule('F', true, false, 0);
			Dragon.DefineDrawingRule('+', false, true, 90);
			Dragon.DefineDrawingRule('-', false, true, -90);
			Dragon.AddRule(new ProductionRule("X", "X+YF"));
			Dragon.AddRule(new ProductionRule("Y", "FX-Y"));
			String Seed = "FX";
			for(int i = 0; i < 11; i ++){
				Seed = Dragon.ApplyRules(Seed);
			}
			//DragonLines = Dragon.ApplyDraw(Seed, 20, 200, 10,0);
			//System.out.println(Seed);
		}
		catch(Exception ex){
			System.out.println(ex);
		}
		
		LSystem Sierpinski = new LSystem();
		List<GameLine> SierpinskiLines = null;
		try{
			Sierpinski.AddVariable('A');
			Sierpinski.AddVariable('B');
			Sierpinski.AddConstant('-');
			Sierpinski.AddConstant('+');
			Sierpinski.AddRule(new ProductionRule("A", "B-A-B"));
			Sierpinski.AddRule(new ProductionRule("B", "A+B+A"));
			Sierpinski.DefineDrawingRule('A', true, false, 0);
			Sierpinski.DefineDrawingRule('B', true, false, 0);
			Sierpinski.DefineDrawingRule('+', false, true, 60);
			Sierpinski.DefineDrawingRule('-', false, true, -60);
			String Seed = "A";
			for(int i = 0; i < 6; i ++){
				Seed = Sierpinski.ApplyRules(Seed);
			}
			SierpinskiLines = Sierpinski.ApplyDraw(Seed, 20, 200, 10,0);
			//System.out.println(Seed);
		}
		catch(Exception ex){
			System.out.println(ex);
		}
		
		LSystem Plant = new LSystem();
		List<GameLine> PlantLines = null;
		try{
			Plant.AddVariable('X');
			Plant.AddVariable('F');
			Plant.AddConstant('-');
			Plant.AddConstant('+');
			Plant.AddConstant('[');
			Plant.AddConstant(']');
			Plant.AddRule(new ProductionRule("X", "F-[[X]+X]+F[+FX]-X"));
			Plant.AddRule(new ProductionRule("F", "FF"));
			Plant.DefineDrawingRule('F', true, false, 0);
			Plant.DefineDrawingRule('-', false, true, -25);
			Plant.DefineDrawingRule('+', false, true, 25);
			Plant.DefineStackRule('[', true);
			Plant.DefineStackRule(']', false);
			String Seed = "X";
			for(int i = 0; i < 5; i ++){
				Seed = Plant.ApplyRules(Seed);
			}
			PlantLines = Plant.ApplyDraw(Seed, 200, 480, 10,90);
			System.out.println(Seed);
		}
		catch(Exception ex){
			System.out.println(ex);
		}
		int count = 0;
		
		
		for(GameLine line : PlantLines){
			//Environment.AddLine(line);
			//count++;
			//if(count > 5) break;
		}
		//testing
		//GameRectangle rect1 = new GameRectangle(50,50,100,100);
		//GameRectangle rect2 = new GameRectangle(200,50,100,100);
		//GameLine line1 = new GameLine(50,50,150,150);
		//Environment.AddRectangle(rect1);
		//Environment.AddRectangle(rect2);
		//Environment.AddLine(line1);

		while (Run) {
			if (doRender) {

				// check to see if enough time has elapsed for the next frame
				if (MainTimer.Tick() >= ((float) 1 / DesiredFPS) * 1000) {
					// reset the frame timer
					MainTimer.Set();
					
					// increase frame count
					Frames++;

					// fps timer details
					Milliseconds += MainTimer.FPSTick();
					if (Milliseconds >= 1000) {
						ActualFPS = Frames / ((float) Milliseconds / 1000f);
						Frames = 0;
						Milliseconds = 0;
					}

					// do the actual rendering here
					try {

						// setting up for drawring
						MainImage = MainGraphicsConfiguration.createCompatibleImage(MainCanvas.getWidth(),MainCanvas.getHeight());

						// clear back buffer...
						G2D = MainImage.createGraphics();
						G2D.setColor(Color.WHITE);
						G2D.fillRect(0, 0, MainCanvas.getWidth(),MainCanvas.getHeight());
						
						//input handling
						float xDelta = 0;
						float yDelta = 0;
						if(MainInputManager.checkKey('w')) yDelta += 1;
						if(MainInputManager.checkKey('s')) yDelta -= 1;
						if(MainInputManager.checkKey('a')) xDelta += 1;
						if(MainInputManager.checkKey('d')) xDelta -= 1;
						BigDecimal MouseWheelDelta = new BigDecimal("0.025");
						MouseWheelDelta = MouseWheelDelta.setScale(10, RoundingMode.HALF_UP);
						MouseWheelDelta = MouseWheelDelta.multiply(new BigDecimal(Integer.toString(MainInputManager.GetMouseWheelDelta())));
						
						//mouse checking
						if(MainInputManager.GetMouseButton(1)){
							float x = MainInputManager.GetMousePosition()[0];
							float y = MainInputManager.GetMousePosition()[1];
							Point2D p = new Point2D.Float(x,y);
							//if(rect1.contains(p)) System.out.println("Inside dat box");
							System.out.println(x + " " + y );
						}
						
						//check on button status
						if(!MainInputManager.CheckButton()){
							System.out.println("Checking Button");
							String LSystem = MainInputManager.HandleButton();
							int level = MainInputManager.GetSliderValue();
							System.out.println(LSystem + " Handled at level: " + level);
							if(LSystem == "Dragon"){
								String Seed = "FX";
								for(int i = 0; i < level; i ++){
									Seed = Dragon.ApplyRules(Seed);
								}
								DragonLines = Dragon.ApplyDraw(Seed, 450, 300, 10,90);
								Environment.ClearLines();
								for(GameLine l : DragonLines) Environment.AddLine(l);
							}
							if(LSystem == "Sierpinski"){
								String Seed = "A";
								for(int i = 0; i < level; i ++){
									Seed = Sierpinski.ApplyRules(Seed);
								}
								SierpinskiLines = Sierpinski.ApplyDraw(Seed, 100, 200, 10,0);
								Environment.ClearLines();
								for(GameLine l : SierpinskiLines) Environment.AddLine(l);
							}
							if(LSystem == "Plant"){
								String Seed = "X";
								for(int i = 0; i < level; i ++){
									Seed = Plant.ApplyRules(Seed);
								}
								PlantLines = Plant.ApplyDraw(Seed, 350, 480, 10,90);
								Environment.ClearLines();
								for(GameLine l : PlantLines) Environment.AddLine(l);
							}
						}
						
						//here goes the bulk of the render code
						float CenterX = MainCanvas.getWidth() / 2f;
						float CenterY = MainCanvas.getHeight() / 2f;
						Point2D Center = new Point2D.Float(CenterX, CenterY);
						if(MouseWheelDelta.compareTo(new BigDecimal("0")) != 0){
							Environment.Zoom(MouseWheelDelta.multiply(new BigDecimal(-1)), Center);
						}
						if(xDelta != 0 || yDelta != 0){
							Environment.Shift(xDelta, yDelta, Center);
						}
						G2D.setColor(Color.black);
						Environment.Draw(G2D);
						

						// display frames per second...
						G2D.setFont(new Font("Courier New", Font.PLAIN, 12));
						G2D.setColor(Color.DARK_GRAY);
						G2D.drawString(String.format("FPS: %s", ActualFPS), 20,20);

						// Blit image and flip...
						MainGraphics = MainBuffer.getDrawGraphics();
						MainGraphics.drawImage(MainImage, 0, 0, null);
						if (!MainBuffer.contentsLost())MainBuffer.show();

					}
					catch(Exception ex){
						System.out.println(ex.toString());
					}
					finally {
						// release resources
						if (MainGraphics != null)
							MainGraphics.dispose();

						if (G2D != null)
							G2D.dispose();
					}// finally

					// Let the OS have a little time...
					Thread.yield();

				}// if enough time has elapsed since the last frame

			}// if we want to render
		}// while we're running

	}
}
