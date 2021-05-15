# Train network with scheduling

# Problem Statement:
Control program for a mail train system.

The system takes as inputs:
- A graph containing a set of nodes, N, and edges, E : N → N. Each edge has a (positive) journey time e_t – this is the number of time steps that it takes a train to travel along an edge (in either direction). 
- Each node contains a set of packages. Each package has an integer weight, W_p and a destination node D_p. 
- You have a set of trains Q, each of which has an integer capacity, C_q. Each train is located at a starting node.
- Trains start off empty.


Your program should output a list of moves. Each move has:
- A time (t) at which the move happens.
- A node (n) at which the move happens.
- A train, (q) making the move.
- A set of packages (O) to move onto the train at this node.
- A set of packages (P) to move off the train at this node.
- An edge (e) for the train to move down.


Each move, the train t must be at node n. It offloads the packages O onto the node, picks up the packages P and then travels down edge e, arriving at t + e_t
- O and P may be empty.
- You cannot overload the train (the sum of the weights of all packages in P must be smaller than or equal to C_q)
- E may be the special value ‘null’, meaning that the train stays at n.
- O must be a subset of the letters currently located at n.
- Once a train has started down an edge, E: n1 → n2 it cannot be called back. It will arrive at n2 in e_t time units.
- A train can only be in one place at once. A train may only traverse an edge if it is at one node or the other of that edge.
- No train may ever be overloaded.
- Any number of trains can be at the same node at the same time.
- A train can carry any number of letters, so long as the total weight of the letters is <= the capacity of the train.

# Assumption:
- Any number of trains may traverse the same edge at the same time (this is somewhat unrealistic, but makes the problem easier to solve)


At the end of your sequence of moves, all letters must have been moved off a train to their destination nodes.
It would obviously be nice to generate a minimal (or at least relatively short) sequence of moves, but correctness is more important than optimality.
Optimality, is measured by the maximum time of any move (ie. we are interested in schedules which minimise the time it takes to deliver the letters rather than that minimise the number of moves).

// example input
3           // number of stations
A           // station name
B           // station name
C           // station name
2           // number of routes
E1,A,B,3       // route from A to B that takes 3 units of time
E2,B,C,1       // route from B to C that takes 1 unit of time
1           // number of deliveries to be performed
P1,A,C,5    // package P1 with weight 5 located currently at
station A that must be delivered to station C
1           // number of trains
Q1,B,6      // train T1 with capacity 6 located at station B
// example output
Train :Q1 moving from:B to:A Packages: Total Weight:0 Total Capacity:6 Time Taken:3 units
Order :P1 picked by Train:Q1 at Station:A
Train :Q1 moving from:A to:B Packages:P1  Total Weight:5 Total Capacity:6 Time Taken:3 units
Train :Q1 moving from:B to:C Packages:P1  Total Weight:5 Total Capacity:6 Time Taken:1 units
Order :P1 delivered by Train:Q1 at Station:C
All Orders delivered by Train:Q1 , Setting train to idle at Station:C

## Solution (Version 1.0) - Simple solution
Step 1: Calculate all possible shortest paths from all stations to other stations. (Used Dijkstra's algorithm, A* can also be used)
Step 2: Iterate via package list and check which train can pick the package (as per Weight limit)
Step 3: Train starts moving to pick the package and is no longer available to pick new package.
Step 4: Train picks the package and moves via shortest path to deliver the package to destination.
Step 5: Repeat from Step 2.
