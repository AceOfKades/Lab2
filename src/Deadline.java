import java.time.LocalDateTime;

public class Deadline extends Event implements Completable{
    protected boolean complete;

    public Deadline(String name, LocalDateTime deadline){
        super(name, deadline);
        this.complete = false;
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
