import java.awt.Color;
import java.awt.Graphics;

public class Button {
	int x;
	int y;
	
	public Button(int a ,int b){
		x=a;
		y=b;
	}
	
	public void render(Graphics g){
		g.setColor(Color.white);
		g.fillRect(x, y, 5, 5);
	}
}
