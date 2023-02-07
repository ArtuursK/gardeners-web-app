


create table gardener (
    gardener_id int auto_increment,
    email varchar(100),
    username varchar(255),
    primary key (gardener_id)
);

# timestamp format - YYYY-MM-DD hh:mm:ss
create table post (
    post_id int auto_increment,
    description varchar(255),
    gardener_id int,
    datetime timestamp default current_timestamp,
    primary key (post_id),
    foreign key (gardener_id) references gardener(gardener_id)
);

select * from post;
select * from gardener;
insert into post (description, gardener_id) values ('desc1', 2);

delete from gardener where gardener_id = 2;

# abu tabulu apvienojums
select * from post, gardener;

# atlasit ierakstus, kur gardener_id sakrit abaas tabulaas
select * from post, gardener
where post.gardener_id = gardener.gardener_id;

# atrast ierakstu (posts) description darzniekiem, kuriem
# lietotajvards ir 'grdnr2'
select post.description from post, gardener
where gardener.username = 'grdnr1' and 
post.gardener_id = gardener.gardener_id;


# izveidot tabulas darznieku grupam
create table ggroup (
    group_id int auto_increment,
    name varchar(255) not null,
    primary key (group_id)
);

create table gardener_groups (
    group_id int,
    gardener_id int,
    #role varchar(255), # --> darznieka loma --> varetu but izmantots lai glabatu informaciju par darznieka saistibu ar grupu (piemeram, vai darznieks ir administrators vai parast lietotajs)
    foreign key (group_id) references ggroup(group_id),
    foreign key (gardener_id) references gardener(gardener_id)
);


create table address (
    address_id int auto_increment,
    street varchar(255) not null,
    gardener_id int,
    unique(gardener_id),
    primary key (address_id),
    foreign key (gardener_id) references gardener(gardener_id)
);


# ievieto ggroup tabulaa grupas
insert into ggroup (name) values ('grp1');
insert into ggroup (name) values ('grp2');

insert into gardener_groups (gardener_id, group_id) values (1, 1); # darznieks ar id 1 pievienots grupai ar id 1
insert into gardener_groups (gardener_id, group_id) values (2, 2); # darznieks ar id 2 pievienots grupai ar id 2
insert into gardener_groups (gardener_id, group_id) values (2, 1); # darznieks ar id 2 pievienots grupai ar id 1


# iegut grupas kuraas ir darznieks ar lietotajvardu 'grdnr2'
select ggroup.group_id, ggroup.name from gardener_groups, ggroup, gardener
where gardener_groups.gardener_id = gardener.gardener_id and 
gardener_groups.group_id = ggroup.group_id and 
gardener.username = 'grdnr1';







