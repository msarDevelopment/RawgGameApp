# RawgGameApp

<img height="150" src="https://user-images.githubusercontent.com/117776720/216512006-acebdda0-4b27-4817-9712-641f57438dd8.png" />

RawgGameApp is an application for exploration and discovery of video games. User can select his favourite genres to narrow listed games to his preferences.

This app uses:
- Jetpack Navigation Component
- MVVM architecture
- LiveData
- Paging 3 library
- Firebase

All data and images used in this application are owned and provided by [RAWG](https://www.rawg.io).

<br/><br/>

If you want to try the app out, please add your API key to the ```local.properties``` file in the root directory of the project in the following format:
```
API_KEY=YOUR_API_KEY
```

<br/><br/>

Navigation graph of the application:

<img height="640" src="https://user-images.githubusercontent.com/117776720/216512303-71a00a73-80d8-4306-add0-83ff808d5267.png" />

<br/><br/>

Home screen of the application is DiscoverGamesFragment where user can discover and explore games based on selected genres.

In there, games are displayed using Paging 3 library and fetched from https://api.rawg.io/api/games?genres={genre0_id},{genre1_id},{genreN_id} or from https://api.rawg.io/api/games. Those two calls are conditional meaning if user has selected some genres, https://api.rawg.io/api/games?genres={genre0_id},{genre1_id},{genreN_id} will be called. If no genres are selected, https://api.rawg.io/api/games will be called.

<img height="640" src="https://user-images.githubusercontent.com/117776720/216504234-350aaeb2-45f8-40c8-871d-9ea8e86f8034.jpg" />

<br/><br/>

On first start of the app, application shows OnboardingFragment in which user can select genres he is interested in and also later on he can change the selection in SettingsFragment.

| OnboardingFragment | SettingsFragment |
| ------------- | ------------- |
| <img height="640" src="https://user-images.githubusercontent.com/117776720/216504365-afd2c371-9455-4991-8c31-f78e498312f8.jpg" /> | <img height= "640" src="https://user-images.githubusercontent.com/117776720/216504400-fc402010-01ba-49eb-8c44-16898ace832c.jpg" />

<br/><br/>

Also, here user can open GenreDetailsBottomSheetFragment to see more info about some genre.

| GenreDetailsBottomSheetFragment peek  | GenreDetailsBottomSheetFragment full |
| ------------- | ------------- |
| <img height="640" src="https://user-images.githubusercontent.com/117776720/216504833-c950f0df-0a7b-4f9c-bca5-50275d15eeda.jpg" /> | <img height= "640" src="https://user-images.githubusercontent.com/117776720/216504872-e207f15c-4440-4257-b7a5-7ea66b86289d.jpg" />

<br/><br/>

User can select as many genres as he wants but he can also select none. In case user doesn't select any genres, app would not block him from entering DiscoverGamesFragment but also, it would not show some empty state either. Instead, it will show all games.

That decision was made because I consider that blocking entry to main screen of the application or showing empty state on first entry could have negative impact on the user experience. It is better to show something rather than nothing to avoid losing user's interest and attention because that can make user to stop using the app.

After onboarding is done, user can navigate from DiscoverGamesFragment to the SettingsFragment to change his genre selection. When selection changes, upon return to the DiscoverGamesFragment list of games is automatically reloaded with games from newest selection.

| Changing genre selection |
| ------------- |
| <video src="https://user-images.githubusercontent.com/117776720/216505641-8cfd46c4-43d1-4a85-8656-041499f48ee0.mp4"> | 
	
<br/><br/>
	
User can tap on any game in list of DiscoverGamesFragment to see more details about that game.
	
| Open game details |
| ------------- |
| <video src="https://user-images.githubusercontent.com/117776720/216506701-57ca7c72-b13d-40fc-b953-8f4e966bc753.mp4">  | 

<br/><br/>

Since used RAWG api calls provide various interesting data about games, some were used to make some additional features to further enrich user experience and make application interesting to use. In DiscoverGamesFragment (on games list items) and GameDetailsFragment (in game details), two badges can appear.
	
First one is fire icon which represents "Hot game" and that means that game is currently being played by significant number of people who own it. 
Second one is flag icon which represents "Challenging game" which means that that game was beaten by very small number of people who own it. I saw this as an opportunity to make the application more engaging and allow user to connect to it.

For example, "Hot game" could make user try some game because it has active community and "Challenging game" can make user who likes challenges to try that game out or make him proud if he is already in a small minority of people who have beaten it.


| Hot game  | Challenging game |
| ------------- | ------------- |
| <img height="640" src="https://user-images.githubusercontent.com/117776720/216507354-1b8f6b39-128d-4a86-b684-e28f807638e7.gif">  | <img height="640" src="https://user-images.githubusercontent.com/117776720/216507401-8bff0e39-f638-4b90-a266-693633e79572.gif">

<br/><br/>

Application also uses Firebase and it uses two tools from it:
- Remote Config
- Crashlytics

To demonstrate usage of Remote Config, there is a Firebase icon in DiscoverGamesFragment which upon tapping on it, opens a dialog with message that can be set on Firebase. Also, visibility of that icon can be set from Firebase as well.

| Firebase message in application  | Firebase message in console |
| ------------- | ------------- |
| <img height="640" width="300" src="https://user-images.githubusercontent.com/117776720/216507651-43c94eea-a6bc-44a9-8e20-269e236ec259.jpg">  | <img width="600" src="https://user-images.githubusercontent.com/117776720/216507693-223ce455-b71d-4343-8e52-5705c27d79b9.png">
	
<br/><br/>

For Crashlytics, network errors (no internet, bad connection, server errors, timeouts etc.) were deliberately not handled to provide consistent and reliable way to send various crashes to Firebase to showcase its successful integration.

| Crashlytics console  | Crashlytics mail notification |
| ------------- | ------------- |
| <img height="640" src="https://user-images.githubusercontent.com/117776720/216507898-88ca48f3-b86c-4347-a584-8be51b2be8e9.png">  | <img src="https://user-images.githubusercontent.com/117776720/216507931-ab5b638a-40ae-4197-b0c0-20b51e53f873.png">

<br/><br/>

Since one of the main requirements of this application is to not show onboarding after first start, there is an UI test that tests that. Remembering is onboarding done is done via SharedPreferences, so this test sets required key value pair to enable showing of onboarding and then completes onboarding, kills the app and then opens the app again to see is now DiscoverGamesFragment first screen shown.
	
| OnboardingFlowTest |
| ------------- |
| <video src="https://user-images.githubusercontent.com/117776720/216508206-4acf5f6c-b236-4547-9a3b-5b5301912c60.mp4">  | 


<br/><br/>


Because there is limited number of api calls, additional effort was put into making as small number of calls as possible while retaining good user experience.

Here are some examples how that was achieved:

Since home screen of application is based on fetching games from network with Paging 3 library and since there is limited monthly number of api calls, main goal was to reduce any other calls as much as possible. Additionally, Paging 3 was set to load maximum number of items per request (40) because it isn't that many items per load that would result in unnecessarily big list in Recycler View but it greatly reduces number of calls compared to when using default number of items (20).

Cutting number of calls was mainly done by deciding which data can be considered as dynamic (fetch from network every time) and which can be considered as static (get from database).

Dynamic:
- list of games in DiscoverGamesFragment - it can be considered as dynamic because their order is set by the provider and it can be different at any time i.e. that order is set on some business rules of the provider that keeps content fresh and relevant so there is no advantage of saving order of the games to the database and deciding manually in which order to show them

Static:
- genre list in OnboardingFragment/SettingsFragment - genres can be considered very well defined so there is no significant changes to expect
- genre details in GenreDetailsBottomSheetFragment - just like genre list, there shouldn't be much relevant changes about genre details
- genre description in GenreDetailsBottomSheetFragment - same like above, this is just mentioned separately because it is fetched from separate call which is another reason to save it to database in order to avoid uneccessary calls
- game details in GameDetailsFragment - game details can also be considered static because there is not much data that would change over time and during the use of the application in long term, user will probably reopen same game many times, so saving games to the database can potentially save hundreds of api calls
- game screenshots in GameDetailsFragment - in order to show screenshots, additional api call would need to be made when opening game screen so saving them to the database also potentialy saves many api calls
	
<br/><br/>	

Optimizing number of api calls in OnboardingFragment/SettingsFragment:

Flow for storing genres is:
	
User opens OnboardingFragment/SettingsFragment
	
App checks is the number of genres in database 0

- if yes -> make https://api.rawg.io/api/genres call and save all genres to database

this way app will fetch genres from network only once and since OnboardingFragment and SettingsFragment both use genres from database, every other opening of SettingsFragment will fetch genres from database instead of making unnecessary api calls
- if no -> load genres from database and display them
	
<br/><br/>	
	
Optimizing number of api calls in GenreDetailsBottomSheetFragment:

From genres list, user can open details about some genre, GenreDetailsBottomSheetFragment.
Genre details consists of:
- Name of the genre
- Number of games in that genre
- Genre description
- Example games

Api call to get all genres has details about all genres in its response but it lacks genre description which was necessary to provide better user experience.

Genre description is fetched from another call, from call to fetch genre details https://api.rawg.io/api/genres/{genre_id} with genre id as path parameter. To further save number of calls, genre description call is called only when user opens genre details of some genre. It is then inserted into description column of corresponding genre already saved in the database.

Also, genre details has list of example games. In that list, only available game data valuable to the user is the game name but I didn't consider that to be enough to provide good user experience. So, decision was made to get main image of each game in the list of that genre and show that to the user. That was made possible by making api call for game details and saving that game to database when user opens genre details.

So, the flow for getting genre details is:
- load genre details from database (they are in database at this point because they were saved to database in previous step, where all genres were fetched) but check is genre's description empty, if it is, make https://api.rawg.io/api/genres/{genre_id} call and save description from response to the coresponding genre in the database
- since genre has its example games, for every game in those example games check whether it already exists in the database, if it doesn't make https://api.rawg.io/api/games/{game_id} call and save game to the database

This way, any new openings of genre details will fetch all data directly from database, thus saving many unnecessary calls.

If it was done without database, number of calls would be 1 genre details call + 6 game details call to get the game image for same static data every time user reopens same genre details.
With help of database, those 7 calls are made only first time user opens genre details.
Also, this way enhances reusability because when opening game details in later part of the app flow, user can potentialy open the game which was already saved when fetching it as example game for genre details, thus making that game load faster.

<br/><br/>

In GameDetailsFragment and DiscoverGamesFragment:
	
User can tap on any game in list of DiscoverGamesFragment to see more details about that game. That opens GameDetailsFragment which loads details of game in a way that it first checks the database whether that game is already saved in database. If not, it fetches game details with same api call that was used in GenreDetailsBottomSheetFragment (genre details screen where game api call was used to save games to database to use their images).

GameDetailsFragment consists of main image of the game and various information that https://api.rawg.io/api/games/{game_id} call provides. But still, it doesn't provide everything I envisioned for good experience in a way that could save api calls. For good user experience, I wanted to put screenshots of the game into that screen. It could be done via api call for fetching screenshots of the game which RAWG provides but that would mean additional api call for every game. But there is a better way.

When making call in DiscoverGamesFragment, every game item in that response has array called short_screenshots. In it there is a link for every image. So to maximize usage of calls and not to waste responses, decision was made to put every short_screenshots item from that call into table called screenshots which takes image link as id and additional field with id of the game to map screenshot to the game. In this way, with just one call all screenshots for games currently loaded in DiscoverGamesFragment are available straight from database when user opens GameDetailsFragment and thus avoiding additional call and longer wait to load screenshots.
