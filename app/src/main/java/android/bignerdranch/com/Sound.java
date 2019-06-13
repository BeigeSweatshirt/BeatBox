package android.bignerdranch.com;

public class Sound {
    private String mAssetPath;
    private String mName;
    private Integer mSoundsId;

    public Sound(String assetPath) {
        mAssetPath = assetPath;
        String[] components = assetPath.split("/");
        String filename = components[components.length - 1];
        mName = filename.replace(".wav", "");
    }

    public String getAssetPath() {
        return mAssetPath;
    }

    public String getName() {
        return mName;
    }

    public Integer getSoundsId() {
        return mSoundsId;
    }

    public void setSoundsId(Integer soundsId) {
        mSoundsId = soundsId;
    }
}
