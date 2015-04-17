package user_interface;

//import functional.XmlFileWorking;

import functional.Add;
import functional.Remove;
import functional.Search;
import functional.XmlFileWorking;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.*;
import java.net.*;
import java.text.ParseException;
import java.util.Date;


public class myServer implements Runnable {
    static int clientCount = 0;
    Socket connection;
    String input;
    String name = "";
    String temp = "";
    XmlFileWorking xmlFileWorking = new XmlFileWorking();
    private final String TEMP_FOLDER = "resources/temp/";
    private final String TEMP_XML = "temp.xml";
    private SAXBuilder builder = new SAXBuilder();


    public myServer(Socket socket) {
        this.connection = socket;
        clientCount++;
    }

    public static void serverInit() throws IOException {
        try {
            Resources.sw = new FileWriter(Resources.serverLogDirect, true);
            Resources.sw.write("\n" + "Session started: " + new java.util.Date().toString() + "\n");
            Resources.sw.close();
        } catch (IOException e) {
            System.out.println("Ошибка в создании при работе с Log файлом!");
        }

        ServerSocket servers = null;

        try {
            servers = new ServerSocket(4444);
        } catch (IOException e) {
            System.out.println("Невозможно запустить сервер, порт занят!");
            System.exit(-1);
        }
        //Ждем подключения клиента, запускаем поток на каждое подключение
        while (true) {
            try {
                Socket connection = servers.accept();
                Runnable runnable = new myServer(connection);
                Thread thread = new Thread(runnable);
                thread.start();
            } catch (IOException e) {
                System.out.println("Ошибка при создании потока!");
                System.exit(-1);
            }
        }
    }

    @Override
    public void run() {
        name = "";
        try {
            BufferedReader inServer = new BufferedReader(new
                    InputStreamReader(connection.getInputStream()));
            PrintWriter outServer = new PrintWriter(connection.getOutputStream(), true);
            //считываем имя клиента
            if ((input = inServer.readLine()) != null) {
                name = input;
            }
            try {
                Resources.sw = new FileWriter(Resources.serverLogDirect, true);

                Resources.sw.write(("\n Client " + name + " connected        " + new Date()));
                System.out.println("Client " + name + "  connected        " + new Date());
                Resources.sw.close();
            } catch (IOException e) {
                System.out.println("Ошибка в создании при работе с Log файлом!");
            }
            clientCount++;
            //чистаем сообщения клиента
            while (true) {
                if ((input = inServer.readLine()) != null) {

                    try {
                        temp = getMethodFromClient(input);
                        outServer.println(temp);


                    } catch (JDOMException e) {
                        //ошибки в хмле
                    } catch (ParseException e) {

                    }

                    //Здесь расшифровываем сообщения и вызываем нужные методы
                    //после чего отправляем результат обратно

                }
            }
        } catch (IOException e) {

        }

    }

    public String getMethodFromClient(String inputMessage) throws IOException, JDOMException, ParseException {
        PrintWriter printWriter;
        File file = new File(TEMP_FOLDER + name + TEMP_XML);
        file.createNewFile();
        printWriter = new PrintWriter(file.getAbsoluteFile());
        printWriter.print(inputMessage);
        printWriter.close();
        Document document = builder.build(file);
        Element root = document.getRootElement();
            if ("add".equals(root.getName())) {
            Add.addTradition(xmlFileWorking.getTraditionFromClient_ADD(TEMP_FOLDER + name + TEMP_XML).getHoliday(),
                    xmlFileWorking.getTraditionFromClient_ADD(TEMP_FOLDER + name + TEMP_XML).getCountry(),
                    xmlFileWorking.getTraditionFromClient_ADD(TEMP_FOLDER + name + TEMP_XML).getDescription(),
                    Resources.traditions);
            xmlFileWorking.saveTradition(Resources.traditions, (TEMP_FOLDER + name + TEMP_XML));
                return xmlFileWorking.xmlToString(TEMP_FOLDER + name + TEMP_XML);
        }
        if ("search".equals(root.getName())) {
            xmlFileWorking.saveTradition(Search.search(xmlFileWorking.getRequestFromClient_Search(TEMP_FOLDER + name + TEMP_XML),
                    Resources.traditions),TEMP_FOLDER + name + TEMP_XML);
            return  xmlFileWorking.xmlToString(TEMP_FOLDER + name + TEMP_XML);

        }
        if ("regularSearch".equals(root.getName())) {
            xmlFileWorking.saveTradition(Search.regularSearch(xmlFileWorking.getRequestFromClient_regularSearch(TEMP_FOLDER+name+TEMP_XML),
                    Resources.traditions),TEMP_FOLDER+name+TEMP_XML);
            return xmlFileWorking.xmlToString(TEMP_FOLDER+name+TEMP_XML);
        }
        if ("maskSearch".equals(root.getName())) {
             xmlFileWorking.saveTradition(Search.maskSearch(xmlFileWorking.getRequestFromClient_maskSearchHolidayName(TEMP_FOLDER+name+TEMP_XML),
                    xmlFileWorking.getRequestFromClient_maskSearchCountryName(TEMP_FOLDER+name+TEMP_XML),
                    xmlFileWorking.getRequestFromClient_maskSearchDescriptionName(TEMP_FOLDER+name+TEMP_XML),Resources.traditions),TEMP_FOLDER+name+TEMP_XML);
            System.out.println(xmlFileWorking.xmlToString(TEMP_FOLDER+name+TEMP_XML));
            return xmlFileWorking.xmlToString(TEMP_FOLDER+name+TEMP_XML);
        }
        if ("remove".equals(root.getName())) {
            System.out.println(Resources.traditions.size());
            Remove.removeTraditionGui(Integer.parseInt(xmlFileWorking.getIdFromClient_Remove(TEMP_FOLDER + name + TEMP_XML)),Resources.traditions);
                    xmlFileWorking.saveTradition(Resources.traditions, TEMP_FOLDER + name + TEMP_XML);
            return xmlFileWorking.xmlToString(TEMP_FOLDER+name+TEMP_XML);
        }
        if ("change".equals(root.getName())) {
        }
        return null;
    }


}
