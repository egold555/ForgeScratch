package org.golde.forge.scratchforge.basemodfiles;

import net.minecraft.util.StatCollector;

//public static final String[] field_150921_b = new String[] {"black", "red", "green", "brown", "blue", "purple", "cyan", "silver", "gray", "pink", "lime", "yellow", "light_blue", "magenta", "orange", "white"};
//public static final int[] field_150922_c = new int[] {      1973019, 11743532,3887386,5320730, 2437522, 8073150, 2651799, 11250603,4408131,14188952,4312372, 14602026, 6719955, 12801229, 15435844, 15790320};

public enum EnumColor
{
	BLACK("0", "black", "Black", new int[] { 0, 0, 0 }, 0, 1973019),  
	DARK_BLUE("1", "darkBlue", "Blue", new int[] { 0, 0, 170 }, 4, 2437522),  
	DARK_GREEN("2", "darkGreen", "Green", new int[] { 0, 170, 0 }, 2, 3887386),  
	DARK_AQUA("3", "darkAqua", "Cyan", new int[] { 0, 255, 255 }, 6, 65535),  
	DARK_RED("4", "darkRed", null, new int[] { 170, 0, 0 }, -1, 11743532),  
	PURPLE("5", "purple", "Purple", new int[] { 170, 0, 170 }, 5, 8073150),  
	ORANGE("6", "orange", "Orange", new int[] { 255, 170, 0 }, 14, 15435844),  
	GREY("7", "grey", "LightGray", new int[] { 170, 170, 170 }, 7, 11250603),  
	DARK_GREY("8", "darkGrey", "Gray", new int[] { 85, 85, 85 }, 8, 4408131),  
	INDIGO("9", "indigo", "LightBlue", new int[] { 85, 85, 255 }, 12, 6719955),  
	BRIGHT_GREEN("a", "brightGreen", "Lime", new int[] { 85, 255, 85 }, 10, 4312372),  
	AQUA("b", "aqua", null, new int[] { 85, 255, 255 }, -1, 5636095),  
	RED("c", "red", "Red", new int[] { 255, 85, 85 }, 1, 16732240),  
	PINK("d", "pink", "Magenta", new int[] { 255, 85, 255 }, 13, 14188952),  
	YELLOW("e", "yellow", "Yellow", new int[] { 255, 255, 85 }, 11, 14602026),  
	WHITE("f", "white", "White", new int[] { 255, 255, 255 }, 15, 15790320),  
	BROWN("6", "brown", "Brown", new int[] { 150, 75, 0 }, 3, 5320730),  
	BRIGHT_PINK("d", "brightPink", "Pink", new int[] { 255, 192, 203 }, 9, 16761035);

	public static EnumColor[] DYES = { BLACK, RED, DARK_GREEN, BROWN, DARK_BLUE, PURPLE, DARK_AQUA, GREY, DARK_GREY, BRIGHT_PINK, BRIGHT_GREEN, YELLOW, INDIGO, PINK, ORANGE, WHITE };
	private final String code;
	private final int[] rgbCode;
	private final int mcMeta;
	private String unlocalizedName;
	private String dyeName;
	private int fireworkColor;

	private EnumColor(String s, String n, String dye, int[] rgb, int meta, int firework)
	{
		this.code = "\u00a7" + s; //from enum chat formatting
		this.unlocalizedName = n;
		this.dyeName = dye;
		this.rgbCode = rgb;
		this.mcMeta = meta;
		this.fireworkColor = firework;
	}

	public String getLocalizedName()
	{
		return StatCollector.translateToLocal("color." + this.unlocalizedName);
	}

	public String getDyeName()
	{
		return StatCollector.translateToLocal("dye." + this.unlocalizedName);
	}

	public String getOreDictName()
	{
		return this.dyeName;
	}

	public String getName()
	{
		return this.code + getLocalizedName();
	}

	public String getDyedName()
	{
		return this.code + getDyeName();
	}

	public float getColor(int index)
	{
		return this.rgbCode[index] / 255.0F;
	}

	public int getMetaValue()
	{
		return this.mcMeta;
	}

	public String toString()
	{
		return this.code;
	}

	public int getFireworkColor() {
		return fireworkColor;
	}
}
