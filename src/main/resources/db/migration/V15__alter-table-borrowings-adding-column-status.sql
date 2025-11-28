ALTER TABLE borrowings
  ADD COLUMN status VARCHAR(20) NOT NULL DEFAULT 'OPENED';

UPDATE borrowings
  SET status = 'OPENED'
  WHERE status IS NULL;

ALTER TABLE borrowings
  ADD CONSTRAINT chk_borrowings_status
  CHECK (status IN ('OPENED','CLOSED', 'DELAYED'));
