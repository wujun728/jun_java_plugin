-- Initial database  data

truncate user_;
truncate user_privilege;
-- admin, password is admin  ( All privileges)
insert into user_(id,guid,create_time,email,password,phone,username,default_user)
values
(21,'29f6004fb1b0466f9572b02bf2ac1be8',now(),'admin@andaily.com','$2a$10$XWN7zOvSLDiyxQnX01KMXuf5NTkkuAUtt23YxUMWaIPURcR7bdULi','028-1234567','admin',1);

insert into user_privilege(user_id,privilege) values (21,'ADMIN');
insert into user_privilege(user_id,privilege) values (21,'UNITY');
insert into user_privilege(user_id,privilege) values (21,'MOBILE');

-- unity, password is unity  ( ROLE_UNITY)
insert into user_(id,guid,create_time,email,password,phone,username,default_user)
values
(22,'55b713df1c6f423e842ad68668523c49',now(),'unity@andaily.com','$2a$10$gq3eUch/h.eHt20LpboSXeeZinzSLBk49K5KD.Ms4/1tOAJIsrrfq','','unity',0);

insert into user_privilege(user_id,privilege) values (22,'UNITY');

-- mobile, password is mobile  ( ROLE_MOBILE)
insert into user_(id,guid,create_time,email,password,phone,username,default_user)
values
(23,'612025cb3f964a64a48bbdf77e53c2c1',now(),'mobile@andaily.com','$2a$10$BOmMzLDaoiIQ4Q1pCw6Z4u0gzL01B8bNL.0WUecJ2YxTtHVRIA8Zm','','mobile',0);

insert into user_privilege(user_id,privilege) values (23,'MOBILE');


-- initial oauth client details test data
-- 'unity-client'   support browser, js(flash) visit,  secret:  unity
-- 'mobile-client'  only support mobile-device visit,  secret:  mobile
truncate  oauth_client_details;
insert into oauth_client_details
(client_id, resource_ids, client_secret, scope, authorized_grant_types,
web_server_redirect_uri,authorities, access_token_validity,
refresh_token_validity, additional_information, create_time, archived, trusted)
values
('unity-client','sos-resource', '$2a$10$QQTKDdNfj9sPjak6c8oWaumvTsa10MxOBOV6BW3DvLWU6VrjDfDam', 'read','authorization_code,refresh_token,implicit',
'http://localhost:8080/spring-oauth-server/unity/dashboard','ROLE_CLIENT',null,
null,null, now(), 0, 0),
('mobile-client','sos-resource', '$2a$10$uLvpxfvm3CuUyjIvYq7a9OUmd9b3tHFKrUaMyU/jC01thrTdkBDVm', 'read','password,refresh_token',
null,'ROLE_CLIENT',null,
null,null, now(), 0, 0);
