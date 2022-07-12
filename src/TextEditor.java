import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import java.util.Scanner;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;

/**
 * The Text Editor
 * 
 * @author Israel Hernandez Dorantes
 * @version 1.0
 * @since July 2022
 */
public class TextEditor extends JFrame {

    /** Text area */
    JTextArea textArea;

    /** Scroll */
    JScrollPane scrollPane;

    /** Font spinner */
    JSpinner fontSpinner;

    /** Font label */
    JLabel fontLabel;

    /** Font color button */
    JButton fontColorButton;

    /** Font box */
    JComboBox fontBox;

    /** Menu bar */
    JMenuBar menuBar;

    /** File menu */
    JMenu fileMenu;

    /** Open item */
    JMenuItem openItem;

    /** Save item */
    JMenuItem saveItem;

    /** Exit item */
    JMenuItem exiItem;


    /**
     * Constructor
     */
    public TextEditor(){

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Copy & Paste: Text Editor");
        this.setSize(650,650);
        this.setResizable(false);
        this.setLayout(new FlowLayout());
        this.setLocationRelativeTo(null);
        this.setBackground(Color.DARK_GRAY);
        
        ImageIcon image = new ImageIcon("../img/Icon.png");
        this.setIconImage(image.getImage());
        
        /* Text area */
        buildTextArea();
        
        /* Scroll pane */
        buildScrollPane();
    
        /* Font spinner */
        buildFontSpinner();
        
        /* Font color button */
        buildFontColorButton();

        /* Font box */
        buildFontBox();

        /* Menu bar */
        buildMenuBar();

        this.setJMenuBar(menuBar);
        this.add(fontLabel);
        this.add(fontSpinner);
        this.add(fontColorButton);
        this.add(fontBox);
        this.add(scrollPane);
        this.setVisible(true);
    }



    /**
     * Builds the text area
     */
    public void buildTextArea(){

        textArea = new JTextArea();
        textArea.setFont(new Font("Arial", Font.PLAIN, 20));
        
        //Salto de linea
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        this.add(textArea); 

    }

    /**
     * Builds the scroll pane
     */
    public void buildScrollPane(){

        scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(550, 550));
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

    }

    /**
     * Builds the font spinner
     */
    public void buildFontSpinner(){

        fontSpinner = new JSpinner();
        fontSpinner.setValue(20); //Default size 20
        fontSpinner.setPreferredSize(new Dimension(55,30));
        //Action
        fontSpinner.addChangeListener(new ChangeListener() {
            
            //Changes the font size
            @Override
            public void stateChanged(ChangeEvent e){
                
                textArea.setFont(new Font(textArea.getFont().getFamily(), Font.PLAIN, (int)fontSpinner.getValue()));
            }
        });
        
        fontLabel = new JLabel("Size: ");

    }

    /**
     * Builds the font color button
     */
    public void buildFontColorButton(){

        fontColorButton = new JButton("Color");
        //Action
        fontColorButton.addActionListener(e -> {

            Color newColor = JColorChooser.showDialog(null, "Choose a color", Color.black);
            textArea.setForeground(newColor);
        });

    }

    /**
     * Builds the font box
     */
    public void buildFontBox(){

        // Getting all the fonts that the computer supports
        String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        fontBox = new JComboBox<String>(fonts);
        fontBox.setSelectedItem("Arial");//Default font
        //Action
        fontBox.addActionListener(e -> {
            textArea.setFont(new Font(fontBox.getSelectedItem().toString(), Font.PLAIN, textArea.getFont().getSize()));
        });
        
    }

    /**
     * Builds the menu bar
     */
    public void buildMenuBar(){

        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");
        openItem = new JMenuItem("Open...");
        saveItem = new JMenuItem("Save");
        exiItem = new JMenuItem("Exit");

        //Actions
        openItem.addActionListener(e -> {

            JFileChooser fileChooser = new JFileChooser();
            //default directory: actual project
            fileChooser.setCurrentDirectory(new File("."));

            //Adds a filter for txt files
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Text files", "txt");
            fileChooser.setFileFilter(filter);

            int resp = fileChooser.showOpenDialog(null);

            if(resp == JFileChooser.APPROVE_OPTION){

                File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                Scanner sc = null;

                try{
                    sc = new Scanner(file);//To read de file

                    while(file.isFile() && sc.hasNextLine()){

                        String line = sc.nextLine() + "\n";
                        textArea.append(line);
                    }
                
                }catch(FileNotFoundException fnfe){

                    System.out.println("File not Found!!");
                }finally{
                    sc.close();
                }

            }
        });

        saveItem.addActionListener(e -> {

            JFileChooser fileChooser = new JFileChooser();
            //default directory: actual project
            fileChooser.setCurrentDirectory(new File("."));

            int resp = fileChooser.showSaveDialog(null);

            if(resp == JFileChooser.APPROVE_OPTION){
                File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                PrintWriter writer = null;

                try{
                    writer = new PrintWriter(file);
                    writer.println(textArea.getText());

                }catch(FileNotFoundException fnfe){

                    System.out.println("File not found!");
                }finally{

                    writer.close();
                }
            }

        });

        exiItem.addActionListener(e -> {
            System.exit(0);
        });


        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.add(exiItem);
        menuBar.add(fileMenu);

    }


}