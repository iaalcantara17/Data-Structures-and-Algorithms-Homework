//Israel Alcantara

package edu.njit.cs114;

import java.io.IOException;
import java.util.*;

/**
 * Author: Ravi Varadarajan
 * Date created: 11/1/2023
 */
public class AnagramFinderListImpl extends AbstractAnagramFinder
{

    private List<WordArrPair> wordArrList = new ArrayList<>();

    private class WordArrPair implements Comparable<WordArrPair>
    {
        public final String word;
        public final char [] charArr;

        public WordArrPair(String word)
        {
            this.word = word;
            charArr = word.toCharArray();
            Arrays.sort(charArr);
        }

        public boolean isAnagram(WordArrPair wordArrPair)
        {
            return Arrays.equals(this.charArr, wordArrPair.charArr);
        }

        @Override
        public int compareTo(WordArrPair wordArrPair)
        {
            return this.word.compareTo(wordArrPair.word);
        }


    }


    @Override
    public void clear()
    {
        wordArrList.clear();
    }

    @Override
    public void addWord(String word)
    {
        if (!wordArrList.contains(word))
        {
            wordArrList.add(new WordArrPair(word));
        }
    }

    @Override
    public List<List<String>> getMostAnagrams()
    {
        List<WordArrPair> wordArrPairList = new ArrayList<>(wordArrList);
        Collections.sort(wordArrPairList);
        ArrayList<List<String>> mostAnagramsList = new ArrayList<>();
        int maxAnagramSize = 0;

        while(!wordArrPairList.isEmpty())
        {
            List<String> currentAnagramGroup = new ArrayList<>();
            ListIterator<WordArrPair> pairListIterator = wordArrPairList.listIterator();
            WordArrPair firstPair = wordArrPairList.get(0);

            while(pairListIterator.hasNext())
            {
                WordArrPair nextPair = pairListIterator.next();
                if(firstPair.isAnagram(nextPair))
                {
                    currentAnagramGroup.add(nextPair.word);
                    pairListIterator.remove();
                }
            }

            if(currentAnagramGroup.size() > maxAnagramSize)
            {
                mostAnagramsList.clear();
                mostAnagramsList.add(currentAnagramGroup);
                maxAnagramSize = currentAnagramGroup.size();
            }
            else if (currentAnagramGroup.size()== maxAnagramSize)
            {
                mostAnagramsList.add(currentAnagramGroup);
                maxAnagramSize = currentAnagramGroup.size();
            }

        }

        /**
         * To be completed
         *  Note : use isAnagram()method of WordArrPair to identify an anagram
         */

        return mostAnagramsList;
    }

    public static void main(String [] args)
    {
        AnagramFinderListImpl finder = new AnagramFinderListImpl();
        finder.clear();
        long startTime = System.nanoTime();
        int nWords=0;
        try
        {
            nWords = finder.processDictionary("words.txt");
        }
        catch (IOException e)
        {
            e.printStackTrace ();
        }
        List<List<String>> anagramGroups = finder.getMostAnagrams();
        long estimatedTime = System.nanoTime () - startTime ;
        double seconds = ((double) estimatedTime /1000000000) ;
        System.out.println("Number of words : " + nWords);
        System.out.println("Number of groups of words with maximum anagrams : "
                + anagramGroups.size());
        if (!anagramGroups.isEmpty())
        {
            System.out.println("Length of list of max anagrams : " + anagramGroups.get(0).size());
            for (List<String> anagramGroup : anagramGroups)
            {
                System.out.println("Anagram Group : "+ anagramGroup);
            }
        }
        System.out.println ("Time (seconds): " + seconds);

    }
}
