package calendar

import griffon.core.artifact.GriffonController
import griffon.metadata.ArtifactProviderFor
import javafx.css.PseudoClass

@ArtifactProviderFor(GriffonController)
class CalendarController {
    static final PseudoClass SELECTED_PSEUDO_CLASS = PseudoClass.getPseudoClass("selected");
}
