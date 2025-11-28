ALTER TABLE reservations
  ADD COLUMN status VARCHAR(20) NOT NULL DEFAULT 'OPENED';

UPDATE reservations
  SET status = 'OPENED'
  WHERE status IS NULL;

ALTER TABLE reservations
  ADD CONSTRAINT chk_reservations_status
  CHECK (status IN ('OPENED','CLOSED'));
