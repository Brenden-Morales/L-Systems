package window;

import input.InputManager;

import java.awt.Canvas;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JSlider;

import rendering.EnvironmentManager;
import rendering.Renderer;

public class MainWindow {
	
	//the main JFrame
	private static JFrame Application = new JFrame();
	
	//the canvas inside the JFrame that is drawn on
	private static Canvas MainCanvas = new Canvas();

	//buffer for blitting / drawring on
	private static BufferStrategy MainBuffer = null;
	
	//graphics variables
	private static GraphicsEnvironment MainGraphicsEnvironment = null;
	private static GraphicsDevice MainGraphicsDevice = null;
	private static GraphicsConfiguration MainGraphicsConfiguration = null;
	
	//the main renderer
	private static Renderer MainRenderer = null;
	static Thread RendererThread;
	
	//the input manager
	private static InputManager MainInputManager = new InputManager();
	
	//the Environment Manager
	private static EnvironmentManager MainEnvironmentManager = new EnvironmentManager();
	

	public static void main(String[] args) {
		
		try{
			//set up the application
			Application.setIgnoreRepaint(true);
			Application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			//BUTTON TEST
			JButton Button1 = new JButton("Dragon");
			JButton Button2 = new JButton("Sierpinski");
			JButton Button3 = new JButton("Plant");
		    Button1.setBounds(10, 60, 120, 30);
		    Button2.setBounds(10,100, 120, 30);
		    Button3.setBounds(10,140, 120, 30);
		    Application.add(Button1);
		    Application.add(Button2);
		    Application.add(Button3);
		    Button1.addActionListener(MainInputManager);
		    Button2.addActionListener(MainInputManager);
		    Button3.addActionListener(MainInputManager);
		    //SLIDER TEST
		    JSlider Slider1 = new JSlider(JSlider.HORIZONTAL, 0,10,3);
		    Slider1.setMajorTickSpacing(2);
		    Slider1.setMinorTickSpacing(1);
		    Slider1.setPaintTicks(true);
		    Slider1.setPaintLabels(false);
		    Slider1.setBounds(10, 30, 120, 20);
		    Application.add(Slider1);
		    Slider1.addChangeListener(MainInputManager);
			
			//set up the canvas
			MainCanvas.setIgnoreRepaint(true);
			MainCanvas.setSize(640,480);
			
			//add inputmanager to canvas
			MainCanvas.addMouseListener(MainInputManager);
			MainCanvas.addKeyListener(MainInputManager);
			MainCanvas.addMouseWheelListener(MainInputManager);
			MainInputManager.addKey('w');
			MainInputManager.addKey('a');
			MainInputManager.addKey('s');
			MainInputManager.addKey('d');
			
			//add the canvas to the game window
			Application.add(MainCanvas);
			
			//launch the UI
			Application.pack();
			Application.setVisible(true);
			
			//set up graphics
			MainCanvas.createBufferStrategy(2);
			MainBuffer = MainCanvas.getBufferStrategy();
			MainGraphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
			MainGraphicsDevice = MainGraphicsEnvironment.getDefaultScreenDevice();
			MainGraphicsConfiguration = MainGraphicsDevice.getDefaultConfiguration();
			
			//launch the renderer thread
			MainRenderer = new Renderer(MainGraphicsConfiguration, MainBuffer, MainCanvas, MainInputManager, MainEnvironmentManager);
			RendererThread = new Thread(MainRenderer);
			RendererThread.start();
			//start rendering
			MainRenderer.StartRender();
		}
		catch(Exception ex){
			System.out.println(ex.toString());
		}
	
		
		
	}

}
