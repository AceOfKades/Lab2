import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.time.LocalDateTime;

public class EventPlanner {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Event Planner");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // Create the EventListPanel
            EventListPanel eventListPanel = new EventListPanel(new ArrayList<>());

            // Add default events
            addDefaultEvents(eventListPanel);

            frame.add(eventListPanel);
            frame.setSize(800, 400);
            frame.setVisible(true);
        });
    }

    // Method to add default events
    public static void addDefaultEvents(EventListPanel eventListPanel) {
        ArrayList<Event> events = new ArrayList<>();

        // Example: Add a Deadline
        events.add(new Deadline("Finish Homework", LocalDateTime.of(2025, 2, 25, 23, 59)));

        // Example: Add a Meeting
        events.add(new Meeting("Team Meeting", LocalDateTime.of(2025, 2, 20, 14, 0),
                LocalDateTime.of(2025, 2, 20, 15, 0), "Conference Room"));

        // Add the events to the EventListPanel
        eventListPanel.setEvents(events);
    }
}
