package fourinarow;

import java.awt.Color;


/**
 * This class is the main class of its package {@link fourinarow} and when it is executed through {@link #main(String[])} the game "Four in the row" starts running.
 * That object creates an object of the class {@link Interf} as an interface for the player.
 * @author Victor
 * @version 1.0
 */
public class FourInARow {
    /**
     * Name of the first player.
     */
    private String player1 = "Player1";
    /**
     * Name of the second player.
     */
    private String player2 = "Player2";
    /**
     * Player-number of the current winner.<p>
     * If {@link #player1} has won then winner = 1.<br>
     * If {@link #player2} has won then winner = 2.</p>
     */
    private int winner;
    
    /**
     * {@link java.awt.Color Colour} of {@link #player1}'s {@link #chips}.
     */
    private Color colour1 = Color.RED;
    /**
     * {@link java.awt.Color Colour} of {@link #player2}'s {@link #chips}.
     */
    private Color colour2 = Color.YELLOW;
    
    /**
     * Colour-number of {@link #player1}'s {@link #chips}.
     * @see #intToColour(int clr)
     */
    private int colourNumber1 = 0;
    /**
     * Colour-number of {@link #player2}'s {@link #chips}.
     * @see #intToColour(int clr)
     */
    private int colourNumber2 = 2;
    
    /**
     * Number of columns that are filled all the way up with {@link #chips}.<p>
     * I.e. those columns are blocked for further {@link #chips}.</p>
     */
    private int numberOfFullColumns = 0;
    /**
     * 2D Colour-number Array of each chip in the whole grid.<p>
     * Use {@link #intToColour(int clr)} to get the number based on a {@link java.awt.Color Colour}.</p><p>
     * chip[0][0] is the bottom left corner.<br>
     * chip[0][5] is the top left corner.<br>
     * chip[6][0] is the bottom right corner.<br>
     * chip[6][5] is the top right corner.</p>
     */
    private Integer[][] chip = new Integer[7][6];
    
    /**
     * Tells whether it is {@link #player1}'s turn.<p>
     * If turn1 = true, it is {@link #player1}'s turn.<br>
     * If turn1 = false, it is {@link #player2}'s turn.</p>
     */
    private boolean turn1 = Math.random()<0.5;
    /**
     * Tells whether the loser of a match starts the next game.
     */
    private boolean losersStarts = true;
    /**
     * Tells whether no one has won yet.<p>
     * If noOneHasWon = true, the game is still running.<br>
     * If noOneHasWon = false, one player has won.</p>
     */
    private boolean noOneHasWon = true;
    
    /**
     * Reference to the interface.
     */
    private Interf interf;
    
    
    /**
     * Constructs a new "four in a row" game including the {@link #interf interface}.
     */
    private FourInARow(){
        interf = new Interf(this);
        interf.setChip(chip);
    }
    
    /**
     * Executes the program.
     */
    public static void main(String[] args){
        new FourInARow();
    }
    
    /**
     * Simulates a button being pressed, by if possible dropping a chip into the correct column.
     * @param x x-value of the column.
     * @throws IllegalArgumentException if <code>x</code> doesn't fit with any column number or the column is already full.
     * @see #chip
     */
    public void button(int x){
        if(x<0 || x>6) throw new IllegalArgumentException("Input out of bound");
        if(chip[x][5]!=null) throw new IllegalArgumentException("Column is already full");
        for(int y=0; y<6; ++y){
            //if there is an empty place
            if(chip[x][y] == null){
                turn1 = !turn1;
                if(!turn1){
                    chip[x][y] = colourNumber1;
                }
                else{
                    chip[x][y] = colourNumber2;
                }
                interf.setChip(chip);
                hasSomeoneWon(x, y);
                //if column is full
                if(y == 5){
                    interf.setEnableButton(x, false);
                    ++numberOfFullColumns;
                    if(numberOfFullColumns == 7 && noOneHasWon){
                        interf.setHasWon("Nobody", Color.BLACK);
                    }
                }
                break;
            }
        }
    }
    
     /**
     * Gets the {@link java.awt.Color Colour} of the player based on the <code>playerNum</code>.
     * @param playerNum number of the player. <code>playerNum</code> has to be either 1 or 2 for {@link #player1} or {@link #player2}.
     * @return {@link java.awt.Color Colour} that player has.
     * @throws IllegalArgumentException if the <code>playerNum</code> is neither 1 nor 2.
     * @see #colour1
     * @see #colour2
     */
    public Color getPlayersColours(int playerNum){
        if(playerNum == 1) return colour1;
        else{
            return colour2;
        }
    }
   
    /**
     * Gets the Colour-number of the player based on the <code>playerNum</code>.<p>
     * For the colour-number see {@link #intToColour(int)}.</p>
     * @param playerNum number of the player. <code>playerNum</code> has to be either 1 or 2 for {@link #player1} or {@link #player2}.
     * @return Color-number that player has.
     * @throws IllegalArgumentException if the <code>playerNum</code> is neither 1 nor 2.
     * @see #colourNumber1
     * @see #colourNumber2
     */
    public int getPlayersClrNumber(int playerNum){
        if(playerNum == 1) return colourNumber1;
        else{
            return colourNumber2;
        }
    }
    
    /**
     * Gets the current name of the player based on the <code>playerNum</code>.
     * @param playerNum number of the player. <code>playerNum</code> has to be either 1 or 2 for {@link #player1} or {@link #player2}.
     * @return Current name that player has.
     * @throws IllegalArgumentException if the <code>playerNum</code> is neither 1 nor 2.
     * @see #player1
     * @see #player2
     */
    public String getPlayersNames(int playerNum){
        if(playerNum == 1)return player1;
        else{
            return player2;
        }
    }
    
    /**
     * Gets the {@link java.awt.Color Colour} of the player who's turn it is.<p>
     * Only used for the bottom {@link Interf#label}.</p>
     * @return {@link java.awt.Color Colour} of the player who's turn it is.
     * @see #turn1
     * @see #colour1
     * @see#colour2
     */
    public Color getNextTurnColour(){
        if(turn1) return colour1;
        else{
            return colour2;
        }
    }
    
    /**
     * Gets the {@link java.lang.String String} of the player who's turn it is.<p>
     * Only used for the bottom {@link Interf#label}.</p>
     * @return {@link java.lang.String String} of the player who's turn it is.
     * @see #turn1
     * @see #player1
     * @see #player2
     */
    public String getNextTurnName(){
        if(turn1) return player1;
        else{
            return player2;
        }
    }
    
    /**
     * Gets the player-number who has won.
     * @return 1 if {@link #player1} has won.<br>
     * 2 if {@link #player2} has won.
     * @see #winner
     */
    public int getWinner(){
        return winner;
    }
    
    /**
     * Returns whether no one has won.<p>
     * Only used for the bottom {@link Interf#label}.</p>
     * @return whether no one has won. if no one has won it returns true otherwise false.
     * @see #noOneHasWon
     */
    public boolean hasNoOneWon(){
        return noOneHasWon;
    }
    
    /**
     * Checks whether someone has won.
     * @param a x-coordinate of the last chip that was placed.
     * @param b y-coordinate of the last chip that was placed.
     * @see #chip
     * @see #noOneHasWon
     * @see #winner
     */
    private void hasSomeoneWon(int a, int b){
        int x = a+3;
        int y = b+3;
        Integer[][] check = new Integer[13][12];
        for(int i=0; i<7; ++i){
            for(int j=0; j<6; ++j){
                check[i+3][j+3] = chip[i][j];
            }
        }
        for(int i=0; i<4; ++i){
            if ((check[x - i][y] == check[x + 1 - i][y] //checks horizontally
                && check[x + 1 - i][y] == check[x + 2 - i][y]
                && check[x + 2 - i][y] == check[x + 3 - i][y])
                ||(check[x][y - i] == check[x][y + 1 - i]//Checks vertically
                && check[x][y + 1 - i] == check[x][y + 2 - i]
                && check[x][y + 2 - i] == check[x][y + 3 - i])
                ||(check[x-i][y-i] == check[x + 1 - i][y + 1 - i]//Checks diagonally ++ --> --
                && check[x + 1 - i][y + 1 - i] == check[x + 2 - i][y + 2 - i]
                && check[x + 2 - i][y + 2 - i] == check[x + 3 - i][y + 3 - i])
                ||(check[x-i][y+i] == check[x + 1 - i][y - 1 + i]//Checks diagonally -+ --> +-
                && check[x + 1 - i][y - 1 + i] == check[x + 2 - i][y - 2 + i]
                && check[x + 2 - i][y - 2 + i] == check[x + 3 - i][y - 3 + i]
                )) {
                    if (check[x][y] == colourNumber1) {
                        interf.setHasWon(player1, colour1);
                        noOneHasWon = false;
                        winner = 1;
                    } else {
                        interf.setHasWon(player2, colour2);
                        noOneHasWon = false;
                        winner = 2;
                    }
                    break;
            }
        }
    }
    
    /**
     * Set whether the loser of the last match gets to start.
     * @param loserStarts if true the loser starts, if false the player starting is randomly picked.
     * @see #losersStarts
     */
    public void setLoserStarts(boolean loserStarts){
        this.losersStarts = loserStarts;
    }
    
    /**
     * Returns the {@link java.awt.Color Colour} based on the colour-number.
     * @param clr number of the colour between 0 and 8.
     * @return {@link java.awt.Color Colour} of the colour-number. If the colour-number is out of bound it returns the default-colour: <code>Colour.RED</code>.
     * @see #colourNumber1
     * @see #colourNumber2
     * @see #colour1
     * @see #colour2
     */
    public Color intToColour(int clr){
        switch(clr){
            case 0:
                return Color.RED;
            case 1:
                return Color.decode("#FFA500"); //Orange
            case 2:
                return Color.YELLOW;
            case 3:
                return Color.decode("#00CF00"); //Green
            case 4:
                return Color.decode("#00D0D0"); //Cyan
            case 5:
                return Color.BLUE;
            case 6:
                return Color.decode("#9400D3"); //Violet
            case 7:
                return Color.decode("#A0522D"); //Brown
            case 8:
                return Color.BLACK;
            case 9:
                return Color.GRAY;
            default:
                System.err.println("Colour out of bound exeption: "+clr+" instead of maximum 9.\n");
                return Color.RED;
        }
    }
    
    /**
     * Sets the player's {@link java.awt.Color colours} based on the colour-number.
     * @param colourNumber1 number of the colour between 0 and 9 for {@link #player1}.
     * @param colourNumber2 number of the colour between 0 and 9 for {@link #player2}.
     * @see #colourNumber1
     * @see #colourNumber2
     * @see #colour1
     * @see #colour2
     * @see Interf#colourOptions() 
     */
    public void setPlayersColours(int colourNumber1, int colourNumber2){
        Color c1 = intToColour(colourNumber1);
        Color c2 = intToColour(colourNumber2);
        for(int x=0; x<7; ++x){
            for(int y=0; y<6; ++y){
                if(chip[x][y] != null){
                    if(chip[x][y] == this.colourNumber1){
                        chip[x][y] = colourNumber1;
                    }
                    else{if(chip[x][y] == this.colourNumber2){
                        chip[x][y] = colourNumber2;
                    }}
                }
            }
        }
        this.colourNumber1 = colourNumber1;
        this.colourNumber2 = colourNumber2;
        colour1 = c1;
        colour2 = c2;
        interf.setChip(chip);
    }
    
    /**
     * Sets the player's names.
     * @param name1 name of {@link #player1}.
     * @param name2 name of {@link #player2}.
     * @see Interf#nameOptions() 
     */
    public void setPlayersNames(String name1, String name2){
        player1 = name1;
        player2 = name2;
    }    
    
    /**
     * Refreshes the {@link #interf interface}.
     */
    public void refresh(){
        interf.setChip(chip);
    }
    
    /**
     * Empties the grid for a new round of playing.
     */
    public void reset(){
        for(int x=0; x<7; ++x){
            for(int y=0; y<6; ++y){
                chip[x][y] = null;
            }
            interf.setEnableButton(x, true);
        }
        if(noOneHasWon || !losersStarts){
            turn1 = Math.random()<0.5;
        }
        noOneHasWon = true;
        numberOfFullColumns = 0;
        winner = 0;
        interf.setChip(chip);
    }
}