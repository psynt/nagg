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

        view.setMinSize(80, 20);

        view.getStyleClass().add("time-slot");



        selectedProperty().addListener({obs, wasSelected, isSelected ->
                view.pseudoClassStateChanged(CalendarView.SELECTED_PSEUDO_CLASS, isSelected)
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
