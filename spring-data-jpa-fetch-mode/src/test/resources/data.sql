-- students
insert into student (first_name, last_name, id) values ('Tej', 'Parker', '7fc444a6-0ea3-409a-a31a-40ff4fa2f068');
insert into student (first_name, last_name, id) values ('Brian', 'O''Conner', '1d75c6f6-0a7f-4ced-a7f9-3839372520c0');
insert into student (first_name, last_name, id) values ('Monica', 'Fuentes', '50b124bc-f251-48f2-a0b7-3e5ddf7cf067');
insert into student (first_name, last_name, id) values ('Luke', 'Hobbs', '96022586-dd67-4b5b-a793-69059f423715');
insert into student (first_name, last_name, id) values ('Hattie', 'Shaw', '64c22543-b484-428d-8dba-aa1d76960ff7');
insert into student (first_name, last_name, id) values ('Roman', 'Pearce', '1e8493a8-a674-44d1-a879-5c82a8c6dfa0');
insert into student (first_name, last_name, id) values ('Dominic', 'Toretto', '855689b7-d263-4e3a-89cb-1a57ce198561');
insert into student (first_name, last_name, id) values ('Deckard', 'Shaw', '1e48d956-4b8c-439b-9525-661a615cd152');
insert into student (first_name, last_name, id) values ('Mia', 'Toretto', 'e908c79f-972c-4953-b3fd-125f16954c58');
insert into student (first_name, last_name, id) values ('Leticia', 'Ortiz', '6bb722f5-0cb7-49be-a828-ee7eb22edb37');
-- universities
insert into university (address, name, id) values ('Massachusetts', 'Harvard University', '20cdd371-2803-4ad6-8c5f-7189d00461e8');
insert into university (address, name, id) values ('Massachusetts', 'MIT - Massachusetts Institute of Technology', '20cdd371-2803-4ad6-8c5f-7189d00461e9');
-- Harvard
insert into university_students (university_id, students_id) values ('20cdd371-2803-4ad6-8c5f-7189d00461e8', '7fc444a6-0ea3-409a-a31a-40ff4fa2f068');
insert into university_students (university_id, students_id) values ('20cdd371-2803-4ad6-8c5f-7189d00461e8', '1d75c6f6-0a7f-4ced-a7f9-3839372520c0');
insert into university_students (university_id, students_id) values ('20cdd371-2803-4ad6-8c5f-7189d00461e8', '50b124bc-f251-48f2-a0b7-3e5ddf7cf067');
insert into university_students (university_id, students_id) values ('20cdd371-2803-4ad6-8c5f-7189d00461e8', '96022586-dd67-4b5b-a793-69059f423715');
insert into university_students (university_id, students_id) values ('20cdd371-2803-4ad6-8c5f-7189d00461e8', '64c22543-b484-428d-8dba-aa1d76960ff7');
-- MIT
insert into university_students (university_id, students_id) values ('20cdd371-2803-4ad6-8c5f-7189d00461e9', '1e8493a8-a674-44d1-a879-5c82a8c6dfa0');
insert into university_students (university_id, students_id) values ('20cdd371-2803-4ad6-8c5f-7189d00461e9', '855689b7-d263-4e3a-89cb-1a57ce198561');
insert into university_students (university_id, students_id) values ('20cdd371-2803-4ad6-8c5f-7189d00461e9', '1e48d956-4b8c-439b-9525-661a615cd152');
insert into university_students (university_id, students_id) values ('20cdd371-2803-4ad6-8c5f-7189d00461e9', 'e908c79f-972c-4953-b3fd-125f16954c58');
insert into university_students (university_id, students_id) values ('20cdd371-2803-4ad6-8c5f-7189d00461e9', '6bb722f5-0cb7-49be-a828-ee7eb22edb37');