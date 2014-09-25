ImageSearch
===========

Codepath assignment 2

This is an Android application which allows users to do image search using google and to select search filters and paginate results infinitely.

Time spent: 16 hours.

Completed user stories:
- [x] Required: User can enter a search query that will display a grid of image results from the Google Image API.
- [x] Required: User can click on "settings" which allows selection of advanced search options to filter results.
- [x] Required: User can configure advanced search filters such as:
 * Size (small, medium, large, extra-large)
 * Color filter (black, blue, brown, gray, green, etc...)
 * Type (faces, photo, clip art, line art)
 * Site (espn.com)
- [x] Required: Subsequent searches will have any filters applied to the search results.
- [x] Required: User can tap on any image in results to see the image full-screen
- [x] Required: User can scroll down “infinitely” to continue loading more image results (up to 8 pages)
- [x] Advanced: Robust error handling, check if internet is available, handle error cases, network failures.
- [x] Advanced: Use the ActionBar SearchView or custom layout as the query box instead of an EditText
- [x] Advanced: User can share an image to their friends or email it to themselves
- [x] Advanced: Replaced Filter Settings Activity with a lightweight modal overlay. 
- [x] Advanced: Improved the user interface and experiment with image assets and/or styling and coloring
- [x] Bonus: Use the StaggeredGridView to display improve the grid of image results
- [x] Bonus: User can zoom or pan images displayed in full-screen detail view
             
Libraries used : android-async-http-1.4.5.jar, picasso-2.3.4.jar, Stocard-StaggereGridViewLibrary, TouchImageView,actionbarsherlock.

Walkthrough of all user stories:

Gif Image


![Alt Text](GridImageSearch.gif)

