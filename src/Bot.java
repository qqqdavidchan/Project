import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

public class Bot {

	public int x ;
	public int y ;
	public char c;
	public Button curbcutton;
	public Button B;
	public boolean moving=false;
	
	public Bot(char color){
		c=color;
		//set position
		if (color == 'B'){
			x= 50;
			y= 50;
		}
		else if( color == 'O'){
			x= 50;
			y= 250;
		}
	}
	
	public void buttonlocation(Button B){
		this.B = B;
		moving=true;
	}
	
	public void move(){
		//move to the button
		if(B  != null){
			if(this.x < B.x){
				x+=10;
			}
			else if(this.x > B.x){
				x-=10;
			}
			else if(this.x == B.x){
				this.x=B.x;
				moving=false;
			}
		}
	}
	
	public void render(Graphics g){
		if(c == 'B'){
			g.setColor(Color.blue);
			g.drawString("Blue Bot is located a " + x, 50, 420);
			if(B !=null)
				g.drawString("Blue Button is located a " + B.x, 50, 430);
		}
		if(c == 'O'){
			g.setColor(Color.orange);
			g.drawString("Orange Bot is located at " + x, 50, 440);
			if(B !=null)
				g.drawString("Orange Button is located a " + B.x, 50, 450);
		}
		g.fillRect(x, y, 5, 10);
		
	}
}
