package com.chartered4.models;

import java.util.ArrayList;

public class AddListingBean {
    String boatName;
    ArrayList<Types> types;
    String capacity;
    String location;
    String latitude;
    String longitude;
    String captainIncluded;
    String description;
    String imagePath;
    String currency;
    String minHour; //Minimum Hours
    String bulkDiscountHour;
    String bulkDiscountPercent;
    String bulkDiscountAmount;
    RentalPrices rentalPrices;
    String boatAmenities;

//    additionalInfo, securityDeposit


    public String getBoatName() {
        return boatName;
    }

    public void setBoatName(String boatName) {
        this.boatName = boatName;
    }

    public ArrayList<Types> getTypes() {
        if (types == null){
            types = new ArrayList<>();
        }
        return types;
    }

    public void setTypes(ArrayList<Types> types) {
        this.types = types;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getCaptainIncluded() {
        return captainIncluded;
    }

    public void setCaptainIncluded(String captainIncluded) {
        this.captainIncluded = captainIncluded;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getMinHour() {
        return minHour;
    }

    public void setMinHour(String minHour) {
        this.minHour = minHour;
    }

    public String getBulkDiscountHour() {
        return bulkDiscountHour;
    }

    public void setBulkDiscountHour(String bulkDiscountHour) {
        this.bulkDiscountHour = bulkDiscountHour;
    }

    public String getBulkDiscountPercent() {
        return bulkDiscountPercent;
    }

    public void setBulkDiscountPercent(String bulkDiscountPercent) {
        this.bulkDiscountPercent = bulkDiscountPercent;
    }

    public String getBulkDiscountAmount() {
        return bulkDiscountAmount;
    }

    public void setBulkDiscountAmount(String bulkDiscountAmount) {
        this.bulkDiscountAmount = bulkDiscountAmount;
    }

    public RentalPrices getRentalPrices() {
        return rentalPrices;
    }

    public void setRentalPrices(RentalPrices rentalPrices) {
        this.rentalPrices = rentalPrices;
    }

    public String getBoatAmenities() {
        return boatAmenities;
    }

    public void setBoatAmenities(String boatAmenities) {
        this.boatAmenities = boatAmenities;
    }

    public static class Types{
        String boatTypeId;

        public Types(String boatTypeId) {
            this.boatTypeId = boatTypeId;
        }

        public String getBoatTypeId() {
            return boatTypeId;
        }

        public void setBoatTypeId(String boatTypeId) {
            this.boatTypeId = boatTypeId;
        }
    }

    public static class RentalPrices{
        String fixedRatePerHour; //Fixed Rate Per Hour
        String hourly; //NA or pass 0
        String sunday;
        String monday;
        String tuesday;
        String wednesday;
        String thursday;
        String friday;
        String saturday;

        public String getFixedRatePerHour() {
            return fixedRatePerHour;
        }

        public void setFixedRatePerHour(String fixedRatePerHour) {
            this.fixedRatePerHour = fixedRatePerHour;
        }

        public String getHourly() {
            return hourly;
        }

        public void setHourly(String hourly) {
            this.hourly = hourly;
        }

        public String getSunday() {
            return sunday;
        }

        public void setSunday(String sunday) {
            this.sunday = sunday;
        }

        public String getMonday() {
            return monday;
        }

        public void setMonday(String monday) {
            this.monday = monday;
        }

        public String getTuesday() {
            return tuesday;
        }

        public void setTuesday(String tuesday) {
            this.tuesday = tuesday;
        }

        public String getWednesday() {
            return wednesday;
        }

        public void setWednesday(String wednesday) {
            this.wednesday = wednesday;
        }

        public String getThursday() {
            return thursday;
        }

        public void setThursday(String thursday) {
            this.thursday = thursday;
        }

        public String getFriday() {
            return friday;
        }

        public void setFriday(String friday) {
            this.friday = friday;
        }

        public String getSaturday() {
            return saturday;
        }

        public void setSaturday(String saturday) {
            this.saturday = saturday;
        }
    }
}


