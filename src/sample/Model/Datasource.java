package sample.Model;


import sample.Album;
import sample.Artist;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Datasource {
    public static final String DB_NAME = "musicbackup1.db";
    public static final String CONNECTION_STRING = "jdbc:sqlite:C:\\Users\\micha\\Documents\\sqlite-tools-win32-x86-3330000\\" + DB_NAME;

    public static final String TABLE_ALBUMS = "albums";
    public static final String COLUMN_ALBUM_ID = "_id";
    public static final String COLUMN_ALBUM_NAME = "name";
    public static final String COLUMN_ALBUM_ARTISTS = "artist";
    public static final int INDEX_ALBUM_ID = 1;
    public static final int INDEX_ALBUM_NAME = 2;
    public static final int INDEX_ALBUM_ARTISTS = 3;

    public static final String TABLE_ARTISTS = "artists";
    public static final String COLUMN_ARTISTS_ID = "_id";
    public static final String COLUMN_ARTIST_NAME = "name";
    public static final int INDEX_ARTIST_ID = 1;
    public static final int INDEX_ARTIST_Name = 2;

    public static final String TABLE_SONGS = "songs";
    public static final String COLUMN_SONG_ID = "_id";
    public static final String COLUMN_SONG_TRACK = "track";
    public static final String COLUMN_SONG_TITLE = "title";
    public static final String COLUMN_SONG_ALBUM = "album";
    public static final int INDEX_SONG_ID = 1;
    public static final int INDEX_SONG_TRACK = 2;
    public static final int INDEX_SONG_TITLE = 3;
    public static final int INDEX_SONG_ALBUM = 4;

    public static final int ORDER_BY_NONE = 1;
    public static final int ORDER_BY_ACS = 2;
    public static final int ORDER_BY_DESC = 3;

    public static final String QUERY_ALBUMS_BY_ARTIST_START =
            "SELECT " + TABLE_ALBUMS + "." + COLUMN_ALBUM_NAME + " FROM " + TABLE_ALBUMS +
                    " INNER JOIN " + TABLE_ARTISTS + " ON " + TABLE_ALBUMS + "." + COLUMN_ALBUM_ARTISTS +
                    " = " + TABLE_ARTISTS + "." + COLUMN_ARTISTS_ID +
                    " WHERE " + TABLE_ARTISTS + "." + COLUMN_ARTIST_NAME + " = \"";
// album name,
    public static final String QUERY_ALBUMS_BY_ARTIST_PREP =
            "SELECT " + TABLE_ALBUMS + "." + COLUMN_ALBUM_NAME + " FROM " + TABLE_ALBUMS +
                    " INNER JOIN " + TABLE_ARTISTS + " ON " + TABLE_ALBUMS + "." + COLUMN_ALBUM_ARTISTS +
                    " = " + TABLE_ARTISTS + "." + COLUMN_ARTISTS_ID +
                    " WHERE " + TABLE_ARTISTS + "." + COLUMN_ARTIST_NAME + " = ?" ;
//                    + "ORDER BY " +
//                    TABLE_ALBUMS + "." + COLUMN_ALBUM_NAME + " COLLATE NOCASE ?";

    public static final String QUERY_ALBUMS_BY_ARTIST_SORT =
            " ORDER BY " + TABLE_ALBUMS + "." + COLUMN_ALBUM_NAME + " COLLATE NOCASE ";

    public static final String QUERY_ARTISTS_START =
            "SELECT * FROM " + TABLE_ARTISTS;

    public static final String QUERY_ARTISTS_BY_ARTISTS_SORT =
            " ORDER BY " + COLUMN_ARTIST_NAME + " COLLATE NOCASE ";


    public static final String QUERY_ARTIST_FOR_SONG_START =
            "SELECT " + TABLE_ARTISTS + "." + COLUMN_ARTIST_NAME + ", " +
                    TABLE_ALBUMS + "." + COLUMN_ALBUM_NAME + ", " +
                    TABLE_SONGS + "." + COLUMN_SONG_TRACK +
                    " FROM " + TABLE_SONGS +
                    " INNER JOIN " + TABLE_ALBUMS + " ON " + TABLE_ALBUMS + "." +
                    COLUMN_ALBUM_ID  + " = " + TABLE_SONGS + "." + COLUMN_SONG_ALBUM +
                    " INNER JOIN " + TABLE_ARTISTS + " ON " + TABLE_ARTISTS + "." +
                    COLUMN_ARTISTS_ID + " = " + TABLE_ALBUMS + "." + COLUMN_ALBUM_ARTISTS +
                    " WHERE " + TABLE_SONGS + "." + COLUMN_SONG_TITLE + " = \"";

    public static final String QUERY_ARTIST_FOR_SONG_END =
            "\" ORDER BY " + TABLE_ALBUMS + "." + COLUMN_ALBUM_NAME + ", " +
                    TABLE_SONGS + "." + COLUMN_SONG_TRACK + " COLLATE NOCASE ";

// way to get every sogn for x artist
    public static final String QUERY_ARTIST_SONGS_START =
        "SELECT " + TABLE_SONGS + "." + COLUMN_SONG_TITLE + " FROM " +  TABLE_SONGS +
                " INNER JOIN " + TABLE_ALBUMS + " ON " +
                TABLE_ALBUMS + "." + COLUMN_ALBUM_ID + " = " + TABLE_SONGS + "." + COLUMN_SONG_ALBUM +
                " INNER JOIN " + TABLE_ARTISTS + " ON " +
                TABLE_ARTISTS + "." + COLUMN_ARTISTS_ID + " = " + TABLE_ALBUMS + "." + COLUMN_ALBUM_ARTISTS+
                " WHERE " + TABLE_ARTISTS + "." + COLUMN_ARTIST_NAME + " = \"";

    public static final String QUERY_ARTIST_SONGS_END =
            "\" ORDER BY " + TABLE_SONGS + "." + COLUMN_SONG_TITLE + " COLLATE NOCASE ";

// song name, track number, album name, artist name
    public static final String QUERY_ALL_BASE_START =
            "SELECT " + TABLE_SONGS + "." + COLUMN_SONG_TITLE +
                    ", " + TABLE_SONGS + "." + COLUMN_SONG_TRACK + ", " +
                    TABLE_ALBUMS + "." + COLUMN_ALBUM_NAME + ", " +
                    TABLE_ARTISTS + "." + COLUMN_ARTIST_NAME +
                    " FROM " + TABLE_SONGS;
    public static final String QUERY_ALL_BASE_MID =
            " INNER JOIN " + TABLE_ALBUMS + " ON " +
                    TABLE_ALBUMS + "." + COLUMN_ALBUM_ID + " = " + TABLE_SONGS + "." + COLUMN_SONG_ALBUM +
                    " INNER JOIN " + TABLE_ARTISTS + " ON " +
                    TABLE_ARTISTS + "." + COLUMN_ARTISTS_ID + " = " + TABLE_ALBUMS + "." + COLUMN_ALBUM_ARTISTS;

    // in order to filter by artist
    public static final String QUERY_ALL_BASE_MID_ARTIST =
            " WHERE " + TABLE_ARTISTS + "." + COLUMN_ARTIST_NAME + " = \"";

    public static final String QUERY_ALL_BASE_END =
            " ORDER BY " + TABLE_SONGS + "." + COLUMN_SONG_TITLE + " COLLATE NOCASE ";


    public static final String TABLE_ARTIST_SONG_VIEW = "artists_list";
//    CREATE VIEW IF NOT EXISTS artists_list as
//    select artists.name as artist, albums.name as album, songs.track as track, songs.title as title from songs
//    inner join albums on songs.album = albums._id
//    inner join artists on albums.artist = artists._id
//    order by artists.name, albums.name, songs.track
    public static final String CREATE_ARTIST_FOR_SONG_VIEW = "CREATE VIEW IF NOT EXISTS " +
            TABLE_ARTIST_SONG_VIEW + " AS SELECT " + TABLE_ARTISTS + "." + COLUMN_ARTIST_NAME + " AS " +
            COLUMN_ALBUM_ARTISTS + ", " + TABLE_SONGS + "." + COLUMN_SONG_TRACK + ", " + TABLE_SONGS +
            "." + COLUMN_SONG_TITLE + " FROM " + TABLE_SONGS +
            " INNER JOIN " + TABLE_ALBUMS + " ON " + TABLE_SONGS + "." + COLUMN_SONG_ALBUM  +
            " = " + TABLE_ALBUMS + "." + COLUMN_ALBUM_ID +
            " INNER JOIN " + TABLE_ALBUMS + " ON " + TABLE_ALBUMS + "." + COLUMN_ALBUM_ARTISTS +
            " = " + TABLE_ARTISTS + "." + COLUMN_ARTISTS_ID +
            " ORDER BY " + TABLE_ARTISTS + "." + COLUMN_ARTIST_NAME + ", " + TABLE_ALBUMS + "." +
            COLUMN_ALBUM_NAME + ", " + TABLE_SONGS + "." + COLUMN_SONG_TRACK;

    public static final String QUERY_VIEW_SONG_INFO = "SELECT " + "artist" + ", " +
            COLUMN_SONG_ALBUM + ", " + COLUMN_SONG_TRACK + " FROM " + TABLE_ARTIST_SONG_VIEW + " WHERE " +
            COLUMN_SONG_TITLE + " = \"";

    public static final String QUERY_VIEW_SONG_INFO_PREP = "SELECT " + "artist" + ", " +
            COLUMN_SONG_ALBUM + ", " + COLUMN_SONG_TRACK + " FROM " + TABLE_ARTIST_SONG_VIEW + " WHERE " +
            COLUMN_SONG_TITLE + " = ?";


    public static final String QUERY_SONG_OR_ARTISTS_ALBUM_PREP =
            "SELECT " + COLUMN_SONG_ALBUM + " FROM " + TABLE_ARTIST_SONG_VIEW + " WHERE " + COLUMN_SONG_TITLE +
                    " = ?" + " OR " + COLUMN_ALBUM_ARTISTS + " = ?";

    public static final String INSERT_ARTIST = "INSERT INTO " + TABLE_ARTISTS + '(' + COLUMN_ARTIST_NAME +
            ") VALUES(?)";

    public static final String INSERT_ALBUMS = "INSERT INTO " + TABLE_ALBUMS + '(' + COLUMN_ALBUM_NAME +
            ", " + COLUMN_ALBUM_ARTISTS + ") Values(?, ?)";

    public static final String INSERT_SONGS = "INSERT INTO " + TABLE_SONGS + '(' + COLUMN_SONG_TRACK +
            ", " + COLUMN_SONG_TITLE + ", " + COLUMN_SONG_ALBUM + ") VALUES(?, ?, ?)";

    // check if exits
    public static final String QUERY_ARTIST = "SELECT " + COLUMN_ARTISTS_ID + " FROM " +
            TABLE_ARTISTS + " WHERE " + COLUMN_ARTIST_NAME + " = ?";

    public static final String QUERY_ALBUM = "SELECT " + COLUMN_ALBUM_ID + " FROM " +
            TABLE_ALBUMS + " WHERE " + COLUMN_ALBUM_NAME + " = ?";

    public static final String QUERY_ALBUMS_BY_ARTIST_ID = "SELECT * FROM " + TABLE_ALBUMS +
            " WHERE " + COLUMN_ALBUM_ARTISTS + " = ? ORDER BY " + COLUMN_ALBUM_NAME + " COLLATE NOCASE";



    private PreparedStatement querySongInfoView;
    private PreparedStatement querySongsByArtistPreparedStatement;
    private PreparedStatement querySongOrArtistsAlbum;

    private PreparedStatement insertIntoArtist;
    private PreparedStatement insertIntoAlbums;
    private PreparedStatement insertIntoSongs;

    private PreparedStatement queryArtist;
    private PreparedStatement queryAlbum;

    private PreparedStatement queryAlbumsByArtistId;



    private Connection connection;

    private static Datasource instance = new Datasource();
    private Datasource() {

    }

    public static Datasource getInstance() {
        return instance;
    }

    public boolean open() {
        try {
            connection = DriverManager.getConnection(CONNECTION_STRING);
            querySongInfoView = connection.prepareStatement(QUERY_VIEW_SONG_INFO_PREP);
            querySongsByArtistPreparedStatement = connection.prepareStatement(QUERY_ALBUMS_BY_ARTIST_PREP);
            querySongOrArtistsAlbum = connection.prepareStatement(QUERY_SONG_OR_ARTISTS_ALBUM_PREP);
            insertIntoArtist = connection.prepareStatement(INSERT_ARTIST, Statement.RETURN_GENERATED_KEYS);
            insertIntoAlbums = connection.prepareStatement(INSERT_ALBUMS, Statement.RETURN_GENERATED_KEYS);
            insertIntoSongs = connection.prepareStatement(INSERT_SONGS);
            queryArtist = connection.prepareStatement(QUERY_ARTIST);
            queryAlbum = connection.prepareStatement(QUERY_ALBUM);
            queryAlbumsByArtistId = connection.prepareStatement(QUERY_ALBUMS_BY_ARTIST_ID);
            return true;
        } catch (SQLException e ) {
            System.out.println("Could not connect to database");
            e.printStackTrace();
            return false;
        }
    }

    public void close() {
        try {
            if (querySongInfoView != null) {
                querySongInfoView.close();
            }
            if (querySongsByArtistPreparedStatement != null) {
                querySongsByArtistPreparedStatement.close();
            }
            if (querySongOrArtistsAlbum != null ) {
                querySongOrArtistsAlbum.close();
            }
            if (insertIntoArtist != null) {
                insertIntoArtist.close();
            }
            if (insertIntoAlbums != null ){
                insertIntoAlbums.close();
            }
            if (insertIntoSongs != null ){
                insertIntoSongs.close();
            }
            if (queryArtist != null) {
                queryArtist.close();
            }
            if (queryAlbum != null) {
                queryAlbum.close();
            }
            if (queryAlbumsByArtistId != null) {
                queryAlbumsByArtistId.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e ) {
            System.out.println("Could not close connection");
            e.printStackTrace();
        }
    }

    public List<Artist> queryArtists(int sortOrder) {
        StringBuilder builder = new StringBuilder(QUERY_ARTISTS_START);
//        builder.append(TABLE_ARTISTS);
        if (sortOrder != ORDER_BY_NONE) {
//            builder.append( " ORDER BY ");
//            builder.append(COLUMN_ARTIST_NAME);
//            builder.append(" COLLATE NOCASE ");
            builder.append(QUERY_ARTISTS_BY_ARTISTS_SORT);
            if (sortOrder == ORDER_BY_DESC) {
                builder.append("DESC");
            } else {
                builder.append("ASC");
            }
        }

        Statement statement = null;
        ResultSet results = null;

        try {
            statement = connection.createStatement();
            results = statement.executeQuery(builder.toString());

            List<Artist> artists = new ArrayList<>();
            while (results.next()) {
                Artist artist = new Artist();
//                try {
//                    Thread.sleep(20);
//                } catch (InterruptedException e ) {
//                    e.printStackTrace();
//                }
                artist.setId(results.getInt(INDEX_ARTIST_ID));
                artist.setName(results.getString(INDEX_ARTIST_Name));
                artists.add(artist);
            }
            return artists;
        } catch (SQLException e ) {
            System.out.println("Query failed: " + e.getMessage());
            e.printStackTrace();
            return null; 
        } finally {
            try {
                if (results != null) {
                    results.close();
                }
            } catch (SQLException e) {
                System.out.println("Error closing results set");
                e.printStackTrace();
            }
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException sqlException) {
                System.out.println("Error closing statement");
                sqlException.printStackTrace();
            }
        }
    }


    // using a prepared statement
    // TODO fix order by method
    public List<Album> queryAlbumsForArtist(String artistName, int sortOrder) {
        try {
            querySongsByArtistPreparedStatement.setString(1, artistName);
//            if (sortOrder != ORDER_BY_NONE) {
//                if (sortOrder == ORDER_BY_DESC) {
//                    querySongsByArtistPreparedStatement.setString(2, "DESC");
//                } else {
//                    querySongsByArtistPreparedStatement.setString(2, "ASC");
//                }
//            }
            ResultSet resultSet = querySongsByArtistPreparedStatement.executeQuery();
            List<Album> albums = new ArrayList<>();
            while (resultSet.next()) {
                Album temp = new Album();
                temp.setName(resultSet.getString(1));
                albums.add(temp);
            }
            return albums;
        } catch (SQLException e ) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Album> queryAlbumForArtistId(int id) {
        try {
            queryAlbumsByArtistId.setInt(1, id);
            ResultSet resultSet = queryAlbumsByArtistId.executeQuery();
            List<Album> albums = new ArrayList<>();
            while (resultSet.next()) {
                Album temp = new Album();
                temp.setId(resultSet.getInt(1));
                temp.setName(resultSet.getString(2));
                temp.setArtistId(id);
                albums.add(temp);
            }
            return albums;
        } catch (SQLException e ) {
            System.out.println("Query Failed ");
            e.printStackTrace();
            return null;
        }
    }
    // view all songs by a artist
    // needs to be rebuild
    public List<String> querySongsByArtist(String artistName, int orderBy) {
        StringBuilder builder = new StringBuilder(QUERY_ARTIST_SONGS_START);
        builder.append(artistName);
        if (orderBy != ORDER_BY_NONE) {
            builder.append(QUERY_ARTIST_SONGS_END);
            if (orderBy == ORDER_BY_DESC) {
                builder.append("DESC");
            } else {
                builder.append("ASC");
            }
        }
        System.out.println(builder.toString());
        Statement statement = null;
        ResultSet resultSet = null;
        ArrayList<String> songs = new ArrayList<>();

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(builder.toString());
            while (resultSet.next()) {
                songs.add(resultSet.getString(1));
            }
        } catch (SQLException e ) {

        } finally {
            try {
                if (resultSet!=null)
                resultSet.close();
            } catch (SQLException e) {

            }
            try {
                statement.close();
            } catch (SQLException e ) {

            }
        }
        return songs;
    }

    public List<SongArtist> querySong(String song, int orderBy) {
        ResultSet resultSet = null;
        List<SongArtist> songArtist = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {

            // getting query serach
            StringBuilder querySerach = new StringBuilder(QUERY_ARTIST_FOR_SONG_START);
            querySerach.append(song);
            if (orderBy != ORDER_BY_NONE) {
                querySerach.append(QUERY_ARTIST_FOR_SONG_END);
                if (orderBy == ORDER_BY_DESC) {
                    querySerach.append("DESC");
                } else {
                    querySerach.append("ASC");
                }
            }
//            System.out.println(querySerach.toString());
            resultSet = statement.executeQuery(querySerach.toString());
            // get result
            while (resultSet.next()) {
                SongArtist songItem = new SongArtist();
                songItem.setArtistName(resultSet.getString(1));
                songItem.setAlbumName(resultSet.getString(2));
                songItem.setTrack(resultSet.getInt(3));
                songArtist.add(songItem);
            }

        } catch (SQLException e ) {
            System.out.println("Something happended");
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null ){
                    resultSet.close();
                }
            } catch (SQLException e ) {
                e.printStackTrace();
            }
        }
//        System.out.println(songArtist.size());
        return songArtist;
    }

    // get detailed song record for x artist
    public List<SongDetail> queryArtistSongsDetailed(String artistName, int order) {
        ArrayList<SongDetail> myList = new ArrayList();
        Statement statement = null;
        ResultSet resultSet = null;

        StringBuilder builder = new StringBuilder(QUERY_ALL_BASE_START);
        builder.append(QUERY_ALL_BASE_MID);
        builder.append(QUERY_ALL_BASE_MID_ARTIST);
        builder.append(artistName);
        builder.append("\"");
        builder.append(QUERY_ALL_BASE_END);
        if (order != ORDER_BY_NONE) {
            if (order == ORDER_BY_DESC) {
                builder.append("DESC");
            } else {
                builder.append("ASC");
            }
        }
        System.out.println(builder.toString());
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(builder.toString());
            while (resultSet.next()) {
                SongDetail song = new SongDetail();
                song.setSongName(resultSet.getString(1));
                song.setTrackNumber(resultSet.getInt(2));
                song.setAlbumName(resultSet.getString(3));
                song.setArtistName(resultSet.getString(4));
                myList.add(song);
            }
        } catch (SQLException e ) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null ){
                    resultSet.close();
                }
            } catch (SQLException e ) {

            }
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e ) {

            }

        }
        return myList;
    }

    // get size of table rows
    public int getCount(String table) {
        String sql = "SELECT COUNT(*) AS count FROM " + table;

        try (Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql)) {
            int count = resultSet.getInt("count");
//            int min = resultSet.getInt("min_id");
//            System.out.format("Count = %d, Min = %d\n", count, min);
            return count;
        } catch (SQLException e ) {
            return -1;
        }
    }

    // create a view artist, track and title

    // method used to create the view
    public boolean createNewSongArtist() {
        try (Statement statement = connection.createStatement()) {
            statement.executeQuery(CREATE_ARTIST_FOR_SONG_VIEW);
            return true;
        } catch (SQLException e ) {
            return false;
        }
    }
    // select name, album, track from artists_list where title = "title";

    public List<SongArtist> querySongInfoView(String title) {
        try {
            querySongInfoView.setString(1,title);
            ResultSet resultSet = querySongInfoView.executeQuery();
            List<SongArtist> songArtists = new ArrayList<>();
            while (resultSet.next()) {
                SongArtist songArtist = new SongArtist();
                songArtist.setArtistName(resultSet.getString(1));
                songArtist.setAlbumName(resultSet.getString(2));
                songArtist.setTrack(resultSet.getInt(3));
                songArtists.add(songArtist);
            }
            return songArtists;
        } catch (SQLException e ) {
            e.printStackTrace();
            return null;
        }

    }

    // select name, album, track from artists_list WHERE title = ? OR artist = ?

    public List<String> querySongOrArtistAlbum(String title, String artist) {
        try {
            querySongOrArtistsAlbum.setString(1, title);
            querySongOrArtistsAlbum.setString(2, artist);
            ResultSet resultSet = querySongOrArtistsAlbum.executeQuery();
            List<String> items = new ArrayList<>();
            while (resultSet.next()) {
                items.add(resultSet.getString(1));
            }
            return items;
        } catch (SQLException e ) {
            e.printStackTrace();
            return null;
        }
    }

    // adding song
    // song title, album, artist, track number
    // insert artist
    private int insertArtist(String name) throws SQLException {
        queryArtist.setString(1, name);
        ResultSet resultSet = queryArtist.executeQuery();
        // check if artist already exist,
        if (resultSet.next()) {
            // if so return value
            return resultSet.getInt(1);
        } else {
            // insert new artist
            insertIntoArtist.setString(1, name);
            // execute update will return how many rows were affected
            int affectRows = insertIntoArtist.executeUpdate();
            // only one row should be affected, if not flag error
            if (affectRows != 1) {
                throw new SQLException("Couldn't insert artist!");
            }
            // if affected rows = 1 get the key number
            ResultSet generatedKeys = insertIntoArtist.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            } else {
                throw new SQLException("Couldn't get artist");
            }
        }
    }

    private int insertAlbum(String name, int artistId) throws SQLException {
        queryAlbum.setString(1, name);
        // was not needed
//        queryAlbum.setInt(2, artistId);
        ResultSet results = queryAlbum.executeQuery();
        // if value is found, return the id
        if (results.next()) {
            return results.getInt(1);
        } else {
            // set up the prepared statement
            insertIntoAlbums.setString(1, name);
            insertIntoAlbums.setInt(2, artistId);

            // get how many rows were changed
            int affectedRows = insertIntoAlbums.executeUpdate();
            if (affectedRows != 1 ) {
                throw new SQLException("Error updating database");
            }

            ResultSet result = insertIntoAlbums.getGeneratedKeys();
            if (result.next()) {
                return result.getInt(1);
            } else {
                throw new SQLException("Error updating database");
            }

        }

    }

    public void insertSong(String name, String artist, String album, int track) {
        try {
            connection.setAutoCommit(false);
            int artistId = insertArtist(artist);
            int albumId = insertAlbum(album, artistId);
            insertIntoSongs.setInt(1, track);
            insertIntoSongs.setString(2, name);
            insertIntoSongs.setInt(3, albumId);

            int affectedRows = insertIntoSongs.executeUpdate();
            if (affectedRows == 1) {
                connection.commit();
            } else {
                throw new SQLException("Error adding song to database");
            }

        } catch (SQLException e ) {
            e.printStackTrace();
            try {
                System.out.println("Performing roll back ");
                connection.rollback();
            } catch (SQLException e2 ) {
                System.out.println("Problem rolling back");
                e2.printStackTrace();
            } finally {
                try {
                    System.out.println("Resetting auto commit to true");
                    connection.setAutoCommit(true);
                } catch (SQLException e3 ) {

                }
            }
        }
    }

}
