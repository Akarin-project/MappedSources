package net.minecraft.server;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;

public class ArgumentMathOperation implements ArgumentType<ArgumentMathOperation.a> {
    private static final Collection<String> a = Arrays.asList("=", ">", "<");
    private static final SimpleCommandExceptionType b = new SimpleCommandExceptionType(new ChatMessage("arguments.operation.invalid", new Object[0]));
    private static final SimpleCommandExceptionType c = new SimpleCommandExceptionType(new ChatMessage("arguments.operation.div0", new Object[0]));

    public ArgumentMathOperation() {
    }

    public static ArgumentMathOperation a() {
        return new ArgumentMathOperation();
    }

    public static ArgumentMathOperation.a a(CommandContext<CommandListenerWrapper> commandcontext, String s) throws CommandSyntaxException {
        return (ArgumentMathOperation.a)commandcontext.getArgument(s, ArgumentMathOperation.a.class);
    }

    public ArgumentMathOperation.a a(StringReader stringreader) throws CommandSyntaxException {
        if (!stringreader.canRead()) {
            throw b.create();
        } else {
            int i = stringreader.getCursor();

            while(stringreader.canRead() && stringreader.peek() != ' ') {
                stringreader.skip();
            }

            return a(stringreader.getString().substring(i, stringreader.getCursor()));
        }
    }

    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> var1, SuggestionsBuilder suggestionsbuilder) {
        return ICompletionProvider.a(new String[]{"=", "+=", "-=", "*=", "/=", "%=", "<", ">", "><"}, suggestionsbuilder);
    }

    public Collection<String> getExamples() {
        return a;
    }

    private static ArgumentMathOperation.a a(String s) throws CommandSyntaxException {
        return (ArgumentMathOperation.a)(s.equals("><") ? (scoreboardscore, scoreboardscore1) -> {
            int i = scoreboardscore.getScore();
            scoreboardscore.setScore(scoreboardscore1.getScore());
            scoreboardscore1.setScore(i);
        } : b(s));
    }

    private static ArgumentMathOperation.b b(String s) throws CommandSyntaxException {
        byte b0 = -1;
        switch(s.hashCode()) {
        case 60:
            if (s.equals("<")) {
                b0 = 6;
            }
            break;
        case 61:
            if (s.equals("=")) {
                b0 = 0;
            }
            break;
        case 62:
            if (s.equals(">")) {
                b0 = 7;
            }
            break;
        case 1208:
            if (s.equals("%=")) {
                b0 = 5;
            }
            break;
        case 1363:
            if (s.equals("*=")) {
                b0 = 3;
            }
            break;
        case 1394:
            if (s.equals("+=")) {
                b0 = 1;
            }
            break;
        case 1456:
            if (s.equals("-=")) {
                b0 = 2;
            }
            break;
        case 1518:
            if (s.equals("/=")) {
                b0 = 4;
            }
        }

        switch(b0) {
        case 0:
            return (var0, i) -> {
                return i;
            };
        case 1:
            return (i, j) -> {
                return i + j;
            };
        case 2:
            return (i, j) -> {
                return i - j;
            };
        case 3:
            return (i, j) -> {
                return i * j;
            };
        case 4:
            return (i, j) -> {
                if (j == 0) {
                    throw c.create();
                } else {
                    return MathHelper.a(i, j);
                }
            };
        case 5:
            return (i, j) -> {
                if (j == 0) {
                    throw c.create();
                } else {
                    return MathHelper.b(i, j);
                }
            };
        case 6:
            return Math::min;
        case 7:
            return Math::max;
        default:
            throw b.create();
        }
    }

    // $FF: synthetic method
    public Object parse(StringReader stringreader) throws CommandSyntaxException {
        return this.a(stringreader);
    }

    @FunctionalInterface
    public interface a {
        void apply(ScoreboardScore var1, ScoreboardScore var2) throws CommandSyntaxException;
    }

    @FunctionalInterface
    interface b extends ArgumentMathOperation.a {
        int apply(int var1, int var2) throws CommandSyntaxException;

        default void apply(ScoreboardScore scoreboardscore, ScoreboardScore scoreboardscore1) throws CommandSyntaxException {
            scoreboardscore.setScore(this.apply(scoreboardscore.getScore(), scoreboardscore1.getScore()));
        }
    }
}