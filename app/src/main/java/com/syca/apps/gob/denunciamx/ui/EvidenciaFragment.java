package com.syca.apps.gob.denunciamx.ui;


import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.syca.apps.gob.denunciamx.R;
import com.syca.apps.gob.denunciamx.ui.utils.MediaStoreSyca;
import com.syca.apps.gob.denunciamx.ui.utils.PictureUtils;
import com.syca.apps.gob.denunciamx.ui.utils.UIUtils;
import com.syca.apps.gob.denunciamx.ui.utils.UtilIntents;

import java.io.File;
import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EvidenciaFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class EvidenciaFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;
    public static final int MEDIA_TYPE_AUDIO = 3;

    static final int CAMERA_PIC_REQUEST = 1;
    static final int CAMERA_VIDEO_REQUEST = 2;
    static final int MIC_SOUND_REQUEST = 3;



    @InjectView(R.id.video_launch_btn) ImageButton mButtonCreateVideo;
    @InjectView(R.id.video_container_view) LinearLayout  mVideosView;
    
    @InjectView(R.id.photo_launch_btn) ImageButton mButtonCreatePhoto;
    @InjectView(R.id.photo_container_view)LinearLayout mPhotosView;
    
    @InjectView(R.id.audio_launch_btn) ImageButton mButtonCreateAudio;
    @InjectView(R.id.audio_container_view) LinearLayout  mAudiosView;

    File mPhotoFile;
    File mVideoFile;
    File mAudioFile;

    EvidenciaActionMode evidenciaActionMode ;

    Activity mContext;

    TestAsync asyncUploader ;


    HashMap<Integer,Uri> photosKeyList;
    HashMap<Integer,Uri> videoKeyList;
    HashMap<Integer,Uri> audioKeyList;

    private static String KEY_VIDEO_FILE="KEY_VIDEO_FILE";
    private static String KEY_PHOTO_FILE="KEY_VIDEO_FILE";
    private static String KEY_AUDIO_FILE="KEY_VIDEO_FILE";

    private static String KEY_VIDEO_LIST="KEY_VIDEO_LIST";
    private static String KEY_PHOTO_LIST="KEY_VIDEO_LIST";
    private static String KEY_AUDIO_LIST="KEY_VIDEO_LIST";

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EvidenciaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EvidenciaFragment newInstance(String param1, String param2) {
        EvidenciaFragment fragment = new EvidenciaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public EvidenciaFragment() {
        // Required empty public constructor
        evidenciaActionMode = new EvidenciaActionMode();
        photosKeyList = new HashMap<Integer, Uri>();
        videoKeyList= new HashMap<Integer, Uri>();
        audioKeyList= new HashMap<Integer, Uri>();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Photo
        if(requestCode==CAMERA_PIC_REQUEST )
        {
            if(resultCode== Activity.RESULT_OK)
            {
                final ImageView template = (ImageView) getActivity().getLayoutInflater().inflate(R.layout.image_template,mPhotosView,false);
                mPhotosView.addView(template);
                asyncUploader = new TestAsync();
                asyncUploader.execute(mPhotoFile);
                PictureUtils.setImageScaled(mContext, template, mPhotoFile.getAbsolutePath());
                template.setId(UIUtils.generateViewId());
                photosKeyList.put(template.getId(), Uri.fromFile(mPhotoFile));
                template.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        v.setSelected(true);
                        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
                            registerForContextMenu(template);
                        } else {
                            getActivity().startActionMode(evidenciaActionMode);
                        }
                        return true;
                    }
                });
                template.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Uri uri = photosKeyList.get(v.getId());
                        File f = new File(uri.getPath());
                        startActivity(UtilIntents.makeIntentShowEvidenciaFile(f));
                    }
                });

            }
            else if(resultCode==Activity.RESULT_CANCELED)
            {

            }
            else
            {

            }

        }
        //Video
        else if(requestCode==CAMERA_VIDEO_REQUEST)
        {
            if(resultCode== Activity.RESULT_OK)
            {
                final FrameLayout template = (FrameLayout) mContext.getLayoutInflater().inflate(R.layout.video_template,mVideosView,false);


                Bitmap videoThumbnail =
                        ThumbnailUtils.createVideoThumbnail(mVideoFile.getPath(), MediaStore.Images.Thumbnails.MINI_KIND);

                if(videoThumbnail==null)
                    videoThumbnail=PictureUtils.createVideoThumbnail(mContext, Uri.fromFile(mVideoFile));

                ((ImageView)template.findViewById(R.id.image_thumbnail)).setImageBitmap(videoThumbnail);

                mVideosView.addView(template);
                template.setId(UIUtils.generateViewId());
                videoKeyList.put(template.getId(), Uri.fromFile(mVideoFile));
                template.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        v.setSelected(true);
                        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
                            registerForContextMenu(template);
                        } else {

                            getActivity().startActionMode(evidenciaActionMode);
                        }
                        return true;
                    }
                });
                template.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Uri uri = videoKeyList.get(v.getId());
                        File f = new File(uri.getPath());
                        startActivity(UtilIntents.makeIntentShowEvidenciaFile(f));
                    }
                });


            }
            else if(resultCode==Activity.RESULT_CANCELED)
            {

            }
            else
            {

            }

        }
        //Audio
        else if(requestCode==MIC_SOUND_REQUEST)
        {
            if(resultCode== Activity.RESULT_OK)
            {

                final FrameLayout template = (FrameLayout) mContext.getLayoutInflater().inflate(R.layout.video_template,mVideosView,false);

                ((ImageView)template.findViewById(R.id.image_thumbnail)).
                        setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_audio_wave));

                mAudiosView.addView(template);
                template.setId(UIUtils.generateViewId());
                audioKeyList.put(template.getId(), Uri.fromFile(mAudioFile));
                template.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        v.setSelected(true);
                        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
                            registerForContextMenu(template);
                        } else {

                            getActivity().startActionMode(evidenciaActionMode);
                        }
                        return true;
                    }
                });
                template.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Uri uri = audioKeyList.get(v.getId());
                        File f = new File(uri.getPath());
                        startActivity(UtilIntents.makeIntentShowEvidenciaFile(f));
                    }
                });

            }
            else if(resultCode==Activity.RESULT_CANCELED)
            {

            }
            else
            {

            }

        }

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        setRetainInstance(true);
        if(null!=savedInstanceState)
        {
            handleSavedInstance(savedInstanceState);
        }
    }

    private void handleSavedInstance(Bundle savedInstanceState) {

        String videoPath = savedInstanceState.getString(KEY_VIDEO_FILE);

        String photoPath = savedInstanceState.getString(KEY_PHOTO_FILE);

        String audioPath = savedInstanceState.getString(KEY_AUDIO_FILE);

        if(null!=videoPath) mVideoFile= new File(videoPath);

        if(null!=photoPath) mPhotoFile= new File(photoPath);

        if(null!=audioPath) mAudioFile= new File(audioPath);

        Object list = savedInstanceState.getSerializable(KEY_AUDIO_LIST);
        if(null!=list) audioKeyList= (HashMap<Integer, Uri>) list;

        list = savedInstanceState.getSerializable(KEY_PHOTO_LIST);
        if(null!=list) photosKeyList= (HashMap<Integer, Uri>) list;

        list = savedInstanceState.getSerializable(KEY_VIDEO_LIST);
        if(null!=list) videoKeyList= (HashMap<Integer, Uri>) list;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_evidencia, container, false);
        ButterKnife.inject(this,view);
        mButtonCreateVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchVideoCameraIntent();
            }
        });

        mButtonCreatePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchPhotoIntent();
            }
        });

        mButtonCreateAudio.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                launchAudioCaptureIntent();
            }

        });


        return view;
    }



    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        saveInstanceState(outState);


    }

    private void saveInstanceState(Bundle outState) {

        if(null!=mVideoFile) outState.putString(KEY_VIDEO_FILE,mVideoFile.getAbsolutePath());

        if(null!=mPhotoFile) outState.putString(KEY_PHOTO_FILE,mPhotoFile.getAbsolutePath());

        if(null!=mAudioFile) outState.putString(KEY_AUDIO_FILE,mAudioFile.getAbsolutePath());

        if(!videoKeyList.isEmpty()) outState.putSerializable(KEY_VIDEO_LIST, videoKeyList);

        if(!photosKeyList.isEmpty()) outState.putSerializable(KEY_PHOTO_LIST,photosKeyList);

        if(!audioKeyList.isEmpty()) outState.putSerializable(KEY_AUDIO_LIST,audioKeyList);

    }


    private void launchVideoCameraIntent()
    {
        // getOutputMediaFile to create a new filename for this specific video;

        mVideoFile = MediaStoreSyca.getOutputMediaFile(EvidenciaFragment.MEDIA_TYPE_VIDEO);

        Intent videoIntent = UtilIntents.makeIntentCaptionVideo(mVideoFile);

        startActivityForResult(videoIntent, CAMERA_VIDEO_REQUEST);
    }

    private void launchPhotoIntent()
    {
        mPhotoFile = MediaStoreSyca.getOutputMediaFile(EvidenciaFragment.MEDIA_TYPE_IMAGE);

        Intent photoIntent = UtilIntents.makeIntentPhoto( mPhotoFile);

        startActivityForResult(photoIntent,CAMERA_PIC_REQUEST);
    }

    private void launchAudioCaptureIntent()
    {
        mAudioFile = MediaStoreSyca.getOutputMediaFile(EvidenciaFragment.MEDIA_TYPE_AUDIO);

        Intent audioIntent = UtilIntents.makeIntentCaptureAudio(mContext,mAudioFile);


        startActivityForResult(audioIntent,MIC_SOUND_REQUEST);

    }

    @TargetApi(11)
    private class EvidenciaActionMode implements ActionMode.Callback {

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.context_media_evidencia, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.menu_item_delete_evidencia:
                    removeSelectedItems();
                    mode.finish();
                    return true;
                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {

        }
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getActivity().getMenuInflater().inflate(R.menu.context_media_evidencia, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();

        int position = info.position;

        switch (item.getItemId())
        {
            case R.id.menu_item_delete_evidencia:
                //TODO:Get all views selected and remove from their layouts
                removeSelectedItems();
                return true;
        }
        return super.onContextItemSelected(item);
    }


    private void removeSelectedItems()
    {
        for (int i = 0;i<mVideosView.getChildCount();i++)
            if(mVideosView.getChildAt(i).isSelected())
                removeItem(mVideosView,mVideosView.getChildAt(i),videoKeyList);

        for (int i = 0;i<mPhotosView.getChildCount();i++)
            if(mPhotosView.getChildAt(i).isSelected())
                removeItem(mPhotosView,mPhotosView.getChildAt(i),photosKeyList);


        for (int i = 0;i<mAudiosView.getChildCount();i++)
            if(mAudiosView.getChildAt(i).isSelected())
                removeItem(mAudiosView,mAudiosView.getChildAt(i),audioKeyList);


    }

    private void removeItem(final LinearLayout view, final View viewToRemove,HashMap list)
    {
        final Integer idView = viewToRemove.getId();
        view.post(new Runnable() {
            @Override
            public void run() {
                view.removeView(viewToRemove);
            }
        });
        view.refreshDrawableState();

        list.remove(idView);
    }

}
