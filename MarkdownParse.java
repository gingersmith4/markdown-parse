// File reading code from https://howtodoinjava.com/java/io/java-read-file-to-string-examples/
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class MarkdownParse {
    public static ArrayList<String> getLinks(String markdown) {
        ArrayList<String> toReturn = new ArrayList<>();
        // find the next [, then find the ], then find the (, then take up to
        // the next )
        int currentIndex = 0;
        while(currentIndex < markdown.length()) {
            System.out.println(currentIndex);
            int nextOpenBracket = markdown.indexOf("[", currentIndex);
            int nextCloseBracket = markdown.indexOf("]", nextOpenBracket);
            //prevents infinite loop if there are lines between urls
            if(nextOpenBracket < 0 || nextCloseBracket < 0){
                break;
            }
            //image files have ![], we don't want to add that to our ArrayList
            if(markdown.substring(nextOpenBracket-1, nextOpenBracket).equals("!")){
                break;
            }
            int openParen = markdown.indexOf("(", nextCloseBracket);
            int closeParen = markdown.indexOf(")", openParen);
            //prevents infinite loop if there are lines between urls
            if(openParen < 0 || closeParen < 0){
                break;
            }
            //if there are quotes around the url we just want to print url
            //start one later and end one earlier
            if(markdown.substring(openParen+1, openParen + 2).equals("\"")){
                toReturn.add(markdown.substring(openParen + 2, closeParen - 1));
            } else {
                toReturn.add(markdown.substring(openParen + 1, closeParen));
            }
            currentIndex = closeParen + 1;
            
        }
        return toReturn;
    }
    public static void main(String[] args) throws IOException {
		Path fileName = Path.of(args[0]);
	    String contents = Files.readString(fileName);
        ArrayList<String> links = getLinks(contents);
        System.out.println(links);
    }
}