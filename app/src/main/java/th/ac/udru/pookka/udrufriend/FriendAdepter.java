package th.ac.udru.pookka.udrufriend;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class FriendAdepter extends RecyclerView.Adapter<FriendAdepter.FriendViewHolder>{

    private Context context;
    private ArrayList<String> displayNameStringArrayList, pathUrlStringArrayList;
    private OnClickItem onClickItem;
    private LayoutInflater layoutInflater;  //layout  เสมือน

    public FriendAdepter(Context context,
                         ArrayList<String> displayNameStringArrayList,
                         ArrayList<String> pathUrlStringArrayList,
                         OnClickItem onClickItem) {
        this.layoutInflater = LayoutInflater.from(context);
                this.displayNameStringArrayList = displayNameStringArrayList;
        this.pathUrlStringArrayList = pathUrlStringArrayList;
        this.onClickItem = onClickItem;
    }

    @NonNull
    @Override
    public FriendViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = layoutInflater.inflate(R.layout.recycler_friend,
                    viewGroup, false);
        FriendViewHolder friendViewHolder = new FriendViewHolder(view);

        return friendViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final FriendViewHolder friendViewHolder, int i) {

        String urlPathString = pathUrlStringArrayList.get(i);
        String displayNameString = displayNameStringArrayList.get(i);

        friendViewHolder.textView.setText(displayNameString);

        Picasso.get().load(urlPathString)
                .resize(100,100)
                .into(friendViewHolder.circleImageView);

        friendViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickItem.onClickItem(v,friendViewHolder.getAdapterPosition());
            }
        });


    }

    @Override
    public int getItemCount() {
        return displayNameStringArrayList.size(); //arrayList use .leang
    }

    public class FriendViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView circleImageView;
        private TextView textView;

        public FriendViewHolder(@NonNull View itemView) {
            super(itemView);

            circleImageView = itemView.findViewById(R.id.circleImageFriend);
            textView = itemView.findViewById(R.id.txtDisplayName);


        } // constructor

    }  //FriendViewHolder class



}  // Main class
