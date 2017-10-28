package calendar

import griffon.core.artifact.GriffonModel
import griffon.metadata.ArtifactProviderFor
import org.example.TimeSlot

import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@ArtifactProviderFor(GriffonModel)
class CalendarModel {

    static final LocalTime firstSlotStart = LocalTime.of(0, 0);
    static final Duration slotLength = Duration.ofMinutes(30);
    static final LocalTime lastSlotStart = LocalTime.of(23, 59);
    final List<List<TimeSlot>> timeSlots = new ArrayList<>();

    CalendarModel(){
        LocalDate today = LocalDate.now();
        LocalDate startOfWeek = today.minusDays(today.getDayOfWeek().getValue() - 1) ;
        LocalDate endOfWeek = startOfWeek.plusDays(6);


        for (LocalDate date = startOfWeek; !date.isAfter(endOfWeek); date = date.plusDays(1)) {
            timeSlots.add(new ArrayList<>())

            for (LocalDateTime startTime = date.atTime(firstSlotStart); ! startTime.isAfter(date.atTime(lastSlotStart)); startTime = startTime.plus(slotLength)) {
                TimeSlot timeSlot = new TimeSlot(startTime, slotLength);
                timeSlots.last().add(timeSlot);
            }
        }


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

    boolean isBetween(TimeSlot testSlot, TimeSlot startSlot, TimeSlot endSlot) {
        boolean daysBetween = testSlot.getDayOfWeek().compareTo(startSlot.getDayOfWeek()) * endSlot.getDayOfWeek().compareTo(testSlot.getDayOfWeek()) >= 0 ;

        boolean timesBetween = testSlot.getTime().compareTo(startSlot.getTime()) * endSlot.getTime().compareTo(testSlot.getTime()) >= 0 ;

        return daysBetween && timesBetween ;
    }
}
