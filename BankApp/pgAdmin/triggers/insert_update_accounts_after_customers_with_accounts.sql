CREATE OR REPLACE FUNCTION insert_update_accounts() 
RETURNS TRIGGER AS 
$iua$
	BEGIN
		IF (TG_OP = 'INSERT') THEN
		
			INSERT INTO accounts VALUES(NEW.customer_id, NEW.savings_number, NEW.savings_amount, 
			NEW.checking_number, NEW.checking_amount, NEW.flagged, NEW.joint, NEW.joint_customer_id);
		
		ELSIF (TG_OP = 'UPDATE') THEN
		
			IF (OLD.* <> NEW.*) THEN
				UPDATE accounts SET 
					savings_number = NEW.savings_number,
					savings_amount = NEW.savings_amount,
					checking_number = NEW.checking_number,
					checking_amount = NEW.checking_amount,
					flagged = NEW.flagged,
					joint = NEW.joint,
					joint_customer_id = NEW.joint_customer_id
				WHERE customer_id = NEW.customer_id;
			END IF;
			
		END IF;
		RETURN NEW;
	END;
$iua$
LANGUAGE plpgsql 

CREATE TRIGGER insert_update_accounts_after
	AFTER INSERT OR UPDATE ON customers_with_accounts
	FOR EACH ROW 
EXECUTE PROCEDURE insert_update_accounts();