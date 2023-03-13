package org.example;

import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Map;

public class HiloServer extends Thread{

     DataInputStream in;
     DataOutputStream out;
     static String nombreCliente;
     static Socket socket;
     private ArrayList<HiloServer> listaHilos;
     private Map<String, JTextArea> mapArea;

    public HiloServer(Socket socket, ArrayList<HiloServer> hilos, String nombreCliente, Map mapArea) {
        this.socket = socket;
        this.listaHilos = hilos;
        this.nombreCliente = nombreCliente;
        this.mapArea = mapArea;
    }

    @Override
    public void run(){
            try {
                in = new DataInputStream(socket.getInputStream());
                out = new DataOutputStream(socket.getOutputStream());
                while(true){
                    String mensaje = in.readUTF();
                    String[] partes = mensaje.split("-");
                    System.out.println(partes[0]);
                    if(mensaje.equals(nombreCliente + " se ha desconectado")) {
                        if(listaHilos.size()>0){
                        for (int i = 0; i < listaHilos.size(); i++) {
                            HiloServer sup = listaHilos.get(i);
                            if (sup.getName().equals(nombreCliente)) {
                                listaHilos.remove(i);
                                SocketServer.borra(i);
                                System.out.println(listaHilos);
                            }
                        }
                        break;
                    }
                    }
                    JTextArea support;
                    if(mapArea.containsKey(partes[0])) {
                        support = mapArea.get(partes[0]);
                        support.append(mensaje + "\n");
                    }
                }
            } catch (IOException e) {
                System.out.println("Se ha desconectado");
            }
    }
}
