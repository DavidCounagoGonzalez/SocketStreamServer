package org.example;

import java.io.InputStream;
import java.net.InetSocketAddress;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;

public class SocketServer {

    public static void main(String[] args){
        try{
            System.out.println("Obteniendo factoria de sockets servidor");

            SSLServerSocketFactory serverSocketFactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();

            System.out.println("Creando socket servidor");

            SSLServerSocket serverSocket=(SSLServerSocket) serverSocketFactory.createServerSocket();

            System.out.println("Realizando el bind");

            InetSocketAddress addr=new InetSocketAddress("localhost", 5555);
            serverSocket.bind(addr);

            System.out.println("Aceptando conexiones");

            SSLSocket newSocket=(SSLSocket) serverSocket.accept();


            System.out.println("Conexión recibida");

            InputStream is= newSocket.getInputStream();

            byte[] mensaje=new byte[25];
            is.read(mensaje);


            System.out.println("Mensaje recibido: "+new String(mensaje));
            System.out.println("Cerrando el nuevo Socket");

            newSocket.close();

            System.out.println("Cerando el socket servidor");

            serverSocket.close();

            System.out.println("Terminado");

        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
