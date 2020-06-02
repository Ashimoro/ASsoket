package com.example.soket;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.net.*;
import java.io.*;

public class MainActivity extends AppCompatActivity {
    private Socket socket;
    private static final int SERVERPORT = 6666;
    private static final String SERVER_IP = "192.168.43.87";
    String line;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Thread(new ClientThread()).start();
    }

    class ClientThread implements Runnable {
        TextView textView = findViewById(R.id.textView);

        @Override
        public void run() {

            try {
                InetAddress serverAddr = InetAddress.getByName(SERVER_IP);
                //System.out.println("Any of you heard of a socket with IP address " + address + " and port " + serverPort + "?");
                socket = new Socket(serverAddr, SERVERPORT);

                // Берем входной и выходной потоки сокета, теперь можем получать и отсылать данные клиентом.
                InputStream sin = socket.getInputStream();
                OutputStream sout = socket.getOutputStream();

                // Конвертируем потоки в другой тип, чтоб легче обрабатывать текстовые сообщения.
                DataInputStream in = new DataInputStream(sin);
                DataOutputStream out = new DataOutputStream(sout);

                // Создаем поток для чтения с клавиатуры.
                BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
//            System.out.println("Type in something and press enter. Will send it to the server and tell ya what it thinks.");
//            System.out.println();

                while (true) {
                    //line = keyboard.readLine(); // ждем пока пользователь введет что-то и нажмет кнопку Enter.
                    //line = "atatatatatata";
                   // System.out.println("Sending this line to the server...");
                    //out.writeUTF(line); // отсылаем введенную строку текста серверу.
                    //out.flush(); // заставляем поток закончить передачу данных.
                    line = in.readUTF(); // ждем пока сервер отошлет строку текста.
                    //System.out.println("СЕРВАК ОТВЕТИЛ ЕПТА : " + line);
                    textView.setText(line);
                }
            } catch (Exception x) {
                x.printStackTrace();
            }
        }
    }
}


