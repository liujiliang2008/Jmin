drop table Cust;
create table Cust(
  custNo  varchar(20),
  name    varchar(20),
  gender  varchar(20),
  phone   varchar(20),
  mobile  varchar(20),
  address varchar(20),
  primary key(custNo)
);

drop table Order_HD;
create table Order_HD(
  orderNo      varchar(20),
  custNo       varchar(20),
  openDate     Date,
  primary key(orderNo)
);

drop table Order_Detail;
create table Order_Detail(
  orderNo     varchar(20),
  productNo   varchar(20),
  remark      varchar(20),
  primary key(orderNo,productNo)
);


drop table Product;
create table Product(
  productNo      varchar(20),
  productName    varchar(20),
  productDesc    varchar(20),
  productType    varchar(20),
  productSize    varchar(20),
  productColor   varchar(20),
  primary key(productNo)
);


drop table USERINFO;
create table USERINFO(
  name      varchar(20),
  sex       varchar(20)
);