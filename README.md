## Turtles Language Code Generation

>To work around an annoying and long-standing GitHub Classroom bug and get the autograder to work reliably, you will have to undertake a couple of extra steps:
>
>1. Open [classroom.yml](.github/workflows/classroom.yml) and click on the little pencil symbol to edit the file.
>2. Add a space somewhere and remove it again (yes, that means the file content won't have changed), then click on the "Commit..." button that has now become enabled. Accept the standard choices and commit. This should allow the auto-grader to run in the future.

Extend your Turtles DSML with a first simple code generator. A good starting point is the file `/uk.ac.kcl.inf.mdd3.turtles/src/uk/ac/kcl/inf/mdd3/generator/TurtlesGenerator.xtend`. Eventually, the autograding should pass and assign 30/30.

Given the example turtle program:

```
5 times do
	forward (2)
	turn left by 90.0 degrees
	forward (2)
	turn right by 20.0 degrees
end
```

Your code generator should generate the summary:

```
Program contains:

- 2 turn commands
- 2 move commands
- 1 top-level loops
```

As another example, given the turtle program:

```
backward (1)
```

Your code generator should generate the summary:

```
Program contains:

- 0 turn commands
- 1 move commands
- 0 top-level loops
```

### Using the repository

There are two ways to do this activity:

1. You can check out the repository and import the projects into Eclipse, then do the activity there. Commit your changes and push them back to GitHub to trigger the autograding so you can see whether you've correctly implemented the grammar. More information about checking out and editing code in Eclipse can be found on KEATS.
2. You can do this activity in your browser.

   Due to an annoying, long-standing GitHub Classroom bug, you need a small extra step for this:

    1. Copy the URL of your repository (see the location bar of your browser).
    2. Open [this form](https://7ccsmmdd.github.io/) and paste your repository URL into the corresponding field.
    3. The URL field should fill with a long complicated URL as soon as you move out of the text field. 
    4. Click on the button at the bottom of the form to open that URL in a new tab: this is the MDENet Education Platform with the activity pre-loaded. You can view visualisations of the meta-model, edit your grammar and test out the changes you have made by generating an editor. See the video on KEATS to find out how to do this. **Note that you must save your changes before switching to the generated editor or you will lose them.** Saving your changes will create a commit in your repository, which will also automatically trigger the autograding process.

### Help with the meta-model

Below is the current meta-model as a reminder to identify correct class names:

<img src="turtles class diagram_after.jpg">
