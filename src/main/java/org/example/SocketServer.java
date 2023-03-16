package org.example;


import javax.swing.*;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SocketServer {
    //Declaro las variables necesarias
    static Socket sc;

    static ArrayList<HiloServer> listaHilos;

    static ArrayList<Socket> puertos;

    static Map<String, JTextArea> mapArea = new HashMap<String, JTextArea>();

    public static void main(String[] args){
        //Inicio la interfaz
        new InterfazServer();
        //Inicio los arraylist
        puertos = new ArrayList<>();
        listaHilos = new ArrayList<>();

        try {
            //Creo el servidor indicando el puerto donde se aloja
            ServerSocket servidor = new ServerSocket(5554);

            System.out.println("Incia el server");

            while(!servidor.isClosed()){
                //Mientras el servidor esté activo acepto todas las peticiones
                sc = servidor.accept();

                DataInputStream in = new DataInputStream(sc.getInputStream());
                DataOutputStream out = new DataOutputStream(sc.getOutputStream());

                //Pido un nombre al cliente para que se identifique
                out.writeUTF("Dame un nombre para identificarte");
                String nombreCliente = in.readUTF();
                //Creo un chat con su nombre y guardo el area de texto con ese mismo en el map
                InterfazServer.tabPanel.add(nombreCliente, new Pestanas(nombreCliente));
                mapArea.put(nombreCliente, Pestanas.chat);
                JTextArea support = mapArea.get(nombreCliente);

                //Se crea un hilo para ese cliente con sus datos almacenados
                HiloServer hilo  = new HiloServer(sc, listaHilos, nombreCliente, mapArea);
                hilo.setName(nombreCliente);
                listaHilos.add(hilo);
                hilo.start();

                //Guardamos la info del socket del cliente en nuestra lista de puertos
                puertos.add(sc);

                System.out.println("Creada la conexión con el cliente " + nombreCliente);
            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /*
    Función que se lanza al presionar el botón envio en la interfaz
     */
    public static void envio(){
        try {
            //Recogemos el título del panel en el que nos encontramos en una variable
            String donde;
            donde = InterfazServer.tabPanel.getTitleAt(InterfazServer.tabPanel.getSelectedIndex());
            //Con ella buscamos el socket de ese cliente en la lista
            if(listaHilos.size()>0) {
                for (int i = 0; i < listaHilos.size(); i++){
                    HiloServer supp = listaHilos.get(i);
                    if(supp.getName().equals(donde)){
                        //Lo asignamos a la variable de socket
                        sc= puertos.get(i);
                    }
                }
            }
            //Y ahora se envía el mensaje al cliente indicado
            DataOutputStream out = new DataOutputStream(sc.getOutputStream());
            out.writeUTF("Server --> "  + InterfazServer.txtEscribe.getText());
            Pestanas.chat.append("Server --> "  + InterfazServer.txtEscribe.getText()+ "\n");
            InterfazServer.txtEscribe.setText("");


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /*
    Borra el puerto y el hilo del cliente cuando se desconecta
    Se lanza en el recibo de mensajes
     */
    public static void borra(int puerto){
        puertos.remove(puerto);
        listaHilos.remove(puerto);
    }

    /*
    Para el botón salir
     */
    public static void Salir() throws IOException {
        InterfazServer.frame.dispatchEvent(new WindowEvent(InterfazServer.frame, WindowEvent.WINDOW_CLOSING));
        sc.close();
    }

}
