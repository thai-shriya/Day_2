# There are different types of caching:

Caching is a mechanism to store frequently accessed data in a cache for improved response time of fetching data from the database.
Caching is used to reduce load on backend servers.
Different types of caching:
FIFO: First-in First-out cache - Data in the cache that is there for the longest period of time is removed first from the cache.
LIFO: Last-in First-out cache - Latest added items to the cache are removed first.
LRU: Least Recently used cache - Data that has not been used often is removed from the cache when cache reaches its limit (Assuming that least recently used cannot be accessed very soon)
MRU: Most Recently used cache - Data that is recently accessed is removed first when the cache is full (Assuming that most recently accessed data might not be accessed very soon)
LFU: Least Frequently used cache - Data that is least frequently accesssed is removed from cache when it is full. It is done by keeping track of a counter for each item in the cache.

Example of LRU cache:
If there is a cache with capacity 3

Access items order:

Item A
Item B
Item C
Item A
Item D

Then, when the Item D is accessed, we need to remove the least recently used item. So, in the cache we will have item A, C, D.
