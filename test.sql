USE finalProject232;

SELECT * FROM user;

SELECT * FROM shopLst;

DELETE FROM user WHERE usr_mail = 'jeanp@gmail.com';

SELECT * FROM user natural join shopLst natural join item where usr_mail = 'jeanp@gmail.com';


SELECT * FROM item  WHERE it_name = "pomme";
SELECT * FROM item natural join shopLst natural join user;

DELETE FROM item WHERE it_name = "pomme"
