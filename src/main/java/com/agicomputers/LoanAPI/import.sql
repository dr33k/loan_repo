insert into role(role_name,role_description,role_authorities)
values("APPUSER","Basic user privileges",ARRAY['appuser:read','appuser:write']);
insert into role(role_name,role_description,role_authorities)
values('ADMIN','Administrator privileges',ARRAY['appuser:read','appuser:write','admin:read','admin:write','blog:write']);
insert into role(role_name,role_description,role_authorities)
values('SUBADMIN','Sub-administrator privileges. Cannot edit state of other ADMINs',ARRAY['appuser:read','appuser:write','admin:read','blog:write']);
insert into role(role_name,role_description,role_authorities)
values('BLOGGER','Blogger privilege.',ARRAY['appuser:read','appuser:write','blog:write']);