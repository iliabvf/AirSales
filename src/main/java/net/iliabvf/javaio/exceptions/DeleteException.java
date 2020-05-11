package net.iliabvf.javaio.exceptions;

public class DeleteException extends Throwable {
    String str1;

    public DeleteException(String str2) {
        str1 = str2;
    }
    public String toString(){
        return ("Delete exception occurred: " + str1) ;
    }
}
