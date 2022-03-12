create table `customer_reward`(
/*Primary Key Field*/
id int not null auto_increment,
/*reward name*/
customer_reward_name varchar(50) not null,
/*reward desciption*/
customer_reward_description varchar(255),
/*reward amount*/
customer_reward_amount float(25) default 0.0,
/*reward unit*/
customer_reward_unit enum("Percent","Days") not null,
/*number of hours reward stays valid*/
customer_validity_period int unsigned not null,
/*date*/
date_added timestamp default current_timestamp,
/*last update*/
date_updated timestamp on update current_timestamp,
/*admin responsible for addition*/
admin_id int not null,

constraint pk_customer_reward primary key(id),
constraint fk_customer_reward foreign key(admin_id) references admin(id)


);