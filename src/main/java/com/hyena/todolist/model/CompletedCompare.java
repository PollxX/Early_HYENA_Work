/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hyena.todolist.model;

import java.util.Comparator;

/**
 *
 * @author k1715939
 */
public class CompletedCompare implements Comparator<Task>{

    @Override
    public int compare(Task o1, Task o2) {
        if(!o1.isComplete() && o2.isComplete()){
            return -1;
        }
        if(o1.isComplete() && !o2.isComplete()){
            return 1;
        }
        else return 0;
    }
    
}
