/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tripprogram;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
      //  SimpleDateFormat df = new SimpleDateFormat("MM-dd-yy hh:mm:ss aa");

        //  LinkedList<Trip> trips = new LinkedList();
        try {

            System.out.println("\nREADING EXCEL FILE...");
            List<Trip> xList = readFile();
            filterTrips(xList, 0, 82, "Monday", TimeCategory.A);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static SortedTripList filterTrips(List<Trip> trips, int from, int to, String day, TimeCategory cat) {
        SortedTripList tList = new SortedTripList();

        System.out.println("Trips that traveled from point:" + from + " to point:" + to + ", Day: " + day + ", TimeCat: " + cat);
        for (Trip t : trips) {
            if (t.isInSegment(from, to) && t.hasTravelledInDayTime(day, cat)) {
                tList.insert(t);
            }
        }
        System.out.println("SORTED TRIPS: " + tList.getSize() + "\n" + tList);
        try {
            tList.excludeFirstAndLast();
            System.out.println("EXCLUDE FIRST AND LAST: " + tList);
            System.out.println("AVERAGE TIME/DISTANCE: " + tList.computerAverageTime());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return tList;
    }

    public static List<Trip> readFile() throws IOException, BiffException, ParseException {
        Workbook wrk1 = Workbook.getWorkbook(new File("C:\\Users\\maine\\Desktop\\thesis\\Data-Entries2.xls"));
        Sheet sheet1 = wrk1.getSheet(1);

        int num_index = 0;
        int date_index = 1;
        int pointx_index = 3;
        int timestart_index = 4;
        int pointy_index = 5;
        int timeend_index = 6;
        int from_index = 15;
        int to_index = 16;

        List<Trip> list = new ArrayList();

        for (int i = 2; i <= 302; i++) {

            int num = 0;
            String timeStart = "";
            String timeEnd = "";
            int pointStart = 0;
            int pointEnd = 0;
            double xlatitude = 0;
            double xlongitude = 0;
            double ylatitude = 0;
            double ylongitude = 0;

            String x = sheet1.getCell(pointx_index, i).getContents();
            if (!x.isEmpty()) {
                String[] tokens = x.split(",");
                xlatitude = Double.parseDouble(tokens[0]);
                xlongitude = Double.parseDouble(tokens[1]);
            }

            String y = sheet1.getCell(pointy_index, i).getContents();
            if (!y.isEmpty()) {
                String[] tokens = y.split(",");
                ylatitude = Double.parseDouble(tokens[0]);
                ylongitude = Double.parseDouble(tokens[1]);
            }

            String snum = sheet1.getCell(num_index, i).getContents();
            if (!snum.isEmpty()) {
                num = Integer.parseInt(snum);
            }

            String sdate = sheet1.getCell(date_index, i).getContents();
            String stimestart = sheet1.getCell(timestart_index, i).getContents();
            if (!sdate.isEmpty() && !stimestart.isEmpty()) {
                timeStart = sdate + " " + stimestart;
                // System.out.println(timeStart);
            }

            String stimeEnd = sheet1.getCell(timeend_index, i).getContents();
            if (!sdate.isEmpty() && !stimeEnd.isEmpty()) {
                timeEnd = sdate + " " + stimeEnd;
                // System.out.println(timeEnd);
            }

            String sPointStart = sheet1.getCell(from_index, i).getContents();
            if (!sPointStart.isEmpty()) {
                pointStart = Integer.parseInt(sPointStart);
                //System.out.println(pointStart);
            }

            String sPointEnd = sheet1.getCell(to_index, i).getContents();
            if (!sPointEnd.isEmpty()) {
                pointEnd = Integer.parseInt(sPointEnd);
                // System.out.println(pointEnd);
            }

            SimpleDateFormat df = new SimpleDateFormat("MM/dd/yy hh:mm:ss aa");
            if (!timeStart.isEmpty() && !timeEnd.isEmpty()) {
                list.add(new Trip(num, df.parse(timeStart), df.parse(timeEnd), pointStart, pointEnd));
            }

        }

        return list;

    }

    public static Date parseTime(String time) {

        final String inputFormat = "hh:mm:ss aa";
        SimpleDateFormat inputParser = new SimpleDateFormat(inputFormat, Locale.US);
        try {
            return inputParser.parse(time);
        } catch (java.text.ParseException e) {
            return new Date(0);
        }
    }
}
