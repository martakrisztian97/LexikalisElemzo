import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexikalis {

    /**
     * Tokenek tarolasara szolgalo osztaly.
     */
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
        StringBuffer domol2 = new StringBuffer("12domosi12:=(**domosi**){domosi}>=domosi$");
        List<Token> tokens = new ArrayList<>();  // lexemak/tokenek listaja
        Pattern pattern;
        Matcher m;
        boolean find;

        while (domol.length() != 0) {
            find = false;
            // <szam>
            if (Character.isDigit(domol.charAt(0))) {
                pattern = Pattern.compile("[0-9]+");
                m = pattern.matcher(domol);
                if (m.find()) {
                    tokens.add(new Token(m.group(), "<szam>"));
                    find = true;
                }
            // <azonosito>
            } else if (Character.isLetter(domol.charAt(0))) {
                pattern = Pattern.compile("[a-zA-Z]+[0-9]+|[a-zA-Z]+");
                m = pattern.matcher(domol);
                if (m.find()) {
                    tokens.add(new Token(m.group(), "<azonosito>"));
                    find = true;
                }
            // <(**) kommentar>
            } else if (domol.charAt(0) == '(') {
                pattern = Pattern.compile("\\(\\*\\*.*\\*\\*\\)");
                m = pattern.matcher(domol);
                if (m.find()) {
                    tokens.add(new Token(m.group(), "<(**) kommentar>"));
                    find = true;
                }
            // <{} kommentar>
            }  else if (domol.charAt(0) == '{') {
                pattern = Pattern.compile("\\{.*\\}");
                m = pattern.matcher(domol);
                if (m.find()) {
                    tokens.add(new Token(m.group(), "<{} kommentar>"));
                    find = true;
                }
            // <ertekadas>
            } else if (domol.charAt(0) == ':' && domol.charAt(1) == '=') {
                tokens.add(new Token(":=", "<ertekadas>"));
                find = true;
            // <nagyobbegyenlo>
            } else if (domol.charAt(0) == '>' && domol.charAt(1) == '=') {
                tokens.add(new Token(">=", "<nagyobbegyenlo>"));
                find = true;
            // <kisebbegyenlo>
            } else if (domol.charAt(0) == '<' && domol.charAt(1) == '=') {
                tokens.add(new Token("<=", "<kisebbegyenlo>"));
                find = true;
            // <nemegyenlo>
            } else if (domol.charAt(0) == '<' && domol.charAt(1) == '>') {
                tokens.add(new Token("<>", "<nemegyenlo>"));
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
        }

        System.out.println(domol2+"\n");
        for (Token token : tokens) {
            System.out.print(token.getToken()+" ");
            for (int i = 0; i < 15-token.getToken().length(); i++) {
                System.out.print(" ");
            }
            System.out.print(token.getType());
            System.out.println();
        }
    }
}