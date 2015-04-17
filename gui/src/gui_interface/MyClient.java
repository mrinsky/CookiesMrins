package gui_interface;

import gui_interface.Resources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class MyClient {
    public static void initClient(String name) {
        String fuser, fserver;

        Socket fromserver = null;
        try {
            fromserver = new Socket("localhost", 4444);
        } catch (IOException e) {

        }


        try {
            Resources.clientIn = new BufferedReader
                    (new InputStreamReader(fromserver.getInputStream()));
        } catch (IOException e) {

        }
        ;
        try {
            Resources.clientOut = new PrintWriter
                    (fromserver.getOutputStream(), true);
        } catch (IOException e) {

        }
        BufferedReader inu = new BufferedReader
                (new InputStreamReader(System.in));
        //Устанавливаем имя 
        Resources.clientOut.println(name);

        //Отправляем сообщение на сервер, читаем ответ

        /*while((fuser = inu.readLine())!= null){
                       Resources.clientOut.println(fuser);
                       fserver = Resources.clientIn.readLine();
                       System.out.println(fserver);
                       if(fuser.equalsIgnoreCase("exit chat")) break;
                       if(fuser.equalsIgnoreCase("close chat")) break;
                   }

               } catch (IOException e) {

               }


               Resources.clientOut.close();
               try {
                   Resources.clientIn.close();
               } catch (IOException e) {

               }
               try {
                   inu.close();
               } catch (IOException e) {

               }
               try {
                   fromserver.close();
                   */
    }
}