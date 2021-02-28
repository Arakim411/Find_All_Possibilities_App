# Find_All_Possibilities_App
It is a puzzle game app which allows to improve performance of mathematical operations in memory.


# Features
## App has 2 modes
 
 
 ### First
 - user enters 3 values: value1, value2, value3 and max result of them
 - main goal is to find all possibilities  of these values using 4 signs (addition, subtraction, multiplication, division) that are not greater than max result
 
 ### Second
 - user enters 2 values "Time" and "Results to find"
 - "Time" - how much time user have to find results
 -  "results to find" - how much results user has to find

# Technologies which was used
- Android Studio
- Kotlin
- Fragments
- mvvm
- Data Binding
- Navigation Component
- Activities
- Interfaces
- Extension Functions

# Advantages
- Data Binding and mvvm architecture makes code maintainable and helps to manage lifecycle
- [SharedPreferencesManager](https://github.com/Arakim411/Find_All_Possibilities_App/blob/master/app/src/main/java/com/applications/all_possibilities/SharedPreferencesManager.kt) makes app more "user friendly" saving values of fields during creating puzzle
- Creative appproach to find every possibility: [CountWithSign](https://github.com/Arakim411/Find_All_Possibilities_App/blob/master/app/src/main/java/com/applications/all_possibilities/ExtensionFunctions.kt) used in [PuzzleCreator](https://github.com/Arakim411/Find_All_Possibilities_App/blob/master/app/src/main/java/com/applications/all_possibilities/PuzzleManager.kt)

# Disadvantages  
- Small amount of animations
- Bug with nestedFragments() that are cover by viewPager in [puzzleCreatorFragment](https://github.com/Arakim411/Find_All_Possibilities_App/blob/master/app/src/main/java/com/applications/all_possibilities/fragments/FragmentCreatePuzzleViewPager.kt).
When we close this fragment and go to another by Navigation components. Fragments that are keep in ViewPager are null. even if fragments are added again it dosn't work. Maybe it is my fault but i am on 80 % sure that is a bug. even when screan is rotating. these fragments are not showing in viewPager but should. I found this bug in google but to repair 
nestedFragments i must add wall of code but i want repair this on my own. Now it works becaues before i close ParentFragment i removeNestedFragment from fragmentManager and add
them again in OnCreatView of Parent Fragment. This is not good approach and not always work (for example when screen is rotating)




