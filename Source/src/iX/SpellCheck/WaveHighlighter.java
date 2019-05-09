package iX.SpellCheck;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Shape;

import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.JTextComponent;
import javax.swing.text.Position;
import javax.swing.text.View;

public class WaveHighlighter extends DefaultHighlighter.DefaultHighlightPainter {

    boolean wave = true;
    Color color = Color.red;

    public WaveHighlighter(Color color/*, int type*/) {
        super(color);
        this.color = color;
        wave = true;//(type == NTSpellCheck.WAVE_UNDERLINE);
    }

    void setColor(Color newColor) {
        color = newColor;
    }

    @Override
    public Shape paintLayer(Graphics g, int offs0, int offs1, Shape bounds, JTextComponent c, View view) {
        try {
            if (color == null) {
                g.setColor(Color.red);
            } else {
                g.setColor(color);
            }

            Shape shape = view.modelToView(offs0, Position.Bias.Forward, offs1, Position.Bias.Backward, bounds);
            int y = (int) (shape.getBounds().getY() + shape.getBounds().getHeight()) - 2;
            int x1 = (int) shape.getBounds().getX();
            int x2 = (int) (shape.getBounds().getX() + shape.getBounds().getWidth()) - 2;
            Rectangle r = (shape instanceof Rectangle) ? (Rectangle) shape : shape.getBounds();
            g.setColor(Color.red);
            for (int i = x1; i <= x2; i++) {
                g.drawLine(i, y, i, y);
                if (++i <= x2) {
                    g.drawLine(i, y, i, y);
                }
                if (++i <= x2) {
                    g.drawLine(i, y + 1, i, y + 1);
                }
                if (++i <= x2) {
                    g.drawLine(i, y + 1, i, y + 1);
                }
            }
            return r;
        } catch (BadLocationException ex) {
            return null;
        }
    }
}