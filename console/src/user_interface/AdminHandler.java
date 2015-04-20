package user_interface;

import functional.Change;
import functional.Remove;
import functional.UserData;
import model.User;
import org.jdom2.JDOMException;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

/**
 * Created by root on 18.04.15.
 */
public class AdminHandler {
    static void printUsers(ArrayList<User> users) {
        int count = 0;
        MainMenu.out.println(Resources.language.getCHOOSE_USER());
        for (User item : users) {
            count++;
            MainMenu.out.printf("%5d%30s\n",count, item.getLogin());
        }
    }

    private static void deleteUser() {
        try {
            int id = Integer.parseInt(MainMenu.reader.readLine());
            User user = UserData.users.get(id - 1);
            if (!user.isAdmin()) {
                Remove.removeUser(user, UserData.users);
            }
            else {
                MainMenu.out.println(Resources.language.getWRONG_CHOICE());
                printUsers(UserData.users);
                deleteUser();
            }
        } catch (IOException ex) {
            MainMenu.out.println(Resources.language.getIO_ERROR());
        }
    }

    private static void changeUser() {
        try {
            int id = Integer.parseInt(MainMenu.reader.readLine());
            User user = UserData.users.get(id - 1);
            if (user.isAdmin()) {
                user = null;
            }
            int choice;
            MainMenu.out.println(Resources.language.getCHANGE_USER());
            choice = Integer.parseInt(MainMenu.reader.readLine());
            switch (choice) {
                case 1 :
                    MainMenu.out.println(Resources.language.getENTER_LOGIN());
                    String login = MainMenu.reader.readLine();
                    UserData.users = (ArrayList <User>)Change.editUser(user, login, 1, UserData.users);
                    break;
                case 2 :
                    MainMenu.out.println(Resources.language.getENTER_PASS());
                    String pass = MainMenu.reader.readLine();
                    UserData.users = (ArrayList <User>)Change.editUser(user, pass, 2, UserData.users);
                    break;
                default:
                    break;
            }
        } catch (IOException ex) {
            MainMenu.out.println(Resources.language.getIO_ERROR());
        } catch (NullPointerException ex) {
            MainMenu.out.println(Resources.language.getWRONG_CHOICE());
        }
    }

    private static void getUser() {
        try {
            int id = Integer.parseInt(MainMenu.reader.readLine());
            User user = UserData.users.get(id - 1);
            //System.out.println(user.getPass().toByteArray().toString());
            UserHandler.loadUserData(user.getLogin(), user.getPass().toByteArray().toString());
            MainMenu.mainMenu();
        } catch (IOException ex) {
            MainMenu.out.println(Resources.language.getIO_ERROR());
        } catch (SAXException ex) {
            MainMenu.out.println(Resources.language.getXML_ERROR());
        } catch (JDOMException ex) {
            MainMenu.out.println(Resources.language.getXML_ERROR());
        } catch (ParseException ex) {
            MainMenu.out.println(Resources.language.getXML_ERROR());
        }
    }

    static void printUserMenu() {
        MainMenu.out.println(Resources.language.getUSER_MENU());
        int choice;
        try {
            choice = Integer.parseInt(MainMenu.reader.readLine());
            switch (choice) {
                case 1 :
                    printUsers(UserData.users);
                    deleteUser();
                    break;
                case 2 :
                    printUsers(UserData.users);
                    changeUser();
                    break;
                case 3 :
                    UserHandler.registration();
                    break;
                case 4 :
                    printUsers(UserData.users);
                    getUser();
                    break;
                default:
                    break;
            }
        }
        catch (IOException ex) {
            MainMenu.out.println(Resources.language.getIO_ERROR());
            //showMenu();
        }
    }
}
