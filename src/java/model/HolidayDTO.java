/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Date;

/**
 *
 * @author Admin
 */
public class HolidayDTO {
    private Date date;
    private String name;
    private boolean isPublicHoliday;

    public HolidayDTO() {
    }

    public HolidayDTO(Date date, String name, boolean isPublicHoliday) {
        this.date = date;
        this.name = name;
        this.isPublicHoliday = isPublicHoliday;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isIsPublicHoliday() {
        return isPublicHoliday;
    }

    public void setIsPublicHoliday(boolean isPublicHoliday) {
        this.isPublicHoliday = isPublicHoliday;
    }
    
    
}
