package net.iliabvf.javaio.exceptions;

public class ReadingException extends Exception  {
    String str1;

    public ReadingException(String str2) {
        str1 = str2;
    }
    public String toString(){
        return ("Reading exception occurred: " + str1) ;
    }
}
