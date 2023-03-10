package org.example;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {

    public static void main(String[] args){
        try{
            System.out.println("Creando socket servidor");

            ServerSocket serverSocket=new ServerSocket();

            System.out.println("Realizando el bind");

            InetSocketAddress addr=new InetSocketAddress("10.0.9.15",5555);
            serverSocket.bind(addr);

            System.out.println("Aceptando conexiones");

            Socket newSocket= serverSocket.accept();

            System.out.println("Conexion recibida");

            InputStream is=newSocket.getInputStream();
            DataInputStream dis = new DataInputStream(is);
            OutputStream os=newSocket.getOutputStream();

            String mensaje = dis.readUTF();

            System.out.println("Mensaje recibido: "+mensaje);

            System.out.println("Cerrando el nuevo socket");

            newSocket.close();

            System.out.println("Cerrando el socket servidor");

            serverSocket.close();

            System.out.println("Terminado");

        }catch (IOException e) {
        }
    }
}
