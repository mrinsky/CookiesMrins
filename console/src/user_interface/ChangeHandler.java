package user_interface;

import functional.Change;
import functional.Remove;
import functional.UserData;
import model.Country;
import model.Holiday;
import model.Tradition;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

public class ChangeHandler {

    protected static void holidayChanger(boolean isAdmin) {
        try {
            MainMenu.out.println(Resources.language.getID_REQUEST());
            if (isAdmin) {
                PrintHandler.printArrayHolidays(Resources.holidays, 0);
            }
            else{
                PrintHandler.printArrayHolidays(UserData.currentUser.getHolidayList());
            }
            int choice = Integer.parseInt(MainMenu.reader.readLine());
            if (choice >= 0 && choice < Resources.holidays.size()) {
                changeHoliday(choice);
            }
            else {
                throw new IndexOutOfBoundsException();
            }
            PrintHandler.showMenu();
        } catch (IOException e) {
            MainMenu.out.println(Resources.language.getIO_ERROR());
        } catch (IndexOutOfBoundsException e) {
            Resources.language.getWRONG_CHOICE();
            holidayChanger(UserData.currentUser.isAdmin());
        }

    }

    protected static void countryChanger(boolean isAdmin) {
        try {
            MainMenu.out.println(Resources.language.getCOUNTRY_CHOICE());
            if (isAdmin) {
                PrintHandler.printArrayCountries(Resources.countries);
            }
            else{
                PrintHandler.printArrayCountries(UserData.currentUser.getCountryList());
            }
            int choice = Integer.parseInt(MainMenu.reader.readLine());
            if (choice >= 0 && choice < Resources.countries.size()) {
                changeCountry(choice);
            }
            else {
                throw new IndexOutOfBoundsException();
            }
            PrintHandler.showMenu();
        } catch (IOException e) {
            MainMenu.out.println(Resources.language.getIO_ERROR());
        } catch (IndexOutOfBoundsException e) {
            Resources.language.getWRONG_CHOICE();
            holidayChanger(UserData.currentUser.isAdmin());
        }
    }

    private static void changeHoliday(int id) throws IOException {
        Holiday holiday = Resources.holidays.get(id);
        Holiday newHoliday = AddHandler.addHoliday();
        Resources.holidays = (LinkedList<Holiday>) Change.editHoliday(id, newHoliday, Resources.holidays);
        Remove.removeHoliday(Resources.holidays.size() - 1, Resources.holidays, Resources.traditions);
        Resources.traditions = (ArrayList<Tradition>)Change.editTradition(holiday, newHoliday, Resources.traditions);
    }

    private static void changeCountry(int id) throws IOException {
        Country country = Resources.countries.get(id);
        MainMenu.out.println(Resources.language.getCOUNTRY_REQUEST());
        String newCountryName = MainMenu.reader.readLine();
        Resources.traditions = (ArrayList<Tradition>)Change.editTradition(newCountryName, id, 4, Resources.traditions,Resources.countries);
        Resources.countries = (LinkedList<Country>)Change.editCountry(country, newCountryName, Resources.countries);
        MainMenu.out.println(Resources.language.getREADY());
    }

    protected static void changeTradition() {
        MainMenu.out.println(Resources.language.getCHANGE_TRADITION());
        try {
            int choice = Integer.parseInt(MainMenu.reader.readLine());
            switch (choice) {
                case 1:
                    changeDescription();
                    break;
                case 2:
                    changeCountryTradition(UserData.currentUser.isAdmin());
                    break;
                case 3:
                    changeHolidayTradition(UserData.currentUser.isAdmin());
                    break;
                default:
                    MainMenu.out.println(Resources.language.getWRONG_CHOICE());
            }
        } catch (IOException e) {
            MainMenu.out.println(Resources.language.getIO_ERROR());
        }
    }

    protected static void changeHolidayTradition(boolean isAdmin) {
        try {
            MainMenu.out.println(Resources.language.getID_REQUEST());
            int choice = Integer.parseInt(MainMenu.reader.readLine());

            MainMenu.out.println(Resources.language.getHOLIDAY_REQUEST());
            if (isAdmin) {
                PrintHandler.printArrayHolidays(Resources.holidays, 0);
            }
            else{
                PrintHandler.printArrayHolidays(UserData.currentUser.getHolidayList());
            }
            int description = Integer.parseInt(MainMenu.reader.readLine());
            if (choice >= 0 && choice < Resources.holidays.size()) {
                Resources.traditions = (ArrayList<Tradition>)
                        Change.editTradition(Resources.holidays.get(description).getName(), choice, 3,
                        Resources.traditions, Resources.countries);
            }
            else throw new IndexOutOfBoundsException();
            MainMenu.out.println(Resources.language.getREADY());
        } catch (IOException e) {
            MainMenu.out.println(Resources.language.getIO_ERROR());
        } catch (IndexOutOfBoundsException e){
            Resources.language.getWRONG_CHOICE();
            //changeDescription();
        }
    }

    protected static void changeCountryTradition(boolean isAdmin) {
        try {
            MainMenu.out.println(Resources.language.getID_REQUEST());
            int choice = Integer.parseInt(MainMenu.reader.readLine());

            MainMenu.out.println(Resources.language.getCOUNTRY_CHOICE());
            if (isAdmin) {
                PrintHandler.printArrayCountries(Resources.countries);
            }
            else{
                PrintHandler.printArrayCountries(UserData.currentUser.getCountryList());
            }
            int description = Integer.parseInt(MainMenu.reader.readLine());
            if (choice >= 0 && choice < Resources.countries.size()) {
                Resources.traditions = (ArrayList<Tradition>)
                        Change.editTradition(Resources.countries.get(description).getName(), choice, 2,
                        Resources.traditions, Resources.countries);
            }
            else throw new IndexOutOfBoundsException();
            MainMenu.out.println(Resources.language.getREADY());
        } catch (IOException e) {
            MainMenu.out.println(Resources.language.getIO_ERROR());
        } catch (IndexOutOfBoundsException e){
            Resources.language.getWRONG_CHOICE();
            //changeDescription();
        }
    }

    protected static void changeDescription() {
        try {
            MainMenu.out.println(Resources.language.getID_REQUEST());
            int choice = Integer.parseInt(MainMenu.reader.readLine());
            MainMenu.out.println(Resources.language.getENTER_DESCRIPTION());

            String description = MainMenu.reader.readLine();

            if (choice >= 0 && choice < Resources.traditions.size()) {
                Resources.traditions = (ArrayList<Tradition>)
                        Change.editTradition(description, choice, 1, Resources.traditions, Resources.countries);
            }
            else throw new IndexOutOfBoundsException();
            MainMenu.out.println(Resources.language.getREADY());
        } catch (IOException e) {
            MainMenu.out.println(Resources.language.getIO_ERROR());
        } catch (IndexOutOfBoundsException e){
            Resources.language.getWRONG_CHOICE();
            changeDescription();
        }
    }
}
