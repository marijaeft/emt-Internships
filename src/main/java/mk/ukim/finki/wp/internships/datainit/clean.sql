DROP TABLE internship_week CASCADE;
DROP TABLE internship CASCADE;
DROP TABLE internship_coordinator CASCADE;
DROP TABLE internship_supervisor CASCADE;
DROP TABLE internship_posting CASCADE;
DELETE FROM auth_user WHERE role='STUDENT' or role='SUPERVISOR' or id='dimitrij.krstev' or id='dimitrij.auth';
DELETE FROM student WHERE student_index LIKE '%999';
DELETE FROM company WHERE id='company1';