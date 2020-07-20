--------------------------- basket ---------------------------
SELECT id, medicine_id, quantity, receipt_id
	FROM public.basket;

INSERT INTO public.basket(
	medicine_id, quantity, receipt_id)
	VALUES (?, ?, ?);

DELETE FROM public.basket;

------- // metoda do dorzucania wybranych leków do basket
INSERT INTO public.basket(
    medicine_id, quantity, receipt_id)
-- SELECT basket.medicine_id,
--     receipt_id FROM receipts WHERE receipt_id IN (SELECT MAX(receipt_id) FROM public.receipts);

--->> uzupelnic medicine_id mając już co podstawić:
SELECT medicine_id FROM public.medicines WHERE medicine_name = '${odczyt wartosci z listy}';

--------------------------- medicines ---------------------------
DELETE FROM public.medicines;

SELECT medicine_id, medicine_name, price
	FROM public.medicines;

INSERT INTO public.medicines(
    medicine_name, price, medicine_description)
VALUES (?, ?, ?);

--------------------------- pharmacies ---------------------------
DELETE FROM public.pharmacies;

SELECT pharmacy_id, pharmacy_name, address, email, phone_number
	FROM public.pharmacies;

INSERT INTO public.pharmacies(
	pharmacy_name, address, email, phone_number)
	VALUES (?, ?, ?, ?);

--------------------------- pharmacy_staff ---------------------------
SELECT id, user_id, job_title, salary, pharmacy_id
	FROM public.pharmacy_staff;

INSERT INTO public.pharmacy_staff(
	user_id, job_title, salary, pharmacy_id)
	VALUES (?, ?, ?, ?);

DELETE FROM public.pharmacy_staff;

--------------------------- pharmacy_storage ---------------------------
SELECT id, pharmacy_id, medicine_id, quantity
	FROM public.pharmacy_storage;

INSERT INTO public.pharmacy_storage(
	pharmacy_id, medicine_id, quantity)
	VALUES (?, ?, ?);

DELETE FROM public.pharmacy_storage;

--------------------------- receipts ---------------------------
DELETE FROM public.receipts;

SELECT receipt_id, pharmacy_id, user_id, total, date, time
	FROM public.receipts;

INSERT INTO public.receipts(
	pharmacy_id, user_id, total, date, time)
	VALUES (?, ?, ?, current_date, current_time);

------- // tworzymy paragon na potrzebę receiptID do basket
INSERT INTO public.receipts(
	pharmacy_id, user_id, date, time)
	VALUES (?, ?, current_date, current_time);

------- // po wciśnięciu przycisku np. suma uzupełniamy >> UPDATE total
UPDATE public.receipts
    SET total=?
    WHERE receipt_id IN (SELECT MAX(receipt_id) FROM public.receipts);

--------------------------- users ---------------------------
SELECT user_id, first_name, last_name, address, email, phone_number
	FROM public.users;

	SELECT user_id, concat(first_name,' ', last_name) as name, address, email, phone_number
FROM public.users;

INSERT INTO public.users(
	first_name, last_name, address, email, phone_number)
	VALUES (?, ?, ?, ?, ?);

------- // imie, nazwisko, job_title
SELECT first_name AS firstname, last_name AS lastname, job_title AS role FROM users
INNER JOIN pharmacy_staff ps
    ON users.user_id = ps.user_id;


DELETE FROM public.users;

--------------------------- user_credentials ---------------------------
SELECT login, password, user_id
	FROM public.user_credentials;

INSERT INTO public.user_credentials(
    login, password, user_id)
    VALUES (?, ?, ?);

-------- // Logowanie i wszystkie potrzebne dane
SELECT user_credentials.user_id AS userid, login, password, job_title AS role, pharmacy_id AS pharmacyid,
       first_name AS firstname, last_name AS lastname
FROM user_credentials
         INNER JOIN pharmacy_staff ps
                    ON user_credentials.user_id = ps.user_id
         INNER JOIN users
                    ON user_credentials.user_id = users.user_id
        WHERE login = 'hownav' AND password = 'howvan2019';

------------------/////// Pobranie info dla kierownika nie znając i znając PharmacyId \\\\\\\------------------
SELECT users.user_id, concat(first_name,' ', last_name) as name, first_name, last_name, job_title,
       salary, email, phone_number FROM users
    INNER JOIN pharmacy_staff ps
    ON users.user_id = ps.user_id
WHERE pharmacy_id IN (SELECT pharmacy_id FROM public.pharmacy_staff WHERE user_id = 5 AND ps.job_title = 'Pharmacist');

SELECT users.user_id, concat(first_name,' ', last_name) as name, first_name, last_name, job_title,
       salary, email, phone_number FROM users
    INNER JOIN pharmacy_staff ps
    ON users.user_id = ps.user_id
WHERE pharmacy_id=2 AND job_title = 'Pharmacist';

------------------/////// Pobranie info o Storage znając PharmacyId \\\\\\\------------------
SELECT m.medicine_id, medicine_name, price, quantity FROM pharmacy_storage
    INNER JOIN medicines m
        ON pharmacy_storage.medicine_id = m.medicine_id
WHERE pharmacy_id=3;

------------------/////// Update pharmacy_storage >> quantity \\\\\\\------------------

UPDATE pharmacy_storage
    SET quantity=?
    WHERE medicine_id=? AND pharmacy_id=?;

INSERT INTO public.users(
    first_name, last_name, address, email, phone_number)
VALUES (?, ?, ?, ?, ?);
INSERT INTO public.user_credentials(
    login, password, user_id)
VALUES (?, ?, (SELECT MAX(users.user_id) FROM public.users));
INSERT INTO public.pharmacy_staff(
    user_id, job_title, salary, pharmacy_id)
VALUES ((SELECT MAX(users.user_id) FROM public.users), ?, ?, ?);

------------------/////// Get ALL Users \\\\\\\------------------

SELECT users.user_id, first_name, last_name, address, email,
       phone_number, login, job_title, salary, pharmacy_id FROM users
INNER JOIN pharmacy_staff ps
           ON users.user_id = ps.user_id
INNER JOIN user_credentials uc
           ON users.user_id = uc.user_id
ORDER BY first_name;

------------------/////// CRUD User \\\\\\\------------------

SELECT users.user_id, first_name, last_name, address, email,
       phone_number, login, password, job_title, salary, pharmacy_id FROM users
INNER JOIN pharmacy_staff ps
    ON users.user_id = ps.user_id
INNER JOIN user_credentials uc
    ON users.user_id = uc.user_id
WHERE users.user_id=?;
