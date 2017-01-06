package gory_moon.moarsigns.integration.tweaker;

import gory_moon.moarsigns.api.ShapedMoarSignRecipe.MatchType;
import minetweaker.IBracketHandler;
import minetweaker.annotations.BracketHandler;
import minetweaker.runtime.GlobalRegistry;
import stanhebben.zenscript.compiler.IEnvironmentGlobal;
import stanhebben.zenscript.expression.ExpressionCallStatic;
import stanhebben.zenscript.expression.ExpressionString;
import stanhebben.zenscript.expression.partial.IPartialExpression;
import stanhebben.zenscript.parser.Token;
import stanhebben.zenscript.symbols.IZenSymbol;
import stanhebben.zenscript.type.natives.IJavaMethod;
import stanhebben.zenscript.type.natives.JavaMethod;
import stanhebben.zenscript.util.ZenPosition;

import java.util.List;

@BracketHandler
public class MatchTypeBracket implements IBracketHandler {

    public static MatchTypeEntry getMatchType(String match) {
        return new MatchTypeEntry(MatchType.getEnum(match));
    }

    public static MatchTypeEntry getMatchType(String match, String modID) {
        return new MatchTypeEntry(MatchType.getEnum(match), modID);
    }

    @Override
    public IZenSymbol resolve(IEnvironmentGlobal environment, List<Token> tokens) {
        if (tokens.size() > 2) {
            if (tokens.get(0).getValue().equals("matchType") && tokens.get(1).getValue().equals(":")) {
                Token token = tokens.get(2);
                if (token.getValue().equals("ALL") || token.getValue().equals("METAL") || token.getValue().equals("WOOD")) {
                    if (tokens.size() > 4 && tokens.get(3).getValue().equals(":")) {
                        return new MatchTypeReferenceSymbol(environment, token.getValue(), tokens.get(4).getValue());
                    }
                    return new MatchTypeReferenceSymbol(environment, token.getValue());
                }
            }
        }
        return null;
    }

    private class MatchTypeReferenceSymbol implements IZenSymbol {

        private final IEnvironmentGlobal environment;
        private final String match;
        private final String modID;

        public MatchTypeReferenceSymbol(IEnvironmentGlobal environment, String value, String modID) {
            this.environment = environment;
            this.match = value;
            this.modID = modID;
        }

        public MatchTypeReferenceSymbol(IEnvironmentGlobal environment, String value) {
            this(environment, value, null);
        }

        @Override
        public IPartialExpression instance(ZenPosition position) {
            if (modID == null) {
                IJavaMethod method = JavaMethod.get(GlobalRegistry.getTypeRegistry(), MatchTypeBracket.class, "getMatchType", String.class);

                return new ExpressionCallStatic(position, environment, method, new ExpressionString(position, match));
            } else {
                IJavaMethod method = JavaMethod.get(GlobalRegistry.getTypeRegistry(), MatchTypeBracket.class, "getMatchType", String.class, String.class);

                return new ExpressionCallStatic(position, environment, method, new ExpressionString(position, match), new ExpressionString(position, modID));
            }
        }
    }
}
