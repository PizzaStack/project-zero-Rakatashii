package utility;

public class Symbols {
	public static String whiteStar = String.valueOf("\u2606".toCharArray());
	public static String colorStar = String.valueOf("\u2B50".toCharArray());
	public static String blackStar = String.valueOf("\u2605".toCharArray());
	public static String whiteCircle = String.valueOf("\u26AA".toCharArray());
	public static String blackCircle = String.valueOf("\u26AB".toCharArray());
	public static String snowman = String.valueOf("\u26C4".toCharArray());
	public static String swirlStar = String.valueOf("\u269D".toCharArray());
	public static String openCross = String.valueOf("\u271B".toCharArray());
	public static String holyCross = String.valueOf("\u271C".toCharArray());
	public static String stressStar = String.valueOf("\u2729".toCharArray());
	public static String diamond = String.valueOf("\u2727".toCharArray());
	public static String whiteKing = String.valueOf("\u2654".toCharArray());
	public static String warning = String.valueOf('\u26A0');
	public static String blackDiamond = String.valueOf('\u2756');
	
	
	public static void printSymbols() {
    	System.out.println(whiteStar);
    	System.out.println(colorStar);
    	System.out.println(blackStar);
    	System.out.println(whiteCircle);
    	System.out.println(blackCircle);
    	System.out.println(snowman);
    	System.out.println(swirlStar);
    	System.out.println(openCross);
    	System.out.println(holyCross);
    	System.out.println(stressStar);
    	System.out.println(diamond);
	}
}
