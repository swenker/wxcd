# wxcd
web and related-python,java


CREATE TABLE orders(
  id INT AUTO_INCREMENT,
  userid INT,
  pid INT,
  num INT,
  dtcreate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY(id)
)ENGINE=Innodb DEFAULT CHARSET=utf8;


CREATE TABLE stock(
    pid INT NOT NULL,
    instock INT NOT NULL
)ENGINE=innodb DEFAULT CHARSET=UTF8;

