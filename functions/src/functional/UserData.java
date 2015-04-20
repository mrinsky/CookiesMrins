package functional;
import model.*;
        import org.jdom2.JDOMException;
        import org.xml.sax.SAXException;

        import java.io.BufferedReader;
        import java.io.IOException;
        import java.io.InputStreamReader;
        import java.io.PrintWriter;
        import java.math.BigInteger;
        import java.text.ParseException;
        import java.util.ArrayList;
        import java.util.LinkedList;
        import java.util.List;

/**
 * Created by root on 05.04.15.
 */

public class UserData {
    public static RSA rsa = new RSA();
    public static User prevUser;
    public static User currentUser;

    public static ArrayList<User> users = new ArrayList<User>();

    public static String loadData(String login, String pass, ArrayList<Tradition> traditions, LinkedList<Country> countries, LinkedList<Holiday> holidays) throws JDOMException, SAXException, ParseException, IOException {
        boolean admin_flag = currentUser != null ? currentUser.isAdmin() : false;
        if (admin_flag && !authentication(login, pass)) {
            int index = Search.searchIndex(UserData.users, "admin");
            if (index >= 0)prevUser = users.get(index);
        }
        if (authentication(login, pass) || admin_flag) {
            currentUser = users.get(Search.searchIndex(users, login));
            new XmlFileWorking().loadUser(traditions,countries,holidays);
            return "";
        }
        return "Error";
    }

    public static void logOut(ArrayList<Tradition> traditions, List<Country> countries, List<Holiday> holidays) throws IOException {
        ArrayList<Tradition> tr_list = new ArrayList<Tradition>();
        for (int i = 0; i < traditions.size(); i++) {
            tr_list.add(traditions.get(i));
        }
        currentUser.setTraditionList(tr_list);
        Remove.removeListTradition(tr_list,traditions);
        LinkedList<Country> c_list = new LinkedList<Country>();
        for (int i = 0; i < countries.size(); i++) {
            c_list.add(countries.get(i));
        }
        currentUser.setCountryList(c_list);
        Remove.removeListCountry(c_list,countries);
        LinkedList<Holiday> h_list = new LinkedList<Holiday>();
        for (int i = 0; i < holidays.size(); i++) {
            h_list.add(holidays.get(i));
        }
        currentUser.setHolidayList(h_list);
        Remove.removeListHoliday(h_list,holidays);

        new XmlFileWorking().saveUser(traditions,holidays,countries);
    }

    static boolean authentication(String login, String pass) {
        int index = -1;
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getLogin().equals(login)) {
                index = i;
            }
        }
        BigInteger message = new BigInteger(pass.getBytes());
        BigInteger encrypt = UserData.rsa.encrypt(message);
        return (index > -1) ? encrypt.equals(users.get(index).getPass()) : false;
    }
}

