import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static class Token {
        private String token;
        private String type;

        public String getToken() {
            return token;
        }

        public String getType() {
            return type;
        }

        public Token(String token, String type) {
            this.token = token;
            this.type = type;
        }
    }

    public static void main(String[] args) {
        StringBuffer domol = new StringBuffer("12domosi12:=(**domosi**){domosi}>=domosi$");
        List<Token> tokens = new ArrayList<>();  // lexémák/tokenek listája
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
                    tokens.add(new Token(m.group(), "<előjelnélküli egész>"));
                    find = true;
                }
            // <azonosító>
            } else if (Character.isLetter(domol.charAt(0))) {
                pattern = Pattern.compile("[a-zA-Z]+[0-9]+|[a-zA-Z]+");
                m = pattern.matcher(domol);
                if (m.find()) {
                    tokens.add(new Token(m.group(), "<azonosító>"));
                    find = true;
                }
            // <(**) kommentár>
            } else if (domol.charAt(0) == '(') {
                pattern = Pattern.compile("\\(\\*\\*.*\\*\\*\\)");
                m = pattern.matcher(domol);
                if (m.find()) {
                    tokens.add(new Token(m.group(), "<(**) kommentár>"));
                    find = true;
                }
            // <{} kommentár>
            }  else if (domol.charAt(0) == '{') {
                pattern = Pattern.compile("\\{.*\\}");
                m = pattern.matcher(domol);
                if (m.find()) {
                    tokens.add(new Token(m.group(), "<{} kommentár>"));
                    find = true;
                }
            // <értékadás>
            } else if (domol.charAt(0) == ':' && domol.charAt(1) == '=') {
                tokens.add(new Token(":=", "<értékadás>"));
                find = true;
            // <nagyobbegyenlő>
            } else if (domol.charAt(0) == '>' && domol.charAt(1) == '=') {
                tokens.add(new Token(">=", "<nagyobbegyenlő>"));
                find = true;
            // <kisebbegyenlő>
            } else if (domol.charAt(0) == '<' && domol.charAt(1) == '=') {
                tokens.add(new Token("<=", "<kisebbegyenlő>"));
                find = true;
            // <nemegyenlő>
            } else if (domol.charAt(0) == '<' && domol.charAt(1) == '>') {
                tokens.add(new Token("<>", "<nemegyenlő>"));
                find = true;
            // <eof>
            } else if (domol.charAt(0) == '$') {
                tokens.add(new Token("$", "<eof>"));
                find = true;
            } else {
                domol.delete(0, 1);
            }

            if (find) {
                domol.delete(0, tokens.get(tokens.size()-1).getToken().length());
            }

            System.out.println(domol+" ");
        }

        System.out.println("tokens:");
        for (Token token : tokens) {

            System.out.println(token.getToken()+"\t"+token.getType());
        }
    }
}