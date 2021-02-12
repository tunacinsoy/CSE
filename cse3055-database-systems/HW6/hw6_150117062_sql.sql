--- Fall 2020 – CSE3055 Database Systems Homework #6
--- Author: Tuna Cinsoy
--- This SQL file creates a "Turkish Super League" table by given date input.
--- Example Run: exec sp_GetStandingsUpToDate '2014-07-31'
--- Example Run: exec sp_GetStandingsUpToDate '2014-05-26'
--- Date has to be between 2013-08-16 and 2014-07-31 otherwise it will raise an error.

CREATE PROCEDURE sp_GetStandingsUpToDate @aDate datetime
as 
begin
	if (@aDate > '2014-07-31' or @aDate < '2013-08-16')
	begin
	raiserror ('Invalid date!',15,1)
	end
	else 
	begin



DECLARE @tclStandings TABLE(Name nvarchar(50), GP smallint, W smallint, T smallint, L smallint, GF smallint, GA smallint, GD smallint, Pts smallint)


INSERT INTO @tclStandings
-- GP (Games Played): the number of matches that a team has played.
-- Get Total Count of ALL Matches
SELECT homeTeam.Name, homeTeam.totalMatchHome + awayTeam.totalMatchAway as totalMatchCount, 0,0,0,0,0,0,0
FROM
-- Get Total Count of Home Matches
(SELECT count(*) as totalMatchHome, t.Name, t.TeamID
FROM Match m, Team t
WHERE m.HomeTeamID = t.TeamID and m.DateOfMatch < @aDate
GROUP BY t.Name,t.TeamID) as homeTeam,

-- Get Total Count of Away Matches
(SELECT count(*) as totalMatchAway, t.Name, t.TeamID
FROM Match m, Team t
WHERE m.VisitingTeamID = t.TeamID and m.DateOfMatch < @aDate
GROUP BY t.Name, t.TeamID) as awayTeam  

WHERE homeTeam.TeamID = awayTeam.TeamID


-- GF (Goals For/Forward): the number of goals scored of a team in all games that have been played, including own goals of the other team.
UPDATE @tclStandings 
SET GF = gf.totalGoal
FROM @tclStandings t, 
(SELECT sum(totalGoal.Goals) as totalGoal, totalGoal.Name

FROM

-- Normal goal count
(SELECT count(*) as Goals, t.Name
FROM Match m, Goals g, PlayerTeam pt, Team t
WHERE g.IsOwnGoal = 0 and g.MatchID = m.MatchID and pt.Season = '13-14' and pt.PlayerID = g.PlayerID and m.DateOfMatch < @aDate and pt.TeamID = t.TeamID
GROUP BY t.Name

UNION ALL

-- Teams who scored own Goal at their own field and their opponents
SELECT count(*) as ownGoalsInHome,  t2.Name as visitingTeam
FROM Match m, Goals g, PlayerTeam pt,  Team t1, Team t2
WHERE m.HomeTeamID = pt.TeamID and g.IsOwnGoal=1 and g.MatchID = m.MatchID and pt.Season = '13-14' and pt.PlayerID = g.PlayerID and m.DateOfMatch < @aDate and pt.TeamID = t1.TeamID and t2.TeamID = m.VisitingTeamID
GROUP BY t2.Name 

UNION ALL

-- Teams who scored own Goal at away field and their home team opponents
SELECT count(*) as ownGoalsInAway, t2.Name as HomeTeam
FROM Match m, Goals g, PlayerTeam pt,  Team t1, Team t2
WHERE m.VisitingTeamID = pt.TeamID and g.IsOwnGoal=1 and g.MatchID = m.MatchID and pt.Season = '13-14' and pt.PlayerID = g.PlayerID and m.DateOfMatch < @aDate and pt.TeamID = t1.TeamID and t2.TeamID = m.HomeTeamID
GROUP BY t2.Name) as totalGoal

GROUP BY totalGoal.Name) gf
WHERE t.Name = gf.Name



-- GA (Goals Against): the number of goals scored by the other teams in all games that have been played, including own goals of the team.
UPDATE @tclStandings 
SET GA = ga.totalYenilenGol
FROM @tclStandings t, 
(SELECT sum(yenilenGol.evdeYenilenGol) as totalYenilenGol, yenilenGol.Name
FROM
(SELECT count(*) as evdeYenilenGol, t1.Name
FROM Match m, Goals g, PlayerTeam pt, Team t1, Team t2
WHERE g.IsOwnGoal = 0 and m.DateOfMatch < @aDate and m.MatchID = g.MatchID and g.PlayerID = pt.PlayerID and pt.Season = '13-14' and pt.TeamID = t2.TeamID and m.HomeTeamID = t1.TeamID and m.VisitingTeamID = t2.TeamID
GROUP BY t1.Name

UNION ALL

SELECT count(*) as deplasmandaYenilenGol, t1.Name
FROM Match m, Goals g, PlayerTeam pt, Team t1, Team t2
WHERE g.IsOwnGoal = 0 and m.DateOfMatch < @aDate and m.MatchID = g.MatchID and g.PlayerID = pt.PlayerID and pt.Season = '13-14' and pt.TeamID = t2.TeamID and m.HomeTeamID = t2.TeamID and m.VisitingTeamID = t1.TeamID
GROUP BY t1.Name

UNION ALL

-- Own Goal Situations
SELECT count(*) as ownGoals, t1.Name as Team
FROM Match m, Goals g, PlayerTeam pt,  Team t1
WHERE g.IsOwnGoal = 1 and m.DateOfMatch < @aDate and m.MatchID = g.MatchID and g.PlayerID = pt.PlayerID and pt.Season = '13-14' and t1.TeamID = pt.TeamID
GROUP BY t1.Name) yenilenGol

GROUP BY yenilenGol.Name) ga
WHERE ga.Name = t.Name


-- GD (Goals Difference): GF-GA.
UPDATE @tclStandings 
SET GD = GF - GA

-- W (Wins): the number of wins of a team in all games that have been played. T (Ties/Deuce): the number of ties of a team in all games that have been played. L (Losts): the number of losts of a team in all games that have been played.

-- Declaring 'matches' table, this table will hold necessary information for further tasks
DECLARE @matches TABLE(matchID int ,homeTeamID int, homeTeamGoal int, visitingTeamID int, visitingTeamGoal int, winnerID int, loserID int)

-- Initializing 'matches' table
INSERT INTO @matches
SELECT m.matchID,t1.TeamID, 0, t2.TeamID, 0, 0, 0
FROM Match m, Team t1, Team t2
WHERE m.DateOfMatch < @aDate and m.HomeTeamID = t1.TeamID and m.VisitingTeamID = t2.TeamID

-- The teams which scored goals at their own field (not ownGoal)
UPDATE @matches
SET homeTeamGoal = htg.totalGoal
FROM @matches m,
(SELECT g.MatchID, pt.TeamID, count(*) as totalGoal
FROM Goals g, PlayerTeam pt
WHERE g.PlayerID = pt.PlayerID and pt.Season = '13-14' and g.IsOwnGoal = 0
GROUP BY g.MatchID, pt.TeamID) as htg
WHERE htg.TeamID = m.homeTeamID and htg.MatchID = m.matchID

-- The teams which scored goals at away field (not ownGoal)
UPDATE @matches
SET visitingTeamGoal = atg.totalGoal
FROM @matches m,
(SELECT g.MatchID, pt.TeamID, count(*) as totalGoal
FROM Goals g, PlayerTeam pt
WHERE g.PlayerID = pt.PlayerID and pt.Season = '13-14' and g.IsOwnGoal = 0
GROUP BY g.MatchID, pt.TeamID) as atg
WHERE atg.TeamID = m.visitingTeamID and m.matchID = atg.MatchID

-- Own Scored Goals by home team, these goals will go to away team
UPDATE @matches
SET visitingTeamGoal = visitingTeamGoal + htg.totalGoal
FROM @matches m,
(SELECT g.MatchID, pt.TeamID, count(*) as totalGoal
FROM Goals g, PlayerTeam pt
WHERE g.PlayerID = pt.PlayerID and pt.Season = '13-14' and g.IsOwnGoal = 1
GROUP BY g.MatchID, pt.TeamID) as htg
WHERE htg.TeamID = m.homeTeamID and htg.MatchID = m.matchID

-- Own Scored Goals by away team, these goals will go to home team
UPDATE @matches
SET homeTeamGoal = homeTeamGoal + atg.totalGoal
FROM @matches m,
(SELECT g.MatchID, pt.TeamID, count(*) as totalGoal
FROM Goals g, PlayerTeam pt
WHERE g.PlayerID = pt.PlayerID and pt.Season = '13-14' and g.IsOwnGoal = 1
GROUP BY g.MatchID, pt.TeamID) as atg
WHERE atg.TeamID = m.visitingTeamID and m.matchID = atg.MatchID

-- Updating matches table to find out which team is the winner of the match
UPDATE @matches
SET winnerID = 
CASE 
WHEN homeTeamGoal > visitingTeamGoal THEN homeTeamID
WHEN visitingTeamGoal > homeTeamGoal THEN visitingTeamID ELSE 0 END

-- Updating matches table to find out which team is the winner of the match
UPDATE @matches
SET loserID = 
CASE 
WHEN homeTeamGoal > visitingTeamGoal THEN visitingTeamID
WHEN visitingTeamGoal > homeTeamGoal THEN homeTeamID ELSE 0 END


-- Entering win count to main table
UPDATE @tclStandings
SET W = totalWin 
FROM @tclStandings tcl,
(SELECT count(*) as totalWin, t1.Name as teamName
FROM @matches m, Team t1
WHERE m.winnerID = t1.TeamID
GROUP BY t1.Name) as winnerTable
WHERE tcl.Name = winnerTable.teamName

-- Entering lose count to main table
UPDATE @tclStandings
SET L = totalLose
FROM @tclStandings tcl,
(SELECT count(*) as totalLose, t1.Name as teamName
FROM @matches m, Team t1
WHERE m.loserID = t1.TeamID
GROUP BY t1.Name) as loserTable
WHERE tcl.Name = loserTable.teamName

-- Entering tie count to main table
UPDATE @tclStandings
SET T = GP - (W + L)

-- Entering points to main table
UPDATE @tclStandings
SET Pts = W * 3 + T * 1

-- Printing whole table
SELECT RANK() OVER(ORDER BY PTS desc, GD desc, GF desc) as Pos, *
FROM @tclStandings

end
end