# jht41_squash

Description:

We needed to create a website where a group of friends could follow their matches. 
The application's target is to create a webpage where users can see all the played matches, the locations and results, in descending order by date. Every played game detail stored in the Database. In the program there is two types of users: User and Admin.
Only the Admin is allowed to update data, Users are only-readers.
Admin is registered by default in the program.

Admin functions:
•	admin functions are available from the ‘/admin’ URL
•	visible only for logged in users who are admin type - in other case the program reload the login page with an error message
•	admin can add a new user to the program: first the admin sign up the user with a generated password, then the user have to change this password by their first login
•	admin can add a new location
•	admin can add games (matches)

User data:
•	Name
•	Password
•	Type

Location data:
•	Name
•	Address
•	Rental price/hour - Given in HUF in the Database, and on the webpage, it displays it also in EUR

Game data:
•	Names of the two participants (users)
•	The result: earned points / participant
•	Location (one of the registered locations by the admin)
•	Date

After logging in, a list of played games appears in a table on the interface. There are two searching method: we can narrow done the result list by the players name or by the location with a select menu.
One bonus function: 
Create EXPORT and IMPORT functions that generate the entire data set of the program in XML format.
