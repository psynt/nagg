package org.example

import javafx.beans.property.BooleanProperty
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.value.ChangeListener
import javafx.scene.Node
import javafx.scene.layout.Region

import java.time.DayOfWeek
import java.time.Duration
import java.time.LocalDateTime
import java.time.LocalTime

// Class representing a time interval, or "Time Slot", along with a view.

// View is just represented by a region with minimum size, and style class.

// Has a selected property just to represent selection.

class TimeSlot {
    private final LocalDateTime start ;
    private final Duration duration ;
    private final Region view ;


    private final BooleanProperty selected = new SimpleBooleanProperty();

    final BooleanProperty selectedProperty() {
        return selected ;
    }

    final boolean isSelected() {
        return selectedProperty().get();
    }

    final void setSelected(boolean selected) {
        selectedProperty().set(selected);
    }

    TimeSlot(LocalDateTime start, Duration duration) {


        this.start = start ;
        this.duration = duration ;

        view = new Region();
//        view.setStyle("""-fx-background-color: black, white ;
//                         -fx-background-insets: 0, 1 1 0 0 ;
//                            """)
//        view.getStylesheets().add(application.resourceHandler.getResourceAsURL('TimeSlot.css').toExternalForm())
        view.setMinSize(80, 20);
        view.getStyleClass().add("time-slot");

        selectedProperty().addListener({obs, wasSelected, isSelected ->
                view.pseudoClassStateChanged(calendar.CalendarView.SELECTED_PSEUDO_CLASS, isSelected)
        } as ChangeListener)

    }

    LocalDateTime getStart() {
        return start ;
    }

    LocalTime getTime() {
        return start.toLocalTime() ;
    }

    DayOfWeek getDayOfWeek() {
        return start.getDayOfWeek() ;
    }

    Duration getDuration() {
        return duration ;
    }

    Node getView() {
        return view;
    }
}