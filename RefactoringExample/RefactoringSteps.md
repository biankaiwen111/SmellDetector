# Refactoring Steps

The sample program is very simple. It is a program to calculate and print a statement of a customer's charges at a video store. The program is told which movies a customer rented and for how long. It then calculates the charges, which depend on how long the movie is rented, and identifies the type movie. There are three kinds of movies: regular, children's, and new releases. In addition to calculating charges, the statement also computes frequent renter points, which vary depending on whether the film is a new release.


## Part 1: Comments on the Starting Program
What are your impressions about the design of this program? I would describe it as not well designed and certainly not object oriented. For a simple program like this, that does not really matter. There's nothing wrong with a quick and dirty simple program. But if this is a representative fragment of a more complex system, then I have some real problems with this program. That long statement routine in the Customer class does far too much. Many of the things that it does should really be done by the other classes.

Even so the program works. Is this not just an aesthetic judgment, a dislike of ugly code? It is until we want to change the system. The compiler doesn't care whether the code is ugly or clean. But when we change the system, there is a human involved, and humans do care. A poorly designed system is hard to change. Hard because it is hard to figure out where the changes are needed. If it is hard to figure out what to change, there is a strong chance that the programmer will make a mistake and introduce bugs.

In this case we have a change that the users would like to make. First they want a statement printed in HTML so that the statement can be Web enabled and fully buzzword compliant. Consider the impact this change would have. As you look at the code you can see that it is impossible to reuse any of the behavior of the current statement method for an HTML statement. Your only recourse is to write a whole new method that duplicates much of the behavior of statement. Now, of course, this is not too onerous. You can just copy the statement method and make whatever changes you need.

But what happens when the charging rules change? You have to fix both statement and htmlStatement and ensure the fixes are consistent. The problem with copying and pasting
code comes when you have to change it later. If you are writing a program that you don't expect to change, then cut and paste is fine. If the program is long lived and likely to change, then cut and paste is a menace.

This brings us to a second change. The users want to make changes to the way they classify movies, but they haven't yet decided on the change they are going to make. They have a number of changes in mind. These changes will affect both the way renters are charged for movies and the way that frequent renter points are calculated. As an experienced developer you are sure that whatever scheme users come up with, the only guarantee you're going to have is that they will change it again within six months.

The statement method is where the changes have to be made to deal with changes in classification and charging rules. If, however, we copy the statement to an HTML statement, we need to ensure that any changes are completely consistent. Furthermore, as the rules grow in complexity it's going to be harder to figure out where to make the changes and harder to make them without making a mistake.

You may be tempted to make the fewest possible changes to the program; after all, it works fine. Remember the old engineering adage: "if it ain't broke, don't fix it." The program may not be broken, but it does hurt. It is making your life more difficult because you find it hard to make the changes your users want. This is where refactoring comes in.

## Part 2: Code Smells
The source code of this program is affected by a few code smells:
God Class and Long Method: The method statement in the class Customer does too much, which leads to the God Class and Long Method.
Duplicated Code: The class Registrar has duplications associated to the logic to store data.
Message Chain: statement method has a long chain of method invocation to get the price code of a Movie


## Part 3: Refactoring Steps
The following steps help us to remove some of these smells and improve the structural quality of the program.

The goal is to make the classes comply with the object-oriented principles. Additionally, we want to improve the extensibility of the system, allowing us to introduce the htmlStatement method.

Creating class hierarchy
-
Most of the classes in the project share similarities (e.g., same field). Therefore, we'll first create a common abstraction to these classes, named DomainObject.
- Apply Extract Superclass: DomainObject is a general class that does a few standard things, for now, it holds a name.
- Use DomainObject to remove the duplicated code in Registrar

Decomposing and Redistributing the Statement Method
-
- Apply the Extract Method: Find the logical clump of code in the Customer statement method that determines the amount for every rental.
- The variable thisAmount is redundant. It is set to the result of each.getAmount and not changed afterward. Thus, we can eliminate thisAmount by using Replace Temp with Query:
- Apply the Move Method: Moving the amount calculation since it uses information from the rental, but does not use information from the customer.


The next step is to do a similar thing for the frequent renter points. The rules vary with the tape,
although there is less variation than with charging. It seems reasonable to put the responsibility
on the rental.
- Use Extract Method on the frequent renter points part of the code
- Apply the Move Method: Moving the newly created method (getFrequentRenterPoints) since it uses information from the rental, but does not use information from the customer.


Replacing the Conditional Logic on Price Code with Polymorphism
-
The first part of this problem is that switch statement. It is a bad idea to do a switch based on an attribute of another object. If you must use a switch statement, it should be on your own data, not on someone else's (Feature Envy).
- This implies that getAmount should move onto movie: Apply Move method.
- We need to update the references in Rental, Tape and Customer
- Do the same with the frequent renter point calculation. 
