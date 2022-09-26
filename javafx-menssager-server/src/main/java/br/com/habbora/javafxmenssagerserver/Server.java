package br.com.habbora.javafxmenssagerserver;

import javafx.scene.layout.VBox;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private ServerSocket serverSocket;
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;

    public Server(ServerSocket serverSocket) {
        try {
            this.serverSocket = serverSocket;
            this.socket = serverSocket.accept();
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erro ao criar o servidor.");
            closedEveryThing(socket, bufferedReader, bufferedWriter);
        }
    }

    public void sendMessageToClient(String menssage) {
        try {
            bufferedWriter.write(menssage);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erro ao enviar mensagem.");
            closedEveryThing(socket, bufferedReader, bufferedWriter);
        }
    }

    public void receiveMenssageFromClient(VBox vbox) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (socket.isConnected()) {
                    try {
                        if (bufferedReader.ready()) {
                            String menssage = bufferedReader.readLine();
                            Controller.addLabel(menssage, vbox);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.out.println("Erro ao receber menssagem");
                        closedEveryThing(socket, bufferedReader, bufferedWriter);
                        break;
                    }
                }
            }
        }).start();
    }

    public void closedEveryThing(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        try {
            if (bufferedReader != null) bufferedReader.close();
            if (bufferedWriter != null) bufferedWriter.close();
            if(socket != null) socket.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erro ao fechar o servidor");
        }
    }
}
