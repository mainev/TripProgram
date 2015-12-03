/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tripprogram;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SimpleDateFormat df = new SimpleDateFormat("mm-dd-yy HH:mm:ss aa");

        LinkedList<Trip> trips = new LinkedList();
        try {
            Trip t1 = new Trip(1, df.parse("10-17-15 11:44:24 AM"), df.parse("10-17-15 13:28:34 PM"), 0, 82);
            Trip t2 = new Trip(2, df.parse("10-17-15 12:44:24 PM"), df.parse("10-17-15 13:44:34 PM"), 41, 82);
            Trip t3 = new Trip(3, df.parse("10-17-15 12:44:24 PM"), df.parse("10-17-15 13:00:00 PM"), 75, 82);
            Trip t4 = new Trip(4, df.parse("10-17-15 9:00:24 AM"), df.parse("10-17-15 09:31:34 AM"), 30, 0);
            Trip t5 = new Trip(5, df.parse("10-17-15 10:00:00 AM"), df.parse("10-17-15 11:00:00 AM"), 30, 0);

            trips.add(t4);
            trips.add(t5);
            trips.add(t3);
            trips.add(t1);
            trips.add(t2);

            filterTrips(trips, 0, 82, "Saturday", TimeCategory.B);

            readFile();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static SortedTripList filterTrips(LinkedList<Trip> trips, int from, int to, String day, TimeCategory cat) {
        SortedTripList tList = new SortedTripList();

        System.out.println("Trips that traveled from point:" + from + " to point:" + to + ", Day: " + day + ", TimeCat: " + cat);
        for (Trip t : trips) {
            if (t.isInSegment(from, to) && t.hasTravelledInDayTime(day, cat)) {
                tList.insert(t);
            }
        }
        System.out.println("TRIPS: " + tList.getSize() + "\n" + tList);
        try {
            tList.excludeFirstAndLast();
            System.out.println("EXCLUDE FIRST AND LAST: " + tList);
            System.out.println("AVERAGE TIME/DISTANCE: " + tList.computerAverageTime());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return tList;
    }

    public static void readFile() throws IOException, BiffException {
        Workbook wrk1 = Workbook.getWorkbook(new File("C:\\Users\\maine\\Desktop\\thesis\\Data-Entries2.xls"));

        Sheet sheet1 = wrk1.getSheet(1);

        int date = 1;
        int pointx = 3;
        int time_start = 4;
        int pointy = 5;
        int time_end = 6;
        int from = 15;
        int to = 16;

        for (int i = 2; i <= 5; i++) {
            System.out.println("from: " + sheet1.getCell(from, i).getContents());
            System.out.println("index " + i + ": " + sheet1.getCell(date, i).getContents());
        }
    }

}
