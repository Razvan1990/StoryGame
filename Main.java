import java.util.Set;

public class Main {
    public static void main(String[] args) {
        DevelopGame developGame = new DevelopGame();
        Set<String> wordsToReplace = developGame.readAndCreateNewStory();
        developGame.writeToFileNewStory();
    }
}