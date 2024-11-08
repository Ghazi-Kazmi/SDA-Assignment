import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Data Access Layer
class Song {
    private String songID;
    private String title;
    private String artist;

    public Song(String songID, String title, String artist) {
        this.songID = songID;
        this.title = title;
        this.artist = artist;
    }

    public String getSongID() {
        return songID;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    @Override
    public String toString() {
        return title + " by " + artist;
    }
}

// Playlist Data Handler (Data Access Layer)
class PlaylistDataHandler {
    private List<Playlist> playlists = new ArrayList<>();

    public void savePlaylist(Playlist playlist) {
        playlists.add(playlist);
    }

    public Playlist retrievePlaylist(String playlistName) {
        for (Playlist playlist : playlists) {
            if (playlist.getName().equals(playlistName)) {
                return playlist;
            }
        }
        return null;
    }

    public void deletePlaylist(String playlistName) {
        playlists.removeIf(playlist -> playlist.getName().equals(playlistName));
    }
}

// Playlist Class (Business Logic Layer)
class Playlist {
    private String name;
    private List<Song> songs;

    public Playlist(String name) {
        this.name = name;
        this.songs = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void addSong(Song song) {
        songs.add(song);
    }

    public void removeSong(String songID) {
        songs.removeIf(song -> song.getSongID().equals(songID));
    }

    public void displaySongs() {
        System.out.println("Playlist: " + name);
        for (Song song : songs) {
            System.out.println("- " + song);
        }
    }
}

// Playlist Manager (Business Logic Layer)
class PlaylistManager {
    private PlaylistDataHandler dataHandler = new PlaylistDataHandler();

    public void createPlaylist(String name) {
        Playlist playlist = new Playlist(name);
        dataHandler.savePlaylist(playlist);
        System.out.println("Playlist \"" + name + "\" created successfully.");
    }

    public void addSongToPlaylist(String playlistName, Song song) {
        Playlist playlist = dataHandler.retrievePlaylist(playlistName);
        if (playlist != null) {
            playlist.addSong(song);
            System.out.println("Added " + song + " to playlist \"" + playlistName + "\".");
        } else {
            System.out.println("Playlist \"" + playlistName + "\" not found.");
        }
    }

    public void removeSongFromPlaylist(String playlistName, String songID) {
        Playlist playlist = dataHandler.retrievePlaylist(playlistName);
        if (playlist != null) {
            playlist.removeSong(songID);
            System.out.println("Removed song with ID " + songID + " from playlist \"" + playlistName + "\".");
        } else {
            System.out.println("Playlist \"" + playlistName + "\" not found.");
        }
    }

    public void displayPlaylist(String playlistName) {
        Playlist playlist = dataHandler.retrievePlaylist(playlistName);
        if (playlist != null) {
            playlist.displaySongs();
        } else {
            System.out.println("Playlist \"" + playlistName + "\" not found.");
        }
    }

    public void deletePlaylist(String playlistName) {
        dataHandler.deletePlaylist(playlistName);
        System.out.println("Playlist \"" + playlistName + "\" deleted.");
    }
}

// User Interface (Presentation Layer)
public class PlaylistApplication {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        PlaylistManager manager = new PlaylistManager();

        while (true) {
            System.out.println("\n1. Create Playlist\n2. Add Song to Playlist\n3. Remove Song from Playlist\n4. Display Playlist\n5. Delete Playlist\n6. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter playlist name: ");
                    String playlistName = scanner.nextLine();
                    manager.createPlaylist(playlistName);
                    break;
                case 2:
                    System.out.print("Enter playlist name: ");
                    String playlistNameToAdd = scanner.nextLine();
                    System.out.print("Enter song ID: ");
                    String songID = scanner.nextLine();
                    System.out.print("Enter song title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter artist name: ");
                    String artist = scanner.nextLine();
                    Song song = new Song(songID, title, artist);
                    manager.addSongToPlaylist(playlistNameToAdd, song);
                    break;
                case 3:
                    System.out.print("Enter playlist name: ");
                    String playlistNameToRemove = scanner.nextLine();
                    System.out.print("Enter song ID to remove: ");
                    String songIDToRemove = scanner.nextLine();
                    manager.removeSongFromPlaylist(playlistNameToRemove, songIDToRemove);
                    break;
                case 4:
                    System.out.print("Enter playlist name: ");
                    String playlistNameToDisplay = scanner.nextLine();
                    manager.displayPlaylist(playlistNameToDisplay);
                    break;
                case 5:
                    System.out.print("Enter playlist name to delete: ");
                    String playlistNameToDelete = scanner.nextLine();
                    manager.deletePlaylist(playlistNameToDelete);
                    break;
                case 6:
                    System.out.println("Exiting application...");
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
                    break;
            }
        }
    }
}
