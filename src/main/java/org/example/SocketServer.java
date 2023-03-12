package org.example;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class SocketServer {
    static Socket sc;

    public static void main(String[] args){
        new InterfazServer();
        ArrayList<HiloServer> listaHilos = new ArrayList<>();

        try {
            ServerSocket servidor = new ServerSocket(5554);

            System.out.println("Incia el server");

            while(!servidor.isClosed()){
                sc = servidor.accept();

                DataInputStream in = new DataInputStream(sc.getInputStream());
                DataOutputStream out = new DataOutputStream(sc.getOutputStream());

                out.writeUTF("Dame un nombre para identidicarte");
                String nombreCliente = in.readUTF();
                InterfazServer.tabPanel.add(nombreCliente, new Pestanas());

                HiloServer hilo  = new HiloServer(sc, listaHilos, nombreCliente);
                listaHilos.add(hilo);
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
            Pestanas.chat.append("Server --> "  + InterfazServer.txtEscribe.getText()+ "\n");
            InterfazServer.txtEscribe.setText("");


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /*public static void recibo(){
        try {
            InputStream dis = sc.getInputStream();
            DataInputStream is = new DataInputStream(dis);
            String recibido = is.readUTF();
            if (recibido == null){
                System.out.println(0);
            }
            Pestanas.chat.append(recibido+"\n");
        }catch(IOException exc){
            exc.printStackTrace();
        }
    }*/
}
