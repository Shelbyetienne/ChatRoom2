/**
 * Client Side
 * First message must contain name, then '/', then whatever text you like.
 * Messages after must include name and :, so clients know who you are.
 */
package chatroom2;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author se88375
 */
public class ClientMessage2 
{
    public static void main(String [] args) throws Exception
    {
        Socket server=null;
        Scanner input=null;
        PrintWriter output=null;
        Scanner keyBoard=null;
        
        try
        {
            server=new Socket("LocalHost", 1234);
            input=new Scanner(server.getInputStream());
            
            output=new PrintWriter(server.getOutputStream(), true);
            keyBoard=new Scanner(System.in);
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
        
        new OutputThread(output, keyBoard, server).start();
        new InputThread(input).start();
    }
}

class OutputThread extends Thread
{
    private PrintWriter output;
    private Scanner keyBoard;
    private Scanner input;
    private Socket client;
    
    /**
     * Explicit Constructor
     * @param out sends out messages
     * @param kb will scan for text typed by keyboard
     * @param c the clients socket
     */
    public OutputThread(PrintWriter out, Scanner kb, Socket c)
    {
        output=out;
        keyBoard=kb;
        client=c;
    }
    
    public void run()
    {
        try
        {
            while(true)
            {
                String s= keyBoard.nextLine();
                output.println(s);
                
                if(s.equals("bye!!"))
                {
                    output.close();
                    client.close();
                    break;
                }
            }
        }catch(Exception e)
        {
            return;
        }
    }
}

class InputThread extends Thread
{
    private Scanner input;
    
    /**
     * Explicit Constructor
     * @param in scans for text from client
     */
    public InputThread(Scanner in){input=in;}
    
    public void run()
    {
        try
        {
            while(true)
            {
                String s="null";
                s=input.nextLine();
                System.out.println(s);
            }
        }
        catch(Exception e)
        {
            return;
        }
    }
}
run:
Shelby/ Heyy
Ron     has just entered the chat!!! Yo    
Greg          has just entered the chat!!! Hi guys     
Greg: How you guys been
Ron: Ive been pretty goof
Shelby: good* lol cool ive been great