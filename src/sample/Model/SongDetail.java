package sample.Model;

public class SongDetail {
    private String songName;
    private int trackNumber;
    private String albumName;
    private String artistName;

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public int getTrackNumber() {
        return trackNumber;
    }

    public void setTrackNumber(int trackNumber) {
        this.trackNumber = trackNumber;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;

    }

    @Override
    public String toString() {
        // song track album artist
        StringBuilder stringBuilder = new StringBuilder(String.format("%-75s",this.songName));
        stringBuilder.append(String.format("%-5d", this.trackNumber));
        stringBuilder.append(String.format("%-50s", this.albumName));
        stringBuilder.append(String.format("%-50s", this.artistName));
        return stringBuilder.toString();
    }
}
