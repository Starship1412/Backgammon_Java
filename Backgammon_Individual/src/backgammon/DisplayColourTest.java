package backgammon;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class DisplayColourTest {

    @Test // Test if the colour escape sequences are correctly defined
    void test() {
        assertEquals("\033[0m", DisplayColour.RESET);
        assertEquals("\033[0;30m", DisplayColour.BLACK);
        assertEquals("\033[0;31m", DisplayColour.RED);
        assertEquals("\033[1;31m", DisplayColour.BOLD_RED);
        assertEquals("\033[0;32m", DisplayColour.GREEN);
        assertEquals("\033[0;33m", DisplayColour.YELLOW);
        assertEquals("\033[0;34m", DisplayColour.BLUE);
        assertEquals("\033[0;35m", DisplayColour.PURPLE);
        assertEquals("\033[0;36m", DisplayColour.CYAN);
        assertEquals("\033[0;37m", DisplayColour.WHITE);
        assertEquals("\033[1;37m", DisplayColour.BOLD_WHITE);
    }
}
