package in.nacode.nacode.ViewModel;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import in.nacode.nacode.ProgrammingLang.Bookmarks.ProgrammingLangC_BookmarkFragmentNotes;
import in.nacode.nacode.ProgrammingLang.Bookmarks.ProgrammingLangC_BookmarkFragmentProgram;


public class PageAdapterSildeTab extends FragmentStatePagerAdapter {

    int mNoOfTabs;

    public PageAdapterSildeTab(FragmentManager fm,int NumberOfTabs) {
        super(fm);
        this.mNoOfTabs = NumberOfTabs;

    }

    @Override
    public Fragment getItem(int position) {

        switch (position)
        {
            case 0:
                ProgrammingLangC_BookmarkFragmentProgram tab1 = new ProgrammingLangC_BookmarkFragmentProgram();
                return tab1;
            case 1:
                ProgrammingLangC_BookmarkFragmentNotes tab2 = new ProgrammingLangC_BookmarkFragmentNotes();
                return tab2;
            default:
                return  null;
        }

    }

    @Override
    public int getCount() {
        return mNoOfTabs;
    }
}
