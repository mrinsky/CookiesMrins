package functional;

import model.Country;
import model.Holiday;
import model.Tradition;
import model.User;

import java.util.ArrayList;
import java.util.List;
import java.io.File;

public class Remove {

    public static void removeListTradition(List<Tradition> traditionList, List<Tradition> from){
        if (traditionList != null){
            for(Tradition item : traditionList) {
                from.remove(item);
            }
        }
    }

    public static void removeListCountry(List<Country> countryList, List<Country> from){
        if (countryList != null){
            for(Country item : countryList) {
                from.remove(item);
            }
        }
    }

    public static void removeListHoliday(List<Holiday> holidayList, List<Holiday> from){
        if (holidayList != null){
            for(Holiday item : holidayList) {
                from.remove(item);
            }
        }
    }

    public static void removeTradition(int id, List<Tradition> traditions) {
        if (id >= 0 && id < traditions.size()) {
            traditions.remove(id);
        }
        else throw new IndexOutOfBoundsException();
    }

    public static void removeTraditionGui(int id, List<Tradition> traditions) {
        if (id < traditions.size()) {
            traditions.remove(id);
        }
        else throw new IndexOutOfBoundsException();
    }

    public static void removeCountry(int country, List<Country> c_list, List<Tradition> from){
        //поиск традиций с этой страной
        removeListTradition(Search.getCountryTraditions(country,c_list,from),from);
        c_list.remove(country);
    }

    public static void removeHoliday(int holiday, List<Holiday> h_list, List<Tradition> from){
        // поиск и удаление традиций с этим праздником
        removeListTradition(Search.getTraditions(h_list.get(holiday), from),from);
        h_list.remove(holiday);
    }

    static void deleteDirectory(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i=0; i<children.length; i++) {
                File f = new File(dir, children[i]);
                deleteDirectory(f);
            }
            dir.delete();
        } else dir.delete();
    }

    public static void removeUser(User user, ArrayList<User> users) {
        File dir = new File(User.ROOT + user.getLogin());
        deleteDirectory(dir);
        users.remove(user);
    }
}