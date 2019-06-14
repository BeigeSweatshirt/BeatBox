package android.bignerdranch.com;

import android.bignerdranch.com.databinding.FragmentBeatboxBinding;
import android.bignerdranch.com.databinding.ListItemSoundBinding;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import java.util.List;

public class BeatBoxFragment extends Fragment {
    private BeatBox mBeatBox;
    private FragmentBeatboxBinding mBinding;

    public static BeatBoxFragment newInstance() {
        return new BeatBoxFragment();
    }

    @Override
    public void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        setRetainInstance(true);
        mBeatBox = new BeatBox(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedState) {
        mBinding = DataBindingUtil
                .inflate(inflater, R.layout.fragment_beatbox, container, false);

        mBinding.recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mBinding.recyclerView.setAdapter(new SoundAdapter(mBeatBox.getSounds()));

        mBinding.seekBar.setMax(200);
        if (savedState == null) mBinding.seekBar.setProgress(100);
        mBinding.seekBarText.setText(getString(R.string.playback_speed, mBeatBox.getPlaybackSpeed()));
        mBinding.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int speed, boolean fromUser) {
                if (speed < 100) speed = 50 + (speed/2);

                mBeatBox.setPlaySpeed(speed * 0.01f);
                mBinding.seekBarText.setText(getString(R.string.playback_speed,
                        speed));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        return mBinding.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mBeatBox.release();
    }

    private void updateSeekbar(FragmentBeatboxBinding binding) {
    }

    private class SoundAdapter extends RecyclerView.Adapter<SoundHolder> {
        private List<Sound> mSounds;

        public SoundAdapter(List<Sound> sounds) {
            mSounds = sounds;
        }

        @Override
        public SoundHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            ListItemSoundBinding binding = DataBindingUtil
                    .inflate(inflater, R.layout.list_item_sound, parent, false);
            return new SoundHolder(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull SoundHolder holder, int position) {
            Sound sound = mSounds.get(position);
            holder.bind(sound);
        }

        @Override
        public int getItemCount() {
            return mSounds.size();
        }
    }

    private class SoundHolder extends RecyclerView.ViewHolder {
        private ListItemSoundBinding mBinding;

        private SoundHolder(ListItemSoundBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
            mBinding.setViewModel(new SoundViewModel(mBeatBox));
        }

        public void bind(Sound sound) {
            mBinding.getViewModel().setSound(sound);
            mBinding.executePendingBindings();
        }
    }
}
