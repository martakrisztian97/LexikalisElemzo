import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
        StringBuffer domol = new StringBuffer("12domosi12:=(**domosi**){domosi}>=domosi$");
        List<String> tokens = new ArrayList<>();  // lexémák/tokenek listája
        Pattern pattern;
        Matcher m;
        boolean find;

        while (domol.length() != 0) {
            find = false;
            // <előjelnélküli egész>
            if (Character.isDigit(domol.charAt(0))) {
                pattern = Pattern.compile("[0-9]+");
                m = pattern.matcher(domol);
                if (m.find()) {
                    tokens.add(m.group());
                    find = true;
                }
            // <azonosító>
            } else if (Character.isLetter(domol.charAt(0))) {
                pattern = Pattern.compile("[a-zA-Z]+[0-9]+|[a-zA-Z]+");
                m = pattern.matcher(domol);
                if (m.find()) {
                    tokens.add(m.group());
                    find = true;
                }
            // <(**) kommentár>
            } else if (domol.charAt(0) == '(') {
                pattern = Pattern.compile("\\(\\*\\*.*\\*\\*\\)");
                m = pattern.matcher(domol);
                if (m.find()) {
                    tokens.add(m.group());
                    find = true;
                }
            // <{} kommentár>
            }  else if (domol.charAt(0) == '{') {
                pattern = Pattern.compile("\\{.*\\}");
                m = pattern.matcher(domol);
                if (m.find()) {
                    tokens.add(m.group());
                    find = true;
                }
            // <értékadás>
            } else if (domol.charAt(0) == ':' && domol.charAt(1) == '=') {
                tokens.add(":=");
                find = true;
            // <nagyobbegyenlő>
            } else if (domol.charAt(0) == '>' && domol.charAt(1) == '=') {
                tokens.add(">=");
                find = true;
            // <kisebbegyenlő>
            } else if (domol.charAt(0) == '<' && domol.charAt(1) == '=') {
                tokens.add("<=");
                find = true;
            // <nemegyenlő>
            } else if (domol.charAt(0) == '<' && domol.charAt(1) == '>') {
                tokens.add("<>");
                find = true;
            // <eof>
            } else if (domol.charAt(0) == '$') {
                tokens.add("$");
                find = true;
            } else {
                domol.delete(0, 1);
            }

            if (find) {
                domol.delete(0, tokens.get(tokens.size()-1).length());
            }

            System.out.println(domol+" ");
        }

        System.out.println("tokens:");
        for (String token : tokens) {
            System.out.println(token);
        }
    }
}