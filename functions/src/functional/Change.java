package functional;

import model.Country;
import model.Holiday;
import model.Tradition;
import model.User;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Change {
    //Правка страны.
    public static List<Country> editCountry(Country country, String newName, List<Country> list) {
        //int index = Search.searchIndex(country, list);
        int index = list.indexOf(country);
        country.setName(newName);
        //for (int i = 0; i < index.length; i++){
        list.set(index, country);
        //}
        return list;
    }

    public static List<Holiday> editHoliday(int id, Holiday newHoliday,
                                            List<Holiday> list) {
        list.set(id, newHoliday);
        return list;
    }

    public static List<Tradition> editTradition(String newStr, int id, int param, List<Tradition> list, List<Country> countries) {
        Tradition tradition = list.get(id);
        switch (param) {
            case 1:
                tradition.setDescription(newStr);
                break;
            case 2:
                tradition.getCountry().setName(newStr);
                break;
            case 3:
                tradition.getHoliday().setName(newStr);
                break;
            case 4:
                ArrayList<Tradition> traditions = Search.getCountryTraditions(id,countries,list);
                //int count = 0;
                for (Tradition trad : list) {
                    for (int i = 0; i < traditions.size(); i++) {
                        if (trad.equals(traditions.get(i)))
                            list.set(list.indexOf(
                                            traditions.get(i)),
                                    new Tradition(tradition.getHoliday(),
                                            new Country(newStr), tradition.getDescription()));
                    }
                    //count++;
                }
                break;
            default:
                break;
        }
        list.set(id, tradition);
        return list;
    }

    public static List<Tradition> editTradition(Holiday holiday, Holiday newHoliday, List<Tradition> list) {
        ArrayList<Tradition> traditions = Search.getTraditions(holiday,list);
        //int count = 0;
        for (Tradition tradition : list) {
            for (int i = 0; i < traditions.size(); i++) {
                if (tradition.equals(traditions.get(i)))
                    list.set(list.indexOf(traditions.get(i)), new Tradition(newHoliday, tradition.getCountry(), tradition.getDescription()));
            }
            //count++;
        }
        return list;
    }

    public static List<Tradition> editTradition(int id, Holiday newHoliday, Country newCountry, String description, List<Tradition> list) {

        Tradition changing = list.get(id);
        changing.setHoliday(newHoliday);
        changing.setCountry(newCountry);
        changing.setDescription(description);
        list.set(id,changing);
        return list;
    }

    public static final String ROOT = "resources/users/";
    private static final String TRADITION_FILE = "/traditionSave.xml";
    private static final String HOLIDAY_FILE = "/holidaySave.xml";
    private static final String COUNTRY_FILE = "/countrySave.xml";

    public static void copyFile(File in, File out) throws IOException {

        byte buffer[] = new byte[100000000];
        try {
            FileInputStream fileIn = new FileInputStream(in);
            int bytes = fileIn.read(buffer,0,100000000);
            fileIn.close();

            FileOutputStream fileOut = new FileOutputStream(out);
            fileOut.write(buffer,0,bytes);
            fileOut.close();
        }
        catch (Exception e) {

        }
    }

    private static void workWithFile(String oldLogin, String newLogin) {
        File folder = new File("resources/users/" + newLogin);
        if(folder.mkdir()) {
            File traditionStart = new File(ROOT + oldLogin + TRADITION_FILE);
            File countryStart = new File(ROOT + oldLogin + COUNTRY_FILE);
            File holidayStart = new File(ROOT + oldLogin + HOLIDAY_FILE);

            File traditionFinal = new File(ROOT + newLogin + TRADITION_FILE);
            File countryFinal = new File(ROOT + newLogin + COUNTRY_FILE);
            File holidayFinal = new File(ROOT + newLogin + HOLIDAY_FILE);

            try {
                copyFile(traditionStart, traditionFinal);
                copyFile(countryStart, countryFinal);
                copyFile(holidayStart, holidayFinal);
            } catch (IOException ex) {
                //MainMenu.out.println()
            }
        }
        File old_folder = new File("resources/users/" + newLogin);
        Remove.deleteDirectory(old_folder);
    }

    public static List<User> editUser(User user, String editString, int param_num, List<User> list) {
        int index = list.indexOf(user);
        switch (param_num) {
            case 1:
                workWithFile(user.getLogin(), editString);
                user.setLogin(editString);
                break;
            case 2:
                UserData.rsa.setModulus(new BigInteger("114300212443049308755638385038607092399228059171843074638659728066396329731870812301666900170326603999649607364454783561463395729169397992550553334308251756497995161575531048559625701582012129417669546314420880750128408561569822198960212709010390091463374475374736305384151906473683969549684741213893356703077"));
                BigInteger pass_value = new BigInteger(editString.getBytes());
                User new_user = new User(user.getLogin(), pass_value.toString(), UserData.rsa);
                user = new_user;
                break;
            default:
                break;
        }
        list.set(index, user);
        return list;
    }
}
