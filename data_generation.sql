USE finalProject232;


CREATE TABLE user (
  usr_id int(11) NOT NULL AUTO_INCREMENT,
  usr_mail varchar(45) NOT NULL,
  usr_name varchar(45) NOT NULL,
  usr_pwd varchar(45) NOT NULL,
  PRIMARY KEY (usr_id),
  UNIQUE  (usr_mail)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

CREATE TABLE item (
  it_id int(10) unsigned NOT NULL AUTO_INCREMENT,
  it_name varchar(45) NOT NULL,
  it_price decimal(20,0) unsigned NOT NULL,
  def_qty decimal(20,0) unsigned NOT NULL,
  PRIMARY KEY (it_id),
  UNIQUE (it_id)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

CREATE TABLE shopLst (
  usr_id int(10) unsigned NOT NULL,
  it_id int(10) unsigned NOT NULL,
  qty int(10) unsigned NOT NULL,
  PRIMARY KEY (usr_id , it_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DELETE FROM item;
DELETE FROM user;
DELETE FROM shopLst;


INSERT INTO item (it_id,it_name, it_price, def_qty) VALUES
(1,"Apple",12,4),
(2,"Orange",7,2),
(3,"Macbook",4,1),
(4,"Book",3,8),
(5,"Hardrive",1,12),
(6,"Sheet",2,9),
(7,"Pen",22,14);

INSERT INTO user (usr_id, usr_mail, usr_name, usr_pwd) VALUES
(1, "ancellin.e@orange.fr", "eloi", "root"),
(2, "eloi@bu.edu", "Jean", "root"),
(3, "eloi.ancellin@edu.ece.fr", "Pierre", "root");

INSERT INTO shopLst (usr_id, it_id, qty) VALUES
(1,1,3  ),
(1, 2, 3),
(1, 3, 8),
(1, 4, 3),
(1, 5, 2),
(1, 6, 5),
(1, 7, 4),
(2,1,4  ),
(2, 2, 5),
(2, 3, 7),
(2, 4, 2),
(2, 5, 9),
(2, 6, 6),
(2, 7, 4),
(3,1,7  ),
(3, 2, 12),
(3, 3, 11),
(3, 4, 17),
(3, 5, 21),
(3, 6, 5),
(3, 7, 8);


DELETE FROM  user WHERE usr_id IS NULL;
DELETE FROM  shopLst WHERE usr_id IS NULL;
DELETE FROM  item WHERE it_id IS NULL;


