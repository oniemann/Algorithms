This project provides an application for the "Weighted Quick Union" algorithm, in which trees are created in lgN time to identify which nodes are attached to each other.

From program descriptions:

Percolation.java
Creates data structure "Quick Union", which keeps track of indexes
in a grid belonging to the same subset. This is then mapped to
a corresponding NxN grid containing a "virtual top" node and a "virtual
bottom" node for simplification of checking for percolation. The grid
is then randomly selected to open and the QU data struct keeps track
of if this index should be chained together with any of its neighboring
tiles by condition of if they are open. When a path from the "virtual
top" and "virtual bottom" is found, percolation has been achieved.

PercolationStats.java
The following program runs Percolation simulation of an NxN grid T
times and then derives the average percentage of tiles in the grid
which need to be flipped in order to create a percolated system.
Other statistics are also provided.

