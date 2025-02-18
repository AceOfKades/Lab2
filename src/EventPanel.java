import javax.swing.*;
import java.awt.*;

public class EventPanel extends JPanel {
    private Event event;
    private JButton completeButton;

    public EventPanel(Event event) {
        this.event = event;
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel nameLabel = new JLabel("Event: " + event.getName());
        JLabel dateTimeLabel = new JLabel("Date and Time: " + event.getDateTime().toString());

        add(nameLabel);
        add(dateTimeLabel);

        if (event instanceof Meeting) {
            Meeting meeting = (Meeting) event;
            JLabel locationLabel = new JLabel("Location: " + meeting.getLocation());
            JLabel durationLabel = new JLabel("Duration: " + meeting.getDuration().toMinutes() + " minutes");
            add(locationLabel);
            add(durationLabel);
        }

        if (event instanceof Completable) {
            completeButton = new JButton("Complete");
            completeButton.addActionListener(e -> {
                ((Completable) event).complete();
                updateCompletionStatus();
            });
            add(completeButton);
        }

        updateCompletionStatus();
    }

    public void updateCompletionStatus() {
        if (event instanceof Completable && ((Completable) event).isComplete()) {
            this.setBackground(Color.GRAY);  // Completed events get a gray background
        } else {
            this.setBackground(Color.WHITE);
        }
    }
}
