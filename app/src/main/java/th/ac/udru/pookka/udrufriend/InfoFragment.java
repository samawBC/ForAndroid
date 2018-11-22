package th.ac.udru.pookka.udrufriend;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class InfoFragment extends Fragment {


    public InfoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // show view
        showView();


    }  //main method

    private void showView() {
        final CircleImageView circleImageView = getView().findViewById(R.id.circleImageFriend);
        final TextView textView = getView().findViewById(R.id.txtDisplayName);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        String uidString = firebaseUser.getUid();

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference().child("User").child(uidString);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DatabaseModel databaseModel = dataSnapshot.getValue(DatabaseModel.class);
                String displayNameString = databaseModel.getNameString();
                String urlPathString = databaseModel.getPathUrlString();
                Log.d("22novV3", "name ==>" + displayNameString);
                Log.d("22novV3", "Path ==>" + urlPathString);

                textView.setText(displayNameString);
                Picasso.get().load(urlPathString).resize(200,200).into(circleImageView);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_info, container, false);
    }

}
