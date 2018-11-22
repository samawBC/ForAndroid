package th.ac.udru.pookka.udrufriend;


import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ServiceFragment extends Fragment {


    public ServiceFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        Create RecyclerView
        createRecyclerView();

    }

    private void createRecyclerView() {
        final RecyclerView recyclerView = getView().findViewById(R.id.recycleViewFriend);

        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,
                LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);

        final String tag = "22novV1";

        final int[] countInts = new int[]{0};
        final ArrayList<String> displayNameStringArrayList = new ArrayList<>();
        final ArrayList<String> urlAvataStringArrayList = new ArrayList<>();
        final ArrayList<String> uidFriendStringArrayList = new ArrayList<>();

//        Get Value From Firebase
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference().child("User");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//dataSnapshot จะอ่านข้อมูลจาก User จาก Firebase มาทั้งก้อน
                int i = (int) dataSnapshot.getChildrenCount();
                Log.d(tag, "จำนวนของ User ==> " + i);

                ArrayList<DatabaseModel> databaseModelArrayList = new ArrayList<>();
                for (DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){
                    DatabaseModel databaseModel = dataSnapshot1.getValue(DatabaseModel.class);
                    databaseModelArrayList.add(databaseModel);

                    DatabaseModel databaseModel1 = databaseModelArrayList.get(countInts[0]);
                    countInts[0] += 1;
                    displayNameStringArrayList.add(databaseModel1.getNameString());
                    urlAvataStringArrayList.add(databaseModel.getPathUrlString());
                    uidFriendStringArrayList.add(databaseModel1.getUidString());

                } // for

                Log.d(tag, "displayNameArrayList ==> " + displayNameStringArrayList.toString());
                Log.d(tag, "urlArrayList ==> " + urlAvataStringArrayList.toString());

                FriendAdepter friendAdepter = new FriendAdepter(getActivity(),
                        displayNameStringArrayList, urlAvataStringArrayList, new OnClickItem() {
                    @Override
                    public void onClickItem(View view, int position) {
                        Log.d("22novV3", "You click ==>" + uidFriendStringArrayList.get(position));

                    }
                });
                recyclerView.setAdapter(friendAdepter);

            } // onData change

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }  // createRecyclerView

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_, container, false);
    }

}
