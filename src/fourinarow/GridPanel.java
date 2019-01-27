package fourinarow;

import java.awt.*;
import javax.swing.*;


/**
 * This class draws the panel with the "Four in the row" grid and the chips in it.
 * @author Victor
 * @version 1.0
 */
public class GridPanel extends JPanel{
    /**
     * Background image of the blue "Four in the row" grid.
     */
    private static final Image BACKGROUND = new ImageIcon(GridPanel.class.getResource("/resources/background.png")).getImage();
    /**
     * Array of images of the 10 different colour chips.
     */
    private static final Image[] CHIPS = new Image[10];
    /**
     * 2D Colour-number Array of each chip in the whole grid, that is handed over, when the method {@link #reDraw(Integer[][])} is called.<p>
     * For {@link Color Colours} and Colour-numbers see: {@link FourInARow#intToColour(int clr)}.</p>
     */
    private Integer[][] chips = new Integer[7][6];
    
    /**
     * Width of the {@link #BACKGROUND}, which has to line up with the width of the {@link Interf#button buttons} in the {@link Interf#buttonPanel}
     * and is picked to fit the width of the {@link #BACKGROUND}, so that the grid looks as crisp as possible.
     */
    private static final int RECT_WIDTH = 504;
    /**
     * Height of the {@link #BACKGROUND}, which has to be proportional to the {@link #RECT_WIDTH}, so that the image is not distorted.
     */
    private static final int RECT_HEIGHT = 432;
    
    /**
     * Adds the different colour chips to the array {@link #CHIPS}.
     */
    public GridPanel(){
    	for(int i=0; i<10; ++i){
    		CHIPS[i] = new ImageIcon(GridPanel.class.getResource("/resources/chip_"+i+".png")).getImage();
        }
    }
    
    /**
     * When this method is called it repaints this panel according to the arrayInteger[][] chips from the parameter.
     * @param chips 2D Colour-number Array of each chip in the whole grid, that is handed over from {@link Interf#setChip(Integer[][] chips)}.
     */
    public void reDraw(Integer[][] chips){
        this.chips = chips;
        repaint();
    }
    
    /**
     * Paints this panel with the different coloured {@link #CHIPS}, based on the Colour-number Array {@link #chips},
     * handed over in the method {@link #reDraw(Integer[][] chips)} and the {@link #BACKGROUND} grid.<p>
     * When painting the chips x = 0 is the left column and y = 0 is the bottom row.</p>
     * @param g don't know.
     */
    @Override
    public void paint(Graphics g){
        g.clearRect(0, 0, this.getWidth(), this.getHeight());
        for(int x=0; x < 7; ++x){
            for(int y=0; y < 6; ++y){
                if(chips[x][y] != null){
                    g.drawImage(CHIPS[chips[x][y]], 2+x*RECT_WIDTH/7, RECT_HEIGHT-(y+1)*RECT_HEIGHT/6, RECT_WIDTH/7, RECT_HEIGHT/6, this);
                }
            }
        }
        g.drawImage(BACKGROUND, 2, 0, RECT_WIDTH, RECT_HEIGHT, this);
    }
}