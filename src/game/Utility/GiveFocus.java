package game.Utility;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GiveFocus extends MouseAdapter {

    @Override
    public void mouseEntered(MouseEvent e) {
        e.getComponent().requestFocus();
    }

}