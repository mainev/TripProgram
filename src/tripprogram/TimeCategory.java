/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tripprogram;

/**
 *
 * @author maine
 */
public enum TimeCategory {

    A("07:00:00 AM", "10:00:00 AM"),
    B("10:00:00 AM", "01:00:00 PM"),
    C("01:00:00 PM", "04:00:00 PM"),
    D("04:00:00 PM", "07:00:00 PM"),
    E("07:00:00 PM", "10:00:00 PM");

    private final String begin;
    private final String end;

    TimeCategory(String begin, String end) {
        this.begin = begin;
        this.end = end;
    }

    public String getBegin() {
        return begin;
    }

    public String getEnd() {
        return end;
    }

}
