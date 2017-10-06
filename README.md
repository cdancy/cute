# cute

Java based IDE created for senior project many moons ago. Uploading this to serve mainly as a reminder to how far I've come and to be nicer to individuals who are still wet behind the ears so to speak.

## Why the name?

When showing an initial concept of this idea to my advisor he looked things over and responded with "That's cute". I was none too thrilled with this response but he encouraged me to continue nonetheless. And so, if only to spite him, I named the project `cute`.

## Goals

1. Tabbed project structure so that each projects files lives within its own workspace.
2. Lots of cool UI effects :)
3. Be able to execute ruby files from within the IDE.
4. Implement all modern shortcut keys to navigate between files/projects/etc without having to use a mouse.

## Where to Start

Run the [Main.java](https://github.com/cdancy/cute/blob/master/src/main/java/main/Main.java) file and the IDE will start as expected. At some point I may create an executable if this ever gets past the POC stage but for now this will have to suffice.

## Global Key Bindings

### Project Bindings
* Close a Project Tab == Ctrl + Q
* Shift to next Project Tab == Shift + Tab

### File Bindings
* Open a new File Tab == Ctrl + T
* Save a File Tab == Ctrl + S
* Close a File Tab == Ctrl + W
* Shift to next File Tab == Ctrl + Tab

### Global Buttons

* Green Button == run file in terminal
* Red Button == kill running file in teminal
* Grey Buttons == don't work :(
* Yellow Buttons == don't work :(
* Blue Buttons == don't work :(

## Mouse Clicks

* Clicking on any project or file within the project-view-panel on the left produces loads of options for each.
