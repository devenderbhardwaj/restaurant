# Java project on restarant Mangement
## What it does
This is a java project for restaurant management  

## You can log in as cusomter or owner
### As owner you can
- See menu
- Change name and price of menu items
- See all customers

### As customer you can
- See menu
- Select food items and their quantity
- Pay bill

## Folder Structure
The workspace contains two folders by default, where:
- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies
- `database`: the folder to database dump file

## Requirements
- Java Virtual Machine
- MySQL database (with our database created)

## Importing database
- Open MySQL workbench.
- Connect to your MySQL database.
- Click ***Server*** on the main tool bar.
- Select ***Data Import***.
- Select ***import from self contained*** file.
- Click the ***...*** and navigate to where your MySQL backup file is located , select the backup you want to load ( *restaurantdatabase.sql* in database *description description* folder of project), and click OK.
- The dump file contain the schema so left ***Default Target Schema*** untouched.
- Select Start Import on the bottom right.
