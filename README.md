# beusable
# Guidelines
1. Clone repository
2. Import project to intellij (via pom.xml)
3. Run MarcinApplication

# Task description
Build a room occupancy optimization tool for one of our hotel clients! Our customer has a
certain number of free rooms each night, as well as potential guests that would like to book a
room for that night.
Our hotel clients have two different categories of rooms: Premium and Economy. Our hotels
want their customers to be satisfied: they will not book a customer willing to pay over EUR
100 for the night into an Economy room. But they will book lower paying customers into
Premium rooms if these rooms would be empty and all Economy rooms will be filled by low
paying customers. Highest paying customers below EUR 100 will get preference for the
“upgrade”. Customers always only have one specific price they are willing to pay for the
night.
Please build a small API that provides an interface for hotels to enter the numbers of
Premium and Economy rooms that are available for the night and then tells them
immediately how many rooms of each category will be occupied and how much money they
will make in total. Potential guests are represented by an array of numbers that is their
willingness to pay for the night.
Use the following raw json file as mock data for potential guests in your tests (include the
downloaded file in the project or get and parse it from the github directly): gist

Test results you should get:
Test 1
Free Premium rooms: 3
Free Economy rooms: 3
Usage Premium: 3 (EUR 738)
Usage Economy: 3 (EUR 167)
Test 2
Free Premium rooms: 7
Free Economy rooms: 5
Usage Premium: 6 (EUR 1054)
Usage Economy: 4 (EUR 189)
Test 3
Free Premium rooms: 2
Free Economy rooms: 7

Usage Premium: 2 (EUR 583)
Usage Economy: 4 (EUR 189)
Test 4 // different that test in your task, because I think that's mistake in description :) 
Free Premium rooms: 10
Free Economy rooms: 1
Usage Premium: 9 (EUR 1221)
Usage Economy: 1 (EUR 22)

# possible improvements
1. Mock response from github to check others bids configurations
2. Move github address to properties
3. Create more sophisticated package structure (in this so small task I propose "package by feature")
4. Change response from internal server error to some other status (It's depends of business cases)