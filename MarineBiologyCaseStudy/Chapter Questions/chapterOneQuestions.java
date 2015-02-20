public class chapterOneQuestions {
	
	/*
	Chapter One Exercises
	Exercise Set 1:
	1. yes
	2. no

	Analysis Question Set 1:
	1. The program models a body of water through a bounded blue rectangular plane and the grid represents the relative location.
	2. The fish can be in any uninhabited grid square within the outer boundaries of the bounded environment. No two fish can occupy the same square. 
	3. All fish do not face the same direction but the direction does matter because a fish can't move opposite of its direction in any given timestep and the fish moves from the square on its back. 
	4. A fish does move every step to one adjacent square either in the direction it is facing or to the side. Fish do not always move in the same direction and can't move backwards. 

	Exercise Set 2: 
	1.	Timestep	Fish's location		Fish's Direction	Did it move?	In what direction?	New location	New direction
	      1                4 3                down             yes               right             4 4             4 4
	      2                4 4                right            yes               up                3 4             3 4
	      3                3 4                up               yes               up                2 4             2 4
	      4                2 4                up               yes               up                1 4             1 4
	      5                1 4                up               yes               up                0 4             0 4


	2. My classmate (Will S.) did not have the same have the same results as I did.  From the single run you can conclude that the fish will probably never move backwards although I will need more data to be more accurate. 

	Analysis Question Set 2:
	1.  The test results did show that the previous conclusions about behavior were right. Therefore five steps was enough to discover patterns however more steps being observed would lead to more accurate data to conclude from. 
	2.	Timestep	Fish's location		Fish's Direction	Did it move?	In what direction?	New location	New direction
	    1                4 4                up               yes               up                3 4             3 4
	    2                3 4                up               yes               up                2 4             2 4
	    3                2 4                up               yes               up                1 4             1 4
	    4                1 4                up               yes               up                0 4             0 4
	    5                0 4                up               yes               left              0 3             0 3
	    6                0 3                left             yes               down              1 3             1 3
	    7                1 3                down             yes               right             1 4             1 4
	    8                1 4                right            yes               down              2 4             2 4
	    9                2 4                down             yes               down              3 4             3 4
	    10               3 4                down             yes               left              3 3             3 3
	    11               3 3                left             yes               up                2 3             2 3
	    12               2 3                up               yes               up                1 3             1 3
	    13               1 3                up               yes               right             1 4             1 4
	    14               1 4                right            yes               up                0 4             0 4
	    15               0 4                up               yes               left              0 3             0 3
	    16               0 3                left             yes               left              0 2             0 2
	    17               0 2                left             yes               down              1 2             1 2
	    18               1 2                down             yes               down              2 2             2 2
	    19               2 2                down             yes               down              3 2             3 2
	    20               3 2                down             yes               right             3 3             3 3

	Analysis Question Set 3:
	1. Locations in the environment are determined using ordered pairs where (like 5 6) where the first number represents the amount of boxes below the upper left box and the second number represents the boxes to the right of the upper left box. 

	Exercise Set 3:
	1. When I ran fish.dat, there were 6 fish and each seemingly moved in random directions (but never back) and no two fish occupied the same square. This is to be expected because the fishes follow similar behavior  as in onefish.dat with the only difference being they can't collide. 
	2. I made test file with two fish in a 8X8 grid and they behave similar to fish.dat by moving in random directions (never back) and never colliding. 

	UPLOAD THE DATA FILE THAT YOU MAKE FOR THE LAST PROBLEM TO YOUR GIT REPOSITORY.
	*/	
	
}
