package com.hyena.todolist.model;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author k1715939
 */
public class Task {
    
    private String description;
    private Date completionDate;
    private int priorityOrder;
    private User user;
    private boolean isComplete;
    private ArrayList<Subtask> subtasks;

    public Task(String description, Date completionDate, int priorityOrder, User user, ArrayList<Subtask> subtasks) {
        this.description = description;
        this.completionDate = completionDate;
        this.priorityOrder = priorityOrder;
        this.user = user;
        this.subtasks = subtasks;
        this.isComplete = false;
    }

    public Task() {
        this.subtasks = new ArrayList<Subtask>();
    }
    
    public String getDescription(){
        return this.description;
    }
    public Date getDate(){
        return this.completionDate;
    }
    public int getPriorityOrder(){
        return this.priorityOrder;
    }

    public ArrayList<Subtask> getSubtasks() {
        return subtasks;
    }

    public void setSubtasks(ArrayList<Subtask> subtasks) {
        this.subtasks = subtasks;
    }
    
    public User getUser(){
        return this.user;
    }
    
    public void setUser(User u){
        this.user = u;
    }
    
    public void setDescription(String d){
        this.description = d;
    }
    public void setCompletionDate(Date cd){
        this.completionDate = cd;
    }
    public void setPriorityOrder(int po){
        this.priorityOrder = po;
    }
    
    public void removeSubtask(int index){
        this.subtasks.remove(index);
    }
    
     public void addSubtask(Subtask s){
         System.out.println(s.toString());
        this.subtasks.add(s);
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setIsComplete(boolean isComplete) {
        this.isComplete = isComplete;
    }
    
    @Override
    public String toString(){
        String commonString = this.getDescription() + " " + this.getDate();
        if (this.priorityOrder == -1){
            commonString += " - assigned to " + this.getUser().getName();
        } else {
            commonString += " (Priority: " + this.getPriorityOrder() + ") - assigned to " + this.getUser().getName();
        }
        
        if (this.isComplete){
            commonString += " - Task Complete";
        }
        
        return commonString;
    }
}
