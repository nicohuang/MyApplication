package menu.myapplication;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import menu.myapplication.slidingmenu.MainInter;

/**
 * 抽屉效果向左滑动时出现的fragment类
 *
 * @author Devin
 */
public class MenuLeftFragment extends Fragment implements View.OnClickListener
{
    //Fragment操作类
    private FragmentManager fragmentManager;
    private long firstTime=0;
    private FragmentTransaction transaction;
    private BlankFragment1 blankFragment1;
    private BlankFragment2 blankFragment2;
    private LinearLayout ll1;
    private LinearLayout ll2;

    MainInter mainInter;


    //现实的fragment
    private List<Integer> showFragmentList = new ArrayList<Integer>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_main_menuleft,null,false);
        ll1 = (LinearLayout)view.findViewById(R.id.ll1);
        ll2 = (LinearLayout)view.findViewById(R.id.ll2);
        ll1.setTag(0);
        ll2.setTag(1);
        ll1.setOnClickListener(this);
        ll2.setOnClickListener(this);
        mainInter = (MainInter) getActivity();
        fragmentManager = getFragmentManager();
        init();
        return view;
    }


    @Override
    public void onClick(View v)
    {

        int tag = (Integer)v.getTag();
        mainInter.closeDraw();
        showFrament(tag);

    }

    /**
     * 显示fragment
     * @param tag
     */
    private void showFrament(int tag)
    {
        for(int i=0;i<showFragmentList.size();i++)
        {
            if(showFragmentList.get(i) == tag)
            {
                showFragmentList.remove(i);
                break;
            }
        }
        showFragmentList.add(0,tag);
        transaction = fragmentManager.beginTransaction();
        switch (tag)
        {
            case 0:
                if (blankFragment1 == null)
                {
                    blankFragment1 = new BlankFragment1();
                    transaction.replace(R.id.center_frame, blankFragment1);
                }
                else
                {
                    transaction.replace(R.id.center_frame, blankFragment1);
                }
                break;
            case 1:
                if (blankFragment2 == null)
                {
                    blankFragment2 = new BlankFragment2();
                    transaction.replace(R.id.center_frame, blankFragment2);
                }
                else
                {
                    transaction.replace(R.id.center_frame, blankFragment2);
                }
                break;
            default:
                break;

        }
        transaction.commit();
    }
    /**
     * 初始化
     */
    private void init()
    {
        transaction = fragmentManager.beginTransaction();
        blankFragment1 = new BlankFragment1();
        transaction.replace(R.id.center_frame, blankFragment1);
        transaction.commit();
    }
    /**
     * 隐藏其他fragment,如果不隐藏将一直累加
     *
     * @param transaction
     */
    private void hideFragment(FragmentTransaction transaction)
    {
        if (blankFragment1 != null)
        {
            transaction.hide(blankFragment1);
        }
        if (blankFragment2 != null)
        {
            transaction.hide(blankFragment2);
        }

    }

    /**
     * 显示上一个framgent
     */
    public void showLastFragment()
    {
        if (showFragmentList.size() > 1)
        {
            transaction = fragmentManager.beginTransaction();
            blankFragment1 = new BlankFragment1();
            transaction.replace(R.id.center_frame, blankFragment1);
            transaction.commit();
            showFragmentList.remove(0);
        }
        else
        {
            long secondTime = System.currentTimeMillis();
            if (secondTime - firstTime > 2000)
            {
                Toast.makeText(getActivity(),"再按一次退出",Toast.LENGTH_SHORT).show();
                //更新firstTime
                firstTime = secondTime;
            }
            else
            {
                System.exit(0);
            }

        }

    }
}
