INSERT INTO `test_oauth`.`oauth_client_details` (`client_id`, `resource_ids`, `client_secret`, `scope`, `authorized_grant_types`, `web_server_redirect_uri`, `authorities`, `access_token_validity`, `refresh_token_validity`, `additional_information`, `autoapprove`) VALUES ('barClientIdPassword', 'oauth2-resource', 'secret', 'bar,read,write', 'password,authorization_code,refresh_token', 'http://127.0.0.1:8082/login/my', NULL, '36000', '36000', NULL, '1');


INSERT INTO `test_oauth`.`oauth_client_details` (`client_id`, `resource_ids`, `client_secret`, `scope`, `authorized_grant_types`, `web_server_redirect_uri`, `authorities`, `access_token_validity`, `refresh_token_validity`, `additional_information`, `autoapprove`) VALUES ('fooClientIdPassword', NULL, 'secret', 'foo,read,write', 'password,authorization_code,refresh_token', NULL, NULL, '36000', '36000', NULL, '1');


INSERT INTO `test_oauth`.`oauth_client_details` (`client_id`, `resource_ids`, `client_secret`, `scope`, `authorized_grant_types`, `web_server_redirect_uri`, `authorities`, `access_token_validity`, `refresh_token_validity`, `additional_information`, `autoapprove`) VALUES ('sampleClientId', NULL, 'secret', 'read,write,foo,bar', 'implicit', NULL, NULL, '36000', '36000', NULL, '0');
