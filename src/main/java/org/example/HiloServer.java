package org.example;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class HiloServer extends Thread{

     DataInputStream in;
     DataOutputStream out;
     String nombreCliente;
     Socket socket;

    public HiloServer(DataInputStream in, DataOutputStream out, String nombreCliente) {
        this.in = in;
        this.out = out;
        this.nombreCliente = nombreCliente;
    }

    @Override
    public void run(){
        while (true){
            SocketServer.recibo();
        }
    }
}
