package com.hyena.todolist.controller;

import com.hyena.todolist.model.Subtask;
import com.hyena.todolist.model.Task;
import com.hyena.todolist.model.User;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author k1715939
 */
public class ToDoList {
    //
    //Add error checking for index's i.e - trying to remove a task/subtask that does not exist.
    //
    private ArrayList<Task> tasks;
    
    public ToDoList(ArrayList<Task> t){
        this.tasks = t;
    }
    
    public void addTask(Task t){
        tasks.add(t);
    }
    
    public void addSubTask(Subtask s, int index){
        System.out.println("-" + index);
        this.tasks.get(index-1).addSubtask(s);
    }
    
    public void removeTask(int index){
        tasks.remove(index-1);
    }
    
    public void removeSubtaskFromTask(int indexTask, int indexSubtask){
       this.tasks.get(indexTask-1).removeSubtask(indexSubtask-1);
    }
    
    public ArrayList<Task> getAllTasks(){
        ArrayList<Task> temp = new ArrayList();
        for(Task task : tasks){
            temp.add(task);
        }
        return temp;
    }
    
    public String getTaskAtIndex(int index){
        return tasks.get(index-1).toString();
    }
    
    public void editTaskDescription(int indexTask, String temp){
        this.tasks.get(indexTask-1).setDescription(temp);
    }
    
    public void editSubTaskDescription(int indexTask, int indexSubTask, String temp){
        this.tasks.get(indexTask-1).getSubtasks().get(indexSubTask-1).setDescription(temp);
    }
    
    public void editSubTaskPriority(int indexTask, int indexSubTask, int temp){
        this.tasks.get(indexTask-1).getSubtasks().get(indexSubTask-1).setPriorityOrder(temp);
    }
    public void editSubTaskDate(int indexTask, int indexSubTask,Date temp){
        this.tasks.get(indexTask-1).getSubtasks().get(indexSubTask-1).setCompletionDate(temp);
    }
    
    public ArrayList<Subtask> getAllSubTaskFromTask(int index){
        ArrayList<Subtask> temp = new ArrayList();
        for(Subtask subtask : tasks.get(index-1).getSubtasks()){
            temp.add(subtask);
        }
        return temp;
    }
    
    public void removeUser(int index){
        tasks.get(index-1).setUser(new User("unassigned", "unassigned", -1));
    }
    
    public void removePriority(int indexTask, int indexSubtask, int type){
        //1 -> Task
        //2 -> Subtask
        if(type == 1){
            tasks.get(indexTask-1).setPriorityOrder(-1);
        }
        else if(type == 2){
            tasks.get(indexTask-1).getSubtasks().get(indexSubtask-1).setPriorityOrder(-1);
        }
        
    }
    
    public void setUsername(int indexTask, String n){
        tasks.get(indexTask-1).getUser().setName(n);
    }
    
    public void setUserPassword(int indexTask, String p){
        tasks.get(indexTask-1).getUser().setPassword(p);
    }
    
    public void setUserLevel(int indexTask, int l){
        tasks.get(indexTask-1).getUser().setUserLevel(l);
    }
    
    public void setComplete(int indexTask){
        tasks.get(indexTask-1).setIsComplete(true);
    }
    
    public void setIncomplete(int indexTask){
        tasks.get(indexTask-1).setIsComplete(false);
    }
    
    public int getSubtaskSize(int index){
        ArrayList<Subtask> temp = tasks.get(index-1).getSubtasks();    
        return temp.size();
    }
    
    public int getTaskSize(int index){    
        return this.tasks.size();
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }
}
