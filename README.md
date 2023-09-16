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
Self replicating programs are also known as [quines](https://en.wikipedia.org/wiki/Quine_(computing)) in computer science. 

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
From the looks of it, the smallest APK to date is a mere 678 bytes. Given my prompt, and the need for a self
replicating snippet of code, I will likely exceed this number.

A h0wdy can dream though.

So now that I have a road map of a bunch of methods to golf the actual APK, there are a couple tasks todo:
	1. Decide on a method to replicated an apk. For this I had two thoughts:
		- Make a system call and copy itself to memory.
		- Download itself.
	2. Write and golf that code.
	3. Apply methods in aforementioned article to golf the actual APK. 

I chose to start with step 3. I wanted to make sure I could actually golf an APK before I went ahead and wrote 
some java code. I ran into a couple bumps along the way, and I'll clear those up now for those who also dare to 
venture this way.

### Trouble Shooting Golfing an APK
First thing I ran into was unpacking then rezipping the APK was not rendering the same results as the ones in the 
tutorial (I was using Ubuntu 20).

The command given in the tutorial for rezipping the APK (an APK is just a zip after all) is as follows:
```
zip -r app app.zip
```
This wasn't working for me. You can see exactly what zip command I ended up using in the [`builder`](https://github.com/h0wdee/bggp4/blob/b6fe99903491e4671a5f932913d6bb802c3db821/builder#L13C1-L13C1), 
but to put the template for my command is this:
```
zip -X app-unsigned.apk <every> <file> <u> <need> <to> <zip>
```
---

The second problem I had in this phase is notable, because it also brought to mind a very important aspect of 
APK golf: versioning. The size of the generated APK varies greatly depending on SDK version, and generally, 
I found that older versions were much more permissive, and also generated smaller APKs. 


For the longest time I was generating signed APKs with a later SDK and was so confused, turns out if I wanted a 
smaller APK, I was going to need to use 26 or lower. I never investigated beyond this, but I'm still really 
curious as to what they changed in later versions. 
You can set this up by running `sdkmanager` with whatever platform you want to install like:
```
sdkmanager "platforms;android-18"
```

These were the largest road blocks I came across while messing with golfing the APK, from there I just had to 
figure out how I was going to make it replicate itself.

### Writing Java for APK Self Replication













