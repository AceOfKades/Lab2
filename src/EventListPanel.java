import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class EventListPanel extends JPanel {
    private ArrayList<Event> events;
    private JPanel controlPanel;
    private JPanel displayPanel;
    private JComboBox<String> sortDropDown;
    private JCheckBox filterCompleted;
    private JCheckBox filterDeadlines;
    private JCheckBox filterMeetings;
    private JButton addEventButton;

    public EventListPanel(ArrayList<Event> events) {
        this.events = events;
        this.setLayout(new BorderLayout());

        // Control panel with sorting and filtering options
        controlPanel = new JPanel();
        sortDropDown = new JComboBox<>(new String[]{"Sort by Name", "Sort by Reverse Name", "Sort by Date"});
        filterCompleted = new JCheckBox("Filter Completed");
        filterDeadlines = new JCheckBox("Filter Deadlines");
        filterMeetings = new JCheckBox("Filter Meetings");
        addEventButton = new JButton("Add Event");

        controlPanel.add(sortDropDown);
        controlPanel.add(filterCompleted);
        controlPanel.add(filterDeadlines);
        controlPanel.add(filterMeetings);
        controlPanel.add(addEventButton);

        displayPanel = new JPanel();
        displayPanel.setLayout(new BoxLayout(displayPanel, BoxLayout.Y_AXIS));

        // Add listeners for the sorting and filtering options
        addEventButton.addActionListener(e -> openAddEventModal());

        sortDropDown.addActionListener(e -> updateEventDisplay());

        filterCompleted.addActionListener(e -> updateEventDisplay());
        filterDeadlines.addActionListener(e -> updateEventDisplay());
        filterMeetings.addActionListener(e -> updateEventDisplay());

        // Display the events when the panel is first created
        updateEventDisplay();

        this.add(controlPanel, BorderLayout.NORTH);
        this.add(displayPanel, BorderLayout.CENTER);
    }

    // Method to get the list of events
    public ArrayList<Event> getEvents() {
        return this.events;
    }

    // Method to update the event display
    public void updateEventDisplay() {
        // Apply filters
        ArrayList<Event> filteredEvents = new ArrayList<>(events);
        if (filterCompleted.isSelected()) {
            filteredEvents.removeIf(event -> event instanceof Completable && ((Completable) event).isComplete());
        }
        if (filterDeadlines.isSelected()) {
            filteredEvents.removeIf(event -> event instanceof Deadline);
        }
        if (filterMeetings.isSelected()) {
            filteredEvents.removeIf(event -> event instanceof Meeting);
        }

        // Sort events based on selected sort option
        String sortOption = (String) sortDropDown.getSelectedItem();
        if (sortOption != null) {
            switch (sortOption) {
                case "Sort by Name":
                    Collections.sort(filteredEvents, Comparator.comparing(Event::getName));
                    break;
                case "Sort by Reverse Name":
                    Collections.sort(filteredEvents, Comparator.comparing(Event::getName).reversed());
                    break;
                case "Sort by Date":
                    Collections.sort(filteredEvents, Comparator.comparing(Event::getDateTime));
                    break;
            }
        }

        // Update the display panel with the filtered and sorted events
        displayPanel.removeAll();
        for (Event event : filteredEvents) {
            EventPanel eventPanel = new EventPanel(event);
            displayPanel.add(eventPanel);
        }

        revalidate();
        repaint();
    }

    // Method to set new list of events (called by addDefaultEvents or AddEventModal)
    public void setEvents(ArrayList<Event> events) {
        this.events = events;
        updateEventDisplay();  // Refresh the panel with new events
    }

    // Opens AddEventModal
    private void openAddEventModal() {
        AddEventModal addEventModal = new AddEventModal((JFrame) SwingUtilities.getWindowAncestor(this), this);
        addEventModal.setVisible(true);
    }
}
