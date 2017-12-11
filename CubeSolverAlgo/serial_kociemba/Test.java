import org.kociemba.twophase.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.Color;
import java.awt.event.*;
import java.time.Duration;
import java.time.Instant;
public class Test{


    //U yellow  1 
    //L green   5
    //F orange  3
    //R blue    2
    //B red     6
    //D white   4
    public static void main(String args[])
    {
    String input;
    //input = "BLRLULFRF RUDBRBDFF UUDFFDUDL LFBRDUFUL RFRDLRUBB BDULBRDBL";
    input = "BLRLULFRFRUDBRBDFFUUDFFDUDLLFBRDUFULRFRDLRUBBBDULBRDBL";
    Instant start = Instant.now();
    String result = Search.solution(input, 21, 3000000, false);
    Instant end = Instant.now();
    System.out.println(result);
    Duration timeElapsed = Duration.between(start, end); 
		System.out.println("Time taken: "+ timeElapsed.toMillis() +" milliseconds");
    }
   }







