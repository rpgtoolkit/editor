//-----------------------------------------------------------
//  RPGToolkit 3.1.0 Basic Startup Program.
//-----------------------------------------------------------

// Include the system library.
#include "system.prg"
autolocal(true);

// Some initial settings.
font("Comic Sans MS");
fontSize(18);
bold(true);
clear();

// Default menu and battle systems.
menuGraphic("layout.png");
fightMenuGraphic("mwin.jpg");
winGraphic("mwin.jpg");

// Play a music file.
mediaPlay("vip - title2.mid");


// Create a cursor map to allow the user to select an option.
// Enter a while loop to allow the cursor map to be rerun if
// the user cancels loading a saved game. If "New Game" or 
// "Quit" is chosen, the loop only runs once.

while (true)
{
	// Call a method to draw the title.
	drawTitle();
	
	// Place the menu options on the screen.
	text(17, 10.5, "New Game");
	text(17, 12, "Load Game");
	text(17, 13.5, "Quit");
	
	// Create a cursor map.
	cMap = createCursorMap();

	// Create cursor map points next to the menu options.
	cursorMapAdd(295, 180, cMap);
	cursorMapAdd(295, 210, cMap);
	cursorMapAdd(295, 230, cMap);

	// Run the cursor map and obtain the user's choice.
	res = cursorMapRun(cMap);

	// Destroy the cursor map after use.
	killCursorMap(cMap);
	
	// Act on the user's choice.
	switch (res)
	{
		case (0)
		{
			// New game: run the intro method.
			intro();
			end();
		}
		case (1)
		{
			// Load game. Show load screen and obtain chosen file.
			file = dirSav("Select a saved file to load");

			if (file ~= "CANCEL")
			{
				// "CANCEL" returned if the user cancelled.
				load(file);
				end();
			}
		}
		case (2)
		{
			// Exit to windows.
			windows();
		}
	}
}


//-----------------------------------------------------------
// Draw the title sequence
//-----------------------------------------------------------
method drawTitle()
{
	// Clear any previous images and set up a title screen.
	clear();
	text(3, 3, "Test Game");
	// bitmap("title.gif");
}

//-----------------------------------------------------------
// Run the game's introduction
//-----------------------------------------------------------
method intro()
{
	// Show the story and wait until the user presses
	// a key before finishing.
	mwin("Your story goes here...");
	pause();	// The pause() method is in the system library.
}
