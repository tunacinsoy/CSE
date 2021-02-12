--a
USE ATC_150117062
SELECT s.fName, s.lName, s.birthDate, s.city
FROM STUDENT s

--b
SELECT s.fName, s.lName, d.dName, st.fName, st.lName
FROM STUDENT s, DEPARTMENT d, ADVISOR a, STAFF st
WHERE s.deptCode=d.deptCode and s.advisorID = a.staffID and a.staffID=st.staffID
ORDER BY d.dName asc, s.lName


--c
SELECT DISTINCT s.fName, s.lName
FROM STUDENT s, DEPARTMENT d 
WHERE s.deptCode=d.deptCode and d.dName='Computer Engineering'

--d
SELECT *
FROM STUDENT s
WHERE s.fName like '%at%'

--e
SELECT m.staffID, st.fName, st.lName
FROM STAFF st, MANAGER m
WHERE m.staffID=st.staffID and st.isMarried='1' and st.age>40 and st.noOfChildren>=2

--f
SELECT s.studentID, s.fName, s.lName, d.dName, di.dateOfGraduation
FROM STUDENT s, DEPARTMENT d, DIPLOMA di
WHERE s.deptCode=d.deptCode and s.studentID=di.studentID and CAST(datediff(day,21-05-2010,di.dateOfGraduation) / 365.2425 as smallint) > 0