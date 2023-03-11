package org.example;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Observable;

public class SocketServer {
    static Socket sc;

    public static void main(String[] args){
        new InterfazServer();

        try {
            ServerSocket servidor = new ServerSocket(5554);


            System.out.println("Incia el server");

            while(true){
                sc = servidor.accept();

                DataInputStream in = new DataInputStream(sc.getInputStream());
                DataOutputStream out = new DataOutputStream(sc.getOutputStream());

                out.writeUTF("Dame un nombre para identidicarte");
                String nombreCliente = in.readUTF();

                HiloServer hilo  = new HiloServer(in, out, nombreCliente);
                hilo.start();

                System.out.println("Creada la conexión con el cliente " + nombreCliente);
            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void envio(){
        try {
            DataOutputStream out = new DataOutputStream(sc.getOutputStream());
            out.writeUTF("Server --> "  + InterfazServer.txtEscribe.getText());
            InterfazServer.txtEscribe.setText("");


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void recibo(){
        try {
            InputStream dis = sc.getInputStream();
            DataInputStream is = new DataInputStream(dis);
            String recibido = is.readUTF();
            InterfazServer.chat.append(recibido+"\n");
        }catch(IOException exc){
            exc.printStackTrace();
        }
    }
}
