package menu.myapplication.slidingmenu;


import menu.myapplication.MenuLeftFragment;

/**
 * MainActivity与Fragment的回调接口
 *
 * @author Devin
 */
public interface MainInter
{

    /**
     * 关闭抽屉效果
     */
    public void closeDraw();

    /**
     * 返回抽屉布局
     *
     */
    public MenuLeftFragment getMenuLeftFragment();
}
