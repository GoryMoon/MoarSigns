import minetweaker.item.IItemStack;

//Adds a new recipe containing any sign from MoarSigns
//OutputStack, InputArray
mods.MoarSigns.addShaped(<minecraft:piston>,
    [[<minecraft:iron_ingot>, <minecraft:iron_ingot>, <minecraft:iron_ingot>],
     [<minecraft:iron_ingot>, <matchType:ALL>, <minecraft:iron_ingot>],
     [<minecraft:iron_ingot>, <minecraft:iron_ingot>, <minecraft:iron_ingot>]]);

//Adds a new recipe containing any metal sing from MoarSigns
//OutputStack, InputArray
mods.MoarSigns.addShapeless(<minecraft:minecart> * 5,
    [<minecraft:iron_ingot>, <minecraft:iron_ingot>,
     <minecraft:iron_ingot>, <matchType:METAL>, <minecraft:iron_ingot>]);

//Adds a new recipe containing any sings made out of spruce from MoarSigns
//OutputStack, InputArray
mods.MoarSigns.addShaped(<minecraft:chest>,
    [[<minecraft:planks>, <minecraft:planks>, <minecraft:planks>],
     [<minecraft:planks>, <signMaterial:spruce>, <minecraft:planks>],
     [<minecraft:planks>, <minecraft:planks>, <minecraft:planks>]]);

//Adds a new recipe containing any sings made out of oak from MoarSigns
//OutputStack, InputArray
mods.MoarSigns.addShapeless(<minecraft:boat>,
    [<minecraft:planks>, <minecraft:planks>,
     <minecraft:planks>, <signMaterial:oak>, <minecraft:planks>]);

//Gets the first sign that matches the InputStack
//InputStack
var sign = mods.MoarSigns.getFirstSign(<signMaterial:tin>) as IItemStack;
mods.MoarSigns.addShaped(<minecraft:stone_pickaxe>, [[sign, <minecraft:stick>]]);

//Gets the first sign that matches the InputStack, a tin sign with material from Railcraft
//InputStack
var sign1 = mods.MoarSigns.getFirstSign(<signMaterial:tin:Railcraft>) as IItemStack;
//Removes all recipes that matches the OutputStack
//InputStack
mods.MoarSigns.removeRecipes(sign1);

//Gets all signs as an array, all tin signs
//InputStack
var signs = mods.MoarSigns.getSigns(<signMaterial:copper>) as IItemStack[];
//Removes all recipes that is in the array
//InputArray
mods.MoarSigns.removeRecipes(signs);