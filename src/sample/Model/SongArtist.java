package sample.Model;

public class SongArtist {
    private String artistName;
    private String albumName;
    private int track;

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public int getTrack() {
        return track;
    }

    public void setTrack(int track) {
        this.track = track;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("Artist Name: " );
        builder.append(this.artistName);
        builder.append("\n");
        builder.append("Album Name: ");
        builder.append(this.albumName);
        builder.append("\n");
        builder.append("Track No: ");
        builder.append(this.track);
        return builder.toString();
    }
}
