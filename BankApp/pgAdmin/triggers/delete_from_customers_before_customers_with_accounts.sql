CREATE OR REPLACE FUNCTION delete_customers () 
RETURNS TRIGGER AS 
$dc$
	BEGIN
		IF (TG_OP = 'DELETE') THEN
			DELETE FROM customers WHERE customer_id = OLD.customer_id;
		END IF;
		RETURN OLD;
	END;
$dc$ LANGUAGE plpgsql

CREATE TRIGGER delete_customers_before
	BEFORE DELETE ON customers_with_accounts
	FOR EACH ROW 
EXECUTE PROCEDURE delete_customers();