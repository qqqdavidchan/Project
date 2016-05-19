import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;


import javax.swing.JFrame;


public class Panel extends Canvas implements Runnable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3174246000755830252L;
	public int WIDTH = 600;
	public int HEIGHT = WIDTH / 4 * 3;
	public int SCALE = 1;
	public String NAME = "Project 2";

	private JFrame frame;
	private BufferedImage image = new BufferedImage(WIDTH, HEIGHT,BufferedImage.TYPE_INT_RGB);

	public boolean running = false;
	public int tickCount = 0;

	public Point clicked;
	
	long lastTime = System.nanoTime();
	long lastTimer = System.currentTimeMillis();
	double nsPerTick = 1000000000D / 60D;
	double delta = 0;
	int ticks = 0;
	int frames = 0;
	
	//the objects
	public Bot Orange;
	public Bot Blue;
	public Button[] OrangeButtons;
	public Button[] BlueButtons;
	public BotTrust botTrust;
	public boolean On = false;
	
	public Panel() {
		setMinimumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		setMaximumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		
		frame = new JFrame(NAME);
	
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add(this, BorderLayout.CENTER);
		frame.pack();
	
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
     
	public synchronized void start() {
		running = true;
		new Thread(this).start();
	}
	
	public synchronized void stop() {
		running = false;
	}
	
	public void init() {
		Orange = new Bot('O');
		Blue = new Bot('B');
		OrangeButtons = new Button[200];
		for(int i = 0;i<OrangeButtons.length;i++){
			OrangeButtons[i] = new Button(50+i*10,40);
		}
		
		BlueButtons = new Button[200];
		for(int i = 0;i<BlueButtons.length;i++){
			BlueButtons[i] = new Button(50+i*10,240);
		}
		
		botTrust = new BotTrust(this);
	}
	
	public void run() {

		init();
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / nsPerTick;
			lastTime = now;
			boolean shouldRender = true;
	
			while (delta >= 1) {
				ticks++;
				tick();
				delta -= 1;
				shouldRender = true;
			}
			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (shouldRender) {
				frames++;
				render();
			}
	
			if (System.currentTimeMillis() - lastTimer >= 1000) {
				lastTimer += 1000;
				//System.out.println(ticks + " ticks , " + frames+ " frames per second");
				frames = 0;
				ticks = 0;
				On=true;
				Orange.move();
	    		Blue.move();
			}
		}
	}
	
	public void tick() {
    		tickCount++;
    		botTrust.tick();
    }
	
	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		//render here
		Orange.render(g);
		Blue.render(g);
		for(int i = 0;i<OrangeButtons.length;i++){
			OrangeButtons[i].render(g);
		}
		
		for(int i = 0;i<BlueButtons.length;i++){
			BlueButtons[i].render(g);
		}
		
		/*
		 * walls 
		 * making a class would be a waste since it serves no function
		 * walls are red
		 */
		
		g.setColor(Color.red);
		g.fillRect(40, 0,this.WIDTH+10,3);
		g.fillRect(40, 200,this.WIDTH+10,3);
		g.fillRect(40, 400,this.WIDTH+10,3);
		g.fillRect(40, 0,3,400);
		
		/*
		 * door
		 */
		g.setColor(Color.green);
		g.fillRect(40, 100-15,3,30);
		g.fillRect(40, 300-15,3,30);
		
		
		g.dispose();
		bs.show();
	}
}
