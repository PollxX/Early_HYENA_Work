package com.hyena.todolist.controller;

import com.hyena.todolist.model.CompletedCompare;
import com.hyena.todolist.model.DateCompare;
import com.hyena.todolist.model.DescriptionCompare;
import com.hyena.todolist.model.PriorityCompare;
import com.hyena.todolist.model.Subtask;
import com.hyena.todolist.model.Task;
import com.hyena.todolist.model.User;
import com.hyena.todolist.model.UserCompare;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author k1715939
 */
public class ConsoleProcessor {

    private ToDoList todolist;
    private DataPersistance dp;

    public ConsoleProcessor() throws IOException {
        dp = new DataPersistance();
        this.todolist = new ToDoList(dp.load());
    }

    public ConsoleProcessor(ToDoList tdl) throws IOException {
        this.todolist = tdl;
    }

    public String sendMultipleCommand(ArrayList<String> a) {
        //try catch will prevent date format exception stopping the program run
        try{
            if(a.get(0).equalsIgnoreCase("add task")){
                Task newTask = new Task();
                newTask.setDescription(a.get(1));
                newTask.setUser(new User(a.get(2), "passsword", 1));
                String split[] = a.get(3).split("-");
                //no validation on date, may not follow regex, also no validation - will fudge up the program...
                newTask.setCompletionDate(new Date(Integer.parseInt(split[0]), Integer.parseInt(split[1])-1, Integer.parseInt(split[2])));
                newTask.setPriorityOrder(Integer.parseInt(a.get(4)));
                this.todolist.addTask(newTask);
                return "Task Successfully Added \n";
            }
            else if(a.get(0).equalsIgnoreCase("add subtask")){
                Subtask newTask = new Subtask();
                int taskNumber = Integer.parseInt(a.get(1));
                System.out.println(taskNumber);
                newTask.setDescription(a.get(2));
                String split[] = a.get(3).split("-");
                //no validation on date, may not follow regex, also no validation - will fudge up the program...
                newTask.setCompletionDate(new Date(Integer.parseInt(split[0]), Integer.parseInt(split[1])-1, Integer.parseInt(split[2])));
                newTask.setPriorityOrder(Integer.parseInt(a.get(4)));
                this.todolist.addSubTask(newTask, taskNumber);
                return "Subtask Successfully Added \n";
            } 
            //search syntax:    SEARCH ATTRIBUTE QUERY
            //complete example: search user James
            //partial example:  search description programmable
            else if (a.get(0).equalsIgnoreCase("search")){
                //description, completion, priority, user(name)
                ArrayList<Task> foundTasks = new ArrayList();
                String query = a.get(2);
                String output = "";
                //for each item found, add to arraylist of found items
                for (Task task : this.todolist.getTasks()) {
                    if (a.get(1).equalsIgnoreCase("description") || a.get(1).equalsIgnoreCase("1")){
                            //check for query as task.ATTRIBUTE.equalsIgnoreCase(query)
                        if (task.getDescription().toLowerCase().contains(query.toLowerCase())){
                            output += (task.toString() + "\n");
                            foundTasks.add(task);
                        }
                    } else if (a.get(1).equalsIgnoreCase("user") || a.get(1).equalsIgnoreCase("2")){
                        if (task.getUser().getName().toLowerCase().contains(query.toLowerCase())){
                            output += (task.toString() + "\n");
                            foundTasks.add(task);
                        }
                    } else if (a.get(1).equalsIgnoreCase("priority") || a.get(1).equalsIgnoreCase("3")){
                        if (task.getPriorityOrder() == Integer.parseInt(query)){
                            output += (task.toString() + "\n");
                            foundTasks.add(task);
                        }
                    } else if (a.get(1).equalsIgnoreCase("date") || a.get(1).equalsIgnoreCase("4")){
                        if (task.getDate().getDate() == Integer.parseInt(query)){
                            output += (task.toString() + "\n");
                            foundTasks.add(task);
                        }
                    }
                }
                //note: i might replace this with the number system that spiro used in 'sort'
                if (!foundTasks.isEmpty()){
                    return output;
                } else if (foundTasks.isEmpty()){
                    if (a.get(1).equalsIgnoreCase("description")){ 
                        return ("No description found for query '"+a.get(2)+"'.");
                    } else if (a.get(1).equalsIgnoreCase("user")){ 
                        return ("No user found for query '"+a.get(2)+"'.");
                    } else if (a.get(1).equalsIgnoreCase("priority")){ 
                        return ("No priority found for query '"+a.get(2)+"'.");
                    } else if (a.get(1).equalsIgnoreCase("date")){ 
                        return ("No dates found for query '"+a.get(2)+"'.");
                    }
                }
                //else
                return "Search attribute not valid.\nValid examples: Description, Completion, Priority, User\n";
            }
            else if(a.get(0).equalsIgnoreCase("sort")){
            try{
                System.out.println("1");
                int option = Integer.parseInt(a.get(1));
                //Sort type: \n 1) Description \n 2) Priority \n 3) User \n 4) Date \n 5) Completed Tasks
                if(option == 1){
                    ArrayList<Task> tasks = todolist.getAllTasks();
                    DescriptionCompare compare = new DescriptionCompare();
                    Collections.sort(tasks, compare);
                    String temp = "\n";
                    for (int i = 0; i < tasks.size(); i++) {
                        temp += (tasks.get(i).toString() + "\n");
                    }
                    return temp;
                }
                else if(option == 2){
                    ArrayList<Task> tasks = todolist.getAllTasks();
                    PriorityCompare compare = new PriorityCompare();
                    Collections.sort(tasks, compare);
                    String temp = "\n";
                    for (int i = 0; i < tasks.size(); i++) {
                        temp += (tasks.get(i).toString() + "\n");
                    }
                    return temp;
                }
                else if(option == 3){
                    ArrayList<Task> tasks = todolist.getAllTasks();
                    UserCompare compare = new UserCompare();
                    Collections.sort(tasks, compare);
                    String temp = "\n";
                    for (int i = 0; i < tasks.size(); i++) {
                        temp += (tasks.get(i).toString() + "\n");
                    }
                    return temp;
                }
                else if(option == 4){
                    ArrayList<Task> tasks = todolist.getAllTasks();
                    DateCompare compare = new DateCompare();
                    Collections.sort(tasks, compare);
                    String temp = "\n";
                    for (int i = 0; i < tasks.size(); i++) {
                        temp += (tasks.get(i).toString() + "\n");
                    }
                    return temp;
                }
                else if(option == 5){
                    ArrayList<Task> tasks = todolist.getAllTasks();
                    CompletedCompare compare = new CompletedCompare();
                    Collections.sort(tasks, compare);
                    String temp = "\n";
                    for (int i = 0; i < tasks.size(); i++) {
                        temp += (tasks.get(i).toString() + "\n");
                    }
                    return temp;
                }
                return "Invalid sort option supplied.";
            }
            catch (IndexOutOfBoundsException e) {
                return ("Something went wrong, please try again. \n");
            }
        } 
        } catch (IndexOutOfBoundsException e){
            return "Failed to create new object: \nNote: Date: YYYY-MM-DD \n";
        } catch (NumberFormatException npe){
            return "Failed to create new object: \nPlease enter numbers where necessary. \n";
        }  
        return "";
        
    }

    public String sendCommand(String s) {
        s = s.trim(); //Removing spacing at start and beginning.
        System.out.println("In Console Processor: " + s);
        String[] split = s.split(" ");
        if (split[0].equalsIgnoreCase("list")) {
            try {
                if (split[1].equalsIgnoreCase("task")) {
                    return this.DisplayAllTasks();
                } else if (split[1].equalsIgnoreCase("subtask")) {
                    try {
                        //Need to check that the attribute index is within range to prevent error.
                        System.out.println(split[2]);

                        if (isAttrValid(Integer.parseInt(split[2]), 1)) {
                            return this.DisplayAllSubTasks(Integer.parseInt(split[2]));
                        } else {
                            return ("The index you supplied is out of range of subtasks. \n");
                        }
                    } catch (NumberFormatException nfe) {
                        return ("It appears that either subtask index is not an integer. \n");
                    } catch (IndexOutOfBoundsException e) {
                        return ("Please specify the task attribute to view all subtasks. \n");
                    }
                }
            } catch (IndexOutOfBoundsException e) {
                return ("Please enter in the correct format, 'list <task/subtask>'");
            }
        } else if (split[0].equalsIgnoreCase("remove")) {
            try {
                if (split[1].equalsIgnoreCase("subtask")) {

                    if (split[2].equalsIgnoreCase("priority")) {

                        int indexTask = Integer.parseInt(split[3]);
                        int indexSubtask = Integer.parseInt(split[3]);
                        todolist.removePriority(indexTask, indexSubtask, 2);
                        return "Successfully Removed Priorty Order.";

                    } else {
                        try {
                            int indexTask = Integer.parseInt(split[2]);
                            int indexSubtask = Integer.parseInt(split[3]);
                            if (isAttrValid(indexTask, 1) && isAttrValid(indexSubtask, 2)) {
                                todolist.removeSubtaskFromTask(indexTask, indexSubtask);
                                //return this.DisplayAllSubTasks(indexTask);
                                return "Subtask Successfully Removed.";
                            } else {
                                return ("The index you supplied is out of range. \n");
                            }
                        } catch (NumberFormatException nfe) {
                            return ("It appears that either your task or subtask index is not an integer. \n");
                        } catch (IndexOutOfBoundsException e) {
                            return ("Please specify the task attribute to view all subtasks. \n");
                        }
                    }
                } else if (split[1].equalsIgnoreCase("task")) {

                    if (split[2].equalsIgnoreCase("user")) {
                        try {
                            int indexTask = Integer.parseInt(split[3]);
                            todolist.removeUser(indexTask);
                            return "Successfully Removed User.";
                        } catch (NumberFormatException nfe) {
                            return ("It appears that either your task or subtask index is not an integer. \n");
                        } catch (IndexOutOfBoundsException e) {
                            return ("Please specify the task attribute to view all subtasks. \n");
                        }
                    } else if (split[2].equalsIgnoreCase("priority")) {
                        try {
                            int indexTask = Integer.parseInt(split[3]);
                            todolist.removePriority(indexTask, -1, 1);
                            return "Successfully Removed Priorty Order.";
                        } catch (NumberFormatException nfe) {
                            return ("It appears that either your task or subtask index is not an integer. \n");
                        } catch (IndexOutOfBoundsException e) {
                            return ("Please specify the task attribute to view all subtasks. \n");
                        }
                    } else {
                        try {
                            int indexTask = Integer.parseInt(split[2]);
                            if (isAttrValid(indexTask, 1)) {
                                todolist.removeTask(indexTask);
                                //return this.DisplayAllTasks();
                                return "Task Successfully Removed.";
                            } else {
                                return ("The index you supplied is out of range. \n");
                            }
                        } catch (NumberFormatException nfe) {
                            return ("It appears that either your task or subtask index is not an integer. \n");
                        } catch (IndexOutOfBoundsException e) {
                            return ("Please specify the task attribute to view all subtasks. \n");
                        }
                    }
                }
            } catch (NumberFormatException nfe) {
                return ("It appears that either your task or subtask index is not an integer. \n");
            } catch (IndexOutOfBoundsException e) {
                return ("Please specify the task attribute to view all subtasks. \n");
            }
        } else if (split[0].equalsIgnoreCase("edit")) {
            if (split[1].equalsIgnoreCase("task")) {
                try {
                    int indexTask = Integer.parseInt(split[2]);

                    if (split[3].equalsIgnoreCase("description")) {
                        String temp = "";
                        for (int i = 4; i < split.length; i++) {
                            temp += split[i] + " ";
                        }
                        todolist.editTaskDescription(indexTask, temp);
                        return "Successfully Edited Description.";
                    } else if (split[3].equalsIgnoreCase("user")) {
                        if (split[4].equalsIgnoreCase("name")) {
                            String temp = todolist.getAllTasks().get(indexTask - 1).getUser().getName();
                            todolist.setUsername(indexTask, split[5]);
                            return temp + "'s Username Was Successfully Updated To " + split[5];
                        } else if (split[4].equalsIgnoreCase("password")) {
                            String temp = todolist.getAllTasks().get(indexTask - 1).getUser().getName();
                            todolist.setUserPassword(indexTask, split[5]);
                            return temp + "'s Password Was Successfully Updated To " + split[5];
                        } else if (split[4].equalsIgnoreCase("level")) {
                            String temp = todolist.getAllTasks().get(indexTask - 1).getUser().getName();
                            todolist.setUserLevel(indexTask, Integer.parseInt(split[5]));
                            return temp + "'s User Level Successfully Updated To " + split[5];
                        }
                    } else if (split[3].equalsIgnoreCase("date")) {
                        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(split[4]);  
                        todolist.getAllTasks().get(indexTask-1).setCompletionDate(date);
                        return "Successfully Edited Date.";
                    } else if (split[3].equalsIgnoreCase("priority")) {
                         todolist.getAllTasks().get(indexTask-1).setPriorityOrder(Integer.parseInt(split[4]));
                         return "Successfully Edited Priority.";
                    }
                } catch (IndexOutOfBoundsException e) {
                    return ("Please specify the task attribute to view all tasks. \n");
                } catch (NumberFormatException nfe) {
                    return ("Please specify the task attribute to view all tasks. \n");
                } catch (ParseException ex) {
                    Logger.getLogger(ConsoleProcessor.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else if (split[1].equalsIgnoreCase("subtask")) {
                try {
                    int indexSubTask = Integer.parseInt(split[2]);
                    int indexTask = Integer.parseInt(split[3]);

                    if (split[4].equalsIgnoreCase("description")) {
                        String temp = "";
                        for (int i = 5; i < split.length; i++) {
                            temp += split[i] + " ";
                        }
                        todolist.editSubTaskDescription(indexTask, indexSubTask, temp);
                        return "Successfully Edited Description.";
                    }
                    else if (split[4].equalsIgnoreCase("date")) {
                        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(split[5]);  
                        todolist.editSubTaskDate(indexTask, indexSubTask, date);
                        return "Successfully Edited Date.";
                    } else if (split[4].equalsIgnoreCase("priority")) {
                        todolist.editSubTaskDescription(indexTask, indexSubTask, split[5]);
                         return "Successfully Edited Priority.";
                    }
                } catch (IndexOutOfBoundsException e) {
                    return ("Please specify the task attribute to view all tasks. \n");
                } catch (NumberFormatException nfe) {
                    return ("Please specify the task attribute to view all tasks. \n");
                } catch (ParseException ex) {
                    Logger.getLogger(ConsoleProcessor.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                return ("The 2nd attribute in the command has not been recogised. Please use 'help' to view all commands. \n");
            } 
        } else if (split[0].equalsIgnoreCase("mark")) {
            try {
                int indexTask = Integer.parseInt(split[1]);
                todolist.setComplete(indexTask);
                return ("Task: " + indexTask + " is marked as complete \n");
            } catch (IndexOutOfBoundsException e) {
                return ("Task not found. \n");
            }
        } else if (split[0].equalsIgnoreCase("unmark")) {
            try {
                int indexTask = Integer.parseInt(split[1]);
                todolist.setIncomplete(indexTask);
                return ("Task: " + indexTask + " is marked as incomplete \n");
            } catch (IndexOutOfBoundsException e) {
                return ("Task not found. \n");
            }
        } else if (split[0].equalsIgnoreCase("help")) {
            //this is just for documentation, help query for listing all commands
            String message = " list task\n list subtask [number]\n add task\n add subtask\n remove task [optional]\n remove subtask [task number] [subtask number]\n mark [task]\n unmark [task] \n edit task [task] description [field] \n edit task [task] user name [field] \n edit task [task] user password [field] \n edit task [task] user level [field] \n edit subtask [subtask number] [task number] description [field] \n edit subtask [subtask number] [task number] date [field] \n edit subtask [subtask number] [task number] priority [field]\n search\n sort\n";
            return message;
        } else {
            return ("The command you have used has not been recognised. Please use 'help' to view all commands. \n");
        }

        return ""; //returns nothing, as a catch-all
    }

    //unused?
    private boolean requiredLength(String s, int size) {
        String[] split = s.split(" ");
        if (split.length > size - 1) {
            return true;
        }
        return false;
    }

    /*Display will get moved to Console class. Currently used for testing purposes*/
    private String DisplayAllTasks() {
        ArrayList<Task> test = todolist.getAllTasks();
        String temp = "";
        //String temp = "---Tasks---" + "\n";
        for (int i = 0; i < test.size(); i++) {
            temp += ((i + 1) + ". " + test.get(i).toString() + "\n");
        }
        //temp += "---End of Tasks---";
        System.out.println(temp);
        return temp;
    }

    private String DisplayAllSubTasks(int index) {
        ArrayList<Subtask> test1 = todolist.getAllSubTaskFromTask(index);
        String temp = "  " + todolist.getTaskAtIndex(index) + "\n";
        //String temp = "---Subtasks---" + "\n";
        for (int i = 0; i < test1.size(); i++) {
            temp += ("      " + (i + 1) + ". " + test1.get(i).toString() + "\n");
        }
        //temp += "---End of Subtask---";
        System.out.println(temp);
        return temp;
    }

    private boolean isAttrValid(int index, int status) {
        //status - 1 -> Task
        //status - 2 -> Subtask
        if (status == 1) {
            if (index <= todolist.getTaskSize(index)) {
                return true;
            }
        } else if (status == 2) {
            if (index <= todolist.getSubtaskSize(index)) {
                return true;
            }
        }
        return false;
    }

    public ToDoList getTodolist() {
        return todolist;
    }

}
