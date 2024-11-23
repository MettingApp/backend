insert into team (id, name, title, description, inviteCode)
values (1L, '박정곤', 'title', 'description', 'hihello');

insert into member (id, username, password, nickname, name, role, team)
values (1L, 'usernameA', 'clark1245!', 'nicknameA', 'parkA', ROLE_USER, 1L);

insert into member (id, username, password, nickname, name, role, team)
values (2L, 'usernameB', 'clark1245!', 'nicknameB', 'parkB', ROLE_USER, 1L);

insert into member (id, username, password, nickname, name, role, team)
values (3L, 'usernameC', 'clark1245!', 'nicknameC', 'parkC', ROLE_USER, 1L);