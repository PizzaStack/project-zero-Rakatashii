CREATE OR REPLACE FUNCTION insert_update_customers() 
RETURNS TRIGGER AS 
$iuc$
	BEGIN
		IF (TG_OP = 'INSERT') THEN
		
			INSERT INTO customers 
			VALUES(NEW.customer_id, NEW.username, NEW.password, 
				NEW.first_name, NEW.last_name, NEW.telephone, NEW.email, NEW.us_citizen,
				NEW.employed, NEW.employer, NEW.flagged);
			
		ELSIF (TG_OP = 'UPDATE') THEN
		
			IF (OLD.* <> NEW.*) THEN
				UPDATE customers SET 
					username = NEW.username,
					password = NEW.password,
					first_name = NEW.first_name,
					last_name = NEW.last_name,
					telephone = NEW.telephone,
					email = NEW.email,
					us_citizen = NEW.us_citizen,
					employed = NEW.employed,
					employer = NEW.employer,
					flagged = NEW.flagged
				WHERE customer_id = NEW.customer_id;
			END IF;
		
		END IF;
		RETURN NEW;
	END;
$iuc$ LANGUAGE plpgsql

CREATE TRIGGER insert_update_customers_after
	AFTER INSERT OR UPDATE ON customers_with_accounts
	FOR EACH ROW 
EXECUTE PROCEDURE insert_update_customers();