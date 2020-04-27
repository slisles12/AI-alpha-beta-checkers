Running the program
1. have javafx installed
2. have a recent compiler installed

Playing the game
1. start the program
2. to play Human vs Human press that button
	a. press button you want to move and then the rectangle you want to move it to
3. to play Human vs AI press that button
	a. The game may appear to be delayed at significant depth in Human vs AI mode but it will execute 
4. to play AI vs AI press that button
5. The game may appear stuck on your turn, however the majority of the time this is because you are required to make a mandatory jump that you might not be seeing 


Changing the game
1. changing the depth in the MinValue and MaxValue is possible by changing the base case return conditions in those methods
2. i would not recommend changing the depth beyond 6 as the performance is doable but slow 
2. to look at the scoring mechanics, the getTotalScore method in the board class is where the scoring mechanics are
