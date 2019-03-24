//

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import javax.swing.BorderFactory;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class THELABYRINTH extends JApplet {

//ROW AND COLS
static int r = 0;
static int c = 0;
static int[][] table;

//AREA TABLE
private JLabel[][] area = new JLabel[r][c];

//BOOLEANS
private boolean click = false;

//JPANELS
private JPanel jPanel1 = new JPanel();
private JPanel PinkJPanel = new JPanel();
private JPanel OrangeJPanel = new JPanel();
private JPanel BlueJPanel = new JPanel();

//JLABELS, GRIDLAYOUT, AND JTEXTFIELD
private JLabel CounterLabel = new JLabel();
private JLabel DimensionsLabel = new JLabel();
private JLabel OccupiedRoomsLabel = new JLabel();
private JLabel NumberofPathsLabel = new JLabel();
private static GridLayout Grid = new GridLayout();
private JTextField EnteredSteps;

//INTS
private int AmountofSteps = -1;
private int Counter = 0;
private static int MazeNumber = 0;
private static int OccupiedRooms = 0;
private static int NumberofPaths = 0;

//INITIALIZE THE APPLET
/********************************************************
*Purpose:   Initializing applet and all its features
*In:        None
*Out:       None
********************************************************/
@Override //Overrides parent class
    public void init() {
    try {
        this.setSize(new Dimension(530, 420));
        jPanel1.setLayout(null);

        //This controls the blue background
        BlueJPanel.setBackground(new Color(70, 130, 180));
        BlueJPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        BlueJPanel.setBounds(new Rectangle(14, 8, 506, 350));
        BlueJPanel.setLayout(null);

        //This controls the pink box and adds the grid to it
        PinkJPanel.setBackground(new Color(250, 128, 114));
        PinkJPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        PinkJPanel.setBounds(new Rectangle(16, 11, 472, 325));
        PinkJPanel.setLayout(Grid);

        //Setting up the gird, row and columns are read from file
        //File is loaded in main method
        Grid.setHgap(0);
        Grid.setVgap(0);
        Grid.setColumns(c);
        Grid.setRows(r);

        //Pink background for the buttons on the bottom
        OrangeJPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        OrangeJPanel.setBackground(new Color(250, 128, 114));
        OrangeJPanel.setBounds(new Rectangle(14, 365, 506, 39));
        OrangeJPanel.setLayout(null);

        //The label for maze number
        CounterLabel.setBounds(new Rectangle(5, 5, 65, 30));
        CounterLabel.setPreferredSize(new Dimension(4, 4));
        CounterLabel.setBackground(Color.white);
        CounterLabel.setOpaque(true);
        CounterLabel.setBorder(BorderFactory.createLineBorder(Color.black));
        CounterLabel.setHorizontalAlignment(SwingConstants.CENTER);
        CounterLabel.setFont(new java.awt.Font("Bookman Old Style", 5, 13));
        CounterLabel.setText("Maze: " + MazeNumber);
        
        //The label for dimensions of maze
        DimensionsLabel.setBounds(new Rectangle(73, 5, 100, 30));
        DimensionsLabel.setPreferredSize(new Dimension(4, 4));
        DimensionsLabel.setBackground(Color.white);
        DimensionsLabel.setOpaque(true);
        DimensionsLabel.setBorder(BorderFactory.createLineBorder(Color.black));
        DimensionsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        DimensionsLabel.setFont(new java.awt.Font("Bookman Old Style", 5, 13));
        DimensionsLabel.setText("Size: " + r + " by " + c);

        //The label for amount occupied rooms
        OccupiedRoomsLabel.setBounds(new Rectangle(175, 5, 145, 30));
        OccupiedRoomsLabel.setPreferredSize(new Dimension(4, 4));
        OccupiedRoomsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        OccupiedRoomsLabel.setBackground(Color.white);
        OccupiedRoomsLabel.setOpaque(true);
        OccupiedRoomsLabel.setBorder(BorderFactory.createLineBorder(Color.black));
        OccupiedRoomsLabel.setFont(new java.awt.Font("Bookman Old Style", 5, 13));
        OccupiedRoomsLabel.setText("Occupied Rooms: " + OccupiedRooms );

        //The label for number of paths
        NumberofPathsLabel.setBounds(new Rectangle(322, 5, 180, 30));
        NumberofPathsLabel.setPreferredSize(new Dimension(4, 4));
        NumberofPathsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        NumberofPathsLabel.setBackground(Color.white);
        NumberofPathsLabel.setOpaque(true);
        NumberofPathsLabel.setBorder(BorderFactory.createLineBorder(Color.black));
        NumberofPathsLabel.setFont(new java.awt.Font("Bookman Old Style", 5, 13));
        NumberofPathsLabel.setText("Number of Paths: " + NumberofPaths);
        
        //DRAWING JPANES 
        jPanel1.setBorder(BorderFactory.createLineBorder(Color.black));
        this.getContentPane().add(jPanel1, BorderLayout.CENTER);

        OrangeJPanel.add(OccupiedRoomsLabel, null);
        OrangeJPanel.add(CounterLabel, null);
        OrangeJPanel.add(DimensionsLabel, null);
        OrangeJPanel.add(NumberofPathsLabel, null);

        jPanel1.add(BlueJPanel, null);
        BlueJPanel.add(PinkJPanel, null);
        jPanel1.add(OrangeJPanel, null);

        for (int i = 0; i < area.length; i++) {
            for (int j = 0; j < area[0].length; j++) {
                area[i][j] = new JLabel();
                area[i][j].setBackground(Color.white);
                area[i][j].setOpaque(true);
                area[i][j].setBorder(BorderFactory.createLineBorder(Color.black));
                PinkJPanel.add(area[i][j]);

                if (table[i][j] == -1) {
                    area[i][j].setBackground(Color.gray);
                } //End if
                if (table[i][j] == 0) {
                    area[i][j].setBackground(Color.white);
                } //End if
                if (r - i == 1 && c - j == 1) {
                    area[i][j].setBackground(Color.red);
                } //End if

            } //End for int j
        } //End for int i
            
        } //End try statement
        catch (Exception e) {
            e.printStackTrace();
        } //End catch statement
} //Closing bracket for init()

//MAIN METHOD
public static void main(String[] args) throws IOException, InterruptedException {
    
ProgramInfo programInfo = new ProgramInfo();
System.out.println(programInfo.getBanner("Labyrinth"));

BufferedReader fin = null;  //Declaring fin as BufferedReader
String delim = "[ ]+";      //Delimiter string for splitting input string
String[] tokens;            //Array for splitting strings

int a = 0;
int b = 0;

//Try to read file
try {
    fin = new BufferedReader(new FileReader("labyrinthData.txt"));
} //End try statement 
catch (FileNotFoundException e) {
    System.out.println("file not found");
} //End catch statement

int Counter = 0;
String strin = fin.readLine();
while (strin != null) {
    System.out.println("******************************************");
    MazeNumber++;
    Counter = 0;
    System.out.println("I am reading in the following: " + strin);
    tokens = strin.split(delim);

    //Trying to read + convert dimensions 
    try {
        r = Integer.parseInt(tokens[0]);
        c = Integer.parseInt(tokens[1]);
    } //End try statement
    catch (NumberFormatException e) {
        System.out.println("Please check values in data file");
        return;
    } //End catch statement

    table = new int[r][c]; //Creating 2d array with dimensions corresponding to maze's

    //Amount of rooms occupied
    OccupiedRooms = Integer.parseInt(fin.readLine());
    System.out.println("Amount of Occupied Rooms: " + OccupiedRooms);
    strin = fin.readLine();

    //Reading in which rooms are occupied
    while (strin != null && Counter < OccupiedRooms) {
        Counter++;
        tokens = strin.split(delim);
        try {
            a = Integer.parseInt(tokens[0]);
            b = Integer.parseInt(tokens[1]);
        } //End try statement
        catch (NumberFormatException e) {
            System.out.println("Please check values in data file");
        return;
        } //End catch statement
        table[a - 1][b - 1] = -1;
        strin = fin.readLine();
    } //End while loop

    //Loading 2d array
    for (int i = 0; i < r; i++) {
        for (int j = 0; j < c; j++) {
            if (table[i][j] == -1) {
            } //End if
            else {
                table[i][j] = 0;
            } //End else
            if (c - j == 1) {
                //System.out.println(table[i][j]);
            } //End if
            else {
                //System.out.print(table[i][j]);
            } //End else
            
        } //End for int j 
    } //End for int i
    
    //Counting paths (see method below)
    NumberofPaths = 0;
    NumberofPaths = countPaths(table);
    System.out.println("Amount of Paths: " + NumberofPaths);
    
    //Applet
    THELABYRINTH applet = new THELABYRINTH();
    JFrame frame = new JFrame();
    frame.setDefaultCloseOperation(3);
    frame.setTitle("*****ERIKA'S LABYRINTH SOLVER*****");
    frame.getContentPane().add(applet, BorderLayout.CENTER);
    frame.setSize(550, 450);
    Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    frame.setLocation((d.width - frame.getSize().width) / 2, (d.height - frame.getSize().height) / 2);
    applet.init();
    applet.start();
    frame.setVisible(true);
    TimeUnit.SECONDS.sleep(3); //time delay
    frame.setVisible(false);
    
} //End of while loop

System.exit(0);
    
} //End main

/********************************************************
*Purpose:   Method that counts amount of paths
*In:        maze (two dimensional array of integers)
*Out:       the amount of paths the maze has
********************************************************/
static int countPaths(int maze[][]) { 
    //If initial room is zero, cannot move anywhere
    //Which means no escape route is possible
    if (maze[0][0]==-1) {
        return 0; 
    } //End if first room is occupied
      
    //Leftmost column 
    for (int i = 0; i < r; i++) { 
        if (maze[i][0] == 0) {
            maze[i][0] = 1; 
        } //End if statement
        
        //If a room is occupied in the leftmost column:
        //You cannot go to rooms below it in same column
        else {
            i = r;
            //I used r instead of break :)
        } //End else statement
    } //End for initializing leftmost column
      
    //Topmost row 
    for (int i =1 ; i < c ; i++) { 
        if (maze[0][i] == 0) {
            maze[0][i] = 1; 
        } //End if statement
    
        //If room is occupied in top row:
        //Cannot visit room directly below it as easily...
        else {
            i = c;
        } //End else statement
    } //End for initializing topmost row
      
    //Compute count value maze[i][j] 
    for (int i = 1; i < r; i++) { 
        for (int j = 1; j < c ; j++) { 
            //If reach occupied room try to find way around
            if (maze[i][j] == -1) {
                continue; 
            } //End if reached occupied room

            //If can get to maze[i][j] from maze[i-1][j] it counts  
            if (maze[i - 1][j] > 0) {
                maze[i][j] = (maze[i][j] +  maze[i - 1][j]); 
            } //End if this route works

            //If can get to maze[i][j] from maze[i][j-1] it counts
            if (maze[i][j - 1] > 0) {
                maze[i][j] = (maze[i][j] +  maze[i][j - 1]); 
            } //End if this route works
        } //Enf for int j
    } //End for int i
      
    //If final room is blocked, number of paths = 0 
    //If not, the answer is:
    return (maze[r - 1][c - 1] > 0) ?  
            maze[r - 1][c - 1] : 0; 
} //End counthPaths method

} //End class
