package org.example;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class HiloServer extends Thread{

     DataInputStream in;
     DataOutputStream out;
     static String nombreCliente;
     static Socket socket;

     private ArrayList<HiloServer> listaHilos;

    public HiloServer(Socket socket, ArrayList<HiloServer> hilos, String nombreCliente) {
        this.socket = socket;
        this.listaHilos = hilos;
        this.nombreCliente = nombreCliente;
    }

    public static Socket getSocket() {
        return socket;
    }

    @Override
    public void run(){
            try {
                in = new DataInputStream(socket.getInputStream());
                out = new DataOutputStream(socket.getOutputStream());
                while(true){
                    String mensaje = in.readUTF();
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
                    Pestanas.chat.append(mensaje + "\n");
                }
            } catch (IOException e) {
                System.out.println("Se ha desconectado");
            }
    }
}
