package functional;

import model.Country;
import model.Holiday;
import model.Tradition;
import model.User;
import org.jdom2.JDOMException;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.math.BigInteger;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by root on 05.04.15.
 */
public class Registration {
    /*************************
     * Constructors
     *************************/
    private Registration() {}
    /*************************
     * Methods
     *************************/
    public static void readLogin(String login) {
        if (checkLogin(login)) {
            throw new IllegalArgumentException();
        }
    }
    public static void registration(String login, String pass1, String pass2,ArrayList<Tradition> traditions, LinkedList<Country> countries, LinkedList<Holiday> holidays
    ) throws IllegalArgumentException, IOException, JDOMException, SAXException, ParseException {
        readLogin(login);
        if (pass1.equals(pass2)) {
            UserData.rsa.setModulus(new BigInteger("114300212443049308755638385038607092399228059171843074638659728066396329731870812301666900170326603999649607364454783561463395729169397992550553334308251756497995161575531048559625701582012129417669546314420880750128408561569822198960212709010390091463374475374736305384151906473683969549684741213893356703077"));
            BigInteger pass = new BigInteger(pass1.getBytes());
            User user = new User(login, pass, UserData.rsa.getPublicKey(),
                    UserData.rsa.getModulus(), UserData.rsa);
            UserData.users.add(user);

            UserData.loadData(login, pass1, traditions, countries, holidays);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public static boolean checkLogin(String login) {
        boolean result = false;
        for (int i = 0; i < UserData.users.size(); i++) {
            if (login.equals(UserData.users.get(i).getLogin())) {
                result = true;
            }
        }
        return result;
    }
}