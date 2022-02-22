import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
        //String domol = new String("12domosi12:=(**domosi**){domosi}>=domosi");
        String domol = new String("12sa12a1ss12as21");
        List<String> tokens = new ArrayList<>();
        Pattern pattern;
        Matcher m;

        while (domol.length() != 0) {
            if (Character.isDigit(domol.charAt(0))) {
                pattern = Pattern.compile("[0-9]+");
                m = pattern.matcher(domol);
                if (m.find()) {
                    tokens.add(m.group());
                }
            } else if (Character.isLetter(domol.charAt(0))) {
                pattern = Pattern.compile("[a-zA-Z]+[0-9]+|[a-zA-Z]+");
                m = pattern.matcher(domol);
                if (m.find()) {
                    tokens.add(m.group());
                }
            }
            domol = domol.replaceFirst(tokens.get(tokens.size()-1), "");
            System.out.println(domol+" ");
        }

        System.out.println("TOKENS:");
        for (String token : tokens) {
            System.out.println(token);
        }
    }
}