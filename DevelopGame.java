import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;

public class DevelopGame {


    private static final String textFile = "G:\\java projects\\GameStory\\src\\story.txt";
    private static final String storyUpdateFile = "G:\\java projects\\GameStory\\src\\storyUpdated.txt";
    private static final String startCharacter = "<";
    private static final String endCharacter = ">";
    private static final List<Character> specialCharacters = Arrays.asList(',',
            '.',
            ':',
            '?',
            '!',
            ';');

    public Set<String> readAndCreateNewStory() {
        List<String> lines = new ArrayList<>();
        List<String> words = new ArrayList<>();
        Set<String> wantedWords = new HashSet<>();
        //HashMap<String,String> mapWords = new HashMap<>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(textFile));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                lines.add(line);
            }
            for (String lineString : lines) {
                String[] wordsLine = lineString.split(" ");
                words.addAll(Arrays.asList(wordsLine));

            }
            // create the set word for with the words
            for (String word : words) {
                if (word.length() >= 2) {
                    String oneToLastChar = Character.toString(word.charAt(word.length() - 2));
                    if (word.startsWith(startCharacter) && (word.endsWith(endCharacter) || oneToLastChar.equals(endCharacter))) {
                        //put a condition to check if word ends with special character - just for future development if needed
                        if (specialCharacters.contains(word.charAt(word.length() - 1))) {
                            wantedWords.add(word.substring(0, word.length() - 1));
                        } else {
                            wantedWords.add(word);
                        }

                    }
                }
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wantedWords;
    }

    public HashMap<String, String> createMapWord(Set<String> wordsToReplace) {
        HashMap<String, String> myMap = new HashMap<>();
        for (String word : wordsToReplace) {
            System.out.println("Please introduce replacing word for: " + word + " ");
            Scanner scanner = new Scanner(System.in);
            String replacement = scanner.nextLine();
            myMap.put(word, replacement);
        }
        return myMap;
    }

    public String convertListStoryToString(List<String> story) {
        StringBuilder builder = new StringBuilder();
        for (String word : story) {
            builder.append(word).append(" ");
        }
        return builder.toString();
    }

    public void writeToFileNewStory() {
        Set<String> words = readAndCreateNewStory();
        List<String> newStory = new ArrayList<>();
        HashMap<String, String> wordsToReplace = createMapWord(words);
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(storyUpdateFile));
            String content = new String(Files.readAllBytes(Paths.get(textFile)));
            //spit words
            String[] allWords = content.split(" ");
            for (String word : allWords) {
                if (wordsToReplace.containsKey(word)) {
                    word = word.replaceAll(word, wordsToReplace.get(word));
                    newStory.add(word);
                } else {
                    newStory.add(word);
                }
            }
            String newContentStory = convertListStoryToString(newStory);
            writer.write(newContentStory);
            writer.close();
            //open file using Desktop method
            File file = new File(storyUpdateFile);
            if(!Desktop.isDesktopSupported()){
                System.out.println("Desktop is not supported");
                return;
            }

            Desktop desktop = Desktop.getDesktop();
            if(file.exists()) desktop.open(file);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}



