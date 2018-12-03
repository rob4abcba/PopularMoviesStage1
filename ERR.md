
blee (ABND) [Nov 30th at 2:56 AM]
Hey all, hoping I might get a response here. I just started AND recently and I’m having trouble with my Popular Movie Stage 1 app. My gridview is displaying my placeholder/error image and not the poster from themoviedb site.  However, my MovieDetailsActivity can display the poster.


5 replies
Curtis Getz (AND) [3 days ago]
your base image url in the Adapter has some errors.
You have https://image.tmb.org/t/p
instead of https://image.tmdb.org/t/p/ (edited)


Curtis Getz (AND) [3 days ago]
It might be better to have your BASE_URL as a public final static String in a utility class that you can reference wherever you need the URL to avoid issues like typos. Also if you have to change anything you can change it in one location instead of searching your app for every usage (edited)


Marc D (AND) [3 days ago]
to test, I just hard coded the link to one image at the start, until I got everything working, and used a downloaded xml file as well for the movie data. This kept the network issues out of the app building process until the end, where I just had to update the app to use the REST call for final testing before submitting for review. (edited)


blee (ABND) [3 days ago]
@Curtis Getz (AND) THANK YOU! I was going insane not understanding what I did wrong. I will move forward with a utility class to prevent this searching madness.


blee (ABND) [3 days ago]
@Marc D (AND) thanks for the testing advice! I will do that for the next project to catch these network issues earlier.

