package com.example.project;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class GuidesForm extends AppCompatActivity {

    // creating variables for EditText and buttons.
    EditText guideTitleEdt, guideDescriptionEdt;
    //ImageView guideImageEdt;
    Button saveDatabtn;
    //GuidesForm guidesform;

    // creating a variable for our Firebase Database.
    FirebaseDatabase firebaseDatabase;

    // creating a variable for our Database Reference for Firebase.
    DatabaseReference databaseReference;

    // creating a variable for our object class
    GuidesInfo guidesInfo;

    Button BSelectImage;
    ImageView IVPreviewImage;
    int SELECT_PICTURE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guides_form);

        // initializing our edittext and button
        guideTitleEdt = findViewById(R.id.editText3);
        guideDescriptionEdt = findViewById(R.id.editText);
        //guideImageEdt = findViewById(R.id.IVPreviewImage);

        // below line is used to get the instance of our Firebase database.
        firebaseDatabase = FirebaseDatabase.getInstance();

        // below line is used to get reference for our database.
        databaseReference = firebaseDatabase.getReference("Guides");

        // initializing our object class variable.
        guidesInfo = new GuidesInfo();

        saveDatabtn = findViewById(R.id.SaveButton);
        saveDatabtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //create functionality
                databaseReference = FirebaseDatabase.getInstance().getReference().child("Guides");//creating the document in the firebase
                DatabaseReference pushRef = databaseReference.push();
                try{
                    if(TextUtils.isEmpty(guideTitleEdt.getText().toString()))
                        Toast.makeText(getApplicationContext(),"Please enter a Title",Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(guideDescriptionEdt.getText().toString()))
                        Toast.makeText(getApplicationContext(),"Please enter a Description",Toast.LENGTH_SHORT).show();
                    else {
                        guidesInfo.setGuideTitle(guideTitleEdt.getText().toString().trim());
                        guidesInfo.setGuideDescription(guideDescriptionEdt.getText().toString().trim());

                        pushRef.setValue(guidesInfo);
                        Toast.makeText(getApplicationContext(),"Guide created successfully",Toast.LENGTH_SHORT).show();

                        //Assigning the document key
                        String GuideKey = pushRef.getKey();

                        Intent intent = new Intent(GuidesForm.this, GuidesFeeds.class);
                        intent.putExtra("Key",GuideKey);
                        startActivity(intent);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

//                // getting text from our edittext fields.
//                String title = guideTitleEdt.getText().toString();
//                String description = guideDescriptionEdt.getText().toString();
//
//                // below line is for checking whether the edittext fields are empty or not.
//                if (TextUtils.isEmpty(title) && TextUtils.isEmpty(description)) {
//                    // if the text fields are empty then show the below message.
//                    Toast.makeText(GuidesForm.this, "Please add some data.", Toast.LENGTH_SHORT).show();
//                } else {
//                    // else call the method to add data to our database.
//                    addDatatoFirebase(title, description);
//                }
            }
        });

        // register the UI widgets with their appropriate IDs
        BSelectImage = findViewById(R.id.BSelectImage);
        IVPreviewImage = findViewById(R.id.IVPreviewImage);

        // handle the Choose Image button to trigger the image chooser function
        BSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser();
            }
        });

    }

//    private void addDatatoFirebase(String title, String description) {
//        // below 3 lines of code is used to set
//        // data in our object class.
//        guidesInfo.setGuideTitle(title);
//        guidesInfo.setGuideDescription(description);
//
//        // we are use add value event listener method
//        // which is called with database reference.
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                // inside the method of on Data change we are setting
//                // our object class to our database reference.
//                // data base reference will sends data to firebase.
//                databaseReference.setValue(guidesInfo);
//
//                // after adding this data we are showing toast message.
//                Toast.makeText(GuidesForm.this, "data added", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                // if the data is not added or it is cancelled then
//                // we are displaying a failure toast message.
//                Toast.makeText(GuidesForm.this, "Fail to add data " + error, Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

    // this function is triggered when the Select Image Button is clicked
    void imageChooser() {
        // create an instance of the intent of the type image
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        // pass the constant to compare it with the returned requestCode
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }

    // this function is triggered when user selects the image from the imageChooser
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            // compare the resultCode with the SELECT_PICTURE constant
            if (requestCode == SELECT_PICTURE) {
                // Get the url of the image from data
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    // update the preview image in the layout
                    IVPreviewImage.setImageURI(selectedImageUri);
                }
            }
        }
    }
}