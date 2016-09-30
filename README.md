
# Practice Fusion Challenge!

Here I will outline some of my thought process and possible problems we may encounter throughout the exercise. Some of them are trivial assumptions while others are made to greatly simply the code at the cost of failure in some extreme circumstances. Many extreme assumptions/shortcuts are taken given the 30 minute time limit.

## General Code Path
I have a map of specialties mapped to a list of doctors of that specialty. Given a doctor, we can get all of his peers (of the specialty), and sort them by their distance the doctor, with tiebreaker going to the higher rated physician. Map makes the getting of the list trivial, and the sorting is done using a priority queue that uses a custom comparator that does what I described above.

## Use case assumptions

There is many ways to approach this problem. Given that the doctors' information are stored in memory, I made the assumption that speed of getting doctors are fairly important. I also made the assumption there are not *too many* doctors. The current usecase specifically caters towards getting a sorted list of doctors by distance that are of the same specialty as the current doctor. If "similar doctors" can include multiple specialties (general surgeon vs neuro surgeon, etc), we can simply accomodate that by simily getting the list of doctors of different specialities and merging them.

I am assuming location is an x/y coordinate on a map. Currently this is the most costly part of calculation, especially if we start to have enormous amount of doctors. In addition, just because two locations are close together in coordinates does not mean it's closer in terms of travel distance. We could pass this calculation to a 3rd party like Google Maps, but that might become time consuming and a bottleneck for the operation. If instead of coordinates, we had discrete location (x hospital system, y hospital system, etc.), We can simply have the doctors pregrouped by these categories, in which case no computation is necessary.

## A completely different implementation
If the list of doctors does not change very often, the FASTEST way to perform this operation is to simply have a precomputed graph of doctors, where each doctor contains a list of doctors that he is "similar" to. I did not opt for this implementation because the getting of similar doctors simplifies to a line code getting his similars. The idea makes sense, as I imagine doctors probably don't change jobs too often, and getSimilarDoctors() seems like an operation that might be called frequently and needs to be pretty snappy.

If the speed of the computation does not need to be as fast, if we have a list of every doctor in the world, or if doctors changed jobs very frequently, we can simply put all the doctors in a some kind of relational detabase. Getting similar doctors and sorting them would simply become writing a specific query, and the computation can be done by the database, which is faster than doing it by hand in memory. 
