# DATABASE ACID PROPERTIES

Trasactions access the data to read and write from the database. To maintain consistency, ACID properties are followed.

## A - Atomicity

Either the entire trasaction takes place at once or transaction will not happen.

If i need to transfer amount of $100 from Account A to Account B.
Account A (1000$ - current balance) Account B (500$ - current balance)

read(x) read(y)
X=X-100; y=y+100;
write(x) write(y)

After transcation:
900$ 600$

The entire transaction takes place if both amount deduction from account A and amount addition to account B is successful.

## C - Consistency

Database must be in consistent state before and after a transaction
It ensures the correctness of the transaction.

Total amount from Account A + B before transaction: 1500$ = 1500$ - Total amount from Account A + B after transaction

## I - Isolation

Execution of one transaction is independent of other transactions

If two users are trying to access the account at the same time, user1 transaction is independent of user2 transaction

## D - Durability

It ensures that changes made in a transaction which is comitted is durable and will remain even after system crashes.

The above transaction is said to be durable, once it is complete and written back to the DB.
