package gory_moon.moarsigns.integration.tweaker;

import crafttweaker.annotations.BracketHandler;
import crafttweaker.zenscript.GlobalRegistry;
import crafttweaker.zenscript.IBracketHandler;
import gory_moon.moarsigns.api.MaterialInfo;
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
public class MaterialBracket implements IBracketHandler {

    public static MaterialEntry getMaterial(String name) {
        return new MaterialEntry(new MaterialInfo(name));
    }

    public static MaterialEntry getMaterial(String name, String modID) {
        return new MaterialEntry(new MaterialInfo(name), modID);
    }

    @Override
    public IZenSymbol resolve(IEnvironmentGlobal environment, List<Token> tokens) {
        if (tokens.size() > 2) {
            if (tokens.get(0).getValue().equals("signMaterial") && tokens.get(1).getValue().equals(":")) {
                if (tokens.size() > 4 && tokens.get(3).getValue().equals(":")) {
                    return new MaterialReferenceSymbol(environment, tokens.get(2).getValue(), tokens.get(4).getValue());
                }
                return new MaterialReferenceSymbol(environment, tokens.get(2).getValue());
            }
        }
        return null;
    }

    private class MaterialReferenceSymbol implements IZenSymbol {

        private final IEnvironmentGlobal environment;
        private final String name;
        private final String modID;

        public MaterialReferenceSymbol(IEnvironmentGlobal environment, String value, String modID) {
            this.environment = environment;
            this.name = value;
            this.modID = modID;
        }

        public MaterialReferenceSymbol(IEnvironmentGlobal environment, String value) {
            this(environment, value, null);
        }

        @Override
        public IPartialExpression instance(ZenPosition position) {
            if (modID == null) {
                IJavaMethod method = JavaMethod.get(GlobalRegistry.getTypes(), MaterialBracket.class, "getMaterial", String.class);

                return new ExpressionCallStatic(position, environment, method, new ExpressionString(position, name));
            } else {
                IJavaMethod method = JavaMethod.get(GlobalRegistry.getTypes(), MaterialBracket.class, "getMaterial", String.class, String.class);

                return new ExpressionCallStatic(position, environment, method, new ExpressionString(position, name), new ExpressionString(position, modID));
            }
        }
    }
}
