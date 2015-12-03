/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tripprogram;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Trip {

    private int num;
    private Date timeStart;   //in minutes
    private Date timeEnd;

    private int pointStart;
    private int pointEnd;

    public Trip(int num, Date timeStart, Date timeEnd, int pointStart, int pointEnd) {
        this.num = num;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.pointStart = pointStart;
        this.pointEnd = pointEnd;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public Date getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(Date timeStart) {
        this.timeStart = timeStart;
    }

    public Date getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(Date timeEnd) {
        this.timeEnd = timeEnd;
    }

    public int getPointStart() {
        return pointStart;
    }

    public void setPointStart(int pointStart) {
        this.pointStart = pointStart;
    }

    public int getPointEnd() {
        return pointEnd;
    }

    public void setPointEnd(int pointEnd) {
        this.pointEnd = pointEnd;
    }

    public int getDirection() {
        return pointEnd - pointStart;
    }

    public int getDistance() {
        //  return ( pointStart < pointEnd < 0) ? (pointEnd - pointStart) : (pointStart - pointEnd);
        return ((pointStart < pointEnd)) ? (pointEnd - pointStart) : (pointStart - pointEnd);

    }

    public double getTripTime() // in minute
    {

        return (double) (timeEnd.getTime() - timeStart.getTime()) / 60000;
    }

    public double getSpeed() //km/m
    {
        return getDistance() / getTripTime();
    }

    public double getAveTime() {
        return (1.0 * getTripTime()) / getDistance();
    }

    public boolean isInSegment(int from, int to) {
        return (pointStart >= from) && (pointEnd <= to);
    }

    public boolean hasTravelledInDayTime(String day, TimeCategory cat) {
        boolean daySatisfied;
        boolean timeSatisfied;

        //check if day is satisfied
        SimpleDateFormat df = new SimpleDateFormat("EEEE", Locale.ENGLISH);
        daySatisfied = (df.format(timeStart.getTime()).equals(day));

        //check if time is satisified
        Date time = parseTime(new SimpleDateFormat("HH:mm:ss aa").format(timeStart));
        Date cBegin = parseTime(TimeCategory.valueOf(cat.name()).getBegin());
        Date cEnd = parseTime(TimeCategory.valueOf(cat.name()).getEnd());
        //  timeSatisfied = (time.after(cBegin) || time.compareTo(cBegin) >=0) && time.before(cEnd);
        timeSatisfied = (time.compareTo(cBegin) >= 0) && (time.compareTo(cEnd) <= 0);

        return daySatisfied && timeSatisfied;
    }

    @Override
    public String toString() {
        return "Trip " + num + ": "
                + "{distance: " + getDistance() + " km"
                + ", tripTime: " + getTripTime() + " minutes"
                + ", speed: " + getSpeed() + " km/minutes"
                + ", aveTime: " + getAveTime() + "}";
    }

    public Date parseTime(String time) {

        final String inputFormat = "HH:mm:ss aa";
        SimpleDateFormat inputParser = new SimpleDateFormat(inputFormat, Locale.US);
        try {
            return inputParser.parse(time);
        } catch (java.text.ParseException e) {
            return new Date(0);
        }
    }

}
