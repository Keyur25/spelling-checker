public class StringArray
{
    private String[] arr;
    private int len;

    public StringArray()
    {
        this.arr = new String[100];
        this.len = 0;
    }

    public StringArray(StringArray a)
    {
        this.arr = new String[a.arr.length];
        this.len = 0;
        for (int i = 0; i < a.size(); i++) // copies contents of StringArray a into this.arr
        {
            this.arr[i] = a.arr[i];
            this.len += 1;
        }
    }

    public int size() { return len; }

    public boolean isEmpty() { return len==0; }

    public String get(int index)
    {
        try { return arr[index]; }
        catch (Exception error) { return null; }
    }

    public void set(int index, String s)
    {
        if (index>=0 & index<len)
        {
            arr[index] = s;
        }
    }

    public void add(String s)
    {
        if (len >= arr.length)
        {
            String[] temp = expandArray();
            System.arraycopy(arr, 0, temp, 0, len);// used a for loop to copy the contents of the arr into
            arr = temp;                                         // temp but the IDE suggested this
        }
        arr[len] = s;
        len += 1;
    }

    public void insert(int index, String s)
    {
        if (index==0 & isEmpty()) { add(s); }
        else if (index>=0 & index<len)
        {
            String[] temp;
            if (len >= arr.length) { temp = expandArray(); }
            else { temp = new String[(arr.length)]; }
            System.arraycopy(arr, 0, temp, 0, index); // used a for loop to copy the contents of the arr
            temp[index] = s;                                       // into temp but the IDE suggested this
            if (len - index >= 0) { System.arraycopy(arr, index, temp, index + 1, len - index); }
            arr = temp;
            len += 1;
        }
    }

    public void remove(int index)
    {
        if (index>=0 & index<len & !isEmpty())
        {
            if (len - index >= 0) { System.arraycopy(arr, index + 1, arr, index, len - index); } // used a
            len -= 1;                   // for loop to copy the contents of the arr into temp but the IDE suggested this
        }
    }

    public boolean contains(String s)
    {
        s = s.toLowerCase();
        return checkContains(s, false);
    }

    public boolean containsMatchingCase(String s)
    {
        return checkContains(s, true);
    }

    public int indexOf(String s)
    {
        s = s.toLowerCase();
        return checkIndexOf(s, false);
    }

    public int indexOfMatchingCase(String s)
    {
        return checkIndexOf(s, true);
    }

    private String[] expandArray()
    {
        int newLength = (int) (arr.length *1.5);
        return new String[newLength];
    }

    private boolean checkContains(String s, boolean matchingCase)
    {
        for (String a:arr)
        {
            if(a==null) { break; }
            if (!matchingCase) { a = a.toLowerCase(); }
            if (a.equals(s)) { return true; }
        }
        return false;
    }

    private int checkIndexOf(String s, boolean matchingCase)
    {
        for (int i = 0; i < len; i++)
        {
            if(arr[i]==null) { break; }
            if (!matchingCase) { arr[i] = arr[i].toLowerCase(); }
            if (arr[i].equals(s)) { return i; }
        }
        return -1;
    }
}