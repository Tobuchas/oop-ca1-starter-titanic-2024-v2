package org.example;
// CA1
import java.io. * ;
import java.util.*;

public class Main {

    public static final PassengerTicketNumberComparator TICKET_NUMBER_COMPARATOR = new PassengerTicketNumberComparator();

    public static void main(String[] args) {

        String fileName = "titanic-data-100.csv"; // file should be in the project folder (below pom.xml)

        ArrayList<Passenger> passengerList= new ArrayList<>();

        loadPassengerDataFromFile( passengerList, fileName);

        displayAllPassengers( passengerList );


        // Assignment: Implement and test the following methods.
        // See the description of each method in the CA1 Specification PDF file from Moodle

        //Q1 output
        System.out.println(Arrays.toString(getPassengerNames(passengerList)));
        System.out.println();

        //Q2 output
        ArrayList<Passenger> passengersContainingNames = getPassengersContainingNames(passengerList, "Pepa");
        displayAllPassengers(passengersContainingNames);
        System.out.println();

        //Q3 output
        displayAllPassengers(getPassengersOlderThan(passengerList,20));
        System.out.println();

        //Q4 output
        ArrayList<Passenger> male = countPassengersByGender(passengerList, "Male");
        displayAllPassengers(male);
        System.out.println("There are "+male.size()+" male passengers.");
        System.out.println();

        //Q5 output
        System.out.println("The total sum of all ticket costs was: "+sumFares(passengerList));
        System.out.println("The total sum of all ticket costs for men was: "+sumFares(male));
        System.out.println();

        //Q6 output
        System.out.println(Arrays.toString(maleSurvivors(passengerList)));
        System.out.println();

        //Q7 output
        System.out.println(ticketOwner(passengerList,passengerList.get(21).getTicketNumber()));
        System.out.println(ticketOwner(passengerList,"65120"));
        System.out.println();

        //Q8 output
        System.out.println("The average age of them passengers was: "+averageAge(passengerList));
        System.out.println("The average age of them male passengers was: "+averageAge(male));
        System.out.println();

        //Q9 output
        ArrayList<Passenger> passengersByTicketClass = getPassengersByTicketClass(passengerList,PassengerClass.FIRST);
        displayAllPassengers(passengersByTicketClass);
        System.out.println();

        //Q10 output
        ArrayList<Passenger> passengersById = sortPassengersByPassengerId(passengerList);
        displayAllPassengers(passengersById);
        System.out.println();

        //Q11 output
        ArrayList<Passenger> passengersByName = sortPassengersByName(passengerList);
        displayAllPassengers(passengersByName);
        System.out.println();

        //Q12 output
        sortPassengersByAgeThenName(passengerList);
        displayAllPassengers(passengerList);
        System.out.println();

        //Q13 output
        sortPassengersByGenderThenPassengerNumber(passengerList);
        displayAllPassengers(passengerList);
        System.out.println();

        //Q14 output
        sortPassengersByFareThenSurvival(passengerList);
        displayAllPassengers(passengerList);
        System.out.println();

        //Q15 output
        sortPassengersByTicketClass(passengerList);
        displayAllPassengers(passengerList);
        System.out.println();

        //Q16 output
        sortPassengersByAge(passengerList);
        displayAllPassengers( passengerList );
        System.out.println();

        //Q17 output
        sortPassengersByTicketNumberLambda(passengerList);
        displayAllPassengers(passengerList);
        System.out.println();

        //Q18 output
        sortPassengersByTicketNumberStatic(passengerList);
        displayAllPassengers(passengerList);
        System.out.println();

        //Q19 output
        System.out.println(findPassengerByTicketNumber(passengerList,passengerList.get(0).getTicketNumber()));
        System.out.println(findPassengerByTicketNumber(passengerList,"bobr"));
        System.out.println();

        //Q20 output
        System.out.println(findPassengerByPassengerId(passengerList,passengerList.get(0).getPassengerId()));
        System.out.println();

        System.out.println("Finished, Goodbye!");
    }

    /**
     * Q15
     * @param passengerList
     * @return
     */
    private static List<Passenger> sortPassengersByTicketClass(List<Passenger> passengerList)
    {
        passengerList.sort((p1, p2)->p1.getPassengerClass().compareTo(p2.getPassengerClass()));
        return passengerList;
    }

    /**
     * Q14
     * 
     * We cannot use a simple subtraction due to rounding issues of doubles and ints so we have to use multiple ifs
     * @param passengerList
     * @return
     */
    private static List<Passenger> sortPassengersByFareThenSurvival(List<Passenger> passengerList)
    {
        passengerList.sort(new Comparator<Passenger>() {
            @Override
            public int compare(Passenger p1, Passenger p2) {
                if (p1.getFare()==(p2.getFare()))
                {
                    return p1.getSurvived() - (p2.getSurvived());
                }
                if (p1.getFare()<(p2.getFare()))
                    return -1;
                if (p1.getFare()>(p2.getFare()))
                    return 1;
                return 0;
            }
        });
        return passengerList;
    }

    /**
     * Q13
     * @param passengerList
     * @return
     */
    private static List<Passenger> sortPassengersByGenderThenPassengerNumber(List<Passenger> passengerList)
    {
        passengerList.sort(new Comparator<Passenger>() {
            @Override
            public int compare(Passenger p1, Passenger p2) {
                if (p1.getGender().equalsIgnoreCase(p2.getGender()))
                {
                    return p1.getPassengerId().compareTo(p2.getPassengerId());
                }
                return p1.getGender().compareTo(p2.getGender());
            }
        });
        return passengerList;
    }

    /**
     * Q12
     * @param passengerList
     * @return
     */
    private static List<Passenger> sortPassengersByAgeThenName(List<Passenger> passengerList)
    {
        passengerList.sort(new Comparator<Passenger>() {
            @Override
            public int compare(Passenger p1, Passenger p2) {
                if (p1.getAge() == p2.getAge())
                {
                    return p1.getName().compareTo(p2.getName());
                }
                return p1.getAge() - p2.getAge();
            }
        });
        return passengerList;
    }

    private static ArrayList<Passenger> sortPassengersByName(ArrayList<Passenger> passengerList)
    {
        ArrayList<Passenger> passengersByName = new ArrayList<>(passengerList);
        passengersByName.sort((p1,p2)->p1.getName().compareTo(p2.getName()));
        return passengersByName;
    }

    private static ArrayList<Passenger> sortPassengersByPassengerId(ArrayList<Passenger> passengerList)
    {
        ArrayList<Passenger> sortedPassengers =  new ArrayList<>(passengerList);
        sortedPassengers.sort(Passenger::compareTo);
        return sortedPassengers;
    }

    /**
     * Q9
     * @param passengerList
     * @param passengerClass
     * @return
     */
    private static ArrayList<Passenger> getPassengersByTicketClass(ArrayList<Passenger> passengerList, PassengerClass passengerClass)
    {
        ArrayList<Passenger> passengersByClass = new ArrayList<>();
        for (Passenger passenger : passengerList)
        {
            if (passengerClass.equals(passenger.getPassengerClass()))
            {
                passengersByClass.add(passenger);
            }
        }
        return passengersByClass;
    }

    /**
     * Q8
     * @param passengerList
     * @return
     */
    private static double averageAge(ArrayList<Passenger> passengerList)
    {
        double totalAge=0;
        for (Passenger passenger : passengerList)
        {
            totalAge += passenger.getAge();
        }
        return totalAge/passengerList.size();
    }

    /**
     * Q7
     * @param passengerList
     * @param ticketNumber
     * @return
     */
    private static Passenger ticketOwner(ArrayList<Passenger> passengerList, String ticketNumber)
    {
        for (Passenger passenger : passengerList)
        {
            if (passenger.getTicketNumber().equals(ticketNumber))
            {
                return passenger;
            }
        }
        return null;
    }

    /**
     * Q6
     * @param passengerList
     * @return
     */
    private static String[] maleSurvivors(ArrayList<Passenger> passengerList)
    {
        ArrayList<String> maleSurvivorsPassengers = new ArrayList<>();
        for (Passenger passenger : passengerList) 
        {
            if (passenger.getGender().equalsIgnoreCase("male") && passenger.getSurvived()==1)
            {
                maleSurvivorsPassengers.add(passenger.getName());
            }
        }
        String[] maleNames = new String[maleSurvivorsPassengers.size()];
        
        maleNames = maleSurvivorsPassengers.toArray(maleNames);
        return maleNames;
    }

    /**
     * Q5
     * @param passengerList
     * @return
     */
    private static double sumFares(ArrayList<Passenger> passengerList)
    {
        double fares = 0;
        for (Passenger passenger : passengerList)
        {
            fares += passenger.getFare();
        }
        return fares;
    }

    /**
     * Q4
     * @param passengerList
     * @param gender
     * @return
     */
    private static ArrayList<Passenger> countPassengersByGender(ArrayList<Passenger> passengerList, String gender)
    {
        ArrayList<Passenger> passengers= new ArrayList<>();
        for (Passenger passenger : passengerList)
        {
            if (passenger.getGender().equalsIgnoreCase(gender))
            {
                passengers.add(passenger);
            }
        }
        return passengers;
    }


    /**
     * Q3
     * @param passengerList
     * @param age
     * @return
     */
    private static ArrayList<Passenger> getPassengersOlderThan(ArrayList<Passenger> passengerList, int age)
    {
        ArrayList<Passenger> passengersOlderThan = new ArrayList<>();
        for (Passenger passenger : passengerList)
        {
            if (passenger.getAge() > age)
            {
                passengersOlderThan.add(passenger);
            }
        }
        return passengersOlderThan;
    }

    /**
     * Q2
     * @param passengerList
     * @param name
     * @return
     */
    private static ArrayList<Passenger> getPassengersContainingNames(ArrayList<Passenger> passengerList, String name)
    {
        ArrayList <Passenger> result = new ArrayList<>();
        for (Passenger passenger : passengerList)
        {
            if (passenger.getName().equals(name))
            {
                result.add(passenger);
            }
        }
        return result;
    }

    /**
     * Q1
     * @param passengerList
     * @return
     */
    private static String[] getPassengerNames(List <Passenger> passengerList)
    {
        String[] passengerNames = new String[passengerList.size()];
        for (int i = 0; i < passengerList.size(); i++)
        {
            passengerNames[i]=passengerList.get(i).getName();
        }
        return passengerNames;
    }


    /**
     * Q19
     * @param passengerList
     * @param ticketNumber
     * @return
     */
    private static Passenger findPassengerByTicketNumber(List<Passenger> passengerList, String ticketNumber)
    {
        sortPassengersByTicketNumberLambda(passengerList);
        int bot=0;
        int top=passengerList.size()-1;
        int mid;
        boolean found=false;
        Passenger passenger=null;

        while (!found)
        {
            mid=(top+bot)/2;
            int comparison =passengerList.get(mid).getTicketNumber().compareTo(ticketNumber);

            if (comparison==0)
            {
                found=true;
                passenger=passengerList.get(mid);
            }
            else if (bot == top)
            {
                break;
            }
            else if (comparison<0)
            {
                bot=mid+1;
            }
            else
            {
                top=mid-1;
            }

        }
        return passenger;
    }

    /**
     * Q20
     * @param passengerList
     * @param PassengerId
     * @return
     */
    private static Passenger findPassengerByPassengerId(ArrayList<Passenger> passengerList, String PassengerId)
    {
        ArrayList<Passenger> sortedById = sortPassengersByPassengerId(passengerList);
        int bot=0;
        int top=sortedById.size()-1;
        int mid;
        boolean found=false;
        Passenger passenger=null;

        while (!found)
        {
            mid=(top+bot)/2;
            int comparison =sortedById.get(mid).getPassengerId().compareTo(PassengerId);

            if (comparison==0)
            {
                found=true;
                passenger=sortedById.get(mid);
            }
            else if (bot == top)
            {
                break;
            }
            else if (comparison<0)
            {
                bot=mid+1;
            }
            else
            {
                top=mid-1;
            }

        }
        return passenger;
    }

    /**
     * Q17
     * @param passengerList
     * @return
     */
    private static List<Passenger> sortPassengersByTicketNumberLambda(List<Passenger> passengerList)
    {
        passengerList.sort((p1, p2) -> p1.getTicketNumber().compareTo(p2.getTicketNumber()));
        return passengerList;
    }

    /**
     * Q16
     * @param passengerList
     * @return
     */
    private static List<Passenger> sortPassengersByAge(List<Passenger> passengerList)
    {
        passengerList.sort(new Comparator<Passenger>() {
            @Override
            public int compare(Passenger p1, Passenger p2) {
                return p1.getAge() - p2.getAge();
            }
        });
        return passengerList;
    }

    /**
     * Q18
     * @param passengerList
     * @return
     */
    private static List<Passenger> sortPassengersByTicketNumberStatic(List<Passenger> passengerList)
    {
        passengerList.sort(TICKET_NUMBER_COMPARATOR);
        return passengerList;
    }

    /**
     * Populate an ArrayList of Passenger objects with data from a text file
     * @param passengerList - an ArrayList to be filled with Passenger objects
     * @param fileName - name of CSV text file containing passenger details
     */
    public static void loadPassengerDataFromFile( ArrayList<Passenger> passengerList, String fileName) {

        // Format of each row of data is:
        // Name,Age,Height(m),GPA  - these heading names are included as the first row in file
        // John Malone,20,1.78,3.55   for example

        // Use a Regular Expression to set both comma and newline as delimiters.
        //  sc.useDelimiter("[,\\r\\n]+");
        // Text files in windows have lines ending with "CR-LF" or "\r\n" sequences.
        // or may have only a newline - "\n"
        // Windows uses CRLF (\r\n, 0D 0A) line endings while Unix just uses LF (\n, 0A).
        //
        try (Scanner sc = new Scanner(new File(fileName))
                .useDelimiter("[,\\r\\n]+"))
        {

            // skip past the first line, as it has field names (not data)
            if(sc.hasNextLine())
                sc.nextLine();   // read the header line containing column titles, but don't use it

            // while there is a next token to read....
            System.out.println("Go...");

            while (sc.hasNext())
            {
                String passengerId = sc.next();    // read passenger ID
                int survived = sc.nextInt();     // 0=false, 1=true
                int passengerClass = sc.nextInt();  // passenger class, 1=1st, 2=2nd or 3rd
                String name = sc.next();
                String gender = sc.next();
                int age = sc.nextInt();
                int siblingsAndSpouses = sc.nextInt();
                int parentsAndChildren = sc.nextInt();
                String ticketNumber = sc.next();
                double fare = sc.nextDouble();
                String cabin = sc.next();
                String embarkedAt = sc.next();

                System.out.println(passengerId +", " + name);

                passengerList.add(
                        new Passenger( passengerId, survived, passengerClass,
                                name, gender, age, siblingsAndSpouses,parentsAndChildren,ticketNumber,
                                fare, cabin, embarkedAt)
                );
            }
        } catch (FileNotFoundException exception)
        {
            System.out.println("FileNotFoundException caught. The file " +fileName+ " may not exist." + exception);
        }
    }

    public static void displayAllPassengers( ArrayList<Passenger> passengerList ) {
        System.out.println("Displaying all passengers:");
        for( Passenger passenger : passengerList)
        {
            System.out.println(passenger);
        }
    }

    private static class PassengerTicketNumberComparator implements Comparator<Passenger> {
        @Override
        public int compare(Passenger p1, Passenger p2) {
            return p1.getAge() - p2.getAge();
        }
    }
}