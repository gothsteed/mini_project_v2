package dto;

import domain.EmployeeStats;

import java.util.Map;
import java.util.TreeMap;

public class EmployeeStatsDto {
    private Map<Integer, Integer> maleAgeRangeCount;
    private Integer maleTotalCount;
    private Map<Integer, Integer> femaleAgeRangeCount;
    private Integer femaleTotalCount;
    private Map<Integer, Integer> ageRangeCount;
    private Integer totalCount;

    private Double malePercentage;
    private Double femalePercentage;
    private Map<Integer, Double> ageRangePercent;

    public EmployeeStatsDto(Map<Integer, Integer> maleAgeRangeCount, Integer maleTotalCount,
                            Map<Integer, Integer> femaleAgeRangeCount, Integer femaleTotalCount,
                            Map<Integer, Integer> ageRangeCount, Integer totalCount,
                            Double malePercentage, Double femalePercentage,
                            Map<Integer, Double> ageRangePercent) {
        this.maleAgeRangeCount = maleAgeRangeCount;
        this.maleTotalCount = maleTotalCount;
        this.femaleAgeRangeCount = femaleAgeRangeCount;
        this.femaleTotalCount = femaleTotalCount;
        this.ageRangeCount = ageRangeCount;
        this.totalCount = totalCount;
        this.malePercentage = malePercentage;
        this.femalePercentage = femalePercentage;
        this.ageRangePercent = ageRangePercent;
    }


    public static EmployeeStatsDto of(EmployeeStats employeeStats) {
        return new EmployeeStatsDto(employeeStats.getMaleAgeRangeCount(),
                employeeStats.getMaleTotalCount(),
                employeeStats.getFemaleAgeRangeCount(),
                employeeStats.getFemaleTotalCount(),
                employeeStats.getAgeRangeCount(),
                employeeStats.getTotalCount(),
                employeeStats.getMalePercentage(),
                employeeStats.getFemalePercentage(),
                employeeStats.getAgeRangePercentage());
    }


    public Map<Integer, Integer> getMaleAgeRangeCount() {
        return maleAgeRangeCount;
    }

    public void setMaleAgeRangeCount(Map<Integer, Integer> maleAgeRangeCount) {
        this.maleAgeRangeCount = maleAgeRangeCount;
    }

    public Integer getMaleTotalCount() {
        return maleTotalCount;
    }

    public void setMaleTotalCount(Integer maleTotalCount) {
        this.maleTotalCount = maleTotalCount;
    }

    public Map<Integer, Integer> getFemaleAgeRangeCount() {
        return femaleAgeRangeCount;
    }

    public void setFemaleAgeRangeCount(Map<Integer, Integer> femaleAgeRangeCount) {
        this.femaleAgeRangeCount = femaleAgeRangeCount;
    }

    public Integer getFemaleTotalCount() {
        return femaleTotalCount;
    }

    public void setFemaleTotalCount(Integer femaleTotalCount) {
        this.femaleTotalCount = femaleTotalCount;
    }

    public Map<Integer, Integer> getAgeRangeCount() {
        return ageRangeCount;
    }

    public void setAgeRangeCount(Map<Integer, Integer> ageRangeCount) {
        this.ageRangeCount = ageRangeCount;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Double getMalePercentage() {
        return malePercentage;
    }

    public void setMalePercentage(Double malePercentage) {
        this.malePercentage = malePercentage;
    }

    public Double getFemalePercentage() {
        return femalePercentage;
    }

    public void setFemalePercentage(Double femalePercentage) {
        this.femalePercentage = femalePercentage;
    }

    public Map<Integer, Double> getAgeRangePercent() {
        return ageRangePercent;
    }

    public void setAgeRangePercent(Map<Integer, Double> ageRangePercent) {
        this.ageRangePercent = ageRangePercent;
    }

    public void printStats() {
        System.out.printf("%-20s %-15s %-15s %-15s%n", "Category", "Male", "Female", "Total");
        System.out.println(String.join("", java.util.Collections.nCopies(65, "-")));
        System.out.printf("%-20s %-15d %-15d %-15d%n", "Total Count", maleTotalCount, femaleTotalCount, totalCount);
        System.out.printf("%-20s %-15.2f %-15.2f%n", "Percentage", malePercentage, femalePercentage);

        System.out.println("\nAge Range Distribution");
        System.out.printf("%-20s %-15s %-15s %-15s%n", "Age Range", "Male Count", "Female Count", "Total Count");
        System.out.println(String.join("", java.util.Collections.nCopies(65, "-")));

        // Assuming the age ranges are consistent across all maps and are sorted
        if (ageRangeCount != null) {
            ageRangeCount.forEach((ageRange, count) -> {
                Integer maleCount = maleAgeRangeCount.getOrDefault(ageRange, 0);
                Integer femaleCount = femaleAgeRangeCount.getOrDefault(ageRange, 0);
                Double percent = ageRangePercent.getOrDefault(ageRange, 0.0);
                System.out.printf("%-20s %-15d %-15d %-15d %-15.2f%%%n", ageRange + " years", maleCount, femaleCount, count, percent);
            });
        }
    }

}
