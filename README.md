# Making a tiny self-replicating APK  (^w^)

## Intro
I've been messing with Android stuff a bit lately, and this year, BGGP4 offered me an excellent opportunity to learn more about 
the android dev environment. What could be a more fun way of learning a dev environment than trying to break it simultaneously?
But before I get ahead of myself, here is [this year's prompt](https://binary.golf/):

``` Goal:
Create the smallest self-replicating file.

Requirements:
- Produce exactly 1 copy of itself
- Name the copy "4"
- Not execute the copied file
- Print, return, or display the number 4
```

For the uninitiated, this challenge is in the tradition of binary golf / [code golf](https://en.wikipedia.org/wiki/Code_golf).
Without getting too heady, I will say: that it's the challenge of trying to make the smallest program possible for doing a thing.
The "thing" for the program to do in this prompt is to "replicate itself." 
Self-replicating programs are also known as [quines](https://en.wikipedia.org/wiki/Quine_(computing)) in computer science. 

In this case, we are given an example written in the programming language `bash`:

```bash
#!/bin/sh
cp $0 4
echo 4
```

The above example is a perfectly valid submission for BGGP4, and it's only 4 bytes!!!! So the prompt I made for myself was, how can I do this with an APK (or Android Package Kit (a.k.a. an Android app))?

## APK Golf
Fortunately for me, the path of APK Golfing has been walked before. 
Other traveler's journeys have been recorded in [this](https://github.com/fractalwrench/ApkGolf) repository.
From the looks of it, the most miniature APK to date is a mere 678 bytes. Given my prompt and the need for a self-replicating code snippet, I will likely exceed this number.

A h0wdy can dream, though.

So now that I have a road map of a bunch of methods to golf the actual APK, there are a couple of tasks to do:
	1. Decide on a method to replicate an APK. For this, I had two thoughts:
		- Make a system call and copy itself to memory.
		- Download itself.
	2. Write and golf that code.
	3. Apply methods in the aforementioned article to golf the actual APK. 

I chose to start with step 3. I wanted to make sure I could actually golf an APK before I went ahead and wrote 
some Java code. I ran into a couple of bumps along the way, and I'll clear those up now for those who also dare to 
venture this way.

### Trouble Shooting Golfing an APK
The first thing I ran into was unpacking and then rezipping the APK was not rendering the same results as the ones in the 
tutorial (I was using Ubuntu 20).

The command given in the tutorial for rezipping the APK (an APK is just a zip, after all) is as follows:
```
zip -r app app.zip
```
This wasn't working for me. You can see what zip command I ended up using in the [`builder`](https://github.com/h0wdee/bggp4/blob/b6fe99903491e4671a5f932913d6bb802c3db821/builder#L13C1-L13C1), 
but the template for my command is this:
```
zip -X app-unsigned.apk <every> <file> <u> <need> <to> <zip>
```
---

The second problem I had in this phase is notable because it also brought to mind a very important aspect of 
APK golf: versioning. The size of the generated APK varies greatly depending on the SDK version, and generally, 
I found that older versions were much more permissive and also generated smaller APKs. 


For the longest time, I was generating signed APKs with a later SDK and was so confused. It turns out that if I wanted a 
smaller APK, I would need to use version 26 or lower. I never investigated beyond this, but I'm still curious about what they changed in later versions. 
You can set this up by running `sdkmanager` with whatever platform you want to install, like:
```
sdkmanager "platforms;android-18"
```

The topic of versioning extends past just the SDK's `build-tools`. Older versions also allowed smaller / less 
dependencies, and therefore, less imports. 

This was also important to keep in mind as I actually wrote the APK, the more I could depend on just `android` 
libraries, and not `androidx` (which would require Kotlin dependencies), the smaller the resulting APK would be.

Now I just had to figure out how I was going to make it replicate itself and write it.

### Writing Java for APK Self-Replication
Regardless of what method I chose to write a self-replicating APK, I was going to have to get read/write 
[permissions](https://developer.android.com/guide/components/intents-filters). 
This was the part where things really started to slow down, and I started running short on time, 
so I just had to find a solution. After fiddling a lot with figuring out if I could write to memory with just a 
generic [Document Provider](https://developer.android.com/guide/topics/providers/create-document-provider), 
I decided to try out a snippet of code I had found that used a [File Provider](https://developer.android.com/reference/androidx/core/content/FileProvider). 


What was interesting about this method was that I realized I didn't have to request permission at all. The current
app doesn't prompt for any user input; however, I would be shocked if it were ever to make it onto the app store.
lol
The downside of this method is that File Provider is an `androidx` class. I removed several lines of code,
but no matter what I did, as long as I depended on something from `androidx`, a slew of dependencies would be added.
Frankly, this is where I got bored and tired of messing around with Java and reading through what was allowed 
in old Android SDK versions. I'm sure I'll pick it up again, but not today.


With this boredom came a different thought, how could I bypass all these dependencies entirely? You may
have already thought of the answer: the Java Native Interface (JNI). What if I wrote a native library that 
replicates the APK? I'm still not totally sure what the overhead will be now that the NDK (Native Development Kit) 
is going to be a dependency, but regardless, I thought it would be a fun and welcome challenge, not to mention I could golf some other files along the way.


If I'm going to write an Android Library, I'm going to do it right... in ARM. lol
So check back here in a month or so for updates. (I'm in the process of going through the Azeria Labs tutorials now, and I also bought her book).

---

Here is a quick road map of what I'm going to be working on in order:
- Write a self-replicating ELF in ARM
- Write a self-replicating Android Library (just a `.so` file that, when called, replicates itself).
- Write a self-replicating APK that calls a native method written in ARM to do so.


# Links
* [BGGP4](https://binary.golf/)
* [APKGolf](https://github.com/fractalwrench/ApkGolf/blob/master/blog/BLOG_POST.md)
* [Android Docs](https://developer.android.com/reference)
* [Android Internals](https://newandroidbook.com/AIvI-M-RL1.pdf)
* [Azeria Labs](https://azeria-labs.com/writing-arm-assembly-part-1/)
* [ARM Assembly Interals & Reverse Engineering](https://www.amazon.com/Blue-Fox-Assembly-Internals-Engineering-ebook/dp/B0C2B5SLYM)
* [Calling native methods, written in ARM Assembly, within an Android App using the JNI](https://community.arm.com/arm-community-blogs/b/architectures-and-processors-blog/posts/calling-native-methods-written-in-arm-assembly-within-an-android-app-using-the-jni)
* [Assemble a native ARMv8 library, and call Android Java methods from its procedures invoked by an Android App, using the JNI conventions](https://community.arm.com/support-forums/f/operating-systems-forum/9524/assemble-a-native-armv8-library-and-call-android-java-methods-from-its-procedures-invoked-by-an-android-app-using-the-jni-conventions)
