/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatroom2;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author se88375
 */
public class ChatRoom2 {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        ServerSocket server;//The server socket
        Map<char[],Socket> test;//where the clients will be stored
        
        try
        {
            server=new ServerSocket(1234);
            test=new HashMap<>();
            int i=0;
            
            while(true)
            {
                i++;
                Socket client=server.accept();//waiting for client
                test.put(("name"+i).toCharArray(), client);//when client connects add into map
                new ServerThread(client, test).start();
            }
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}

/**
 * ServerThread is the server that can accept connections from multiple clients
 * @author se88375
 */
class ServerThread extends Thread
{
    private Socket client;
    private PrintWriter output;
    private Scanner input;
    private Map<char[], Socket> name1;
   
    private Map<Socket , String> name2;
    String [] bag;
    int size;
    
    /**
     * Explicit Constructor
     * @param c the client that is connecting to the Server
     * @param m the Sockets of all the clients connected
     * @throws Exception 
     */
    public ServerThread(Socket c, Map<char[], Socket> m) throws Exception
    {
        client = c;
        output = new PrintWriter(client.getOutputStream(), true);
        input = new Scanner(client.getInputStream());
        name1=m; 
        
        name2=new HashMap<>();
        bag=new String[1000];
        size=0;
    }
    
    public void run()
    {
        
        Map<char[], Socket> Names=new HashMap<>();
        String s="null",m="null",u="null";
        char [] user, message;
        
        while(true)
        {
            //waits for first message to be sent from client
            s=input.nextLine();
            
            //will find the name only when a '/' is in the string
            if(s.contains("/")) 
            {
                user=new char[s.length()];
                message=new char[s.length()];
                int i=0;
                for(;s.charAt(i) != '/';i++)
                    user[i]=s.charAt(i);
                
                i++;//increment i in order to skip '/'
                
                for(int j=0;(i)<s.length();i++,j++)
                    message[j]=s.charAt(i);

                u=new String(user);//stores the first name
                m=new String(message);//stores the message sent
                
                try 
                {
                    //iterates through all the client sockets
                    for(Socket c : name1.values())
                    {
                        if(c!=client)
                        {
                            PrintWriter cout=new PrintWriter(c.getOutputStream(),true);
                            //prints text after client connects and send first message
                            cout.println(u + " has just entered the chat!!!"+m);
                        }
                    }
                } 
                catch (Exception e) 
                {
                    System.out.println(e.getMessage());
                }
            }
            //Sends regular messages that do not contain '/' format
            else
            {
                try 
                {
                    for(Socket c : name1.values())
                    {
                        if(c!=client)
                        {
                            PrintWriter cout=new PrintWriter(c.getOutputStream(),true);
                            //prints text message to all other client
                            cout.println(s);
                        }
                    }
                } 
                catch (Exception e) 
                {
                    System.out.println(e.getMessage());
                }
            }
        }
    }
    
}