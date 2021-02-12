--- This program is built for storing the logs of operations that have occurred in table called "Goal" at database called "TurkishSuperLeague"
--- Author: Tuna Cinsoy


--- Creating Table Standings
Create Table Standings (
Pos tinyint,
[Team Name] nvarchar(30),
GP tinyint,
W tinyint,
T tinyint,
L tinyint,
GF smallint,
GA smallint,
GD smallint,
Pts tinyint
)

--- Creating Table TransactionLog
Create Table TransactionLog (
LogID int identity(1,1) primary key,
LogTime datetime,
LogType char(1),
BeforeState nvarchar(500),
AfterState nvarchar(500),
)

--- Filling Standings Table with our predeclared stored procedure
INSERT INTO Standings
exec sp_GetStandingsUpToDate '2014-07-15'

-- When we execute the trigger, we firstly delete our standings table to recreate it
DELETE FROM Standings

-- Printing Standings Table
SELECT * FROM Standings


--  Trigger "Trg_RearrangeStandings"
GO
CREATE TRIGGER Trg_RearrangeStandings ON Goals
AFTER INSERT, DELETE, UPDATE
AS
BEGIN 
	-- Declaring variables which will be initialized and used at further queries
	DECLARE @triggeredTime int
	DECLARE @playerID int
	DECLARE @matchID int
	DECLARE @isEntryConsistent int
	DECLARE @logTime datetime
	DECLARE @logType char(1)
	DECLARE @beforeState nvarchar(500)
	DECLARE @afterState nvarchar(500)

	-- UPDATE CASE
	IF ((SELECT COUNT(*) FROM deleted) = 1 and (SELECT COUNT(*) FROM inserted) = 1)
	BEGIN
		-- Initializing variables from inserted table
		SET @triggeredTime = (SELECT inserted.Minute FROM inserted)
		SET @playerID = (SELECT inserted.PlayerID FROM inserted)
		SET @matchID = (SELECT inserted.MatchID FROM inserted)
		
		-- Checking given input whether correct or not. (player has to belong one of the teams in the match)
		SET @isEntryConsistent = (SELECT count(*)
								FROM Goals g, PlayerTeam pt, Match m
								WHERE g.PlayerID = pt.PlayerID and g.MatchID = m.MatchID and (pt.TeamID = m.HomeTeamID or pt.TeamID = m.VisitingTeamID) 
								and @matchID = m.MatchID and g.PlayerID = @playerID and pt.Season = '13-14')
		
		-- Time of the match has to be between 1 and 90
		IF (@triggeredTime < 1 or @triggeredTime > 90)
		BEGIN
			ROLLBACK TRANSACTION
			RAISERROR('Time attribute cannot be larger than 90 or less than 1.',15,1)
			RETURN
		END

		-- If the entry is not consistent, i.e, player is not present in the match, we stop executing the trigger
		IF (@isEntryConsistent = 0)
		BEGIN
			ROLLBACK TRANSACTION
			RAISERROR('Your input parameters are inconsistent. Please check them again.',15,1)
			RETURN
		END

		-- Inserting new row to TransactionLog table with appropriate values
		SET @logType = 'U'
		SET @beforeState = (SELECT CONCAT(d.MatchID, ';', d.PlayerID, ';', d.IsOwnGoal, ';', d.Minute) FROM deleted d)
		SET @afterState = (SELECT CONCAT(i.MatchID, ';', i.PlayerID, ';', i.IsOwnGoal, ';', i.Minute) FROM inserted i)
		
		INSERT INTO TransactionLog
		SELECT SYSDATETIME(), @logType, @beforeState, @afterState

	END
	
	-- DELETE CASE
	ELSE IF ((SELECT COUNT(*) FROM deleted) = 1 and (SELECT COUNT(*) FROM inserted) = 0)
	BEGIN
		-- Initializing variables from deleted table
		SET @triggeredTime = (SELECT deleted.Minute FROM deleted)
		SET @playerID = (SELECT deleted.PlayerID FROM deleted)
		SET @matchID = (SELECT deleted.MatchID FROM deleted)
		
		-- Checking given input (player has to belong one of the teams in the match)
		SET @isEntryConsistent = (SELECT count(*)
								FROM Goals g, PlayerTeam pt, Match m
								WHERE g.PlayerID = pt.PlayerID and g.MatchID = m.MatchID and (pt.TeamID = m.HomeTeamID or pt.TeamID = m.VisitingTeamID) 
								and @matchID = m.MatchID and g.PlayerID = @playerID and pt.Season = '13-14')
		
		-- Time of the match has to be between 1 and 90
		IF (@triggeredTime < 1 or @triggeredTime > 90)
		BEGIN
			ROLLBACK TRANSACTION
			RAISERROR('Time attribute cannot be larger than 90 or less than 1.',15,1)
			RETURN
		END

		-- If the entry is not consistent, i.e, player is not present in the match, we stop executing the trigger
		IF (@isEntryConsistent = 0)
		BEGIN
			ROLLBACK TRANSACTION
			RAISERROR('Your input parameters are inconsistent. Please check them again.',15,1)
			RETURN
		END

		-- Inserting new row to TransactionLog table with appropriate values
		SET @logType = 'D'
		SET @beforeState = (SELECT CONCAT(d.MatchID, ';', d.PlayerID, ';', d.IsOwnGoal, ';', d.Minute) FROM deleted d)
		SET @afterState = NULL

		INSERT INTO TransactionLog
		SELECT SYSDATETIME(), @logType, @beforeState, @afterState

	END

	-- INSERT CASE
	ELSE IF ((SELECT COUNT(*) FROM deleted) = 0 and (SELECT COUNT(*) FROM inserted) = 1)
	BEGIN 
		-- Initializing variables from inserted table
		SET @triggeredTime = (SELECT inserted.Minute FROM inserted)
		SET @playerID = (SELECT inserted.PlayerID FROM inserted)
		SET @matchID = (SELECT inserted.MatchID FROM inserted)
		
		-- Checking given input whether correct or not. (player has to belong one of the teams in the match)
		SET @isEntryConsistent = (SELECT count(*)
								FROM Goals g, PlayerTeam pt, Match m
								WHERE g.PlayerID = pt.PlayerID and g.MatchID = m.MatchID and (pt.TeamID = m.HomeTeamID or pt.TeamID = m.VisitingTeamID) 
								and @matchID = m.MatchID and g.PlayerID = @playerID and pt.Season = '13-14')
		
		-- Time of the match has to be between 1 and 90
		IF (@triggeredTime < 1 or @triggeredTime > 90)
		BEGIN
			ROLLBACK TRANSACTION
			RAISERROR('Time attribute cannot be larger than 90 or less than 1.',15,1)
			RETURN
		END

		-- If the entry is not consistent, i.e, player is not present in the match, we stop executing the trigger
		IF (@isEntryConsistent = 0)
		BEGIN
			ROLLBACK TRANSACTION
			RAISERROR('Your input parameters are inconsistent. Please check them again.',15,1)
			RETURN
		END

		-- Inserting new row to TransactionLog table with appropriate values
		SET @logType = 'I'
		SET @beforeState = NULL
		SET @afterState = (SELECT CONCAT(i.MatchID, ';', i.PlayerID, ';', i.IsOwnGoal, ';', i.Minute) FROM inserted i)
		
		INSERT INTO TransactionLog
		SELECT SYSDATETIME(), @logType, @beforeState, @afterState


	END

	-- Updating Standings Table
	BEGIN
	DELETE FROM Standings
	INSERT INTO Standings
		exec sp_GetStandingsUpToDate '2014-07-15'
	END

END