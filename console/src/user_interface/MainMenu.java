package user_interface;

import functional.DataSaveLoad;
import functional.UserData;
import functional.XmlFileWorking;
import lang.Strings_EN;
import lang.Strings_RU;
import model.User;
import org.jdom2.JDOMException;
import org.xml.sax.SAXException;

import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;

public class MainMenu {
    protected static PrintWriter out = new PrintWriter(System.out, true);
    protected static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    protected static DataSaveLoad xmlFiles = new XmlFileWorking();

    public static void init() {
        runServerInit();
        chooseLocale();

    }

    private static void chooseLocale() {
        int N = 1024;
        UserData.rsa.init(N);
        int choice;
        while (true) {
            out.println(Resources.language.getSTART_CHOICE());
            try {
                choice = Integer.parseInt(reader.readLine());
                switch (choice) {
                    case 1:
                        Resources.language = new Strings_RU();
                        readArrays();
                        if (UserData.currentUser == null) {
                            UserHandler.logIn();
                        } else {
                            mainMenu();
                        }
                        break;
                    case 2:
                        Resources.language = new Strings_EN();
                        readArrays();
                        if (UserData.currentUser == null) {
                            UserHandler.logIn();
                        } else {
                            mainMenu();
                        }
                        break;
                    case 3:
                        System.exit(0);
                        break;
                    default:
                        out.println(Resources.language.getWRONG_CHOICE());
                }
            } catch (NumberFormatException ex) {
                out.println(Resources.language.getWRONG_CHOICE());
            } catch (IOException ex) {
                out.println(Resources.language.getIO_ERROR());
            }
        }
    }
/*
    private static void readLog() {
        try {
            FileInputStream fis = new FileInputStream("c:\\temp\\temp.txt");
            InputStreamReader in = new InputStreamReader(fis);
            BufferedReader buf_read = new BufferedReader(in);
            String buffer;

            while ((buffer = buf_read.readLine()) != null) {
                char[] bf = buffer.toCharArray();
                for (int i=0; i<bf.length;i++) {
                    if(bf[i] == '№')
                        Thread.sleep(20000);
                    else
                        System.out.print(bf[i]);
                }
                System.out.println();
            }

            fis.close();
            in.close();
            buf_read.close();

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
    }
*/
    protected static void adminMenu() {
        out.println(Resources.language.getADMIN_MENU());
    int choice;
        try {
            choice = Integer.parseInt(reader.readLine());
            switch (choice) {
                case 1:
                    AdminHandler.printUserMenu();
                    break;
                case 2:
                    mainMenu();
                    break;
                case 3:
                    help("./serverLog.txt");
                    break;
                case 4:
                    exit();
                    break;
                default:
                    out.println(Resources.language.getWRONG_CHOICE());
                    adminMenu();
                    break;
            }
        }
        catch (NumberFormatException ex) {
            out.println(Resources.language.getWRONG_CHOICE());
            adminMenu();
        } catch (IOException e) {
            out.println(Resources.language.getIO_ERROR());
            adminMenu();
        }
    }

    protected static void mainMenu() {
        if (UserData.currentUser != null) {
            out.println(Resources.language.getMAIN_MENU());
        } else {
            out.println(Resources.language.getGUEST_MAIN_MENU());
        }
        int choice;
        boolean guest_flag = false;
        try {
            choice = Integer.parseInt(reader.readLine());
            switch (choice) {
                case 1:
                    if (UserData.currentUser != null) {
                        AddHandler.addMenu();
                        break;
                    } else {
                        guest_flag = true;
                    }
                case 2:
                    if (UserData.currentUser != null || guest_flag) {
                        SearchHandler.searchMenu();
                        break;
                    } else {
                        guest_flag = true;
                    }
                case 3:
                    if (UserData.currentUser != null || guest_flag) {
                        PrintHandler.showMenu();
                        break;
                    } else {
                        guest_flag = true;
                    }
                case 4:
                    if (UserData.currentUser != null || guest_flag) {
                        if (Resources.language.getClass() == Strings_EN.class) help("./resources/helps/help_en.txt");
                        else help("./resources/helps/help_ru.txt");
                        break;
                    } else {
                        guest_flag = true;
                    }
                case 5:
                    if (UserData.currentUser != null || guest_flag) {
                        chooseLocale();
                        break;
                    }
                case 6:
                    if (UserData.currentUser != null) {
                        UserData.logOut(Resources.traditions, Resources.countries, Resources.holidays);
                        boolean admin_flag = UserData.prevUser != null ? UserData.prevUser.isAdmin() : false;
                        if (admin_flag) {
                            UserData.currentUser = UserData.prevUser;
                            adminMenu();
                        }
                        else UserHandler.logIn();
                        break;
                    }
                case 7:
                    exit();
                    break;
                default:
                    out.println(Resources.language.getWRONG_CHOICE());
                    mainMenu();
                    break;
            }
        } catch (NumberFormatException ex) {
            out.println(Resources.language.getWRONG_CHOICE());
            mainMenu();
        } catch (IOException e) {
            out.println(Resources.language.getIO_ERROR());
            mainMenu();
        }
    }

    private static void help(String path) {
        File helpFile = new File(path);
        try {
            BufferedReader fileReader = new BufferedReader(new FileReader(helpFile));
            while (fileReader.ready()) out.println(fileReader.readLine());
            out.println(Resources.language.getHELP_MENU());
            int choice = Integer.parseInt(reader.readLine());
            switch (choice) {
                case 1:
                    mainMenu();
                    break;
                case 2:
                    exit();
                    break;
                default:
                    out.println(Resources.language.getWRONG_CHOICE());
                    mainMenu();
            }
        } catch (FileNotFoundException e) {
            out.println(Resources.language.getHELP_FILE_ERROR());
            mainMenu();
        } catch (IOException e) {
            out.println(Resources.language.getIO_ERROR());
            mainMenu();
        } catch (NumberFormatException ex) {
            out.println(Resources.language.getWRONG_CHOICE());
            mainMenu();
        }
    }

    protected static void exit() { //Куча Исключений, нужны try и catch
        try {
            if (UserData.currentUser != null) {
                UserData.logOut(Resources.traditions, Resources.countries, Resources.holidays);
            }
            writeArrays();
            reader.close();
            out.close();
            System.exit(0);
        } catch (IOException e) { // прописать сюда ошибкииииииии
        } catch (JDOMException e) {
        } catch (ParseException e) {
        } catch (ClassNotFoundException e) {
        }
    }

    private static void readArrays() throws IOException {
        try {
            if (Resources.language.getClass() == Strings_EN.class) xmlFiles.loadAllEN(Resources.traditions, Resources.countries, Resources.holidays);
            else xmlFiles.loadAllRU(Resources.traditions, Resources.countries, Resources.holidays);
        } catch (ClassNotFoundException ex) {
            MainMenu.out.println(Resources.language.getNO_CLASS());
        } catch (JDOMException e) {
            MainMenu.out.println(Resources.language.getXML_ERROR());
        } catch (ParseException e) {
            MainMenu.out.println(Resources.language.getPARSE_ERROR());
        } catch (SAXException e) {
            MainMenu.out.println(Resources.language.getXML_ERROR());
        }
    }

    private static void writeArrays() throws IOException, JDOMException, ParseException, ClassNotFoundException {
        if (Resources.language.getClass() == Strings_EN.class) {
            xmlFiles.saveAllEN(Resources.traditions, Resources.countries, Resources.holidays);
// serFiles.saveAllEN();
        } else {
            xmlFiles.saveAllRU(Resources.traditions, Resources.countries, Resources.holidays);
// serFiles.saveAllRU();
        }
    }

    private static void runServerInit(){
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    myServer.serverInit();
                } catch (IOException e) {

                }

            }
        }).start();
    }

//endregion
}
