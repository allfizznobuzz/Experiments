package com.techelevator.model;

import java.math.BigDecimal;

public class Space {

    private Long spaceId;
    private String spaceName;
    String openingMonth;
    String closingMonth;
    int maxOccupancy;
    boolean isAccessible;
    BigDecimal dailyRate;

    public Long getSpaceId() {
        return spaceId;
    }

    public void setSpaceId(Long spaceId) {
        this.spaceId = spaceId;
    }

    public String getSpaceName() {
        return spaceName;
    }

    public void setSpaceName(String spaceName) {
        this.spaceName = spaceName;
    }

    public String getOpeningMonth() {
        return openingMonth;
    }

    public void setOpeningMonth(String openingMonth) {
        this.openingMonth = openingMonth;
    }

    public String getClosingMonth() {
        return closingMonth;
    }

    public void setClosingMonth(String closingMonth) {
        this.closingMonth = closingMonth;
    }

    public int getMaxOccupancy() {
        return maxOccupancy;
    }

    public void setMaxOccupancy(int maxOccupancy) {
        this.maxOccupancy = maxOccupancy;
    }

    public String isAccessible() {
        if (isAccessible) {
            return "Yes";
        }
        return "No";
    }

    public void setAccessible(boolean accessible) {
        isAccessible = accessible;
    }

    public BigDecimal getDailyRate() {
        return dailyRate;
    }

    public void setDailyRate(BigDecimal dailyRate) {
        this.dailyRate = dailyRate;
    }
}
