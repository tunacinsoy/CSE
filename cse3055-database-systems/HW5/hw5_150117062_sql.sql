use TurkishSuperLeague

--2
UPDATE Player 
SET Age = datediff(year,Birthdate,getdate())

--3
SELECT p.PlayerID, (p.FirstName + ' ' + p.LastName) as FullName
FROM Player p, PlayerTeam pt, Team t
WHERE p.PlayerID=pt.PlayerID and pt.TeamID=t.TeamID and t.Name = 'Beşiktaş' and p.age < (SELECT avg(p.age) FROM Player p) and p.FirstName not like '%nec%'

--4
UPDATE Team
SET Team.City = teamCity.City + ' #p' + CAST(teamPlayerCount.countOfPlayers as nvarchar) + ' #g' + CAST(goals.goalCount as nvarchar)
FROM

	(SELECT t.TeamID, t.City
	FROM TEAM t) as teamCity,

	(SELECT pt.TeamID, Count(pt.TeamID) as countOfPlayers
	FROM PlayerTeam pt
	WHERE pt.Season ='13-14'
	GROUP BY pt.TeamID) as teamPlayerCount,

	(SELECT t.TeamID, Count(t.TeamID) as goalCount
	FROM Goals g, PlayerTeam pt, Team t
	WHERE pt.PlayerID = g.PlayerID and pt.TeamID = t.TeamID and pt.Season = '13-14'
	GROUP BY t.TeamID) as goals

WHERE teamCity.TeamID = teamPlayerCount.TeamID and teamCity.TeamID = goals.TeamID and Team.TeamID = teamCity.TeamID


--5

SELECT mainTable.*, (totalMatchTable.totalPlayedMatchCount - totalScoredMatchTable.totalPlayedMatchThatScoredGoal) as notScoredMatchCount, CAST((mainTable.numberOfGoals * 1.0 / totalScoredMatchTable.totalPlayedMatchThatScoredGoal) as decimal(3,2)) as avgGoalPerScoredMatch
FROM 

(SELECT Top 10 g.PlayerID,p.FirstName, p.LastName , Count(g.playerID) as numberOfGoals
FROM Goals g, Player p
WHERE p.PlayerID = g.PlayerID 
GROUP BY g.PlayerID, p.FirstName, p.LastName 
ORDER BY Count(g.playerID) desc) as mainTable,			


(SELECT Count(*) as totalPlayedMatchCount, pt.PlayerID
FROM PlayerTeam pt, Match m
WHERE (pt.TeamID = m.HomeTeamID or pt.TeamID = m.VisitingTeamID) and pt.Season = '13-14' 
GROUP BY pt.PlayerID) as totalMatchTable,


(SELECT g.playerID, Count(DISTINCT g.MatchID) as totalPlayedMatchThatScoredGoal
FROM Goals g
GROUP BY g.PlayerID) as totalScoredMatchTable

WHERE mainTable.PlayerID = totalMatchTable.PlayerID and totalMatchTable.PlayerID = totalScoredMatchTable.PlayerID 
ORDER BY numberOfGoals desc



