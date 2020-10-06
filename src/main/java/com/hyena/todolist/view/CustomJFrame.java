package com.hyena.todolist.view;

import com.dropbox.core.DbxException;
import com.hyena.todolist.controller.ConsoleProcessor;
import com.hyena.todolist.controller.DataPersistance;
import com.hyena.todolist.controller.FileStorage;
import com.hyena.todolist.controller.ToDoList;
import com.hyena.todolist.model.Task;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

/**
 *
 * @author James
 */
public class CustomJFrame extends JFrame implements WindowListener {

    //constructor
    private static int stage = 0;
    private static int iteration = 0;
    private static ArrayList<String> temp = new ArrayList<String>();
    private int prevCLocation = 0;
    private ConsoleProcessor cp;

    //this is used for the location of the previous command as a pointer for the arraylist
    public CustomJFrame() throws IOException {

        cp = new ConsoleProcessor();

        //mainwindow frame
        JFrame mainWindow = new JFrame("To Do List");
        mainWindow.setLayout(new BorderLayout());

        //add menus as follows:
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem menuSave = new JMenuItem("Save to file");
        JMenuItem menuLoad = new JMenuItem("Load from file");
        JMenuItem menuLoadFromWeb = new JMenuItem("Load from Webservice");
        JMenuItem menuLoadDropBox = new JMenuItem("Load from Dropbox");
        JMenuItem menuSaveDropBox = new JMenuItem("Save to Dropbox");
        JMenuItem menuQuit = new JMenuItem("Quit");

        //declaration of console, output box
        Console console = new Console();
        console.println("On Startup: Loaded from Webservice \nType 'help' for full list of commands \n");

        menuSave.addActionListener((ActionEvent e) -> {
            try {
                DataPersistance dp = new DataPersistance();
                FileStorage fs = new FileStorage();
                fs.setCurrentTDL(cp.getTodolist().getAllTasks());
                fs.saveFile();
                console.println("Saved to file (data.json) \n");
            } catch (IOException ex) {
                Logger.getLogger(CustomJFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        menuSaveDropBox.addActionListener((ActionEvent e) -> {
            Thread thread = new Thread() {
                public void run() {
                    System.out.println("Thread Running");
                    DataPersistance dp = new DataPersistance();
                    FileStorage fs = new FileStorage();
                    try {
                        fs.setCurrentTDL(cp.getTodolist().getAllTasks());
                        fs.saveFileDropBox();
                        dp.saveToDropBox();
                        console.println("Saved to Dropbox (Dropbox.json) \n");
                    } catch (IOException ex) {
                        Logger.getLogger(CustomJFrame.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (NullPointerException ex) {
                        System.out.println(ex);
                    } catch (DbxException ex) {
                        Logger.getLogger(CustomJFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                }
            };
            thread.start();
        });

        menuLoad.addActionListener((ActionEvent e) -> {
            try {
                DataPersistance dp = new DataPersistance();
                FileStorage fs = new FileStorage();
                cp = new ConsoleProcessor(new ToDoList(fs.loadFile()));
                console.println("Loaded from File (data.json) \n");
            } catch (IOException ex) {
            }
            
        });

        menuLoadDropBox.addActionListener((ActionEvent e) -> {
            Thread thread = new Thread() {
                public void run() {
                    System.out.println("Thread Running");
                    try {
                        DataPersistance dp = new DataPersistance();
                        ArrayList<Task> temp = dp.loadFromDropBox();
                        System.out.println(temp.size());
                        ToDoList tdl = new ToDoList(temp);
                        cp = new ConsoleProcessor(tdl);
                        console.println("Loaded from Dropbox (data.json) \n");
                    } catch (IOException ex) {
                    }catch (NullPointerException ex) {
                        System.out.println(ex);
                    } 
                    catch (DbxException ex) {
                        Logger.getLogger(CustomJFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            };
            thread.start();

        });

        menuLoadFromWeb.addActionListener((ActionEvent e) -> {
            try {
                DataPersistance dp = new DataPersistance();
                cp = new ConsoleProcessor();
                console.println("Loaded from Webservice (Nooblab.com) \n");
            } catch (IOException ex) {
                Logger.getLogger(CustomJFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        menuQuit.addActionListener((ActionEvent e) -> {
            System.exit(0);
        });

        fileMenu.add(menuSave);
        fileMenu.add(menuLoad);
        fileMenu.add(menuSaveDropBox);
        fileMenu.add(menuLoadDropBox);
        fileMenu.add(menuLoadFromWeb);
        fileMenu.add(menuQuit);
        menuBar.add(fileMenu);
        mainWindow.setJMenuBar(menuBar);

        //declaration of textfield, used as command input
        JTextField textField = new JTextField();

        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (textField.getText().length() >= 64) // limit of textfield to 64 characters
                {
                    e.consume();
                }
            }
        });

        ArrayList<String> prevCommands = new ArrayList();
        prevCommands.add("");
        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent event) {

                if (event.getKeyCode() == KeyEvent.VK_ENTER) {
                    //output text to console, then wipe current text
                    String temp = textField.getText().trim();
                    System.out.println(temp);
                    if (temp.equalsIgnoreCase("add task") || CustomJFrame.stage == 1) {
                        prevCommands.add(temp);
                        CustomJFrame.stage = 1;
                        if (CustomJFrame.iteration == 0) {
                            CustomJFrame.temp.add("add task");
                            console.println("Description:");
                            textField.setText("");
                            CustomJFrame.iteration++;
                        } else if (CustomJFrame.iteration == 1) {
                            CustomJFrame.temp.add(textField.getText().trim());
                            console.println(textField.getText().trim() + "\n");
                            console.println("Name:");
                            textField.setText("");
                            CustomJFrame.iteration++;
                        } else if (CustomJFrame.iteration == 2) {
                            CustomJFrame.temp.add(textField.getText().trim());
                            console.println(textField.getText().trim() + "\n");
                            console.println("Date (YYYY-MM-DD):");
                            textField.setText("");
                            CustomJFrame.iteration++;
                        } else if (CustomJFrame.iteration == 3) {
                            CustomJFrame.temp.add(textField.getText().trim());
                            console.println(textField.getText().trim() + "\n");
                            console.println("Priority:");
                            textField.setText("");
                            CustomJFrame.iteration++;
                        } else if (CustomJFrame.iteration == 4) {
                            CustomJFrame.temp.add(textField.getText().trim());
                            console.println(textField.getText().trim() + "\n");
                            textField.setText("");
                            console.println(cp.sendMultipleCommand(CustomJFrame.temp));

                            CustomJFrame.stage = 0;
                            CustomJFrame.iteration = 0;
                            CustomJFrame.temp.clear();
                        }
                    } else if (temp.equalsIgnoreCase("add subtask") || CustomJFrame.stage == 2) {
                        prevCommands.add(temp);
                        CustomJFrame.stage = 2;
                        if (CustomJFrame.iteration == 0) {
                            CustomJFrame.temp.add("add subtask");
                            console.println("Task Number:");
                            textField.setText("");
                            CustomJFrame.iteration++;
                        } else if (CustomJFrame.iteration == 1) {
                            CustomJFrame.temp.add(textField.getText().trim());
                            console.println(textField.getText().trim() + "\n");
                            console.println("Description:");
                            textField.setText("");
                            CustomJFrame.iteration++;
                        } else if (CustomJFrame.iteration == 2) {
                            CustomJFrame.temp.add(textField.getText().trim());
                            console.println(textField.getText().trim() + "\n");
                            console.println("Date (YYYY-MM-DD):");
                            textField.setText("");
                            CustomJFrame.iteration++;
                        } else if (CustomJFrame.iteration == 3) {
                            CustomJFrame.temp.add(textField.getText().trim());
                            console.println(textField.getText().trim() + "\n");
                            console.println("Priority:");
                            textField.setText("");
                            CustomJFrame.iteration++;
                        } else if (CustomJFrame.iteration == 4) {
                            CustomJFrame.temp.add(textField.getText().trim());
                            console.println(textField.getText().trim() + "\n");
                            textField.setText("");
                            console.println(cp.sendMultipleCommand(CustomJFrame.temp));

                            CustomJFrame.stage = 0;
                            CustomJFrame.iteration = 0;
                            CustomJFrame.temp.clear();
                        }
                    } else if (temp.equalsIgnoreCase("search") || CustomJFrame.stage == 3) {
                        prevCommands.add(temp);
                        CustomJFrame.stage = 3;
                        if (CustomJFrame.iteration == 0) {
                            CustomJFrame.temp.add("search");
                            console.println("Attribute: \n 1) Description \n 2) User \n 3) Priority \n 4) Date \n Option:");
                            textField.setText("");
                            CustomJFrame.iteration++;
                        } else if (CustomJFrame.iteration == 1) {
                            CustomJFrame.temp.add(textField.getText().trim());
                            console.println(textField.getText().trim());
                            console.println("Query:");
                            textField.setText("");
                            CustomJFrame.iteration++;
                        } else if (CustomJFrame.iteration == 2) {
                            CustomJFrame.temp.add(textField.getText().trim());
                            console.println(textField.getText().trim() + "\n");
                            textField.setText("");
                            console.println(cp.sendMultipleCommand(CustomJFrame.temp));

                            CustomJFrame.stage = 0;
                            CustomJFrame.iteration = 0;
                            CustomJFrame.temp.clear();
                        }
                    } else if (temp.equalsIgnoreCase("sort") || CustomJFrame.stage == 4) {
                        prevCommands.add(temp);
                        CustomJFrame.stage = 4;
                        if (CustomJFrame.iteration == 0) {
                            CustomJFrame.temp.add("sort");
                            console.println("Sort type: \n 1) Description \n 2) Priority \n 3) User \n 4) Date \n 5) Completed Tasks");
                            textField.setText("");
                            CustomJFrame.iteration++;
                        } else if (CustomJFrame.iteration == 1) {
                            CustomJFrame.temp.add(textField.getText().trim());
                            console.println("\n Option " + textField.getText().trim() + " selected. \n");
                            textField.setText("");
                            console.println(cp.sendMultipleCommand(CustomJFrame.temp));

                            CustomJFrame.stage = 0;
                            CustomJFrame.iteration = 0;
                            CustomJFrame.temp.clear();
                        }
                    } else {
                        console.println(cp.sendCommand(textField.getText().trim()));
                        prevCommands.add(textField.getText());
                        textField.setText("");
                    }
                }

            }
        });

        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent event) {
                try {
                    if (event.getKeyCode() == KeyEvent.VK_UP) {
                        textField.setText(prevCommands.get(getPrevCLocation()));
                        setPrevCLocation(getPrevCLocation() + 1);
                        //would return the previous command sent by user
                    }
                } catch (IndexOutOfBoundsException oob) {
                    setPrevCLocation(0);
                }
            }
        });

        //mainWindow added elements
        mainWindow.add(console, BorderLayout.CENTER);
        mainWindow.add(textField, BorderLayout.SOUTH);

        //essential window constraints (size, etc.)
        mainWindow.setSize(800, 600);
        mainWindow.setResizable(true);
        mainWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainWindow.setVisible(true);
        textField.requestFocusInWindow();
    }

    public int getPrevCLocation() {
        return prevCLocation;
    }

    public void setPrevCLocation(int prevCLocation) {
        this.prevCLocation = prevCLocation;
    }

    //implementing windowListener means we need these from an abstract class, not used but require for build
    @Override
    public void windowOpened(WindowEvent we) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void windowClosing(WindowEvent we) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void windowClosed(WindowEvent we) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void windowIconified(WindowEvent we) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void windowDeiconified(WindowEvent we) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void windowActivated(WindowEvent we) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void windowDeactivated(WindowEvent we) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
