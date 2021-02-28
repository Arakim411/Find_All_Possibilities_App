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


# Advantages
- Data Binding and mvvm architecture makes code maintainable and helps to manage lifecycle
- [SharedPreferencesManager](https://github.com/Arakim411/Find_All_Possibilities_App/blob/master/app/src/main/java/com/applications/all_possibilities/SharedPreferencesManager.kt) makes app more "user friendly" saving values of fields during creating puzzle
- Creative appproach to find every possibility: [CountWithSign](https://github.com/Arakim411/Find_All_Possibilities_App/blob/master/app/src/main/java/com/applications/all_possibilities/ExtensionFunctions.kt) used in [PuzzleCreator](https://github.com/Arakim411/Find_All_Possibilities_App/blob/master/app/src/main/java/com/applications/all_possibilities/PuzzleManager.kt)

# Disadvantages  
- Small amount of animations
- Bug with nested Fragments that are covered by viewPager in [puzzleCreatorFragment](https://github.com/Arakim411/Find_All_Possibilities_App/blob/master/app/src/main/java/com/applications/all_possibilities/fragments/FragmentCreatePuzzleViewPager.kt).
When user closes this fragment and go to another by Navigation components. Fragments that are keep in ViewPager disappear Even if fragments are added again it doesn't work. Maybe it is my fault but I am almost sure that is a bug When screan is rotating these fragments are not showing in viewPager, even though they should. I found answer on google but to repair nestedFragments i would have to add wall of code. I want repair this on my own. Now it works becaues before i close ParentFragment i remove Nested Fragments from fragmentManager and add them again in OnCreateView. This is not good approach and doesn't always  work (e.g. when screen is rotating)




