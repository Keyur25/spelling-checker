public class SpellCheck
{
    private String text;
    private StringArray wordsInText;
    private StringArray dictionary;
    private StringArray textArray;
    private StringArray incorrectWords;

    public SpellCheck(String text)
    {
        this.text = text;
        this.wordsInText = splitText(tidyText(text));
        innitDictionary();
        this.textArray = splitText(text);
        this.incorrectWords = findIncorrectWords();
    }

    public String getText() { return text; }

    public StringArray getWordsInText() { return wordsInText; }

    public StringArray getDictionary() { return dictionary; }

    public StringArray getTextArray() { return textArray; }

    public StringArray getIncorrectWords() { return incorrectWords; }

    public StringArray similarWords(String word)
    {
        StringArray similar = new StringArray();
        int largest = 0;
        for (int i = 0; i < dictionary.size(); i++)
        {
            String check = dictionary.get(i);
            int similarity = similarityScore(check, word);
            int differenceInLength = Math.abs(check.length()-word.length());
            if (similarity>=largest & differenceInLength<2) // ranks all the words based on length and similarity and
            {                                               // places the most similar at the top
                similar.insert(0,check);
                largest = similarity;
            }
            else if (similarity>0) { similar.add(check); }
        }
        return topResults(similar);
    }

    public String replaceIncorrectText(StringArray correctedWords)
    {
        for (int i = 0; i < incorrectWords.size(); i++)
        {
            int index = wordsInText.indexOf(incorrectWords.get(i));// find index of wrong word
            textArray.set(index, correctedWords.get(i));           // replace wrong word with corrected word
        }
        return convertTextArrayToString();
    }

    private String convertTextArrayToString()
    {
        int length = textArray.size();
        String[] temp = new String[length];
        for (int i = 0; i < length; i++)
        {
            temp[i] = textArray.get(i);
        }
        return String.join(" ", temp);
    }

    private StringArray topResults(StringArray a)
    {
        int loop = Math.min(5,a.size());
        StringArray temp = new StringArray();
        for (int i = 0; i < loop; i++)
        {
            temp.add(a.get(i));
        }
        return temp;// returns the top highest ranked words
    }

    private int similarityScore(String check, String word)
    {
        int score = 0;
        int shortest = Math.min(check.length(),word.length());
        for (int j = 0; j < shortest; j++)
        {
            String letter1 = word.substring(j,j+1).toLowerCase();
            String letter2 = check.substring(j,j+1).toLowerCase();
            if (letter1.equals(letter2))
            {
                score += 1;// scores each word based on how many characters match in both words
            }
        }
        return score;
    }

    private StringArray findIncorrectWords()
    {
        StringArray temp = new StringArray();
        for (int i = 0; i < wordsInText.size(); i++)
        {
            String word = wordsInText.get(i);
            if (!dictionary.contains(word))
            {
                temp.add(word);
            }
        }
        return temp;
    }

    private StringArray splitText(String t)
    {
        String[] words = t.split("\\s");// splits t everywhere there is a space
        StringArray temp = new StringArray();
        for (String word : words)
        {
            temp.add(word);
        }
        return temp;
    }

    private String tidyText(String t)
    {
        return t.replaceAll("[^a-zA-Z\\s-]", ""); // removes all punctuation apart from the dash
    }

    private void innitDictionary()
    {
        dictionary = new StringArray();
        try
        {
            FileInput input = new FileInput("words");
            generatingDictionary(input);
        }
        catch (Exception error)
        {
            System.err.println("Dictionary Could Not Be Initialized");
            System.exit(1);
        }
    }

    private void generatingDictionary(FileInput input)
    {
        while (input.hasNextLine())
        {
            String word = input.nextLine();
            dictionary.add(word);
        }
    }
}