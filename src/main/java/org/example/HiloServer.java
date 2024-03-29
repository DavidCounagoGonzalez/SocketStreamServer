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
                    /*
                    Cuando llegue un mensaje el servidor recoge el nombre del cliente
                    Tanto para seleccionar la pestaña en que se imprime el mensaje,
                    como para poder saber en caso de desconexión la posición de sus datos y eliminarlos
                     */
                    JTextArea support;
                    String mensaje = in.readUTF();
                    String[] partes = mensaje.split("-");
                    if(mensaje.equals(partes[0] + "--> se ha desconectado")) {
                        if(mapArea.containsKey(partes[0])) {
                            support = mapArea.get(partes[0]);
                            support.append(mensaje + "\n");
                        }
                        if(listaHilos.size()>0){
                        for (int i = 0; i < listaHilos.size(); i++) {
                            HiloServer sup = listaHilos.get(i);
                            if (sup.getName().equals(partes[0])) {
                                SocketServer.borra(i);
                                mapArea.remove(partes[0]);
                            }
                        }
                        break;
                    }
                    }
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
