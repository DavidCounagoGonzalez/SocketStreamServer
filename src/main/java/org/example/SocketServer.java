package org.example;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;


public class SocketServer {
    static Socket sc;

    static ArrayList<HiloServer> listaHilos;

    static ArrayList<Socket> puertos;

    public static void main(String[] args){
        new InterfazServer();
        puertos = new ArrayList<>();
        listaHilos = new ArrayList<>();

        try {
            ServerSocket servidor = new ServerSocket(5554);

            System.out.println("Incia el server");

            while(!servidor.isClosed()){
                sc = servidor.accept();
                System.out.println(sc);

                DataInputStream in = new DataInputStream(sc.getInputStream());
                DataOutputStream out = new DataOutputStream(sc.getOutputStream());

                out.writeUTF("Dame un nombre para identificarte");
                String nombreCliente = in.readUTF();
                InterfazServer.tabPanel.add(nombreCliente, new Pestanas());

                HiloServer hilo  = new HiloServer(sc, listaHilos, nombreCliente);
                hilo.setName(nombreCliente);
                listaHilos.add(hilo);
                System.out.println(listaHilos);
                hilo.start();

                puertos.add(sc);
                System.out.println(puertos);

                System.out.println("Creada la conexión con el cliente " + nombreCliente);
            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void envio(){
        try {
            String donde;
            donde = InterfazServer.tabPanel.getTitleAt(InterfazServer.tabPanel.getSelectedIndex());
            if(listaHilos.size()>0) {
                for (int i = 0; i < listaHilos.size(); i++){
                    HiloServer supp = listaHilos.get(i);
                    if(supp.getName().equals(donde)){
                        sc= puertos.get(i);
                    }
                }
            }
            DataOutputStream out = new DataOutputStream(sc.getOutputStream());
            out.writeUTF("Server --> "  + InterfazServer.txtEscribe.getText());
            Pestanas.chat.append("Server --> "  + InterfazServer.txtEscribe.getText()+ "\n");
            InterfazServer.txtEscribe.setText("");


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void borra(int puerto){
        puertos.remove(puerto);
        listaHilos.remove(puerto);
    }
}
