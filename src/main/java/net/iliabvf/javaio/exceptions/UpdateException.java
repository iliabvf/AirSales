package net.iliabvf.javaio.exceptions;

public class UpdateException extends Throwable {
    String str1;

    public UpdateException(String str2) {
        str1 = str2;
    }
    public String toString(){
        return ("Update exception occurred: " + str1) ;
    }
}
