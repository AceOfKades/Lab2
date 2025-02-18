import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class AddEventModal extends JDialog {
    private JTextField nameField;
    private JTextField dateField;
    private JTextField endDateField;
    private JTextField locationField;
    private JButton addButton;

    private JRadioButton meetingRadioButton;
    private JRadioButton deadlineRadioButton;
    private ButtonGroup eventTypeGroup;

    private JLabel endDateLabel;
    private JLabel locationLabel;

    private EventListPanel eventListPanel;

    public AddEventModal(JFrame parent, EventListPanel eventListPanel) {
        super(parent, "Add Event", true);
        this.eventListPanel = eventListPanel;
        setLayout(new GridLayout(9, 2));

        // Name field
        nameField = new JTextField();
        dateField = new JTextField();
        endDateField = new JTextField();
        locationField = new JTextField();
        addButton = new JButton("Add Event");

        // Labels
        JLabel nameLabel = new JLabel("Event Name");
        JLabel dateLabel = new JLabel("Date and Time (yyyy-MM-dd HH:mm)");
        endDateLabel = new JLabel("End Date and Time (yyyy-MM-dd HH:mm)");
        locationLabel = new JLabel("Location");

        // Radio buttons for selecting event type (Meeting or Deadline)
        meetingRadioButton = new JRadioButton("Meeting");
        deadlineRadioButton = new JRadioButton("Deadline");

        // Grouping radio buttons
        eventTypeGroup = new ButtonGroup();
        eventTypeGroup.add(meetingRadioButton);
        eventTypeGroup.add(deadlineRadioButton);

        // Set default selection to "Deadline"
        deadlineRadioButton.setSelected(true);

        // Add components to the modal
        add(nameLabel);
        add(nameField);
        add(dateLabel);
        add(dateField);

        add(new JLabel("Event Type"));
        JPanel typePanel = new JPanel();
        typePanel.add(meetingRadioButton);
        typePanel.add(deadlineRadioButton);
        add(typePanel);

        // Add End Date and Location fields (Initially hidden)
        add(endDateLabel);
        add(endDateField);
        add(locationLabel);
        add(locationField);

        // Initially hide the meeting-specific fields and labels
        endDateLabel.setVisible(false);
        endDateField.setVisible(false);
        locationLabel.setVisible(false);
        locationField.setVisible(false);

        // Add listeners for event type radio buttons
        meetingRadioButton.addActionListener(e -> showMeetingFields(true));
        deadlineRadioButton.addActionListener(e -> showMeetingFields(false));

        addButton.addActionListener(e -> addEvent());

        add(addButton);

        setSize(500, 350);
        setLocationRelativeTo(parent);
    }

    // Method to show/hide meeting-specific fields and labels based on event type
    private void showMeetingFields(boolean show) {
        endDateLabel.setVisible(show);
        endDateField.setVisible(show);
        locationLabel.setVisible(show);
        locationField.setVisible(show);

        // Revalidate to ensure the layout is updated after visibility change
        revalidate();
        repaint();
    }

    private void addEvent() {
        String name = nameField.getText();
        String dateTimeStr = dateField.getText();
        LocalDateTime dateTime = LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        Event event = null;

        // Check if the user selected 'Meeting' or 'Deadline'
        if (meetingRadioButton.isSelected()) {
            // For Meeting, we need to get the end time and location
            String endDateStr = endDateField.getText();
            LocalDateTime endDateTime = LocalDateTime.parse(endDateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            String location = locationField.getText();
            event = new Meeting(name, dateTime, endDateTime, location);
        } else if (deadlineRadioButton.isSelected()) {
            // For Deadline, we only need the name and dateTime
            event = new Deadline(name, dateTime);
        }

        // Add the event to the event list and refresh the display
        if (event != null) {
            ArrayList<Event> events = new ArrayList<>(eventListPanel.getEvents());
            events.add(event);
            eventListPanel.setEvents(events);  // Refresh the panel with the new event
        }

        dispose();  // Close the modal
    }
}
