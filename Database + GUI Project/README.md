Database and GUI Project

Sam Largaespada

Source Course: CISC450

The purpose of this project was to create a small but interconnected database that a user could interact with, either through a command line interface, or via a GUI. The database we decided to model was based around videogames. We wanted to make something where users could share a library of their videogames, be friends with other users, create lists of games such as a “wishlist” or “favorite GameCube games”, create reviews for games and rate them, and send messages to other users. We found out that this idea exists in the form of backloggd.com, but it was still a very valuable experience in terms of creating a database, modeling it with a python ORM called SQLAlchemy, and tying it all together with a somewhat user-friendly GUI.

There are a few steps that need to happen to actually run this project. First, the SQLAlchemy package needs to be installed, as that is how we create and interact with the database file. We were given the opportunity to use built-in python functions like executescript() and commit(), but the SQLAlchemy was easier to use and more robust in a lot of ways. It is a lot easier to create relationships between tables with SQLAlchemy, since each table is considered a python object with associated variables. Additionally, if starting from scratch the files need to be run in a specific order to create the database in memory, create the actual .db file, and then insert some dummy data into the database. The order is alchemyBase.py, creatDB.py, then bulkinsertp2.py. After this GUI.py can be run to get to the actual user interface.

Using the interface is pretty straightforward, you can sign in as one of the dummy users like Alice or Bob, or create a new user. Once logged in you can look at your games library, friends list, messages, pending friend requests, and a few other features. Overall it is quite barebones, but it does accurately insert and retrieve data from the database file.
