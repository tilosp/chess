package de.tilosp.chess.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class HoverButton extends JButton implements MouseListener {

    private Color color;
    private Color colorHover;
    private boolean hover;

    public HoverButton() {
        super();
        addMouseListener(this);
    }

    public void setBackground(Color color, Color colorHover) {
        this.color = color;
        this.colorHover = colorHover;
        updateBackground();
    }


    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        updateBackground();
    }

    private void updateBackground() {
        if (hover && isEnabled())
            setBackground(colorHover);
        else
            setBackground(color);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        hover = true;
        updateBackground();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        hover = false;
        updateBackground();
    }
}
