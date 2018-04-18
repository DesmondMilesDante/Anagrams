/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.engedu.anagrams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.security.Key;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class AnagramDictionary {
    ArrayList <String> wordList=new ArrayList<String>();
    HashSet <String> wordSet=new HashSet<String>();
    HashMap<String,ArrayList<String>>lettersToWords =new HashMap<String,ArrayList<String>>();
    HashMap<Integer,ArrayList<String>> lengthToWords=new HashMap<>();
    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    private Random random = new Random();
    int wordLength=DEFAULT_WORD_LENGTH;

    public AnagramDictionary(Reader reader) throws IOException {
        BufferedReader in = new BufferedReader(reader);
        String line;
        while((line = in.readLine()) != null) {
            String word = line.trim();
            wordList.add(word);
            wordSet.add(word);
            String s=sortLetters(word);
            if (lettersToWords.containsKey(s))
            {
                lettersToWords.get(s).add(word);

            }
            else
            {
                ArrayList<String> arr=new ArrayList<String>();
                lettersToWords.put(s,arr);
                lettersToWords.get(s).add(word);


            }
            int l=word.length();
            if(lengthToWords.containsKey(l)){
                lengthToWords.get(l).add(word);
            }
            else{
                ArrayList<String> arr=new ArrayList<>();
                lengthToWords.put(l,arr);
                lengthToWords.get(l).add(word);
            }


        }

    }
    public String sortLetters(String input)
    {

        int l=input.length();
        char s[]=new char[l];
        for(int i=0;i<l;i++)
        {
        s[i]=input.charAt(i);

        }
        Arrays.sort(s);
        StringBuilder sb=new StringBuilder();
        for(int i=0;i<l;i++)
        {
         sb.append(s[i]);
        }
        return sb.toString();

    }

    public boolean isGoodWord(String word, String base) {
        if(wordSet.contains(word))
        {
            if(word.indexOf(base)==-1){
                return true;
            }
            else {
                return false;
            }


        }

        else{
            return false;
        }

    }

    public List<String> getAnagrams(String targetWord) {
        ArrayList<String> result = new ArrayList<String>();
        String t=sortLetters(targetWord);
        if(lettersToWords.containsKey(t)){
            return lettersToWords.get(t);
        }
        return result;
    }

    public List<String> getAnagramsWithOneMoreLetter(String word) {
        ArrayList<String> result = new ArrayList<String>();
        for(char c='a';c<='z';c++)
        {
            String s=word+c;

            result.addAll(getAnagrams(s));
        }
        return result;
    }

    public String pickGoodStarterWord()
    {
        Random r=new Random();
        ArrayList<String> arr=lengthToWords.get(wordLength);
        wordLength++;
        int n=r.nextInt(arr.size());
        for(int i=n;i<arr.size();i++)
        {
            String s=sortLetters(arr.get(i));
            int a=getAnagrams(s).size();
            if(a>=MIN_NUM_ANAGRAMS) {
                return arr.get(i);
            }
        }
        for(int i=0;i<n-1;i++)
        {
            String s=sortLetters(arr.get(i));
            int a=getAnagrams(s).size();
            if(a>=MIN_NUM_ANAGRAMS) {
                return arr.get(i);
            }
        }

        return pickGoodStarterWord();
    }
}
