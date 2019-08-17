package id.ac.umn.made_basiliusbias_submission3.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import id.ac.umn.made_basiliusbias_submission3.R;
import id.ac.umn.made_basiliusbias_submission3.Utility;
import id.ac.umn.made_basiliusbias_submission3.adapters.MovieGridAdapter;
import id.ac.umn.made_basiliusbias_submission3.adapters.TvListAdapter;
import id.ac.umn.made_basiliusbias_submission3.models.DiscoverMovieViewModel;
import id.ac.umn.made_basiliusbias_submission3.models.DiscoverTvViewModel;

import java.util.Objects;

public class MainFragment extends Fragment {

    // TODO :: Menyimpan Posisi Scroll RecyclerView Dari Fragment
    //         Ga Bisa Pake Parcelable Juga Ke `savedInstanceState`
    //         Help Donk Mas, Mba .. Biar Tidak mental Ke Atas Aja ..

    // Parameter Key
    private static final String FRAGMENT_NAME = "FRAGMENT_NAME";
    private static final String RECYCLER_TYPE = "RECYCLER_TYPE";

    // Parameter Data
    private String fragmentName;
    private String recyclerType;

    // Adapter
    private MovieGridAdapter movieGridAdapter;
    private TvListAdapter tvListAdapter;

    public MainFragment() {
        // Required empty public constructor
    }

    public static MainFragment newInstance(String fragmentName, String recyclerType) {

        MainFragment fragment = new MainFragment();

        Bundle args = new Bundle();
        args.putString(FRAGMENT_NAME, fragmentName);
        args.putString(RECYCLER_TYPE, recyclerType);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get Parameters
        if (getArguments() != null) {
            fragmentName = getArguments().getString(FRAGMENT_NAME);
            recyclerType = getArguments().getString(RECYCLER_TYPE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_recycler, container, false);

        // Determine Size Of Column To View As Grid
        int mNoOfColumns = Utility.calcNumOfCols(v.getContext(), 175);

        // Setting Up RecyclerView
        RecyclerView recyclerView = v.findViewById(R.id.recycler_fragment);
        recyclerView.setVisibility(View.GONE);

        // RecyclerType
        if(recyclerType != null && !recyclerType.equals("") && recyclerType.equals("GridLayout")) {
            recyclerView.setLayoutManager(new GridLayoutManager(v.getContext(), mNoOfColumns));
        }
        else if(recyclerType != null && !recyclerType.equals("") && recyclerType.equals("LinearLayout")) {
            recyclerView.setLayoutManager(new LinearLayoutManager(v.getContext()));
        }

        // Check Fragment What Will Show
        switch (fragmentName) {

            // Coming Soon
            case "ComingSoon":

                // ReAssign View
                v = inflater.inflate(R.layout.fragment_coming_soon, container, false);

                // Remove Loading Perk
                Objects.requireNonNull(getActivity()).findViewById(R.id.loading_image).setVisibility(View.GONE);
                Objects.requireNonNull(getActivity()).findViewById(R.id.loading_text).setVisibility(View.GONE);
                break;

            // Discover Movie
            case "DiscoverMovie":

                // Setting Up API
                DiscoverMovieViewModel discoverMovieViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(DiscoverMovieViewModel.class);
                discoverMovieViewModel.getDiscoverMovie().observe(this, movies -> {
                    if (movies != null) {

                        // Adding New Live Data To Adapter
                        movieGridAdapter.setMovies(movies);
                    }
                });

                // Setting Up, Load, & Adding Data To Adapter
                movieGridAdapter = new MovieGridAdapter(v.getContext(), R.layout.item_grid);
                discoverMovieViewModel.loadDiscoverMovie(
                    v,
                    Objects.requireNonNull(getActivity()).findViewById(R.id.loading_image),
                    Objects.requireNonNull(getActivity()).findViewById(R.id.loading_text),
                    "popularity.desc",
                    2019
                );
                recyclerView.setAdapter(movieGridAdapter);
                break;

            // Discover TV
            case "DiscoverTV":

                // Setting Up API
                DiscoverTvViewModel discoverTvViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(DiscoverTvViewModel.class);
                discoverTvViewModel.getDiscoverTV().observe(this, tvs -> {
                    if (tvs != null) {

                        // Adding New Live Data To Adapter
                        tvListAdapter.setTvs(tvs);
                    }
                });

                // Setting Up, Load, & Adding Data To Adapter
                tvListAdapter = new TvListAdapter(v.getContext(), R.layout.item_list);
                discoverTvViewModel.loadDiscoverTV(
                    v,
                    Objects.requireNonNull(getActivity()).findViewById(R.id.loading_image),
                    Objects.requireNonNull(getActivity()).findViewById(R.id.loading_text),
                    "popularity.desc",
                    2019
                );
                recyclerView.setAdapter(tvListAdapter);
                break;

            // TODO :: Tempat Buat Fragment `MainActivity` Lainnya Di Sini
            //         `MoviesPopular`, `MoviesTopRated`, `MoviesUpcoming`, `MoviesNowPlaying`
            //         `TvShowsPopular`, `TvShowsTopRated`, `TvShowsOnTv`, `TvShowsAiringToday`

        }

        return v;
    }
}
