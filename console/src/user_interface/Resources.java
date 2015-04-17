package user_interface;

import lang.Language;
import lang.Strings_EN;
import model.Country;
import model.Holiday;
import model.Tradition;
import model.User;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;

public class Resources {

    public static LinkedList<Holiday> holidays = new LinkedList<Holiday>();
    public static LinkedList<Country> countries = new LinkedList<Country>();
    public static ArrayList<Tradition> traditions = new ArrayList<Tradition>();
    public static LinkedList<User> users = new LinkedList<User>();
    public static PrintWriter out = new PrintWriter(System.out, true);
    public static Language language = new Strings_EN();
    public static String serverLogDirect = "serverLog.txt";
    public static FileWriter sw;

}
