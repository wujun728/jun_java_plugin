insert into t_number values(6,23.4564,123556);

select abs(n1) from t_number;

select round(n1,2) from t_number;

select ceil(-12.3) from dual;

select floor(-12.3) from dual;

select mod(5,3) from dual;

select sign(-100) from dual;

select sqrt(9) from dual;

select power(2,3) from dual;

select trunc(123.456,2) from dual;

123.45

select to_char(123.45,'9999.999') from dual;

select to_char(123123,'99,999,999.99') from dual;

select to_char(123123.3,'FM99,999,999.99') from dual;

select to_char(123123.3,'FM$99,999,999.99') from dual;

select to_char(123123.3,'L99,999,999.99') from dual;

select to_char(123123.3,'FM99,999,999.99C') from dual;