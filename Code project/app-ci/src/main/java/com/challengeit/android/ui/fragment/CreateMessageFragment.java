package com.challengeit.android.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.VideoView;

import com.challengeit.android.ui.activity.CreateGameActivity;
import com.challengeit.android.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/*
 * Allow a user to create a message in order to create or respond to a challenge by
 * using text messages, attached files and a media (photo or video).
 * Not currently used in the app
 */
public class CreateMessageFragment extends Fragment {
	private Preview mPreview;
    private Camera mCamera;
    private MediaRecorder mMediaRecorder;
    private boolean isRecording = false;

    static String mCurrentPhotoPath;
	public static final int MEDIA_TYPE_IMAGE = 1;
	public static final int MEDIA_TYPE_VIDEO = 2;
    public static int RESULT_LOAD_IMAGE = 1;
    public static String attachment_path ="";
    public static List<String> attachment_paths_list;
    private boolean delete;
    private boolean zoomedIn = false;
    private int sizeOfAttachments = 300;

    class Preview extends SurfaceView implements SurfaceHolder.Callback {
		SurfaceHolder mHolder;

		Preview(Context context) {
			super(context);
			mHolder = getHolder();
			mHolder.addCallback(this);
			mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
           // mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

		}

		public void surfaceCreated(SurfaceHolder holder) {
			try {

				mCamera.setPreviewDisplay(holder);
                Camera.Parameters parameters = mCamera.getParameters();

                //mCamera.setDisplayOrientation(getResources().getConfiguration().orientation);
                if(getResources().getConfiguration().orientation==1) {
                    mCamera.setDisplayOrientation(90);

                }
                else {
                    mCamera.setDisplayOrientation(0);
                }
                mCamera.setParameters(parameters);
                List<Camera.Size> sizes = parameters.getSupportedPictureSizes();

               /* RelativeLayout camera_layout;
                Camera.Parameters parameters = mCamera.getParameters();
                camera_layout = ((RelativeLayout) findViewById(R.id.camera_layout));
                parameters.setPreviewSize(camera_layout.getWidth(), camera_layout.getHeight());*/
			} catch (IOException exception) {
				mCamera.release();
				mCamera = null;
			}
		}

		public void surfaceDestroyed(SurfaceHolder holder) {
			mCamera.stopPreview();
            Log.i("Preview", "Preview has stopped");
			mCamera.release();
			mCamera = null;
		}

		public void surfaceChanged(SurfaceHolder holder, int format, int w,
				int h) {

            if(mCamera!=null){
            mCamera.stopPreview();
			Camera.Parameters parameters = mCamera.getParameters();
            parameters.setRotation(90);
            List<Camera.Size> sizes = parameters.getSupportedPreviewSizes();
            for(int i=0; i<sizes.size(); i++) {
                Log.i("supported size", i+" w:" + sizes.get(i).width+" h:"+sizes.get(i).height);
            }
            Camera.Size bestSize= getOptimalPreviewSize(sizes, w, h);
                Log.i("Size",w+" "+h);
                Log.i("Optimal size", "Width: "+bestSize.width+" Height: "+bestSize.height);

            parameters.setPreviewSize(bestSize.width, bestSize.height);
            mCamera.setParameters(parameters);
            }
            //parameters.setPreviewSize(sizes.get(0).width, sizes.get(0).height);
			//mCamera.setParameters(parameters);

            mCamera.startPreview();
		}

        public void setPreviewSizeCamera(int width, int height){
            mCamera.getParameters().setPreviewSize(width, height);
        }

        private Camera.Size getOptimalPreviewSize(List<Camera.Size> sizes, int w, int h) {
            final double ASPECT_TOLERANCE = 0.1;
            double targetRatio = (double) h / w;

            if (sizes == null) return null;

            Camera.Size optimalSize = null;
            double minDiff = Double.MAX_VALUE;

            int targetHeight = h;

            for (Camera.Size size : sizes) {
                double ratio = (double) size.width / size.height;
                if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE) continue;
                if (Math.abs(size.height - targetHeight) < minDiff) {
                    optimalSize = size;
                    minDiff = Math.abs(size.height - targetHeight);
                }
            }

            if (optimalSize == null) {
                minDiff = Double.MAX_VALUE;
                for (Camera.Size size : sizes) {
                    if (Math.abs(size.height - targetHeight) < minDiff) {
                        optimalSize = size;
                        minDiff = Math.abs(size.height - targetHeight);
                    }
                }
            }
            return optimalSize;
        }
	}


	public void takePicture() {

		Thread t = new Thread(new Runnable() {
			Camera.PictureCallback jpegCallback;


			public void run() {

				jpegCallback = new Camera.PictureCallback() {

					public void onPictureTaken(byte[] data, Camera camera) {

						File pictureFile = getOutputMediaFile(MEDIA_TYPE_IMAGE);

						if (pictureFile == null) {
							Log.i("TAG",
									"Error creating media file, check storage permissions ");
							return;
						}

						try {

							FileOutputStream fos = new FileOutputStream(
									pictureFile);

							fos.write(data);
							fos.close();
						} catch (FileNotFoundException e) {
							Log.i("TAG", "File not found: " + e.getMessage());
						} catch (IOException e) {
							Log.i("TAG",
									"Error accessing file: " + e.getMessage());
						}

						Log.i("save", mCurrentPhotoPath);
                        attachment_path=mCurrentPhotoPath;
                        galleryAddPic();
                        addAttachmentBelowText();
					}

				};
				Log.i("Le thread", "commence");
				mCamera.takePicture(null, null, jpegCallback);
				Log.i("thread", "photo prise");
				// Insert some method call here.
			}
		});

		Thread t2 = new Thread(new Runnable() {
			public void run() {
				
//				mCamera.stopPreview();
//                mCamera.startPreview();
				try {
					
					Log.i("Le thread", "attend" );
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Log.i("La camera", "reprend");
                mCamera.stopPreview();
                Camera.Parameters parameters = mCamera.getParameters();
                parameters.setRotation(90);
                mCamera.setParameters(parameters);
				mCamera.startPreview();
			}
		});
		
		//Log.i("qui est le thread?",Thread.currentThread().toString().toString());
		
		t.start();
		t2.start();		

	}

    public void takeVideo(){

        Toast.makeText(this.getActivity(),"Recording Video", Toast.LENGTH_SHORT).show();

        if (isRecording) {
            // stop recording and release camera
            mMediaRecorder.stop();  // stop the recording
            releaseMediaRecorder(); // release the MediaRecorder object
            mCamera.lock();         // take camera access back from MediaRecorder
            attachment_path=mCurrentPhotoPath;
            addAttachmentBelowText();
            ((Chronometer)this.getActivity().findViewById(R.id.chronometer)).setVisibility(View.INVISIBLE);
            ((Chronometer)this.getActivity().findViewById(R.id.chronometer)).stop();
            galleryAddPic();

            isRecording = false;
        } else {
            // initialize video camera
            if (prepareVideoRecorder()) {
                // Camera is available and unlocked, MediaRecorder is prepared,
                // now you can start recording
                ((Chronometer)this.getActivity().findViewById(R.id.chronometer)).setVisibility(View.VISIBLE);
                ((Chronometer)this.getActivity().findViewById(R.id.chronometer)).setBase(SystemClock.elapsedRealtime());
                ((Chronometer)this.getActivity().findViewById(R.id.chronometer)).start();
                mMediaRecorder.start();

                isRecording = true;
            } else {
                // prepare didn't work, release the camera
                releaseMediaRecorder();
                // inform user
            }
        }

}

    private boolean prepareVideoRecorder(){

        //mCamera = this.getActivity().getCameraInstance();
        mMediaRecorder = new MediaRecorder();

        // Step 1: Unlock and set camera to MediaRecorder
        mCamera.unlock();
        mMediaRecorder.setCamera(mCamera);

        // Step 2: Set sources
        mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
        mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
        mMediaRecorder.setOrientationHint(90);


        // Step 3: Set a CamcorderProfile (requires API Level 8 or higher)
        mMediaRecorder.setProfile(CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH));

        // Step 4: Set output file
        mMediaRecorder.setOutputFile(getOutputMediaFile(MEDIA_TYPE_VIDEO).toString());

        // Step 5: Set the preview output
        mMediaRecorder.setPreviewDisplay(mPreview.getHolder().getSurface());

        // Step 6: Prepare configured MediaRecorder
        try {
            mMediaRecorder.prepare();
            mMediaRecorder.setOutputFile(getOutputMediaFile(MEDIA_TYPE_VIDEO).toString());
        } catch (IllegalStateException e) {
            releaseMediaRecorder();
            return false;
        } catch (IOException e) {
            releaseMediaRecorder();
            return false;
        }
        return true;
    }

    private void releaseMediaRecorder(){
        if (mMediaRecorder != null) {
            mMediaRecorder.reset();   // clear recorder configuration
            mMediaRecorder.release(); // release the recorder object
            mMediaRecorder = null;
            mCamera.lock();           // lock camera for later use
        }
    }

    /** Create a file Uri for saving an image or video */
	public static Uri getOutputMediaFileUri(int type) {
		return Uri.fromFile(getOutputMediaFile(type));
	}

	/** Create a File for saving an image or video */
	public static File getOutputMediaFile(int type) {
		// To be safe, you should check that the SDCard is mounted
		// using Environment.getExternalStorageState() before doing this.

		File mediaStorageDir = new File(
				Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
				"DareIt");
		// This location works best if you want the created images to be shared
		// between applications and persist after your app has been uninstalled.

		// Create the storage directory if it does not exist
		if (!mediaStorageDir.exists()) {
			Log.i("gallerie", "n existe pas");
			if (!mediaStorageDir.mkdirs()) {
				Log.d("MyCameraApp", "failed to create directory");
				return null;
			}
		}

		// Create a media file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
				.format(new Date());
		File mediaFile;
		if (type == MEDIA_TYPE_IMAGE) {
			mediaFile = new File(mediaStorageDir.getPath() + File.separator
					+ "IMG_" + timeStamp + ".jpg");
			mCurrentPhotoPath = /*"file:" +*/ mediaFile.getAbsolutePath();
		} else if (type == MEDIA_TYPE_VIDEO) {
			mediaFile = new File(mediaStorageDir.getPath() + File.separator
					+ "VID_" + timeStamp + ".mp4");
            mCurrentPhotoPath = /*"file:" +*/ mediaFile.getAbsolutePath();
            Log.i("video enregistrée",mCurrentPhotoPath);
		} else {
			return null;
		}

		return mediaFile;
	}

	public void galleryAddPic() {

		Intent mediaScanIntent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");


            File f = new File(mCurrentPhotoPath);

            Uri contentUri = Uri.fromFile(f);
            mediaScanIntent.setData(contentUri);
            this.getActivity().sendBroadcast(mediaScanIntent);
            Log.i("Image", "Sauvée");


	}

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.camera, container, false);
        Log.i("Resume","View Resume");
		mCamera = Camera.open();
		mPreview = new Preview(this.getActivity());
		((FrameLayout) rootView.findViewById(R.id.camera_preview))
				.addView(mPreview);

        Fragment textFragment = new WriteTextFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.text_layout, textFragment).commit();

        RelativeLayout.LayoutParams default_layout_params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        default_layout_params.addRule(RelativeLayout.BELOW, textFragment.getId());
        FrameLayout fl = (FrameLayout) rootView.findViewById(R.id.text_layout);
        fl.getLayoutParams().height=0;
        fl.setLayoutParams(fl.getLayoutParams());
        ((RelativeLayout) rootView.findViewById(R.id.camera_layout)).setLayoutParams(default_layout_params);
        setHasOptionsMenu(true);

        //set listener
        delete=false;
		return rootView;
	}

    @Override
    public void onStart() {
        super.onStart();
        if(mCamera==null) {
            Log.i("Onstart","Onstart is called");
            mCamera = Camera.open();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("Resume","Fragment Resume");
        if(mCamera==null) {
            Log.i("Onstart","Onstart is called");
            mCamera = Camera.open();
        }
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Auto-generated method stub
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.create_game, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_create_game:
                Log.i("new Game", "create a game_player");
                Intent intent = new Intent(this.getActivity(), CreateGameActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void selectNewAttachment(){

        //Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        //Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Files.);
        galleryIntent.setType("*/*");
        startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);

    }

    public void addAttachmentBelowText(){

        int height= ((RelativeLayout) this.getActivity().findViewById(R.id.fragment_layout)).getHeight();
        final FrameLayout text_layout;
        text_layout = (FrameLayout) this.getActivity().findViewById(R.id.text_layout);
        RelativeLayout.LayoutParams text_layout_params = new RelativeLayout.LayoutParams(text_layout.getWidth(), text_layout.getHeight());
        text_layout_params.height = (int) (height * .3);
        ( this.getActivity().findViewById(R.id.text_layout)).setLayoutParams(text_layout_params);

        LinearLayout rl = (LinearLayout)(this.getActivity().findViewById(R.id.attachment_layout));
        final RelativeLayout image_button = new RelativeLayout((this.getActivity()));

        if(attachment_path.contains("jpg")||attachment_path.contains("png")||attachment_path.contains("bin")) {
            Log.i("addattachment","Image");
            ImageView image_view = new ImageView(this.getActivity());
            image_view.setImageBitmap(BitmapFactory.decodeFile(attachment_path));
            rl.addView(image_button);
            image_button.addView(image_view);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(sizeOfAttachments, sizeOfAttachments);
            image_button.setLayoutParams(params);
            RelativeLayout.LayoutParams params_image = new RelativeLayout.LayoutParams(params.width, params.height);
            image_view.setLayoutParams(params_image);
        }
        else if(attachment_path.contains("mp4"))
        {
            Log.i("addattachment","Video"+attachment_path);
            VideoView video_view = new VideoView(this.getActivity());
            video_view.setVideoPath(attachment_path);
            //Create video controllers
            MediaController mediacontroller = new MediaController(this.getActivity());
            mediacontroller.setAnchorView(video_view);

            //Set controllers to video
            video_view.setMediaController(mediacontroller);

            video_view.seekTo(100);
            rl.addView(image_button);
            image_button.addView(video_view);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(sizeOfAttachments, sizeOfAttachments);
            image_button.setLayoutParams(params);
            RelativeLayout.LayoutParams params_image = new RelativeLayout.LayoutParams(params.width, params.height);
            video_view.setLayoutParams(params_image);

        }

        if(attachment_paths_list==null){
            attachment_paths_list = new ArrayList<String>();

            //add long click listener
            //add long click listener
            final Context mContext = this.getActivity();
            LinearLayout ll = (LinearLayout) (this.getActivity().findViewById(R.id.attachment_layout));
            ll.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    if(attachment_paths_list.size()!=0) {
                        delete = true;
                       // RelativeLayout relative_layout = (RelativeLayout) (CreateMessageFragment.this.getActivity().findViewById(R.id.text_fragment));
                        Log.i("Size of attachment list", ""+attachment_paths_list.size());
                        for (int i = 1; i <= attachment_paths_list.size(); i++) {
                            RelativeLayout relative_layout = (RelativeLayout) (CreateMessageFragment.this.getActivity().findViewById(i));
                            final Button delete_button = new Button(CreateMessageFragment.this.getActivity());


                            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(100, 100);
                            delete_button.setText("X");
//                            params.addRule(RelativeLayout.ALIGN_TOP, i);
//                            params.addRule(RelativeLayout.ALIGN_RIGHT, i);
                            delete_button.setLayoutParams(params);
                            relative_layout.addView(delete_button);
                            delete_button.setId(1000+i);

                            View.OnClickListener listener = new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if(view == delete_button){
                                        Toast.makeText(view.getContext(), "Delete Attachment "+view.getId(), Toast.LENGTH_SHORT).show();
                                    int id= view.getId()-1000;

                                    ((LinearLayout) (CreateMessageFragment.this.getActivity().findViewById(R.id.attachment_layout))).removeView(((RelativeLayout) (CreateMessageFragment.this.getActivity().findViewById(id))));
                                    //((RelativeLayout) (CreateMessageFragment.this.getActivity().findViewById(id))).setVisibility(View.GONE);
                                    for(int i=id+1;i<=attachment_paths_list.size();i++ ){

                                        ((Button)(CreateMessageFragment.this.getActivity().findViewById(1000+i))).setId(1000+i-1);
                                       ((RelativeLayout) (CreateMessageFragment.this.getActivity().findViewById(i))).setId(i-1);
                                    }
                                    CreateMessageFragment.attachment_paths_list.remove(id - 1);
                                    Log.i("size",""+attachment_paths_list.size());
                                    }
                                    else{
                                        delete=false;
                                        Log.i("clique","ailleurs");
                                        for(int i=1; i <= attachment_paths_list.size(); i++) {
                                            ((RelativeLayout) (CreateMessageFragment.this.getActivity().findViewById(i))).removeView(((Button) (CreateMessageFragment.this.getActivity().findViewById(1000 + i))));
                                        }
                                    }
                                }
                            };
                            delete_button.setOnClickListener(listener);
                            ((RelativeLayout) CreateMessageFragment.this.getActivity().findViewById(R.id.fragment_layout)).setOnClickListener(listener);

                        }
                    }
                    return true;
                }
            });

        }
        attachment_paths_list.add(attachment_path);
        //image_view.setId(attachment_paths_list.size());
        //rl.setId(2000);
        image_button.setId(attachment_paths_list.size());
        //image_view.setTag(attachment_paths_list.size());

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == this.getActivity().RESULT_OK && null != data) {

            Uri selectedImage = data.getData();
            Log.i("onActivityResult path",selectedImage.getEncodedPath());

            if(selectedImage.getEncodedPath().contains("images")) {
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = this.getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                attachment_path = cursor.getString(columnIndex);
                cursor.close();
                addAttachmentBelowText();
            }
            if(selectedImage.getEncodedPath().contains("video")) {
                String[] filePathColumn = {MediaStore.Video.Media.DATA};
                Cursor cursor = this.getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                attachment_path = cursor.getString(columnIndex);
                cursor.close();
                addAttachmentBelowText();
            }
        }
    }
}
