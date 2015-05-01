/*compilation:  javac CommentStripper.java
 *  Execution:    java CommentStripper < source.java
 *  Dependencies: StdIn.java
 *  
 *  Reads in a Java program and removes all of the comments using a 5
 *  state finite state automaton.
 *
 *  Known issues
 *  ------------
 *   - not designed to handle quoted strings, e.g., s = "/***//*"
 *   - assumes lines end with '\n' - can be OS dependent
 *
 *************************************************************************/

public class CommentStripper { 
    public static void main(String[] args) {
        final int CODE  = 0;   // parsing normal code
        final int QUOTE = 1;   // found a quote
        final int SLASH = 2;   // found a leading '/'
        final int BLOCK = 3;   // in a block comment
        final int LINE  = 4;   // in a line comment
        final int STAR  = 5;   // found a trailing * in a block comment

        int state = CODE;      // current state

        while(!StdIn.isEmpty()) {
            char c = StdIn.readChar();
            switch(state) {
                case QUOTE: if      (c == '"')  { state = CODE;                            }
                            else                {System.out.print(c);                      }
                            break;
                                                
                case CODE:  if      (c == '/')  { state = SLASH;                           }
                            else if (c == '"')  { state = QUOTE; System.out.print("\" "    }
                            else                { System.out.print(c);                     }
                            break;

                case SLASH: if      (c == '*')  { state = BLOCK;                           }
                            else if (c == '/')  { state = LINE;                            }
                            else                { state = CODE; System.out.print("/" + c); }
                            break;

                case BLOCK: if      (c == '*')  { state = STAR;                            }
                            break;

                case STAR:  if      (c == '/')  { state = CODE; System.out.print(" ");     }
                            else if (c == '*')  { state = STAR;                            }
                            else                { state = BLOCK;                           }
                            break;

                case LINE:  if      (c == '\n') { state = CODE; System.out.println();      }
                            break;
             }
        }
    }

}
