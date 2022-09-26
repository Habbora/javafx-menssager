package br.com.habbora.javafxmenssagerclient;

import javafx.scene.layout.VBox;

import java.io.*;
import java.net.Socket;

public class Client {

    Socket socket;
    BufferedReader bufferedReader;
    BufferedWriter bufferedWriter;

    public Client(Socket socket) {
        try {
            this.socket = socket;
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erro ao conectar ao servidor");
            closedEveryThing(socket, bufferedReader, bufferedWriter);
        }
    }

    public void sendMenssage(String menssage) {
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

    public void receiveMenssage(VBox vBox) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (socket.isConnected()) {
                    try {
                        if (bufferedReader.ready()) {
                            String menssage = bufferedReader.readLine();
                            Controller.addLabel(menssage, vBox);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.out.println("Erro ao receber menssagem");
                        closedEveryThing(socket,bufferedReader,bufferedWriter);
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
