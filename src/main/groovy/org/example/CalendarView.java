package org.example;

import javafx.application.Application;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.css.PseudoClass;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;



public class CalendarView extends Application {



    private final LocalTime firstSlotStart = LocalTime.of(0, 0);

    private final Duration slotLength = Duration.ofMinutes(30);

    private final LocalTime lastSlotStart = LocalTime.of(23, 59);



    private static final PseudoClass SELECTED_PSEUDO_CLASS = PseudoClass.getPseudoClass("selected");



    private final List<TimeSlot> timeSlots = new ArrayList<>();



    @Override

    public void start(Stage primaryStage) {

        GridPane calendarView = new GridPane();



        ObjectProperty<TimeSlot> mouseAnchor = new SimpleObjectProperty<>();



        LocalDate today = LocalDate.now();

        LocalDate startOfWeek = today.minusDays(today.getDayOfWeek().getValue() - 1) ;

        LocalDate endOfWeek = startOfWeek.plusDays(6);



        for (LocalDate date = startOfWeek; ! date.isAfter(endOfWeek); date = date.plusDays(1)) {

            int slotIndex = 1 ;



            for (LocalDateTime startTime = date.atTime(firstSlotStart);

                 ! startTime.isAfter(date.atTime(lastSlotStart));

                 startTime = startTime.plus(slotLength)) {





                TimeSlot timeSlot = new TimeSlot(startTime, slotLength);

                timeSlots.add(timeSlot);



                registerDragHandlers(timeSlot, mouseAnchor);



                calendarView.add(timeSlot.getView(), timeSlot.getDayOfWeek().getValue(), slotIndex);

                slotIndex++ ;

            }

        }



        //headers:



        DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("E\nMMM d");



        for (LocalDate date = startOfWeek; ! date.isAfter(endOfWeek); date = date.plusDays(1)) {

            Label label = new Label(date.format(dayFormatter));

            label.setPadding(new Insets(1));

            label.setTextAlignment(TextAlignment.CENTER);

            GridPane.setHalignment(label, HPos.CENTER);

            calendarView.add(label, date.getDayOfWeek().getValue(), 0);

        }



        int slotIndex = 1 ;

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("H:mm");

        for (LocalDateTime startTime = today.atTime(firstSlotStart);

             ! startTime.isAfter(today.atTime(lastSlotStart));

             startTime = startTime.plus(slotLength)) {

            Label label = new Label(startTime.format(timeFormatter));

            label.setPadding(new Insets(2));

            GridPane.setHalignment(label, HPos.RIGHT);

            calendarView.add(label, 0, slotIndex);

            slotIndex++ ;

        }



        ScrollPane scroller = new ScrollPane(calendarView);



        Scene scene = new Scene(scroller);

//        scene.getStylesheets().add(getClass().getResource("").toExternalForm());

        primaryStage.setScene(scene);

        primaryStage.show();



    }



    // Registers handlers on the time slot to manage selecting a range of slots in the grid.



    private void registerDragHandlers(TimeSlot timeSlot, ObjectProperty<TimeSlot> mouseAnchor) {

        timeSlot.getView().setOnDragDetected(event -> {

            mouseAnchor.set(timeSlot);

            timeSlot.getView().startFullDrag();

            timeSlots.forEach(slot ->

                    slot.setSelected(slot == timeSlot));

        });



        timeSlot.getView().setOnMouseDragEntered(event -> {

            TimeSlot startSlot = mouseAnchor.get();

            timeSlots.forEach(slot ->

                    slot.setSelected(isBetween(slot, startSlot, timeSlot)));

        });



        timeSlot.getView().setOnMouseReleased(event -> mouseAnchor.set(null));

    }



    // Utility method that checks if testSlot is "between" startSlot and endSlot

    // Here "between" means in the visual sense in the grid: i.e. does the time slot

    // lie in the smallest rectangle in the grid containing startSlot and endSlot

    //

    // Note that start slot may be "after" end slot (either in terms of day, or time, or both).



    // The strategy is to test if the day for testSlot is between that for startSlot and endSlot,

    // and to test if the time for testSlot is between that for startSlot and endSlot,

    // and return true if and only if both of those hold.



    // Finally we note that x <= y <= z or z <= y <= x if and only if (y-x)*(z-y) >= 0.



    private boolean isBetween(TimeSlot testSlot, TimeSlot startSlot, TimeSlot endSlot) {



        boolean daysBetween = testSlot.getDayOfWeek().compareTo(startSlot.getDayOfWeek())

                * endSlot.getDayOfWeek().compareTo(testSlot.getDayOfWeek()) >= 0 ;



        boolean timesBetween = testSlot.getTime().compareTo(startSlot.getTime())

                * endSlot.getTime().compareTo(testSlot.getTime()) >= 0 ;



        return daysBetween && timesBetween ;

    }



    // Class representing a time interval, or "Time Slot", along with a view.

    // View is just represented by a region with minimum size, and style class.



    // Has a selected property just to represent selection.





}