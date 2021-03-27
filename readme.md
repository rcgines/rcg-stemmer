# The Stemmer algorithm processes the following steps:

1. Reads in a text file.
2. Removes stop words (as listed in stopwords.txt file) and all non-alphabetical texts.
3. Stems the words into their root form (e.g.: jumping, jumps, jumped -> jump).
4. Computes the frequency of each word and add it to sortable Map.
5. Displays the 20 most commonly occurring words in descending order of frequency.

# To run the application as standalone

1. Create a folder C:\Stemmer

2. Copy the following files from \program to C:\Stemmer
 	- stopwords.txt
    - Text1.txt
    - Text2.txt   
    - rcg-stemmer.jar

3. Execute the following command:
    - C:\Stemmer\java -jar rcg-stemmer.jar

4. Open Chrome browser and type the URL
    - http://localhost:8500/stemmer

5. Select text file from the list and click on Process