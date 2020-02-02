package canopy;

public interface Strings {

    static String unquote(String s) {
        for (;;) {
            var c = s.charAt(0);
            if (c == '"' || c == '\'') {
                var n = s.length() - 1;
                if (n > 0 && c == s.charAt(n)) {
                    s = s.substring(1, n);
                    continue;
                }
            }
            break;
        }
        return s;
    }

    static CharSequence escape(CharSequence cs) {
        var s = new StringBuilder();
        int i = 0, n = cs.length();
        while (i < n) {
            var c = cs.charAt(i++);
            switch (c) {
                case '"':  s.append("\\\""); break;
                case '\\': s.append("\\\\"); break;
                case '\b': s.append("\\b"); break;
                case '\t': s.append("\\t"); break;
                case '\n': s.append("\\n"); break;
                case '\f': s.append("\\f"); break;
                case '\r': s.append("\\r"); break;
                default:   s.append(c); break;
            }
        }
        return s;
    }

    static CharSequence unescape(CharSequence cs) {
        var s = new StringBuilder();
        int i = 0, n = cs.length();
        while (i < n) {
            var c = cs.charAt(i++);
            if (c != '\\') {
                s.append(c);
            } else {
                c = cs.charAt(i++);
                switch (c) {
                    case '"':  s.append('"'); break;
                    case '\\': s.append('\\'); break;
                    case 'b':  s.append('\b'); break;
                    case 't':  s.append('\t'); break;
                    case 'n':  s.append('\n'); break;
                    case 'f':  s.append('\f'); break;
                    case 'r':  s.append('\r'); break;
                    default:   s.append('\\').append(c); break;
                }
            }
        }
        return s;
    }

}
