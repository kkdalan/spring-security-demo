CREATE TABLE spring_demo.users0 (
    id bigint(20) NOT NULL AUTO_INCREMENT,
    username varchar(50) NOT NULL,
    password varchar(60),
    enable tinyint(4) NOT NULL DEFAULT 1 COMMENT '用戶是否可用',
    role text CHARACTER SET utf8 COMMENT '用戶角色,多個角色間用逗號隔開',
    PRIMARY KEY (id),
    KEY username (username)
);


insert into spring_demo.users0 (username, password, roles) values('admin','123', 'ROLE_ADMIN,ROLE_USER'); 
insert into spring_demo.users0 (username, password, roles) values('user','123', 'ROLE_USER'); 
 