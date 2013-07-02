/*
*Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.net.impl.tcp;

import java.net.ServerSocket;
import java.net.Socket;

import org.jmin.net.event.ConnectEvent;
import org.jmin.net.event.ConnectedEvent;
import org.jmin.net.event.DisconnectEvent;
import org.jmin.net.event.ErrorEvent;
import org.jmin.net.impl.ProtocolServerThread;
import org.jmin.net.impl.util.CloseUtil;

/**
 * Socket server under IO mode
 *
 * @author Chris
 */
public class TcpSocketServerThread extends ProtocolServerThread{

  /**
   * server thread
   * @param ioSocketServer
   */
  public TcpSocketServerThread(TcpSocketServer server){
  	super(server);
  }

  /**
   * Thread method
   */
  public void run(){
  	TcpConnection connection  = null;
  	TcpSocketServer tcpServer = (TcpSocketServer)this.getProtocolSocketServer();
  	ServerSocket serverSocket = tcpServer.getServerSocket();
  	
    while(this.startThreadActive()&& !tcpServer.isClosed()){
      try{
        Socket socket = serverSocket.accept();
        connection = new TcpConnection(socket);
        connection.setConnectionEventPublisherFromServer(tcpServer.getConnectionEventPublisher());
        tcpServer.getServerEventPublisher().publish(new ConnectEvent(connection));
        tcpServer.isAcceptable(connection); //是合法的才可以被接受
        
        connection.setConnectionDataFilterListFromServer(tcpServer.getConnectionDataFilterList());
        tcpServer.getServerEventPublisher().publish( new ConnectedEvent(connection));
	      connection.run();
      
      }catch(Throwable e){
      	CloseUtil.close(connection);
    		ErrorEvent errorEvent = new ErrorEvent(connection,e);
        tcpServer.getServerEventPublisher().publish(errorEvent);
        connection.getSocketEventPublisher().publish(errorEvent);
        
        DisconnectEvent disconnectEvent = new DisconnectEvent(connection);
        tcpServer.getServerEventPublisher().publish(disconnectEvent);
      } 
    }
  }
}