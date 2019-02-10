package multilevel.multilevelmarkitning.Admin;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import multilevel.multilevelmarkitning.R;


public class Admin_Profile extends Fragment {


    public Admin_Profile() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.admin_profile, container, false);
    }

}
