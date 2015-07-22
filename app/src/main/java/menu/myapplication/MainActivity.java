package menu.myapplication;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import menu.myapplication.slidingmenu.MainInter;
import menu.myapplication.slidingmenu.SlidingFragmentActivity;
import menu.myapplication.slidingmenu.SlidingMenu;

/**
 * 首页Activity
 *
 * @author deVin
 */
public class MainActivity extends SlidingFragmentActivity implements MainInter
{


    //加载fragment用
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;

    private MenuLeftFragment menuLeft;

    //手机的宽度
    public static int width;

    //手机的高度
    public static int height;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView(savedInstanceState);
    }

    /**
     * 初始化
     */
    private void initView(Bundle savedInstanceState)
    {
        // 检查menu_fragme
        if (findViewById(R.id.menu_frame) == null)
        {
            setBehindContentView(R.layout.menu_frame);
            getSlidingMenu().setSlidingEnabled(true);
            getSlidingMenu()
                    .setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        }
        else
        {
            // add a dummy view
            View v = new View(this);
            setBehindContentView(v);
            getSlidingMenu().setSlidingEnabled(false);
            getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        }

        //加载邮箱默认布局
        fragmentManager = getFragmentManager();
        transaction = fragmentManager.beginTransaction();
        menuLeft = new MenuLeftFragment();
        transaction.add(R.id.menu_frame, menuLeft);
        transaction.commit();


        // 设置slidingMenu的参数
        SlidingMenu sm = getSlidingMenu();
        sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        sm.setFadeEnabled(false);
        sm.setBehindScrollScale(0.1f);
        sm.setFadeDegree(0.25f);

        //设置背景图片
        sm.setBackgroundImage(R.mipmap.bg_main_backgroud);
        //设置Menu的缩放参数
        sm.setBehindCanvasTransformer(new SlidingMenu.CanvasTransformer()
        {
            @Override
            public void transformCanvas(Canvas canvas, float percentOpen)
            {
                float scale = (float) (percentOpen * 0.1 + 0.9);
                canvas.scale(scale, scale, -canvas.getWidth() / 2,
                        canvas.getHeight() / 2);
            }
        });
        //设置滑动后main的缩放参数
        sm.setAboveCanvasTransformer(new SlidingMenu.CanvasTransformer()
        {
            @Override
            public void transformCanvas(Canvas canvas, float percentOpen)
            {
                float scale = (float) (1 - percentOpen * 0.1);
                canvas.scale(scale, scale, 0, canvas.getHeight() / 2);
            }
        });

        sm.setOnOpenListener(new SlidingMenu.OnOpenListener()
        {
            @Override
            public void onOpen()
            {

            }
        });
    }



    /**
     * 接口回调，关闭抽屉
     */
    @Override
    public void closeDraw()
    {
        getSlidingMenu().toggle();
    }

    /**
     * 获取抽屉布局
     *
     * @return
     */
    @Override
    public MenuLeftFragment getMenuLeftFragment()
    {
        return menuLeft;
    }




    /**
     * 重写返回键
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK && (!getSlidingMenu().isMenuShowing()))
        {
            menuLeft.showLastFragment();
            return false;
        }
        else if (keyCode == KeyEvent.KEYCODE_BACK && getSlidingMenu().isMenuShowing())
        {
            getSlidingMenu().showMenu(false);
        }
        return super.onKeyDown(keyCode, event);
    }
}
