import java.time.Duration;
import java.time.LocalDateTime;

public class Meeting extends Event implements Completable{
    protected String location;
    protected LocalDateTime endDateTime;
    protected boolean complete;

    public Meeting(String name, LocalDateTime start, LocalDateTime end, String location){
        super(name, start);
        this.endDateTime = end;
        this.location = location;
        this.complete = false;
    }

    public LocalDateTime getEndDateTime(){
        return this.endDateTime;
    }

    public String getLocation(){
        return this.location;
    }

    public Duration getDuration(){
        return Duration.between(getDateTime(), this.endDateTime);
    }

    public void setEndDateTime(LocalDateTime end){
        this.endDateTime = end;
    }

    public void setLocation(String location){
        this.location = location;
    }

    public void complete(){
        this.complete = true;
    }

    public boolean isComplete(){
        return this.complete;
    }

    @Override
    public String getName(){
        return this.name;
    }

    @Override
    public LocalDateTime getDateTime(){
        return this.dateTime;
    }
}
