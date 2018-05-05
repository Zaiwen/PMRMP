use `bpep`;
create table `epml`(
	`id` int(11) AUTO_INCREMENT primary key,
	`creator` varchar(40),
	`name` varchar(40) not null,
	`content` longtext,
	`time` varchar(40)
);