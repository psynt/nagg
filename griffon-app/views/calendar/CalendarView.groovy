package calendar

import griffon.core.artifact.GriffonView
import griffon.inject.MVCMember
import griffon.metadata.ArtifactProviderFor
import javafx.geometry.HPos
import javafx.geometry.Insets
import javafx.scene.Scene
import javafx.scene.control.Label
import javafx.scene.control.ScrollPane
import javafx.scene.layout.GridPane
import javafx.scene.text.TextAlignment
import javafx.stage.Stage

import javax.annotation.Nonnull
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@ArtifactProviderFor(GriffonView)
class CalendarView {


    @MVCMember @Nonnull
    CalendarModel model

    void initUI(){
        def timeSlots = model.timeSlots
        GridPane calendarView = new GridPane();
        LocalDate today = LocalDate.now();
        LocalDate startOfWeek = today.minusDays(today.getDayOfWeek().getValue() - 1) ;
        LocalDate endOfWeek = startOfWeek.plusDays(6);

        model.timeSlots.each {
            def slotIndex = 1;
            it.each { e->
                calendarView.add(e.getView(), e.getDayOfWeek().getValue(), slotIndex++)
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

        for (LocalDateTime startTime = today.atTime(model.firstSlotStart); ! startTime.isAfter(today.atTime(model.lastSlotStart)); startTime = startTime + model.slotLength) {

            Label label = new Label(startTime.format(timeFormatter));
            label.setPadding(new Insets(2));
            GridPane.setHalignment(label, HPos.RIGHT);
            calendarView.add(label, 0, slotIndex);
            slotIndex++ ;
        }

        def scene = new Scene(new ScrollPane(calendarView))

        scene.getStylesheets().add(application.resourceHandler.getResourceAsURL('/TimeSlot.css').toExternalForm())

        Stage stage = (Stage) getApplication()
            .createApplicationContainer(Collections.<String, Object>emptyMap());
        stage.setTitle(getApplication().getConfiguration().getAsString("application.title"));
        stage.setWidth(480);
        stage.setHeight(320);
        stage.setScene(scene);
        getApplication().getWindowManager().attach("mainWindow", stage);
    }
}
