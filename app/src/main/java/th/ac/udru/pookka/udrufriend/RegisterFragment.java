package th.ac.udru.pookka.udrufriend;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {

//    explicit

    private Uri uri;
    private ImageView imageView;
    private boolean aBoolean = true;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        create toolbar
        createToolbar();

//        avata controller
        avataController();

    }   // main Method

    //สร้าง Override Method ด้วยการกด  Alt+insert


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.itemUpload) {

            checkData();
            return true;
        }

        return super.onOptionsItemSelected(item);


    }

    private void checkData() {

        MyAlert myAlert = new MyAlert(getActivity());

//        Get value from EditText to String
        EditText nameEditText = getView().findViewById(R.id.edtName);
        EditText emailEditText = getView().findViewById(R.id.edtEmail);
        EditText passwordEditText = getView().findViewById(R.id.edtPassword);
        EditText repasswordEditText = getView().findViewById(R.id.edtRepassword);
        EditText telEditText = getView().findViewById(R.id.edtTel);

        String nameString = nameEditText.getText().toString().trim();
        String emailString = emailEditText.getText().toString().trim();
        String passwordString = passwordEditText.getText().toString().trim();
        String repasswordString = repasswordEditText.getText().toString().trim();
        String telString = telEditText.getText().toString().trim();


        if (aBoolean) {
            // myAlert.normalDialog(getString(R.string.title_have_space),getString(R.string.message_have_space));
            myAlert.normalDialog("No Avata", "Choose img");
        } else if (checkSpace(nameString,emailString,passwordString,repasswordString,telString)) { //สร้างเมธอท checkspace มารับค่าชุดตัวแปรสตริงพวกนี้ เ
            myAlert.normalDialog(getString(R.string.title_have_space),getString(R.string.message_have_space));

        } else if (passwordString.equals(repasswordString)) {
            //pss match
            uploadToFirebase(nameString,emailString,passwordString,telString);
        } else {
            myAlert.normalDialog("Pss not match","Please type Pss again");

        }

    }

    private void uploadToFirebase(final String nameString, String emailString, String passwordString, String telString) {

        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Please waite...");
        progressDialog.show();

//        upload Image
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageReference = firebaseStorage.getReference();
        StorageReference storageReference1 = storageReference.child("Avata/" + nameString);
        storageReference1.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(getActivity(),"Success upload" , Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();


//              Register email

                String urlAvata = FindURLavata(nameString);
                Log.d("20nov1", "urlAvata ==> " + urlAvata);
                // ถ้าไม่ได้ให้ใช้ Logใwtf





            } //onSuccess
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(),"Cannot upload" , Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }
        });


//        register email


    }

    private String FindURLavata(String nameString) {


        myFindURL(nameString);

        return nameString;

    }

    private void myFindURL(String nameString) {
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageReference = firebaseStorage.getReference();

        final String[] strings = new String[1];
        storageReference.child("Avata").child(nameString)
                .getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        strings[0] = uri.toString();


                    }
                });
    }

    // รับค่าชุดตัวแปรสติง แล้วส่งข้อมูลไปให้การทำงานใน aboolean
    private boolean checkSpace(String nameString,
                               String emailString,
                               String passwordString,
                               String repasswordString,
                               String telString) {

        boolean result = false;
        if (nameString.isEmpty() || emailString.isEmpty() || passwordString.isEmpty() ||repasswordString.isEmpty() || telString.isEmpty()) {
            result =true;


        }

        return false;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_register, menu);


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == getActivity().RESULT_OK) {

            uri = data.getData();
            aBoolean = false;

            try {
                //
                Bitmap bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(uri));
                //resize image
                Bitmap bitmap1 = Bitmap.createScaledBitmap(bitmap, 800, 600, false);
                imageView.setImageBitmap(bitmap1);

            } catch (Exception e) {             // Exception e =การเออเร่อที่ยอมรับได้
                e.printStackTrace();

            }

        } else {
            Toast.makeText(getActivity(),
                    "Please Choose Image",
                    Toast.LENGTH_SHORT).show();
        }

    }  //result

    private void avataController() {
        imageView = getView().findViewById(R.id.imageViewAvartar);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent,
                        "Please choose App and Image"), 5);
            }
        });

    }

    private void createToolbar() {
        Toolbar toolbar = getView().findViewById(R.id.toolbarRegister);
        ((MainActivity) getActivity()).setSupportActionBar(toolbar);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle(R.string.register);
        ((MainActivity) getActivity()).getSupportActionBar().setSubtitle(R.string.title_have_space);
        ((MainActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });


        setHasOptionsMenu(true);

    }

    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

}
