import java.awt.Graphics;
import java.util.Scanner;

public class BotTrust {
	public Panel panel;
	Scanner input;
	int T;
	int curT=1;
	int O;
    int B;
    int cur;
    int idx;
    int free;
    int cost;
    int X=1;
    int[] a= new int[X];
	char t ;
    int n ;
    int tmp;
    int nxt;
	boolean trials=true;
	boolean Got_T=false;
	boolean SeqCall=false;
	boolean GoingThouSeq = false;

	public BotTrust(Panel panel){
		this.panel = panel;
	}
	
	public void tick(){
		if(panel.Orange.moving==false && panel.Blue.moving==false){
			//get the number of trials
			//will only be called onces and only once
			if(panel.On == true && trials==true){
				System.out.println("Executed");
				input = new Scanner(System.in) ;
				T = input.nextInt() ;
				trials=false;
				Got_T=true;
				SeqCall=true;
				System.out.println("Executed trials");
			}
			
			//get the sequence
			//called n trials amount of times
	        if ( curT <= T && Got_T==true && SeqCall==true)
	        {
	        	System.out.println("Executed");
	            X = input.nextInt() ;
	            a = new int[X] ;
	            for (int j = 0 ; j < X ; j ++)
	            {
	                t = input.next().charAt(0) ;
	                n = input.nextInt() ;
	                if (t == 'B') n += 200 ;
	                a[j] = n ;
	            }
	            panel.Blue.buttonlocation(panel.BlueButtons[0]);
	            panel.Orange.buttonlocation(panel.OrangeButtons[0]);
	            O=1;
	            B=1;
	            cur = a[0] < 200 ? 0 : 1 ;
	            idx = 0 ;
	            free = 0 ;
	            cost = 0 ;
	            SeqCall = false;
	            GoingThouSeq=true;
				System.out.println("Executed seq");
	        }
	        
	        if (idx < a.length && GoingThouSeq == true)
	        {
	        	System.out.println("Executed");
	            //System.out.println(free);
	            tmp = a[idx] ;
	            nxt = tmp < 200 ? 0 : 1 ;
	            if (cur == nxt)
	            {
	                
	                // continue with this d00d:
	                tmp = tmp > 200 ? tmp - 200 : tmp ;
	                if (nxt == 0)
	                {
	                    // using O
	                    cost += Math.abs(O-tmp) + 1 ;
	                    free += Math.abs(O-tmp) + 1 ;
	                    panel.Orange.buttonlocation(panel.BlueButtons[tmp-1]);
	                    O = tmp ;
	                }
	                else if(nxt == 1)
	                {
	                    // using O
	                    cost += Math.abs(B-tmp) + 1 ;
	                    free += Math.abs(B-tmp) + 1 ;
	                    panel.Blue.buttonlocation(panel.BlueButtons[tmp-1]);
	                    B = tmp ;
	                }
	            }
	            else
	            {
	                // free money! :D
	                tmp = tmp > 200 ? tmp - 200 : tmp ;
	                if (nxt == 0)
	                {
	                    // using O
	                    int y ;
	                    cost += (y=Math.max(1,Math.abs(O-tmp) + 1 - free)) ;
	                    free = y ;
	                    panel.Orange.buttonlocation(panel.BlueButtons[tmp-1]);
	                    O = tmp ;
	                }
	                else if(nxt == 1)
	                {
	                    // using B
	                    int y ;
	                    cost += (y=Math.max(1,Math.abs(B-tmp) + 1 - free)) ;
	                    free = y ;
	                    panel.Blue.buttonlocation(panel.BlueButtons[tmp-1]);
	                    B = tmp ;
	                }
	            }
	            idx ++ ; 
	            cur = nxt ;
				System.out.println("Executed array");
	        }
	        
	        if(idx >= a.length && curT <= T){
	        	System.out.println("Case #" + curT + ": " + cost);
	        	GoingThouSeq = false;
	        	curT++;
	        	SeqCall=true;
				System.out.println("NEXT SEQUENCE");
	        }
		}
	}
}
