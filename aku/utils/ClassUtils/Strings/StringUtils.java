/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aku.utils.ClassUtils.Strings;

/**
 *
 * @author Robert Kollar
 */
public class StringUtils {

    public static void main(String[] args) {
        String yourString = "omg.omg.omg";
        String what = "omg";
        String toWhat = "lol";

        String someStr = replaceLast(yourString, what, toWhat);

        System.out.println("" + someStr);
    }

    public static String replaceLast(String yourString, String what, String toWhat) {
        return yourString.substring(0, yourString.lastIndexOf(what)) + toWhat;
    }

}
