package org.spes.common;

public class StringUtil {

    /**
     * check input string is null or empty
     * 
     * @param aInputStr
     * @return
     */
    public static boolean isNull(String aInputStr) {
        if (aInputStr == null || aInputStr.trim().equals("")) {
            return true;
        }else return false;
    }
    
    /**
     *check input string is not null or empty
     * 
     * @param aInputStr
     * @return
     */
    public static boolean isNotNull(String aInputStr) {
        if (aInputStr == null || aInputStr.trim().equals("")) {
            return false;
        }else return true;
    }
    /**
     * check the 2 string is the same
     * if both null,retuen true.
     * 
     * @param strOne
     * @param strOther
     * @return
     */
    public static boolean isTheSame(String strOne, String strOther) 
    {
        if(strOne == null && strOther == null)
        {
            return true;
        }
        
        if(strOne!=null)
        {
            return strOne.equals(strOther);
        }
        
        return false;
    }
}

