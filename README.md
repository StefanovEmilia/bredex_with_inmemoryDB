# bredex_with_inmemoryDB
Try to create my backend test excercise with inmemory DB

This is the same project like this:  https://github.com/StefanovEmilia/bredex_with_MySQL_Hibernate.git

DIFFERENCES: 
1. Instead of MySQL and Hibernate I use HSQLDB and JDBC.
2. According to the first point, the Database methods was changed.
3. There is also a little difference in the AppController: in the createPosition() method there was a bit correction about to get the registered position's id. 
4. I'm not sure if this is normal, but: 
While the program runs, I can register new positions and clients, data is stored in the HSQLDB. However, after I stop the program and restart it, 
all the previous data were gone and only those are available that are updating from the 'data.sql'.
