package domain;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class EmployeeStats {
    private Map<Integer, Integer> maleAgeRangeCount = new TreeMap<>();
    private Integer maleTotalCount = 0;
    private Map<Integer, Integer> femaleAgeRangeCount = new TreeMap<>();
    private Integer femaleTotalCount = 0;
    private Map<Integer, Integer> ageRangeCount = new TreeMap<>();
    private Integer totalCount = 0;


    public EmployeeStats(List<Employee> employeeList) {
        //통계 로직
        for(Employee employee : employeeList) {

            String gender = employee.getGender();
            int ageRange = employee.getAgeRange();

            if(gender.equalsIgnoreCase("MALE")) {
                if(maleAgeRangeCount.containsKey(ageRange)) {
                    maleAgeRangeCount.put(ageRange, maleAgeRangeCount.get(ageRange) + 1);
                }
                else {
                    maleAgeRangeCount.put(ageRange, 1);
                }
                maleTotalCount ++;
            }
            else {
                if(femaleAgeRangeCount.containsKey(ageRange)) {
                    femaleAgeRangeCount.put(ageRange, femaleAgeRangeCount.get(ageRange) + 1);
                }
                else {
                    femaleAgeRangeCount.put(ageRange, 1);
                }
                femaleTotalCount ++;
            }

            totalCount++;
            if(ageRangeCount.containsKey(ageRange)) {
                ageRangeCount.put(ageRange, ageRangeCount.get(ageRange) + 1);
            }else {
                ageRangeCount.put(ageRange, 1);
            }

        }

    }

    public Map<Integer, Integer> getMaleAgeRangeCount() {
        return maleAgeRangeCount;
    }

    public Integer getMaleTotalCount() {
        return maleTotalCount;
    }

    public Map<Integer, Integer> getFemaleAgeRangeCount() {
        return femaleAgeRangeCount;
    }

    public Integer getFemaleTotalCount() {
        return femaleTotalCount;
    }

    public Map<Integer, Integer> getAgeRangeCount() {
        return ageRangeCount;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public double getMalePercentage() {
        return (double) maleTotalCount/totalCount;
    }

    public double getFemalePercentage() {
        return (double) femaleTotalCount/totalCount;
    }

    public Map<Integer, Double> getAgeRangePercentage() {

        Map<Integer, Double> agePercent = new TreeMap<>();

        for(int key : ageRangeCount.keySet()) {

            agePercent.put(key, (double)ageRangeCount.get(key)/totalCount);

        }

        return agePercent;
    }
}
