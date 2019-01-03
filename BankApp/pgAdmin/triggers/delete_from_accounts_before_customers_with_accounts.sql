CREATE OR REPLACE FUNCTION delete_accounts () 
RETURNS TRIGGER AS 
$da$
	BEGIN
		IF (TG_OP = 'DELETE') THEN			
			DELETE FROM accounts WHERE customer_id = OLD.customer_id;
		END IF;
		RETURN OLD;
	END;
$da$ LANGUAGE plpgsql

CREATE TRIGGER delete_accounts_before
	BEFORE DELETE ON customers_with_accounts
	FOR EACH ROW 
EXECUTE PROCEDURE delete_accounts();