package com.frank.addams;

import com.frank.emojis.Emogis;
import com.frank.exceptions.DataFileErrorException;
import com.frank.exceptions.InvalidMenuResponseException;
import com.frank.types.AddamsSearchCriteria;
import com.frank.types.Person;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.*;
/********************************************************************************************
 * Class representing the Addams Family members and various manipulation methods
 ********************************************************************************************/
public class AddamsFamilyApplication {

        /********************************************************************************************
        *  Keyboard object to get user input
        ********************************************************************************************/
        private static Scanner userKeyboardDevice = new Scanner(System.in);

        /********************************************************************************************
        * Constants representing menu options
        ********************************************************************************************/
        private static final String DISPLAY_ALL_ADDAMS_OPTION       = "Display all Addams'";
        private static final String DISPLAY_BY_NAME_OPTION          = "Find an Addams";
        private static final String CHANGE_AN_ADDAMS_NAME_OPTION    = "Change an Addams name";
        private static final String REMOVE_AN_ADDAMS_OPTION         = "Remove an Addams";
        private static final String DISPLAY_ADDAMS_IN_REVERSE_ORDER = "Display all Addams in reverse order";
        private static final String ADD_A_NEW_ADDAMS                = "Add a new Addams";
        private static final String REFRESH_DATA_FROM_FILE          = "Refresh data from file";
        private static final String EXIT_OPTION                     = "Exit";

        /********************************************************************************************
        * Array of menu options display to users as needed
        ********************************************************************************************/
        private static final String[] mainMenuOptions = { DISPLAY_ALL_ADDAMS_OPTION
                                                        , DISPLAY_BY_NAME_OPTION
                                                        , CHANGE_AN_ADDAMS_NAME_OPTION
                                                        , REMOVE_AN_ADDAMS_OPTION
                                                        , DISPLAY_ADDAMS_IN_REVERSE_ORDER
                                                        , ADD_A_NEW_ADDAMS
                                                        , REFRESH_DATA_FROM_FILE
                                                        , EXIT_OPTION
                                                        };
        /********************************************************************************************
         * List of Addams Family members
         ********************************************************************************************/
        private List<Person> theAddamsFamily;          // Application data store

        /********************************************************************************************
         * Constructor for this application
         ********************************************************************************************/
        public AddamsFamilyApplication() throws FileNotFoundException {
                // Note: use of LinkedList rather than ArrayList due to efficiency when adding/removing
                theAddamsFamily = new LinkedList<>();  // Instantiate structure to hold family members
                loadFamilyMembersFromFile();           // Load data structure with family members in a file
        }
        /********************************************************************************************
         * Application controller
         ********************************************************************************************/
        public void run() throws FileNotFoundException {

            startOfApplicationProcessing();           // Display greeting
            String whatTheyChose = new String("");    // Hold response from user prompt
            boolean shouldLoop = true;                // Main processing loop control variable
        /********************************************************************************************
        * main processing loop
        ********************************************************************************************/
            while(shouldLoop) {
                 try {
                        whatTheyChose = displayMenuAndGetResponse();           // Display main menu and get response
                        System.out.println("\nYou chose: " + whatTheyChose);   // Display menu option chosen

                        switch (whatTheyChose) {                               // Process based on menu option chosen
                                case DISPLAY_ALL_ADDAMS_OPTION: {              // If this menu item is chosen...
                                     displayAllPeople();                       //     Run this helper method
                                     break;                                    //         and exit switch
                                 }
                                 case DISPLAY_BY_NAME_OPTION: {                // If this menu item is chosen...
                                      displayByName();                         //     Run this helper method
                                      break;                                   //         and exit switch
                                 }
                                 case CHANGE_AN_ADDAMS_NAME_OPTION: {           // If this menu item is chosen...
                                      changePersonName();                       //     Run this helper method
                                      break;                                    //         and exit switch
                                 }
                                case REMOVE_AN_ADDAMS_OPTION: {                 // If this menu item is chosen...
                                     removeAnAddams();                          //     Run this helper method
                                     break;                                     //         and exit switch
                                }
                                case DISPLAY_ADDAMS_IN_REVERSE_ORDER: {         // If this menu item is chosen...
                                     displayAllInReverseOrder();                //     Run this helper method
                                     break;                                     //         and exit switch
                                }
                                case ADD_A_NEW_ADDAMS: {                         // If this menu item is chosen...
                                     addANewAddams();                            //     Run this helper method
                                     break;                                      //         and exit switch
                                }
                                case REFRESH_DATA_FROM_FILE: {                   // If this menu item is chosen...
                                     theAddamsFamily.removeAll(theAddamsFamily); //     Remove all app data structure current entries
                                     loadFamilyMembersFromFile();                //     Reload app data structure
                                     break;                                      //         and exit switch
                                }
                                 case EXIT_OPTION: {                             // If this menu item is chosen...
                                      shouldLoop = false;                        //     Set loop to end
                                      break;                                     //         and exit switch
                                 }
                                 default: {    // if somehow an unexpected option was returned - throw an exception
                                         throw new InvalidMenuResponseException("Invalid menu option " + whatTheyChose + " entered: ");
                                 }
                         }
                 }
                 catch(InvalidMenuResponseException exceptionObject) {
                         System.out.println("\nUh-Oh, Looks like you entered an invalid response, please try again");
                 }
            }
            endOfApplicationProcessing();     // Perform any clean up at end of the application
        }  // End of main processing method - run()

/**********************************************************************************************************
 * main processing helper methods
 *********************************************************************************************************/

        /********************************************************************************************
        * Display main menu, get response and return response
        ********************************************************************************************/
        public String displayMenuAndGetResponse() {

                int response = -1;  // initialze response to invalid value to be sure we store what user enters

                System.out.println("\nYou rang??? WattaYaWannaDo? (enter number of option)\n");
                for (int i = 0; i < mainMenuOptions.length; i++) {              // Loop through menu option array
                        System.out.println(i + 1 + ". " + mainMenuOptions[i]);  //     display a menu option
                }
                System.out.print("\nYour choice: ");                               // Ask user for choice
                try {
                        response = Integer.parseInt(userKeyboardDevice.nextLine());// Get user choice and convert to int value
                        return mainMenuOptions[response - 1];                      // Return menu option from option array
                }
                catch (NumberFormatException exceptionObject) {
                         throw new InvalidMenuResponseException("Invalid menu option " + response + " entered: ");
                }
                catch (ArrayIndexOutOfBoundsException exceptionObject) {
                         throw new InvalidMenuResponseException("Invalid menu option " + response + " entered");
                }
        }  // End of displayMenuAndGetResponse()
        /********************************************************************************************
        * Starting of application setup processing - display welcome screen
        ********************************************************************************************/
        public void startOfApplicationProcessing() throws FileNotFoundException{

                // Since System.out writes to screen and System.err writes to screen
                //                                       unless you assign it a file
                // Direct error messages to a file rather than the screen
                //    to avoid the intermingling of program output messages

                // 1. Define a PrintStream file to hold error messages
                PrintStream fileProcessingErrorLogFile = new PrintStream("fileProcessingError.log");
                // 2. Tell the system to use that file for anything writing to System.err object
                System.setErr(fileProcessingErrorLogFile);

                System.out.println(Emogis.ALIEN_MONSTER.repeat(40));
                System.out.printf("%1s %s \n", Emogis.BLACK_SPIDER_WITH_EIGHT_LEGS,"Welcome to the Addams Family app!");
                System.out.println(Emogis.BLACK_SPIDER_WITH_EIGHT_LEGS.repeat(40));
        }

        /********************************************************************************************
         * End of application takedown processing - display goodbye message
         ********************************************************************************************/
        public void endOfApplicationProcessing() {
                System.out.println("-".repeat(60) + "\nThank you for using our app!\n" + "-".repeat(60));
        }

        /********************************************************************************************
         * Display a entries in data sturcture holding application data
         ********************************************************************************************/
        public void displayAllPeople() {
                int personCount = 0;
                String borderIcon = Emogis.TELEVISION;

                System.out.println("\n"+ (borderIcon + " ").repeat(13)) ;

                // for (Person anAddams : theAddamsFamily) {  // replaced by Iterator
                //       class     iterator-name   = collection-object.iterator()
                Iterator<Person> personIterator = theAddamsFamily.iterator();
                while(personIterator.hasNext()) {             // Loop while the Iterator has another object
                    Person anAddams = personIterator.next();  // Get the next object from the iterator
                    personCount++;
                   System.out.printf("%s %2d. %-30s %-8s",borderIcon,personCount,anAddams.getName(),borderIcon);
                   if (personCount != theAddamsFamily.size()) {
                        System.out.println("");
                   }
                }
                System.out.println("\n"+ (borderIcon + " ").repeat(13)) ;
        }
        /********************************************************************************************
         * Display selected entries from application data store
         ********************************************************************************************/
        public void displayByName() {

                List<Person> listOfAddams = new LinkedList<>();                    // Hold selected entries from data structure

                AddamsSearchCriteria whatTheyWant = solicitAddamsSearchCriteria(); // Ask user for search criteria

                // Extract entries from data structure based on user seacrh criterial
                listOfAddams = findAnAddamsByName(whatTheyWant.getSearchValue().strip(),whatTheyWant.isCaseSensitiveSearch());

                // Display number of entries extracted from data structiue
                System.out.println("\nNumber of Addams' found containing " + whatTheyWant.getSearchValue() + " in name: " + listOfAddams.size());

                // Loop through extracted entries and display them one at a time
                //for(Person anAddams : listOfAddams) {
                Iterator<Person> anIterator = theAddamsFamily.iterator();
                while(anIterator.hasNext()) {
                        Person anAddams = anIterator.next();

                        System.out.printf("%10d %-30s\n",anAddams.getId(),anAddams.getName());
                }
        }
        /********************************************************************************************
         * Allow user to change the name of selected entries in the data store
         ********************************************************************************************/
        public void changePersonName() {
                // Hold entries to be changed
                List<Person> listOfAddams = new LinkedList<>();

                // Ask user for search criteria for entries to be changed
                AddamsSearchCriteria whatTheyWant = solicitAddamsSearchCriteria();

                // Extract entries based on user entered search criteria
                listOfAddams = findAnAddamsByName(whatTheyWant.getSearchValue().strip(),whatTheyWant.isCaseSensitiveSearch());

                // Loop through extracted entries, display each one and ask for new values

                // for(Person anAddams : listOfAddams) {   // Replaced by Iterator
                ListIterator<Person> cousinItterator = theAddamsFamily.listIterator();// Define an Iterator for the Collection
                while(cousinItterator.hasNext()) {// Loop while the Iterator has another object to process
                        Person anAddams = cousinItterator.next(); // Retrieve the next object from the Iterator

                        // Show user the current name from extracted entries
                        System.out.println("Found: " + anAddams);

                        // Ask user if they would liek to change this entries value
                        System.out.println("Do you want to change their name? (Y or N default is No");
                        String changeResponse = userKeyboardDevice.nextLine().strip().toUpperCase();

                        if (changeResponse.startsWith("Y")) {                           // if user wants to change value
                                System.out.println("Please enter new name: ");          //   Ask for new value
                                String newName = userKeyboardDevice.nextLine().strip(); //   Get new value from user
                                findAnAddamsById(anAddams.getId()).setName(newName);    //   change value in data structure
                                System.out.println("----- Name changed to: " + newName);// Confirm to user change was made
                        }
                        else {                                                          // If user does not want to change
                                System.out.println("----- Name is unchanged -----");    //    display message to that effect
                        }
                }

        }
        /********************************************************************************************
         * Allow user to remove selected entries in the data store
         ********************************************************************************************/
        public void removeAnAddams() {
                // Hold entries to be changed
                List<Person> aListOfAddams = new LinkedList<>();

                // Ask user for search criteria for entries to be changed
                AddamsSearchCriteria whatTheyWant = solicitAddamsSearchCriteria();

                // Extract entries based on user entered search criteria
                aListOfAddams = findAnAddamsByName(whatTheyWant.getSearchValue().strip(), whatTheyWant.isCaseSensitiveSearch());

                // Loop through extracted entries, display each one and ask if user wants to delete it
                // Replace the for-each with an Iterator so we don't get the ConcurrentModificationException
                //         when removing the element from the List while iterating through
                // for (Person anAddams : aListOfAddams) {
                ListIterator<Person> removeIterator = aListOfAddams.listIterator();  // Define an Iterator for the collection
                while(removeIterator.hasNext()) {    // Loop while the Iterator has a object to process
                        // Note use of the Iterator.remove() instead of List.remove()
                        Person anAddams = removeIterator.next();  // Retrieve the next object from the Iterator
                        // Show user the current entry from extracted entries
                        System.out.println("Found: " + anAddams);

                        // Ask user if they would like to delete this entry from the data structure
                        System.out.println("Do you wany to delete this Addams from the database? (Y or N default is No");
                        String deleteResponse = userKeyboardDevice.nextLine().strip().toUpperCase();

                        if (deleteResponse.startsWith("Y")) {             // If user wants to delete entry...
                                if (theAddamsFamily.remove(anAddams)) {   //    remove it from the data store
                                    removeIterator.remove();              //       and from the extracted entries
                                    // aListOfAddams.remove(anAddams);    //       replaced by iterator .remove()
                                    System.out.println("----- Removal of " + anAddams.getName() + " was successful");
                                } else {                                  // if user does not want to delete entry...
                                    System.out.println("----- Removal of " + anAddams.getName() + " failed");
                                }
                        }
                }
        }
        /********************************************************************************************
         * Obtain search criteria for extracting entries from the data store
         ********************************************************************************************/
        public AddamsSearchCriteria solicitAddamsSearchCriteria() {
                String response = "";                // Hold response from user
                String personToFind = "";            // Hold name of entry to find
                boolean wantsCaseSensitiveSearch;    // Hold if search should be case sensitivity

                System.out.println("\nPlease enter part or all of the person would like to find");
                response = userKeyboardDevice.nextLine();     // Get response from user
                personToFind = response.strip();              // Remove extraneous spaces

                System.out.println("\nWould you like the search to be case sensitive? (Y or N) - default is No");
                response = userKeyboardDevice.nextLine();      // Get response from user

                if (response.toUpperCase().startsWith("Y")) {  // if user wants case-sensitive search...
                        wantsCaseSensitiveSearch = true;       //    set case-sensitive search indicator to true
                }
                else {                                          // if user does not wants case-sensitive search..
                        wantsCaseSensitiveSearch = false;       //    set case-sensitive search indicator to false
                }
                // Return search criterial object
                return new AddamsSearchCriteria(personToFind, wantsCaseSensitiveSearch);
        }
        /********************************************************************************************
         * Load application data structure from data in a file
         ********************************************************************************************/
        private void loadFamilyMembersFromFile() throws FileNotFoundException, DataFileErrorException {

                String aLine = null;                                         // Hold a line from the file
                String MEMBERS_FILE_NAME = "theAddamsFamilyMembers.txt";     // Name of file holding data to be loaded
                File theAddamsFamilyFile = null;                             // File object to represent file to be loaded
                Scanner memberFileReader  = null;                            // Scanner object to read the file

                try {
                        theAddamsFamilyFile = new File(MEMBERS_FILE_NAME);   // Instantiate File object resprenenting data
                        memberFileReader  = new Scanner(theAddamsFamilyFile);// Instantiate Scanner to read file
                        while (memberFileReader.hasNextLine()) {             // Loop as long as there is data in the file
                                aLine = memberFileReader.nextLine().strip(); //      Get a line from the file and store it
                                theAddamsFamily.add(new Person(aLine));      //      Instantiate a Person and add to data structure
                        }
                }
                catch(FileNotFoundException exceptionObj) {
                        System.err.println(exceptionObj.getMessage());
                        exceptionObj.printStackTrace();
                        throw new DataFileErrorException(MEMBERS_FILE_NAME + " not found - see error log for details");
                }
                catch (IllegalStateException exceptionObject) {
                        System.err.println("Error processing family member file: " + MEMBERS_FILE_NAME);
                        System.err.println("Call stack:");
                        exceptionObject.printStackTrace();

                        System.out.println("Error processing family member file: " + MEMBERS_FILE_NAME);
                        System.out.println("Please see error log file for details");
                        System.err.println("System message: " + exceptionObject.getMessage() );
                }
                finally {   // Whether there is an exception
                        memberFileReader.close();
                }
        }  // End of loadMembers()

        /********************************************************************************************
         * Find entries in the data structure by id
         ********************************************************************************************/
        public Person findAnAddamsById(int requestedPersonId) {
                Person foundPerson = null;                       // Hold entry found in application data structure

                for(Person currentAddams : theAddamsFamily) {    // Loop through application data structure
                        if (currentAddams.getId() == requestedPersonId) { // if current entry id = requested id
                                foundPerson = currentAddams;              //    remember it
                                break;                                    //    and exit loop
                        }
                }
                return foundPerson;   // Return entry found (or null)
        }
        /********************************************************************************************
         * Find entries in the data structure by full or partial name
         ********************************************************************************************/
        public List<Person>findAnAddamsByName(String requestedPersonName, boolean isCaseSensitive) {
                List<Person> foundPeople = new LinkedList<>();            // Hold entry(ies) found in application data structire
                boolean personFound;                                      // Indicate is requested entry was found or not
                List<Person> theAddamsFamilyList =new ArrayList<>();      // Hold entries based on full or partial name

                for(Person currentAddams : theAddamsFamily) {             // Loop through the app data structure
                        personFound = false;                              //      Assume entry will not be found
                        switch(Boolean.toString((isCaseSensitive))) {     //      Determine if case sensitivty is requested
                                case "false":                             //         If not, convert both values to same case and compare
                                        if (currentAddams.getName().toUpperCase().contains(requestedPersonName.toUpperCase())) {
                                                personFound = true;       //         if entry is found - indicate so
                                        }
                                        break;                            //                             and exit switch
                                case "true":                              //      if case sensitivity requested, compare values as is
                                        if (currentAddams.getName().contains(requestedPersonName)) {
                                                personFound = true;       //      if entry is found - indicate so
                                        }
                                        break;                            //                         and exit switch

                        }
                        if (personFound) {                        // if requested entry is found in app data structure
                                foundPeople.add(currentAddams);   //    add it container holding found entries
                        }
                }
                return foundPeople;    // return container holding found entries
        }
        /********************************************************************************************
         * Display all entries in the data structure in reverse
         ********************************************************************************************/
        public void displayAllInReverseOrder() {

                // Use a ListIterator to process the List in reverse order
                //
                // When defining a ListIterator you need to tell it where to start
                //      if going forward  - start at the first element - .listIterator()
                //      if going backward - start at the last element  - .listIterator(position-to-start)

                // Whenever you see the word 'position', think like a human: last element is listname.size()
                //                                       start counting at 1
                // Whenever you see the word 'index', think like a computer: last element is listname.size() - 1
                //                                       start counting at 0

                ListIterator<Person> reverseIterator = theAddamsFamily.listIterator(theAddamsFamily.size());
                while(reverseIterator.hasPrevious()) {
                        Person previousAddams = reverseIterator.previous();
                        System.out.println(previousAddams);
                }
        }
        /********************************************************************************************
         * Code-Along Project
         *
         * Add a new Addams to the List data store before an existing existing entry in the List
         ********************************************************************************************/
        public void addANewAddams() {
                // Code-Along TODO: Add code to implement this feature
                ListIterator<Person> addIterator = theAddamsFamily.listIterator();

                System.out.println("Do you want to add this Addams from the database? (Y or N default is No");
                String addResponse = userKeyboardDevice.nextLine().strip().toUpperCase();

                if (addResponse.startsWith("Y")) {                           // if user wants to change value
                        System.out.println("Please enter name: ");          //   Ask for new value
                        String addName = userKeyboardDevice.nextLine().strip(); //   Get new value from user
                        Person newPerson = new Person(addName);
                        addIterator.add(newPerson);
                        System.out.println("----- Name is: " + addName);// Confirm to user change was made
                }
                else {                                                          // If user does not want to change
                        System.out.println("----- Name is unchanged -----");    //    display message to that effect
                }

//                System.out.println("\n" + "-".repeat(60) +"\n----- Sorry, this feature has not been implemented yet -----\n"
//                                        + "-".repeat(60) + "\n");
        }

} // End of ApplicationProgram class
