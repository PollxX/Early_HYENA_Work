package com.hyena.todolist.model;

import java.util.Date;

/**
 *
 * @author k1715939
 */
public class Subtask{

    private String description;
    private Date completionDate;
    private int priorityOrder;
    
    public Subtask(){
        
    }

    public Subtask(String description, Date completionDate, int priorityOrder) {
        this.description = description;
        this.completionDate = completionDate;
        this.priorityOrder = priorityOrder;
    }
    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(Date completionDate) {
        this.completionDate = completionDate;
    }

    public int getPriorityOrder() {
        return priorityOrder;
    }

    public void setPriorityOrder(int priorityOrder) {
        this.priorityOrder = priorityOrder;
    }

    @Override
    public String toString(){        
        if (this.priorityOrder == -1){
            return this.getDescription() + " " + this.getCompletionDate();
        }
        return this.getDescription() + " " + this.getCompletionDate() + " (Priority: " + this.getPriorityOrder() + ")";
    }
}
