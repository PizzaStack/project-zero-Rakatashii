CREATE OR REPLACE FUNCTION insert_update_delete_employees() 
RETURNS TRIGGER AS 
$iude$
	BEGIN
		IF (TG_OP = 'INSERT') THEN
			INSERT INTO employees VALUES(NEW.employee_id, NEW.username, NEW.password, 
			NEW.admin, NEW.admin_id);
		
		ELSIF (TG_OP = 'UPDATE') THEN
		
			IF (OLD.* <> NEW.*) THEN
				UPDATE employees SET 
					username = NEW.username,
					password = NEW.password,
					admin = NEW.admin,
					admin_id = NEW.admin_id
				WHERE employee_id = NEW.employee_id;
			END IF;
		ELSIF (TG_OP = 'DELETE') THEN
			
			DELETE FROM employees WHERE employee_id = OLD.employee_id;

			RETURN OLD;
			
		END IF;
		RETURN NEW;
	END;
$iude$
LANGUAGE plpgsql 

CREATE TRIGGER insert_update_delete_employees_after
	AFTER INSERT OR UPDATE OR DELETE ON admins
	FOR EACH ROW 
EXECUTE PROCEDURE insert_update_delete_employees();