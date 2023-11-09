Tourism Agency System

This will be a quick guide to this project.

You can find the SQL code required to set up the schema and tables in sql_queries.txt.


This is an Entity Relation Diagram describing the relations between the various tables in the schema.

![img_4.png](image/img_4.png)



The entrance of the program is App.java. The theme is configurable with "Utility.setTheme()",

![img_5.png](image/img_5.png)



You must log in to access the system. You can log in either as an admin or agent. An admin has the role of managing users
while an agent has the role of managing the information related to hotels.

![img_6.png](image/img_6.png)



You can right-click on users in the table to update or delete them.

![img_7.png](image/img_7.png)
![img_8.png](image/img_8.png)



Clicking on "Add":

![img_9.png](image/img_9.png)



Clicking on "Search":

![img_10.png](image/img_10.png)



Clicking on "Logout" brings you back to the Log in screen.


The agent panel features 3 tabs pertaining to different aspects of the hotel management process:
![img_11.png](image/img_11.png)
![img_12.png](image/img_12.png)
![img_13.png](image/img_13.png)



Hotel Tab:

Similar to the admin panel, the hotel tab features adding and searching popups when clicked:
![img_14.png](image/img_14.png)
![img_15.png](image/img_15.png)



Right-clicking on a hotel in the table provides the 5 options: "Update," "Delete," "Access Facility Specs," 
"Access Lodging Options," and "Access Hotel Periods."

![img_16.png](image/img_16.png)



The room tab filters rooms based on their hotel. You can do this through the use of this combo box:

![img_17.png](image/img_17.png)



Clicking on "Add":

![img_18.png](image/img_18.png)

Right-clicking on "Update":

![img_19.png](image/img_19.png)



The reservation tab allows the agent to view previous reservations, delete them, and reserve a room for the customer by 
inputting certain criteria. 

Clicking on "Reserve a room" (valid dates are in yyyy-MM-dd format):

![img_20.png](image/img_20.png)



Based on the input, you receive all possible lodging options for each suitable room:

![img_21.png](image/img_21.png)

You can reserve one of these rooms by right-clicking on them.



As the last step, you must enter guest information:

![img_22.png](image/img_22.png)



Reserving a room decreases its stock by one and deleting a reservation increases the reserved rooms stock by one.