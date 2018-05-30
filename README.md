README - Biblio - April 2018

I. The project
-----------------------
This application was developped by ******* and ******* for the module 644-1 Mobile developpement and the topic is Book.


II. Database
--------------------------

---MASTER BRANCH---
The database was build using Room and is composed with 3 tables :

- Categories
- Authors
- Book (fk_author, fk_category)

---FIREBASE BRANCH---
Database on firebase

structure :

authors
-authorUid
--Firstname
--Lastname
--Bday
--Bio

books
-bookUid
--title
--date
--summary
--author : authorUid
--category : categoryUid


categories
-categoryUid
--name


III. Fonctionalities
--------------------------

You can add, modify and delete books, authors, and categories.
The delete behaviors is on cascade

When you are on the details of an author, you can open the list of his book.
When you are on the details of a book you can open the details of the author.
You can see the book from a specific category in the menu categories.

You have a search bar for all list.

You can switch the language between French, English, German and Italian.


IV. Authors
--------------------------
*******
*******





