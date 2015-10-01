package co.example.junjen.mobileinstagram;

import android.net.Uri;
import android.os.Bundle;
import android.print.PrintDocumentAdapter;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import co.example.junjen.mobileinstagram.customLayouts.ExpandableScrollView;
import co.example.junjen.mobileinstagram.customLayouts.ScrollViewListener;
import co.example.junjen.mobileinstagram.elements.Parameters;
import co.example.junjen.mobileinstagram.elements.Post;
import co.example.junjen.mobileinstagram.elements.TimeSince;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UserFeedFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UserFeedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserFeedFragment extends Fragment implements ScrollViewListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    // keep track of timeSince last post generated to generate new set of posts
    private TimeSince timeSinceLastPost = new TimeSince(Parameters.default_timeSince);

    // flag to check if posts are being loaded before loading new ones
    private boolean loadPosts;

    // counter for new posts to be placed in the right order when loaded
    private int postCount = 0;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserFeedFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserFeedFragment newInstance(String param1, String param2) {
        UserFeedFragment fragment = new UserFeedFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public UserFeedFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ExpandableScrollView userFeedFragment = (ExpandableScrollView)
                inflater.inflate(R.layout.fragment_user_feed, container, false);

        userFeedFragment.setScrollViewListener(this);

        getUserFeedPosts(inflater, userFeedFragment);

        return userFeedFragment;
    }

    // loads another chunk of posts when at the bottom of a user feed's scroll view
    @Override
    public void onScrollEnded(ExpandableScrollView scrollView, int x, int y, int oldx, int oldy) {
        // load new posts if no posts are currently being loaded
        if(loadPosts){
            LayoutInflater inflater = (LayoutInflater)
                    this.getContext().getSystemService(this.getContext().LAYOUT_INFLATER_SERVICE);

            getUserFeedPosts(inflater, scrollView);
        }
    }

    // loads a chunk of posts on the user feed view
    private View getUserFeedPosts(LayoutInflater inflater, View userFeedFragment){

        loadPosts = false;
        ViewGroup userFeedView = (ViewGroup) userFeedFragment.findViewById(R.id.userfeed_view);
        ArrayList<Post> posts = new ArrayList<>();

        int maxPosts = Parameters.postsToLoad;
        int i = 0;
        while (i < maxPosts){
            i++;

            // TODO: use getPost(..) method from Data Object class

            posts.add(new Post(inflater, userFeedView));

        }

        // TODO: arrange ArrayList of posts by timeSince

        // TODO: change this.timeSinceLastPost to actual timeSince of last post
        this.timeSinceLastPost = new TimeSince(Parameters.default_timeSince);

        // add posts to view
        int size = posts.size();
        for (i = 0; i < size; i++){
            userFeedView.addView(posts.get(i).getPostView(), i + postCount);
        }
        postCount += Parameters.postsToLoad;
        loadPosts = true;
        return userFeedView;
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }



    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
