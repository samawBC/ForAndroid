package th.ac.udru.pookka.udrufriend;

public class MyConstant {

    private int[] iconInts = new int[]{
            R.drawable.ic_action_iconmenu1,
            R.drawable.ic_action_map,
            R.drawable.ic_action_iconmenu3,
            R.drawable.ic_action_exit2};

    private String[] titleStrings = new String[]{
            "My Friends",
            "My Map",
            "About Me",
            "Sign Out"};

    public int[] getIconInts() {
        return iconInts;
    }

    public String[] getTitleStrings() {
        return titleStrings;
    }
}
