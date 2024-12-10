package csc230project4;

/**
 * The HipHopLyrics class represents a line of data from the CSV file "genius_hip_hop_lyrics." which stores data
 * on 2016 presidential candidates that were mentioned in hip hop songs
 * It stores information about a specific line of lyrics in a hip-hop song, including details about
 * the candidate mentioned, song, artist, sentiment, theme, album release date, line itself, and a URL
 * for additional information.
 * 
 * This class also implements the Comparable interface to allow for sorting and comparison
 * of HipHopLyrics objects.
 * 
 * @author Marcus V
 */
public class HipHopLyrics implements Comparable<HipHopLyrics>{
    private String id;
    private String candidate;
    private String song;
    private String artist;
    private String sentiment;
    private String theme;
    private String albumReleaseDate;
    private String line;
    private String url;
    
    /**
     * Constructs a HipHopLyrics object with the provided data.
     *
     * @param id               The unique identifier of the lyrics line.
     * @param candidate        The name of the 2016 candidate mentioned in the lyrics.
     * @param song             The name of the song containing the lyrics.
     * @param artist           The name of the artist who performed the song.
     * @param sentiment        The sentiment associated with the lyrics (e.g., positive, negative).
     * @param theme            The theme of the lyrics.
     * @param albumReleaseDate The release date of the album containing the song.
     * @param line             The actual lyrics line.
     * @param url              A URL providing additional information about the song and lyrics.
     */
    public HipHopLyrics(String id, String candidate, String song, String artist, String sentiment,
                        String theme, String albumReleaseDate, String line, String url) {
        this.id = id;
        this.candidate = candidate;
        this.song = song;
        this.artist = artist;
        this.sentiment = sentiment;
        this.theme = theme;
        this.albumReleaseDate = albumReleaseDate;
        this.line = line;
        this.url = url;
    }

    /**
     * Returns the unique identifier of the lyrics line.
     *
     * @return The ID of the lyrics line.
     */
    public String getId() {
        return id;
    }

    /**
     * Returns the name of the 2016 candidate mentioned in the lyrics.
     *
     * @return The candidate's name.
     */
    public String getCandidate() {
        return candidate;
    }

    /**
     * Returns the name of the song containing the lyrics.
     *
     * @return The song's name.
     */
    public String getSong() {
        return song;
    }

    /**
     * Returns the name of the artist who performed the song.
     *
     * @return The artist's name.
     */
    public String getArtist() {
        return artist;
    }

    /**
     * Returns the sentiment associated with the lyrics (e.g., positive, negative).
     *
     * @return The sentiment of the lyrics.
     */
    public String getSentiment() {
        return sentiment;
    }

    /**
     * Returns the theme of the lyrics.
     *
     * @return The theme of the lyrics.
     */
    public String getTheme() {
        return theme;
    }

    /**
     * Returns the release date of the album containing the song.
     *
     * @return The album's release date.
     */
    public String getAlbumReleaseDate() {
        return albumReleaseDate;
    }

    /**
     * Returns the actual lyrics line.
     *
     * @return The lyrics line.
     */
    public String getLine() {
        return line;
    }

    /**
     * Returns a URL providing additional information about the song and lyrics.
     *
     * @return The URL for additional information.
     */
    public String getUrl() {
        return url;
    }

    /**
     * Compares this HipHopLyrics object with another HipHopLyrics object for sorting purposes.
     * This method is required for implementing the Comparable interface, but it currently returns 0
     * as no specific comparison logic has been implemented.
     *
     * @param o The other HipHopLyrics object to compare to.
     * @return 0 (indicating equal) as a placeholder for comparison logic.
     */
    @Override
    public int compareTo(HipHopLyrics o) {
        // TODO: Implement comparison logic here if needed.
        return 0;
    }

    /**
     * Returns a string representation of the HipHopLyrics object.
     * The string contains information about the lyrics line, including candidate, sentiment, song, artist,
     * theme, album release date, lyrics line, and a URL for additional details.
     *
     * @return A formatted string representing the HipHopLyrics object.
     */
    @Override
    public String toString() {
        return "The 2016 candidate " + candidate + " was " + sentiment + "ly mentioned by " + artist +
                " in the song '" + song + "'.\nThe theme of the song was " + theme + " and it was released in " +
                albumReleaseDate + ".\nThe artist writes " + line + ".\nDetails on the song and line are listed here " + url;
    }
    
}// end of HipHopLyrics
