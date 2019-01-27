package fourinarow;

import java.awt.*;
import java.awt.event.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import javax.swing.*;



/**
 * This class is the interface of the class {@link FourInARow} and the {@link JFrame} has a
 * {@link #bar menu bar} at the top, a {@link #buttonPanel} for the {@link #button buttons} to insert a chip,
 * a {@link #gridPanel} (an Object of the class {@link GridPanel} with the actual grid) and a {@link #label} at the bottom.
 * @author Victor
 * @version 1.0
 */
public class Interf extends JFrame implements ActionListener{
    /**
     * Icon for the frame.
     */
	private static final Image icon = new ImageIcon(Interf.class.getResource("/resources/icon.png")).getImage();
    /**
     * Menu bar at the top including: {@link #item menu items} and a {@link #menu menu} in the top right corner for other options.
     */
    private static final JMenuBar bar = new JMenuBar();
        /**
         * Array of 3 menu items: one for changing names, one for changing colours and one in the {@link #menu menu} for resetting the game.<br>
         * All 3 menu item are in the {@link #bar menu bar}.
         */
        private JMenuItem[] item = new JMenuItem[3];
        /**
         * Menu for other options: resetting the game with one of the {@link #item menu items} and a {@link #box check box} in it.
         */
        private static final JMenu menu = new JMenu("Other options");
            /**
             * Check box in the {@link #menu menu} for "Other options" that is selected by default and when selected makes the looser start the next game.
             */
            private JCheckBox box; 
    /**
     * Panel in a {@link java.awt.GridLayout grid layout} for the seven {@link #button buttons} at the top.
     */
    private static final JPanel buttonPanel = new JPanel();
        /**
         * Array of Buttons in the {@link #buttonPanel} that if pressed drops a chip down that column.
         */
        private JButton [] button = new JButton [7];
        /**
         * Arrow image on the enabled {link #button buttons}.
         */
        private static final Image arrow = new ImageIcon(Interf.class.getResource("/resources/arrow.png")).getImage();
        /**
         * Cross image on the disabled {@link #button buttons}.
         */
        private final static Image cross = new ImageIcon(Interf.class.getResource("/resources/cross.png")).getImage();
    /**
     * {@link GridPanel}, that extends {@link JPanel} and has the actual grid for four in the row.
     */
    private static GridPanel gridPanel = new GridPanel();
    /**
     * Label below the grid.<p>
     * It indicates who's turn it is or who has won.</p>
     */
    private final JLabel label = new JLabel("", SwingConstants.CENTER);
    
    
    /**
     * Width of the frame.<p>
     * Value is determined so that it lines up the {@link #button buttons} in {@link #buttonPanel} with
     * the grid of the {@link GridLayout.#BACKGROUND background image} in the class {@link GridPanel}.</p>
     * @see #gridPanel
     */
    private static final int FRAME_WIDTH = 516;
    /**
     * Height of the frame.
     */
    private static final int FRAME_HEIGHT = 580;
    /**
     * Font size for the {@link #label} at the bottom.
     */
    private static final int FONT_SIZE = 48;
        /**
         * Font for the {@link #label} at the bottom.
         */
        private static final Font LABEL_FONT = new Font("Sans Serifs", Font.BOLD, FONT_SIZE); //Font of the text at the bottom
    
    
    /**
     * Reference to the class: {@link FourInARow}.
     */
    private final FourInARow fourInARow;
    
    /**
     * Constructs the interface for the game "Four in a row" including {@link #gridPanel},
     * an object of the class {@link GridPanel} with the actual grid for "Four in a row".
     * @param fourInARow is the reference to the object of the class {@link FourInARow}, that called this constructor.
     */
    public Interf(FourInARow fourInARow){
        super.setTitle("Four in a row");
        super.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        super.setLayout(new BorderLayout());
        super.setDefaultCloseOperation(EXIT_ON_CLOSE);
        super.setResizable(false);
        super.setIconImage(icon);
        
        this.fourInARow = fourInARow;
        
        item[0] = new JMenuItem (new AbstractAction ("Change players' names"){
            @Override
            public void actionPerformed (ActionEvent e){
                if (e.getSource() == item[0]){
                    nameOptions();
                }
            }
        });
        
        item[1] = new JMenuItem (new AbstractAction ("Change players' colours"){
            @Override
            public void actionPerformed (ActionEvent e){
                if (e.getSource() == item[1]){
                    colourOptions();
                }
            }
        });
        
        item[2] = new JMenuItem (new AbstractAction ("Restart game"){
            @Override
            public void actionPerformed (ActionEvent e){
                if (e.getSource() == item[2]){
                    fourInARow.reset();
                }
            }
        });
        
        box = new JCheckBox (new AbstractAction ("Losers start"){
            @Override
            public void actionPerformed(ActionEvent e){
                if (e.getSource() == box){
                    fourInARow.setLoserStarts(box.isSelected());
                }
            }
        });
        
        menu.add(item[2]);
        box.doClick();
        menu.add(box);
        bar.add(item[0]);
        bar.add(item[1]);
        bar.add(menu);
        super.setJMenuBar(bar);
        
        
        buttonPanel.setLayout(new GridLayout(1, 7));
        gridPanel.reDraw(new Integer[7][6]); //calls reDraw(Integer[][]) in order for the grid to be drawn onto the gridPanel
        label.setFont(LABEL_FONT);
        
        for(int i = 0; i < 7; ++i){
            button[i] = new JButton (new AbstractAction (){
                @Override
                public void actionPerformed (ActionEvent e) {
                    for(int j=0; j<7; ++j){
                        if(e.getSource() == button[j]) {
                            fourInARow.button(j); //when button[j] is pressed it calls fourInARow.button(j) in order to add a new chip to the grid
                            break;
                        }
                    } 
                }
            });
            button[i].setIcon(new ImageIcon(arrow)); //when button[i] is enabled it shows the arrow
            button[i].setDisabledIcon(new ImageIcon(cross)); //when button[i] is disabled it shows the red cross
            buttonPanel.add(button[i]);
        }
        
        super.add(buttonPanel, BorderLayout.NORTH);
        super.add(gridPanel, BorderLayout.CENTER);
        super.add(label, BorderLayout.SOUTH);
        super.setVisible(true);
    }
    
    /**
     * Enables or disables a {@link #button} based on <code>buttonNum</code>.
     * @param buttonNum decides which button is dis-/enabled.<p>
     * If <code>buttonNum</code> = 0 the leftmost button is dis-/enabled.<br>
     * If <code>buttonNum</code> = 6 the rightmost button is dis-/enabled.</p>
     * @param enable sets whether the button should be disabled or enabled.<p>
     * If <code>enable</code> = true the button is enabled.<br>
     * If <code>enable</code> = false the button is disabled.</p>
     * @see #arrow
     * @see #cross
     * @see FourInARow#numberOfFullColumns
     */
    public void setEnableButton (int buttonNum, boolean enable){
        button[buttonNum].setEnabled(enable);
    }
    
    /**
     * Is called when someone has won or when there is a tie.<p>
     * It disables the {@link #button buttons} and sets the {@link #label} to show the {@link FourInARow#winner winner's} name and {@link java.awt.Color Colour}.
     * @param name sets {@link #label label's} text to: <code>name</code>+" has won!!".
     * @param colour sets {@link #label label's} {@link java.awt.Color Colour} to that {@link java.awt.Color Colour}.
     * @see FourInARow#hasNoOneWon() 
     * @see FourInARow#hasSomeoneWon(int a, int b) 
     */
    public void setHasWon(String name, Color colour){
        for(int i=0; i<7; ++i){
            setEnableButton(i, false);
        }
        
        label.setText(name+" has won!!");
        if(colour != Color.YELLOW){
            label.setForeground(colour);
        }else{
            label.setForeground(Color.decode("#CCCC00"));
        }
    }
    
    /**
     * When called it uses {@link GridPanel#reDraw(Integer[][] chips)} to re draw the {@link #gridPanel} and it updates the {@link #label}.
     * @param chips 2D Colour-number Array of each chip in the whole grid, that is handed over from {@link FourInARow#chip}.
     */
    public void setChip (Integer[][] chips){
        gridPanel.reDraw(chips);
        
        if(fourInARow.hasNoOneWon()){
            label.setText(("It is "+fourInARow.getNextTurnName()+"'s turn."));
            if(fourInARow.getNextTurnColour() != Color.YELLOW){
                label.setForeground(fourInARow.getNextTurnColour());
            }else{
                label.setForeground(Color.decode("#CCCC00"));
            }
        }else{
            setHasWon(fourInARow.getPlayersNames(fourInARow.getWinner()), fourInARow.getPlayersColours(fourInARow.getWinner()));
        }
    }
    
    /**
     * When this method is called it opens a sort of dialogue box for {@link #player1} and {@link #player2} to change their names.<p>
     * It is called when the {@link #item menu item} in the {@link #bar menu bar} for changing the players names is clicked.</p>
     * @see FourInARow#setPlayersNames(String name1, String name2) 
     */
    private void nameOptions(){
        JFrame f1 = new JFrame();
        f1.setTitle("Change names");
        f1.setSize(280, 140);
        f1.setResizable(false);
        
        JPanel p1 = new JPanel();
        
        JLabel l1 = new JLabel("Name of player1");
        JLabel l2 = new JLabel("Name of player2");
        JLabel lError = new JLabel();
        
        lError.setForeground(Color.decode("#EF0000"));
        
        JTextField tf1 = new JTextField(fourInARow.getPlayersNames(1), 10); //previous names of the players is set as text for the text field
        JTextField tf2 = new JTextField(fourInARow.getPlayersNames(2), 10);
        
        Canvas c1 = new Canvas(){ //small circle to indicate the players' colours
            public void paint(Graphics g){
                g.setColor(fourInARow.getPlayersColours(1));
                g.fillOval(5, 0, 20, 20);
            }
        };
        c1.setVisible (true);
        c1.setSize (40, 20);
        
        Canvas c2 = new Canvas(){ //small circle to indicate the players' colours
            public void paint(Graphics g){
                g.setColor(fourInARow.getPlayersColours(2));
                g.fillOval(5, 0, 20, 20);
            }
        };
        c2.setVisible (true);
        c2.setSize (40, 20);
        
        JButton b1 = new JButton (new AbstractAction("    OK    "){
            public void actionPerformed(ActionEvent e) {
                tf1.setText(tf1.getText().trim());//removes leading or trailing spaces
                tf2.setText(tf2.getText().trim());//removes leading or trailing spaces
                if(!"".equals(tf1.getText()) && !"".equals(tf2.getText())){ //checks if both new names are at least one character long
                    if(!tf1.getText().equals(tf2.getText())){ //checks if both new names are different
                        FontRenderContext fr = new FontRenderContext(new AffineTransform(), true, true); //checks if the names are short enough to be displayed in the label
                        if(LABEL_FONT.getStringBounds(tf1.getText(), fr).getWidth() <= 260 && LABEL_FONT.getStringBounds(tf2.getText(), fr).getWidth() <= 260 ){
                            fourInARow.setPlayersNames(tf1.getText(), tf2.getText());
                            f1.dispose();
                            
                            if(fourInARow.hasNoOneWon()){
                                label.setText(("It is "+fourInARow.getNextTurnName()+"'s turn."));
                            }else{
                                label.setText(fourInARow.getPlayersNames(fourInARow.getWinner())+" has won!!");
                            }
                        }else{
                            lError.setText("Your name is too long!");
                        }
                    }else{
                        lError.setText("    Your names have to be different!    ");
                    }
                }else{
                    lError.setText("Your name has to be at least one character long!");
                }
            }
        });
        JButton b2 = new JButton(new AbstractAction("Cancel"){
            public void actionPerformed(ActionEvent e) {
                f1.dispose();
            }
        });
        
        p1.add(l1); //line after line the label, canvas and textfield are added to the panel, with the label at the bottom for potential input errors
        p1.add(c1);
        p1.add(tf1);
        p1.add(l2);
        p1.add(c2);
        p1.add(tf2);
        p1.add(b2);
        p1.add(b1);
        p1.add(lError);
        
        f1.add(p1);
        f1.setVisible(true);
    }
    
    /**
     * When this method is called it opens a sort of dialogue box for {@link #player1} and {@link #player2} to change their {@link java.awt.Color colours}.<p>
     * It is called when the {@link #item menu item} in the {@link #bar menu bar} for changing the players {@link java.awt.Color colours} is clicked.</p>
     * @see FourInARow#setPlayersColours(int colourNumber1, int colourNumber2)
     */
    private void colourOptions(){
        JFrame f1 = new JFrame();
        JPanel p1 = new JPanel();
        JLabel l1 = new JLabel("Colour of player1");
        JLabel l2 = new JLabel("Colour of player2");
        JLabel lError = new JLabel();
        
        lError.setForeground(Color.decode("#EF0000"));
        
        f1.setTitle("Change colours");
        f1.setSize(280, 145);
        f1.setResizable(false);
 
        final String colours[] = {"Red", "Orange", "Yellow",
            "Green", "Cyan", "Blue", "Violet", "Brown", "Black", "Grey"};
 
        JComboBox colourChoice1 = new JComboBox(colours);
        JComboBox colourChoice2 = new JComboBox(colours);
        
        colourChoice1.setSelectedIndex(fourInARow.getPlayersClrNumber(1)); //previous colours of the players are set as selected index
        colourChoice2.setSelectedIndex(fourInARow.getPlayersClrNumber(2));
        
        Canvas c1 = new Canvas(){ //small circle to indicate the players' previous colours
            public void paint(Graphics g){
                g.setColor(fourInARow.getPlayersColours(1));
                g.fillOval(5, 0, 20, 20);
            }
        };
        c1.setVisible (true);
        c1.setSize (40, 20);
        
        Canvas c2 = new Canvas(){ //small circle to indicate the players' previous colours
            public void paint(Graphics g){
                g.setColor(fourInARow.getPlayersColours(2));
                g.fillOval(5, 0, 20, 20);
            }
        };
        c2.setVisible (true);
        c2.setSize (40, 20);
        
        JButton b1 = new JButton (new AbstractAction("    OK    "){
            public void actionPerformed(ActionEvent e) {
                if(colourChoice1.getSelectedIndex() != colourChoice2.getSelectedIndex()){ //checks if both selected colours are different
                    fourInARow.setPlayersColours(colourChoice1.getSelectedIndex(), colourChoice2.getSelectedIndex());
                    f1.dispose();
                    
                    if(fourInARow.hasNoOneWon()){
                        label.setText(("It is "+fourInARow.getNextTurnName()+"'s turn."));
                        if(fourInARow.getNextTurnColour() != Color.YELLOW){
                            label.setForeground(fourInARow.getNextTurnColour());
                        }else{
                            label.setForeground(Color.decode("#CCCC00"));
                        }
                    }else{
                        setHasWon(fourInARow.getPlayersNames(fourInARow.getWinner()), fourInARow.getPlayersColours(fourInARow.getWinner()));
                    }
                    fourInARow.refresh();
                }else{
                    lError.setText("Both Colours are the same! Change one!");
                }
            }
        });
        JButton b2 = new JButton(new AbstractAction("Cancel"){
            public void actionPerformed(ActionEvent e) {
                f1.dispose();
            }
        });
        
        p1.add(l1); //line after line the label, canvas and textfield are added to the panel, with the label at the bottom for potential input errors
        p1.add(c1);
        p1.add(colourChoice1);
        p1.add(l2);
        p1.add(c2);
        p1.add(colourChoice2);
        p1.add(b2);
        p1.add(b1);
        p1.add(lError);
        
        f1.add(p1);
        f1.setVisible(true);
    }
    
    /**
     * Seems to be useful, don't quite understand, why.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}