# Making a tiny self replicating APK  (^w^)

## Intro
I've been messing with Android stuff a bit lately, and this year, BGGP4 offered me a great opportunity to learn more about 
the android dev environment. What could be a more fun way of learning a dev environment than trying to break it at the same time?
But before I get ahead of myself, here was [this year's prompt](https://binary.golf/):

``` Goal:
Create the smallest self-replicating file.

Requirements:
- Produce exactly 1 copy of itself
- Name the copy "4"
- Not execute the copied file
- Print, return, or display the number 4
```

For the uninitiated, this challenge is in the tradition of binary golf / [code golf](https://en.wikipedia.org/wiki/Code_golf).
Without getting too heady, I will just say: it's the challenge of trying to make the smallest program possible for doing a thing.
The "thing" for the program to do in this prompt is to "replicate itself." 
Self replicating programs are also known as <a href="https://en.wikipedia.org/wiki/Quine_(computing)" target="_blank">quines</a> in computer science. 

In this case, we are given an example written in the programming language `bash`:

```bash
#!/bin/sh
cp $0 4
echo 4
```

The above example is a perfectly valid submission for BGGP4, and it's only 4 bytes!!!! So the prompt I made for myself was, how can I do this with an APK (or Android Package Kit (a.k.a. an android app)).

## APK Golf
Fortunately for me, the path of APK Golfing has been walked before. 
Other traveller's journeys have been recorded in [this](https://github.com/fractalwrench/ApkGolf) repository.
From the looks of it, the smallest APK to date is a mere 678 bytes. Given my prompt, and the need for code 
in order to replicate code, I will likely exceed this number.

A h0wdy can dream though.















