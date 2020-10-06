package com.hyena.todolist.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.hyena.todolist.model.Task;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;

/**
 *
 * @author k1625607
 */
public class FileStorage {
    private ArrayList<Task> currentTDL = new ArrayList();
    private File file = new File("data.json");
    private Gson gson = new Gson();
    
    public ArrayList<Task> loadFile() throws FileNotFoundException, IllegalStateException, IOException{
        JsonReader reading = new JsonReader(new FileReader(this.file));
        reading.beginArray();
        ArrayList<Task> tasksArr = new ArrayList();
        while(reading.hasNext()){
            tasksArr.add(gson.fromJson(reading,Task.class));
        }
        reading.endArray();
        //there is no check for if the file exists
        this.setCurrentTDL(tasksArr);
        return tasksArr;
    }
    
    public ArrayList<Task> loadFileDropBox() throws FileNotFoundException, IllegalStateException, IOException{
        JsonReader reading = new JsonReader(new FileReader(new File("Dropbox.json")));
        reading.beginArray();
        ArrayList<Task> tasksArr = new ArrayList();
        while(reading.hasNext()){
            tasksArr.add(gson.fromJson(reading,Task.class));
        }
        reading.endArray();
        //there is no check for if the file exists
        this.setCurrentTDL(tasksArr);
        return tasksArr;
    }
    
    public void saveFile() throws IOException{
        try(Writer write = new FileWriter(this.file)){
            Gson gsonb = new GsonBuilder().create();
            Task[] Tasks = new Task[this.getCurrentTDL().size()];
            for (int i = 0; i < this.getCurrentTDL().size(); i++){
                Tasks[i] = this.getCurrentTDL().get(i);
            }
            gsonb.toJson(Tasks, write);
            write.close();
        }
    }
    
    public void saveFileDropBox() throws IOException{
        try(Writer write = new FileWriter(new File("Dropbox.json"))){
            Gson gsonb = new GsonBuilder().create();
            Task[] Tasks = new Task[this.getCurrentTDL().size()];
            for (int i = 0; i < this.getCurrentTDL().size(); i++){
                Tasks[i] = this.getCurrentTDL().get(i);
            }
            gsonb.toJson(Tasks, write);
            write.close();
        }
    }
    
    public ArrayList<Task> getCurrentTDL() {
        return this.currentTDL;
    }

    public void setCurrentTDL(ArrayList<Task> currentTDL) {
        this.currentTDL = currentTDL;
    }
    
}
