package io.github.some_example_name;

import java.io.File;
import java.io.IOException;

public class JSONHandler {

    private int position = 1; // top position in leaderboard
    
    /**
	 * <code> writeLeaderboard </code>
     * the method <code> writeLeaderboard </code> writes the player's score to the leaderboard JSON file if it is within the top 5 scores, 
     * implemented using a binary search to find the correct position for the new score.
     * if the file does not exist, it creates a new one 'leaderboard.json'.
	 * @param finalScore The calculated final score from the most recent attempt
	 */
    public void writeLeaderboard(int finalScore) throws IOException {
        File file = new File("leaderboard.json"); 
        if (file.createNewFile()) { // file created successfully, write new score in position 1 to the file
            java.io.FileWriter fw = new java.io.FileWriter(file); // open connection to writer
            fw.write("Position: " + position + ", Score: " + finalScore + "\n");
            fw.close();
        } 
        else {
            java.io.FileReader fr = new java.io.FileReader(file); // open connection to reader
            while (fr.ready() && position <= 5) {         
                // split each line into position and score
                String line = new java.io.BufferedReader(fr).readLine();
                String[] parts = line.split(", ");
                String score = parts[1];
                int existingScore = Integer.parseInt(score.split(": ")[1]);

                if (finalScore > existingScore) {
                    // new score is higher, insert into leaderboard
                    java.io.FileWriter fw = new java.io.FileWriter(file);
                    fw.write("Position: " + position + ", Score: " + finalScore + "\n");
                    fw.close();
                    position++;
                    // need to loop back through file at point after inserting new score, increment each position and write back to file
                    java.io.FileReader fr2 = new java.io.FileReader(file);
                    while (fr2.ready()) {
                        String line2 = new java.io.BufferedReader(fr2).readLine();
                        String[] parts2 = line2.split(", ");
                        String pos = parts2[0];
                        int existingPosition = Integer.parseInt(pos.split(": ")[1]);
                        if (existingPosition >= position) {
                            java.io.FileWriter fw2 = new java.io.FileWriter(file, true); // append
                            fw2.write("Position: " + (existingPosition + 1) + ", Score: " + existingScore + "\n");
                            fw2.close();
                        }
                    }
                    fr2.close();
                    break; // exit after inserting new score
                } else {
                    incrementPosition();
                }
            }
            fr.close();
        }
    }

    public void resetFile() throws IOException {
        File file = new File("leaderboard.json");
        if (file.exists()) {
            file.delete();
        }
        file.createNewFile();
    }

    public void updatePosition(int newPosition) {
        position = newPosition;
    }

    public void incrementPosition() {
        position++;
    }

    public int getPosition() {
        return position;
    }

    public void resetPosition() {
        position = 1;
    }

    /**
     * the method <code> readLeaderboard </code> returns the top 5 scores from the leaderboard JSON file as a String array.
     * if the file does not exist, it returns a String array with a message indicating no scores yet.
     * @return String[] array of top 5 scores from leaderboard JSON file    
    */
    public String[] readLeaderboard() throws IOException {
        File file = new File("leaderboard.json");
        String[] topScores = new String[5];
        if (file.exists()) {
            java.io.FileReader fr = new java.io.FileReader(file);
            int index = 0;
            while (fr.ready() && index < 5) {
                String line = new java.io.BufferedReader(fr).readLine();
                topScores[index] = line;
                index++;
            }
            fr.close();
            return topScores;
        }
        else {
            resetFile(); //if error with file
            topScores[0] = "error: no scores yet";
            return topScores;
        }
    }
}