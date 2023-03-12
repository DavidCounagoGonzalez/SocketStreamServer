package org.example;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class HiloServer extends Thread{

     DataInputStream in;
     DataOutputStream out;
     private String nombreCliente;
     private Socket socket;

     private ArrayList<HiloServer> listaHilos;

    public HiloServer(Socket socket, ArrayList<HiloServer> hilos, String nombreCliente) {
        this.socket = socket;
        this.listaHilos = hilos;
        this.nombreCliente = nombreCliente;
    }

    @Override
    public void run(){
            try {
                in = new DataInputStream(socket.getInputStream());
                out = new DataOutputStream(socket.getOutputStream());
                while(true){
                    String mensaje = in.readUTF();
                    if(mensaje.equals(nombreCliente + " se ha desconectado")){
                        InterfazServer.tabPanel.removeTabAt(InterfazServer.tabPanel.indexOfTab(nombreCliente));
                        break;
                    }
                    Pestanas.chat.append(mensaje + "\n");
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
    }
}
