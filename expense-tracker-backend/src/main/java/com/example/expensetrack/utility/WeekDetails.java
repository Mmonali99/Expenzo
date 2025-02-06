package com.example.expensetrack.utility;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

public class WeekDetails {

    public static class WeekInfo {
        private int weekNumber;
        private int startDay;
        private int endDay;
        private int daysInWeek;

        public WeekInfo(int weekNumber, int startDay, int endDay, int daysInWeek) {
            this.weekNumber = weekNumber;
            this.startDay = startDay;
            this.endDay = endDay;
            this.daysInWeek = daysInWeek;
        }

        public int getWeekNumber() {
            return weekNumber;
        }

        public int getStartDay() {
            return startDay;
        }

        public int getEndDay() {
            return endDay;
        }

        public int getDaysInWeek() {
            return daysInWeek;
        }

        @Override
        public String toString() {
            return "Week-" + weekNumber + " (" + startDay + " to " + endDay + ")";
        }
    }

    public List<WeekInfo> getWeekDetails(int year, int month) {
        List<WeekInfo> weekDetails = new ArrayList<>();
        LocalDate firstDayOfMonth = LocalDate.of(year, month, 1);
        LocalDate lastDayOfMonth = firstDayOfMonth.with(TemporalAdjusters.lastDayOfMonth());

        LocalDate currentStart = firstDayOfMonth;
        LocalDate currentEnd = firstDayOfMonth.with(DayOfWeek.SATURDAY);

        if (currentEnd.isAfter(lastDayOfMonth)) {
            currentEnd = lastDayOfMonth;
        }

        int weekNumber = 1;

        while (!currentStart.isAfter(lastDayOfMonth)) {
            int start = currentStart.getDayOfMonth();
            int end = currentEnd.getDayOfMonth();
            int daysInWeek = end - start + 1;

            weekDetails.add(new WeekInfo(weekNumber, start, end, daysInWeek));

            currentStart = currentEnd.plusDays(1);
            currentEnd = currentStart.plusDays(6);

            if (currentEnd.isAfter(lastDayOfMonth)) {
                currentEnd = lastDayOfMonth;
            }

            weekNumber++;
        }
        return weekDetails;
    }

    public int getDaysInTheMonth(int year, int month) {
        LocalDate firstDayOfMonth = LocalDate.of(year, month, 1);
        LocalDate lastDayOfMonth = firstDayOfMonth.with(TemporalAdjusters.lastDayOfMonth());
        return lastDayOfMonth.getDayOfMonth();
    }

    public List<Integer> getDaysCountOfTheWeek(int year, int month) {
        List<Integer> daysCountList = new ArrayList<>();
        LocalDate firstDayOfMonth = LocalDate.of(year, month, 1);
        LocalDate lastDayOfMonth = firstDayOfMonth.with(TemporalAdjusters.lastDayOfMonth());

        LocalDate currentStart = firstDayOfMonth;
        LocalDate currentEnd = firstDayOfMonth.with(DayOfWeek.SATURDAY);

        if (currentEnd.isAfter(lastDayOfMonth)) {
            currentEnd = lastDayOfMonth;
        }

        while (!currentStart.isAfter(lastDayOfMonth)) {
            int daysInWeek = currentEnd.getDayOfMonth() - currentStart.getDayOfMonth() + 1;
            daysCountList.add(daysInWeek);

            currentStart = currentEnd.plusDays(1);
            currentEnd = currentStart.plusDays(6);

            if (currentEnd.isAfter(lastDayOfMonth)) {
                currentEnd = lastDayOfMonth;
            }
        }
        return daysCountList;
    }
}
