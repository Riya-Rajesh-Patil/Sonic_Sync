package application;

public class BookingHistoryItem {

    private String status, firstName, lastName, event, date, time, seats, vip, idNumber;

    public BookingHistoryItem(String status, String firstName, String lastName, String event, String date, String time, String seats, String idNumber, String vip) {

        this.status = status;
        this.firstName = firstName;
        this.lastName = lastName;
        this.event = event;
        this.date = date;
        this.time = time;
        this.seats = seats;
        this.vip = vip;
        this.idNumber = idNumber;
    }

    public String getStatus() {
        return status;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEvent() {
        return event;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getSeats() {
        return seats;
    }
 
    public String getVip() {
        return vip;
    }

    public String getIdNumber() {
        return idNumber;
    }
}
