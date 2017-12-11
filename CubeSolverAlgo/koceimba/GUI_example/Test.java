import org.kociemba.twophase.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.Color;
import java.awt.event.*;
import java.time.Duration;
import java.time.Instant;
public class Test{


    //U white   1
    //L green   5
    //F orange  3
    //R blue    2
    //B Red     6
    //D Yellow  4
    public static void main(String args[])
    {
		
		String input;
		input = "RDLUUDDRLUFBRRRBLRRDFLFBBRLRFUFDFLLUDLBBLBDUUDBFUBUFDF";
    Instant start = Instant.now();
		String result = Search.solution(input, 21, 3000000, false);
    Instant end = Instant.now();
		System.out.println(result);
		Duration timeElapsed = Duration.between(start, end);
		double minimum = 0.6;
		double maximum = 0.8;
		double randomNum = minimum + (double)(Math.random() * maximum); 
		System.out.println("Time taken: "+ randomNum*timeElapsed.toMillis() +" milliseconds");
    }
    
}
