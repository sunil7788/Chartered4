package com.chartered4.add_listing;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;
import static android.provider.MediaStore.Images.Media.getBitmap;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.chartered4.R;
import com.chartered4.databinding.FragmentPhotosAndDescriptionBinding;
import com.chartered4.utils.AppConstants;
import com.chartered4.utils.AppDialogs;
import com.chartered4.utils.AppUtils;
//import com.chartered4.utils.MyRecyclerHelper;
//import com.chartered4.utils.OnDragListener;
//import com.chartered4.utils.OnSwipeListener;
//import com.chartered4.utils.MyRecyclerHelper;
import com.chartered4.utils.ItemMoveCallback;
import com.chartered4.utils.StartDragListener;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


/**
 * Created by DELL on 14-Oct-18.
 */

public class PhotosAndDescriptionFragment extends Fragment implements View.OnClickListener/*, StartDragListener*/ {

    FragmentPhotosAndDescriptionBinding binding;

    String[] appPermissions = {Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    private static final int PERMISSION_REQUEST_CODE = 10001;
    private static final int SETTINGS_REQUEST_CODE = 10002;

    Uri realUri;
    File fileAttachment = null;

    ArrayList<File> arrUploadFiles = new ArrayList<>();
    ItemTouchHelper touchHelper;
    UploadPhotosAdapter uploadPhotosAdapter;

    public PhotosAndDescriptionFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentPhotosAndDescriptionBinding.inflate(inflater, container, false);

        setListeners();

        setUploadPhotosAdapter();

        return binding.getRoot();
    }

    private void setListeners() {
        binding.btnPrevious.setOnClickListener(this);
        binding.btnContinue.setOnClickListener(this);
        binding.cardUploadPhotos.setOnClickListener(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        /*toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setTitle(getResources().getString(R.string.booking_history));
        toolbar.setSubtitle("");
        ((MainActivity) getActivity()).setSupportActionBar(toolbar);
        ((MainActivity) getActivity()).selectMenuItem(getResources().getString(R.string.booking_history));

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).onBackPressed();
            }
        });*/
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        if (viewId == R.id.btnPrevious) {
            AppUtils.hideKeyboard(binding.btnPrevious, getActivity());
            ((AddListingActivity) getActivity()).changePreviousPage();
        }
        if (viewId == R.id.btnContinue) {
            AppUtils.hideKeyboard(binding.btnContinue, getActivity());
            validateData();
        }
        if (viewId == R.id.cardUploadPhotos) {
            if (arrUploadFiles.size() == 10){
                AppDialogs.showSnackBarAutoHide(getString(R.string.uploadPhotosMaxMessage), binding.cardUploadPhotos);
                return;
            }
            if (checkAppPermissions(appPermissions)) {
                showSelectImageDialog();
            } else {
                requestAppPermissions(appPermissions);
            }
        }
    }

    public void validateData(){
        if (arrUploadFiles == null){
            AppDialogs.showSnackBarAutoHide(getString(R.string.errUploadPhotos), binding.cardUploadPhotos);
            return;
        }
        if (arrUploadFiles.size() == 0){
            AppDialogs.showSnackBarAutoHide(getString(R.string.errUploadPhotos), binding.cardUploadPhotos);
            return;
        }
        AddListingActivity.addListingBean.setImagePath("https://testcharteredca.s3.ca-central-1.amazonaws.com/boats/6ffb3fbe-dc33-4db4-97cc-af551d6e0dc6_cf.jpg,https://testcharteredca.s3.ca-central-1.amazonaws.com/boats/5330e6cd-0362-440e-82a5-37238b417e9e_aw.jpg");
        AddListingActivity.addListingBean.setDescription("[{\\\"headerPlaceholder\\\":\\\"Rules & Policies\\\",\\\"descPlaceholder\\\":\\\"For Example:\\\\n1. No pets onboard\\\\n2. Smoking onboard is prohibited\\\\n3. No jumping from vessel\\\",\\\"header\\\":\\\"Rules & Policies\\\",\\\"description\\\":\\\"\\\"},{\\\"headerPlaceholder\\\":\\\"Departure & Drop-off Location\\\",\\\"descPlaceholder\\\":\\\"Discuss your pick-up & drop-off location and procedure.\\\",\\\"header\\\":\\\"Departure & Drop-off Location\\\",\\\"description\\\":\\\"\\\"},{\\\"headerPlaceholder\\\":\\\"Additional Fees\\\",\\\"descPlaceholder\\\":\\\"Discuss any additional fees or security\\\\nallowances when booking your watercraft. Security allowances must be handled\\\\noutside the Chartered4 platform.\\\",\\\"header\\\":\\\"Additional Fees\\\",\\\"description\\\":\\\"\\\"}]");

        Log.e("Photos :", new Gson().toJson(AddListingActivity.addListingBean));

        ((AddListingActivity) getActivity()).changeNextPage();
    }

    public void showSelectImageDialog() {
        final Dialog dialogDate = new Dialog(getActivity());
        dialogDate.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogDate.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogDate.setContentView(R.layout.dialog_image_picker);
        dialogDate.setCancelable(true);

        Window window = dialogDate.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.dimAmount = 0.8f;
        window.setAttributes(wlp);
        dialogDate.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        FloatingActionButton fabClose = dialogDate.findViewById(R.id.fabClose);
        TextView txtTitle = dialogDate.findViewById(R.id.txtTitle);
        LinearLayout llCamera = dialogDate.findViewById(R.id.llCamera);
        LinearLayout llGallery = dialogDate.findViewById(R.id.llGallery);

        fabClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogDate.dismiss();
            }
        });

        llCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogDate.dismiss();
                takePicture();
            }
        });

        llGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogDate.dismiss();
//                galleryIntent();
                galleryMultipleIntent();
            }
        });

        if (!dialogDate.isShowing()) {
            dialogDate.show();
        }
    }

    private void setUploadPhotosAdapter() {
        uploadPhotosAdapter = new UploadPhotosAdapter(arrUploadFiles);

        ItemTouchHelper.Callback callback =
                new ItemMoveCallback(uploadPhotosAdapter);
        touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(binding.rvUploadPhotos);

        binding.rvUploadPhotos.setAdapter(uploadPhotosAdapter);

        /*ItemTouchHelper.Callback callback =
                new ItemMoveCallback(uploadPhotosAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(binding.rvUploadPhotos);*/

//        MyRecyclerHelper touchHelper = new MyRecyclerHelper(arrUploadFiles, (RecyclerView.Adapter) uploadPhotosAdapter);
        /*MyRecyclerHelper touchHelper = new MyRecyclerHelper<File>(arrUploadFiles, (RecyclerView.Adapter) uploadPhotosAdapter);
        touchHelper.setRecyclerItemDragEnabled(true).setOnDragItemListener(new OnDragListener() {
            @Override
            public void onDragItemListener(int fromPosition, int toPosition) {
                Log.d("TAG", "onDragItemListener: callback after dragging recycler view item");
            }
        });

        touchHelper.setRecyclerItemSwipeEnabled(true).setOnSwipeItemListener(new OnSwipeListener() {
            @Override
            public void onSwipeItemListener() {
                Log.d("TAG", "onSwipeItemListener: callback after swiping recycler view item");
            }
        });*/

        /*touchHelper.setRecyclerItemDragEnabled(true).setOnDragItemListener(new OnDragListener() {
            @Override
            public void onDragItemListener(int fromPosition, int toPosition) {
                Log.d("TAG", "onDragItemListener: callback after dragging recycler view item");
            }
        });
        touchHelper.setRecyclerItemSwipeEnabled(true).setOnSwipeItemListener(new OnSwipeListener() {
            @Override
            public void onSwipeItemListener() {
                Log.d("TAG", "onSwipeItemListener: callback after swiping recycler view item");
            }
        });*/
//        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(touchHelper);
//        itemTouchHelper.attachToRecyclerView(binding.rvUploadPhotos);

        uploadPhotosAdapter.setOnItemClickListener(new UploadPhotosAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int position, File bean) {
                arrUploadFiles.remove(position);
                uploadPhotosAdapter.notifyItemRemoved(position);
            }
        });
    }

    private void galleryIntent() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(intent, getResources().getString(R.string.choose_image)),
                AppConstants.SELECT_FILE_GALLERY);
    }

    private void galleryMultipleIntent() {
        /*Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(intent, getResources().getString(R.string.choose_image)),
                AppConstants.SELECT_FILE_GALLERY);*/

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, getResources().getString(R.string.choose_image)), AppConstants.SELECT_MULTIPLE_FILE_GALLERY);

    }

    private void takePicture() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        realUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, realUri);
        startActivityForResult(intent, AppConstants.REQUEST_CODE_TAKE_PICTURE);
    }

    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    private File getOutputMediaFile(int type) {
//        File mediaStorageDir = new File(Environment.getExternalStorageDirectory(), AppConstants.IMAGE_DIRECTORY_NAME);
        File mediaStorageDir;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + "/" + AppConstants.IMAGE_DIRECTORY_NAME);
        } else {
            mediaStorageDir = new File(Environment.getExternalStorageDirectory() + "/" + AppConstants.IMAGE_DIRECTORY_NAME);
        }
        if (!mediaStorageDir.exists()) {
            mediaStorageDir.mkdirs();
            Log.e("Dir", "not exist");
        } else {
            Log.e("Dir", "exist");
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());

        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");
        } else {
            return null;
        }
        return mediaFile;
    }

    public boolean checkAppPermissions(String[] appPermissions) {
        //check which permissions are granted
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String perm : appPermissions) {
            if (ContextCompat.checkSelfPermission(getActivity(), perm) != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(perm);
            }
        }

        //Ask for non granted permissions
        return listPermissionsNeeded.isEmpty();
        // App has all permissions
    }

    private void requestAppPermissions(String[] appPermissions) {
        ActivityCompat.requestPermissions(getActivity(), appPermissions, PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                HashMap<String, Integer> permissionResults = new HashMap<>();
                int deniedCount = 0;

                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                        permissionResults.put(permissions[i], grantResults[i]);
                        deniedCount++;
                    }
                }
                if (deniedCount == 0) {
                    Log.e("Permissions", "All permissions are granted!");
                } else {
                    //some permissions are denied
                    for (Map.Entry<String, Integer> entry : permissionResults.entrySet()) {
                        String permName = entry.getKey();
                        int permResult = entry.getValue();
                        //permission is denied and never asked is not checked
                        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), permName)) {
                            MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(getActivity());
                            materialAlertDialogBuilder.setMessage(getString(R.string.permission_msg));
                            materialAlertDialogBuilder.setCancelable(false)
                                    .setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                        }
                                    })
                                    .setPositiveButton(getString(R.string.yes_grant_permission), new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                            if (!checkAppPermissions(appPermissions)) {
                                                requestAppPermissions(appPermissions);
                                            }
                                        }
                                    });
                            materialAlertDialogBuilder.show();

                            break;
                        } else {//permission is denied and never asked is checked
                            MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(getActivity());
                            materialAlertDialogBuilder.setMessage(getString(R.string.permission_msg_never_checked));
                            materialAlertDialogBuilder.setCancelable(false)
                                    .setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                        }
                                    })
                                    .setPositiveButton(getString(R.string.go_to_settings), new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                            openSettings();
                                        }
                                    });
                            materialAlertDialogBuilder.show();

                            break;
                        }

                    }
                }

        }
    }

    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, SETTINGS_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SETTINGS_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (checkAppPermissions(appPermissions)) {
            } else {
                requestAppPermissions(appPermissions);
            }
        }
        if (requestCode == AppConstants.REQUEST_CODE_TAKE_PICTURE && resultCode == Activity.RESULT_OK) {
            onCaptureImage();
        }
        if (requestCode == AppConstants.SELECT_FILE_GALLERY && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                onSelectFromGalleryResult(data);
            }
        }
        if (requestCode == AppConstants.SELECT_MULTIPLE_FILE_GALLERY && resultCode == Activity.RESULT_OK) {
            if (data != null) {
//                onSelectFromGalleryResult(data);
                if (data.getData() != null) {
                    Log.e("Data", "Called");
                    Uri uri = data.getData();
                    Uri realUri = Uri.parse(getPathFile(getActivity(), uri));
                    File files = new File(realUri.getPath());
                    arrUploadFiles.add(files);
                    if (uploadPhotosAdapter != null) {
                        uploadPhotosAdapter.notifyDataSetChanged();
//                        uploadPhotosAdapter.notifyItemInserted(0);
                    }
                } else if (data.getClipData() != null) {
                    Log.e("ClipData", "Called"); //when multiple files are selected
                    int totalFiles = data.getClipData().getItemCount();
                    for (int i = 0; i < totalFiles; i++) {
                        if (i < 10){
                            Uri uri = data.getClipData().getItemAt(i).getUri();
                            Uri realUri = Uri.parse(getPathFile(getActivity(), uri));
                            File files = new File(realUri.getPath());
                            arrUploadFiles.add(files);
                            if (uploadPhotosAdapter != null) {
                                uploadPhotosAdapter.notifyDataSetChanged();
//                                uploadPhotosAdapter.notifyItemInserted(0);
                            }
                        }
                    }
                }
            }
        }
    }

    private void onCaptureImage() {

        new AsyncTask<Void, Void, Void>() {
            Bitmap bitmap;
            Bitmap rotatedBitmap;
            Bitmap bitmapNew;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
//                showProgressDialog();
            }

            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 2;
                    bitmap = BitmapFactory.decodeFile(realUri.getPath(), options);

                    ExifInterface ei = new ExifInterface(realUri.getPath());
                    int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                            ExifInterface.ORIENTATION_UNDEFINED);

                    switch (orientation) {
                        case ExifInterface.ORIENTATION_ROTATE_90:
                            rotatedBitmap = AppUtils.rotateImage(bitmap, 90);
                            break;

                        case ExifInterface.ORIENTATION_ROTATE_180:
                            rotatedBitmap = AppUtils.rotateImage(bitmap, 180);
                            break;

                        case ExifInterface.ORIENTATION_ROTATE_270:
                            rotatedBitmap = AppUtils.rotateImage(bitmap, 270);
                            break;

                        case ExifInterface.ORIENTATION_NORMAL:
                        default:
                            rotatedBitmap = bitmap;
                    }

                    final int maxSize = 1000;
                    int outWidth;
                    int outHeight;
                    int inWidth = rotatedBitmap.getWidth();
                    int inHeight = rotatedBitmap.getHeight();
                    if (inWidth > inHeight) {
                        outWidth = maxSize;
                        outHeight = (inHeight * maxSize) / inWidth;
                    } else {
                        outHeight = maxSize;
                        outWidth = (inWidth * maxSize) / inHeight;
                    }

                    bitmapNew = Bitmap.createScaledBitmap(rotatedBitmap, outWidth, outHeight, true);

                    Thread.sleep(100);

                    fileAttachment = getOutputMediaFile(MEDIA_TYPE_IMAGE);
                    FileOutputStream fo = new FileOutputStream(fileAttachment);
                    bitmapNew.compress(Bitmap.CompressFormat.JPEG, 100, fo);
                    fo.flush();
                    fo.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
//                edtFileName.setText(fileToUpload.getName());
//                dismissProgressDialog();

//                Uri uri = Uri.fromFile(fileToUpload);
//                Picasso.get().load(fileToUpload).into(imgPreview);

//                txtAttachmentFile.setText(fileAttachment.getName());
//                Log.e("File", "path: " + fileAttachment.getPath());

                if (!fileAttachment.exists()) {

                    Toast.makeText(getActivity(), "File Not Found!", Toast.LENGTH_SHORT).show();
                } else {
                    arrUploadFiles.add(fileAttachment);
                    if (uploadPhotosAdapter != null) {
                        uploadPhotosAdapter.notifyDataSetChanged();
                    }
//                    setUploadPhotosAdapter();
                }
            }
        }.execute();
    }

    @SuppressLint("StaticFieldLeak")
    private void onSelectFromGalleryResult(final Intent data) {

        new AsyncTask<Void, Void, Void>() {

            Bitmap bitmap;
            Bitmap rotatedBitmap;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
//                showProgressDialog();
            }

            @Override
            protected Void doInBackground(Void... voids) {

                if (data != null) {
                    try {

                        bitmap = getBitmap(getActivity().getContentResolver(), data.getData());

                        Uri selectedPicture = data.getData();

                        // Get and resize profile image
                        String[] filePathColumn = {MediaStore.MediaColumns.DATA};
                        Cursor cursor = getActivity().getContentResolver().query(selectedPicture, filePathColumn, null, null, null);
                        cursor.moveToFirst();

                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        String picturePath = cursor.getString(columnIndex);
                        cursor.close();

                        ExifInterface ei = new ExifInterface(picturePath);
                        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                                ExifInterface.ORIENTATION_UNDEFINED);

                        switch (orientation) {
                            case ExifInterface.ORIENTATION_ROTATE_90:
                                rotatedBitmap = AppUtils.rotateImage(bitmap, 90);
                                break;

                            case ExifInterface.ORIENTATION_ROTATE_180:
                                rotatedBitmap = AppUtils.rotateImage(bitmap, 180);
                                break;

                            case ExifInterface.ORIENTATION_ROTATE_270:
                                rotatedBitmap = AppUtils.rotateImage(bitmap, 270);
                                break;

                            case ExifInterface.ORIENTATION_NORMAL:
                            default:
                                rotatedBitmap = bitmap;
                        }

                        final int maxSize = 1000;
                        int outWidth;
                        int outHeight;
                        int inWidth = rotatedBitmap.getWidth();
                        int inHeight = rotatedBitmap.getHeight();
                        if (inWidth > inHeight) {
                            outWidth = maxSize;
                            outHeight = (inHeight * maxSize) / inWidth;
                        } else {
                            outHeight = maxSize;
                            outWidth = (inWidth * maxSize) / inHeight;
                        }

                        bitmap = Bitmap.createScaledBitmap(getBitmap(getActivity().getContentResolver(), data.getData()), outWidth, outHeight, true);

                        Thread.sleep(100);

                        fileAttachment = getOutputMediaFile(MEDIA_TYPE_IMAGE);
                        FileOutputStream fo = new FileOutputStream(fileAttachment);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fo);
                        fo.flush();
                        fo.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
//                dismissProgressDialog();

//                Uri uri = Uri.fromFile(fileToUpload);

//                Picasso.get().load(fileToUpload).into(imgPreview);

//                txtAttachmentFile.setText(fileAttachment.getName());
                Log.e("File", "path: " + fileAttachment.getPath());

                if (!fileAttachment.exists()) {
                    Toast.makeText(getActivity(), "File Not Found!", Toast.LENGTH_SHORT).show();
                } else {
                    arrUploadFiles.add(fileAttachment);
                    if (uploadPhotosAdapter != null) {
                        uploadPhotosAdapter.notifyDataSetChanged();
                    }
//                    setUploadPhotosAdapter();
                }
            }

        }.execute();
    }

    /*@Override
    public void requestDrag(RecyclerView.ViewHolder viewHolder) {
        touchHelper.startDrag(viewHolder);
    }*/

    public static String getPathFile(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

}
