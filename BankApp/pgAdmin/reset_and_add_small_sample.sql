delete from customers_With_accounts where customer_id > 0;
Insert Into customers_with_accounts VALUES(1, 'Guy.Fieri', 'password', 'Ryan', 'Sweet', '407-234-3456', 'rsweetwps@gmail.com', true, true, 'ESPN', '1234509876', 0.0, '6789054321', 0.0, false, false, -1);
Insert Into customers_with_accounts VALUES(2, 'Guy.Harvey', 'password', 'Guy', 'Harvey', '109-294-2267', 'gharvey@letsail.com', true, true, 'self', '9284914356', 0.0, '2839481236', 0.0, false, false, -1);
delete from unverified_customers where unverified_id > 0;
Insert into unverified_customers VALUES(1, 'Tim', 'Leary', '407-817-8117', 'toleary@florida.edu', true, true, 'UCF Medical School');
delete from admins where admin_id > 0;
Insert Into admins VALUES(1, 'Rakatashii', 'password', true, 1);
delete from employees where employee_id > 0;
Insert Into employees VALUES(2, 'PokemonFreak', 'charmanderbulbasaur', false, -1);